package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Timetable;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.time.LocalDate;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {
    @FXML
    private StackPane datePicker;

    @FXML
    private VBox showDate;

    @FXML
    private ChoiceBox<String> teacherChoiceBox;

    @FXML
    private ChoiceBox<String> subjectChoiceBox;

    @FXML
    private ChoiceBox<String> classChoiceBox;

    @FXML
    private ChoiceBox<String> timeChoiceBox;

    @FXML
    private DatePicker datePickerField;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button addButton;

    @FXML
    private Button resetButton;

    @FXML
    private TableView<Timetable> timetableTableView;

    @FXML
    private TableColumn<Timetable, String> timeColumn;

    @FXML
    private TableColumn<Timetable, String> classNameColumn;

    @FXML
    private TableColumn<Timetable, String> teacherColumn;

    @FXML
    private TableColumn<Timetable, String> subjectColumn;

    @FXML
    private TableColumn<Timetable, String> descriptionColumn;

    @FXML
    private TableColumn<Timetable, String> dateColumn; 

    @FXML
    private TableColumn<Timetable, Void> actionColumn; 

    @FXML
    private ChoiceBox<String> filterClassChoiceBox;

    @FXML
    private StackPane refreshButton;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calendarPicker();
        populateChoiceBoxes();
        addButton.setOnAction(event -> insertTimetable());
        resetButton.setOnAction(event -> resetForm());
        
        // Chỉnh sửa cột
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Căn giữa cột
        centerAlignColumn(timeColumn);
        centerAlignColumn(classNameColumn);
        centerAlignColumn(teacherColumn);
        centerAlignColumn(subjectColumn);
        centerAlignColumn(descriptionColumn);
        centerAlignColumn(dateColumn);

        // Cấu hình cột Action
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button();

            {
                // Cấu hình nút xóa
                Image deleteImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/cross.png")));
                ImageView deleteImageView = new ImageView(deleteImage);
                deleteImageView.setFitHeight(20);
                deleteImageView.setFitWidth(20);
                deleteButton.setGraphic(deleteImageView);
                deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                deleteButton.setOnAction(event -> {
                    Timetable timetable = getTableView().getItems().get(getIndex());
                    // Hộp thoại xác nhận
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Xác nhận xóa");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to delete this timetable entry?");
                    Optional<ButtonType> result = confirmationAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        deleteTimetable(timetable);
                        resetFilterClassChoiceBox(); // Đặt lại bộ lọc sau khi xóa
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // **Add** the following line to apply filters based on the current selections (e.g., selected date)
        applyFilters();

        // Bộ lọc tìm kiếm
        filterClassChoiceBox.setOnAction(event -> applyFilters());

        // Hành động nút Refresh
        refreshButton.setOnMouseClicked(event -> {
            refreshTable();
            resetFilterClassChoiceBox(); // Đặt lại bộ lọc sau khi làm mới
        });
    }

    private <T> void centerAlignColumn(TableColumn<T, String> column) {
        column.setStyle("-fx-alignment: CENTER;"); 
    }

    @FXML
    public void refreshTable() {
        loadTimetableData();
        resetFilterClassChoiceBox(); // Đặt lại bộ lọc sau khi làm mới
    }

    void calendarPicker() {
        DatePicker datePickerControl = new DatePicker(LocalDate.now());
        updateShowDate(LocalDate.now());

        DatePickerSkin datePickerSkin = new DatePickerSkin(datePickerControl);
        Node datePickerContent = datePickerSkin.getPopupContent();

        datePickerContent.setStyle("-fx-pref-width: 500px; -fx-pref-height: 500px;");
        datePicker.getChildren().add(datePickerContent);

        // Đặt datePickerField thành ngày hôm nay
        datePickerField.setValue(LocalDate.now());

        datePickerControl.setOnAction(event -> {
            LocalDate selectedDate = datePickerControl.getValue();
            updateShowDate(selectedDate);
            datePickerField.setValue(selectedDate); // Cập nhật giá trị datePickerField
            applyFilters(); // Áp dụng bộ lọc tìm kiếm dựa trên ngày được chọn
        });
    }

    private void updateShowDate(LocalDate date) {
        showDate.getChildren().clear();
        Label dateLabel = new Label(date.format(dateFormatter));
        dateLabel.setStyle("-fx-font-family: 'Sitka Text'; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #AB8905;");
        showDate.getChildren().add(dateLabel);
    }

    private void populateChoiceBoxes() {
        ObservableList<String> timeOptions = FXCollections.observableArrayList(
                "7:00-8:00", "8:00-9:00", "9:00-10:00", "10:00-11:00",
                "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"
        );
        timeChoiceBox.setItems(timeOptions);
        timeChoiceBox.setValue("Select time"); 


        try (Connection connection = ConnectDB.connection()) {
            populateChoiceBoxWithSelect(connection, "SELECT CONCAT(FirstName, ' ', LastName) AS FullName FROM staffs", teacherChoiceBox, "Select teacher");
            populateChoiceBoxWithSelect(connection, "SELECT SubjectName FROM subjects", subjectChoiceBox, "Select subject");
            populateChoiceBoxWithSelect(connection, "SELECT ClassName FROM classes", classChoiceBox, "Select class");
            populateChoiceBoxWithSelect(connection, "SELECT ClassName FROM classes", filterClassChoiceBox, "Select class");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức thêm "Select class" vào choicebox sau đó đặt lại giá trị
     */
    private void populateChoiceBoxWithSelect(Connection connection, String query, ChoiceBox<String> choiceBox, String selectOption) throws SQLException {
        choiceBox.getItems().add(selectOption); // Thêm lựa chọn mặc định
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                choiceBox.getItems().add(resultSet.getString(1)); // Sử dụng chỉ số cột đúng
            }
        }
        choiceBox.setValue(selectOption); // Đặt giá trị mặc định
    }


    private void insertTimetable() {
        String teacher = teacherChoiceBox.getValue();
        String subject = subjectChoiceBox.getValue();
        String className = classChoiceBox.getValue();
        String time = timeChoiceBox.getValue();
        LocalDate date = datePickerField.getValue();
        String description = descriptionField.getText();

        // Kiểm tra các trường bắt buộc
        if (teacher == null || subject == null || className == null || time == null || date == null || description.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill all the fields");
            return;
        }

        try (Connection connection = ConnectDB.connection()) {
            // Kiểm tra xung đột thời gian giáo viên
            if (isTeacherTimeConflict(connection, teacher, date, time)) {
                showAlert(Alert.AlertType.ERROR, "Time Conflict", "The teacher is already scheduled to teach another class at this time");
                return;
            }

            // Kiểm tra xung đột thời gian lớp
            if (isClassTimeConflict(connection, className, date, time)) {
                showAlert(Alert.AlertType.ERROR, "Time Conflict", "The class already has another teacher scheduled at this time");
                return;
            }

            int timetableID = getNextTimetableID(connection);
            String insertQuery = "INSERT INTO timetable (TimetableID, ClassID, SubjectID, StaffID, Date, Time, Description) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, timetableID);
                preparedStatement.setInt(2, getClassID(connection, className));
                preparedStatement.setInt(3, getSubjectID(connection, subject));
                preparedStatement.setInt(4, getStaffID(connection, teacher));
                preparedStatement.setDate(5, Date.valueOf(date));
                preparedStatement.setString(6, time);
                preparedStatement.setString(7, description);
                preparedStatement.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Timetable added successfully");
                resetForm();
                loadTimetableData(); 
                resetFilterClassChoiceBox(); // Đặt lại bộ lọc sau khi thêm mới
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to insert timetable");
        }
    }

    private boolean isTeacherTimeConflict(Connection connection, String teacher, LocalDate date, String time) throws SQLException {
        String query = "SELECT COUNT(*) FROM timetable t " +
                       "JOIN staffs s ON t.StaffID = s.StaffID " +
                       "WHERE CONCAT(s.FirstName, ' ', s.LastName) = ? AND t.Date = ? AND t.Time = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, teacher);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setString(3, time);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private boolean isClassTimeConflict(Connection connection, String className, LocalDate date, String time) throws SQLException {
        String query = "SELECT COUNT(*) FROM timetable t " +
                       "JOIN classes c ON t.ClassID = c.ClassID " +
                       "WHERE c.ClassName = ? AND t.Date = ? AND t.Time = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, className);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setString(3, time);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private int getNextTimetableID(Connection connection) throws SQLException {
        String query = "SELECT MAX(TimetableID) FROM timetable";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            }
        }
        return 1; // Mặc định ID nếu bảng trống
    }

    private int getClassID(Connection connection, String className) throws SQLException {
        String query = "SELECT ClassID FROM classes WHERE ClassName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, className);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("ClassID");
                }
            }
        }
        throw new SQLException("Class not found");
    }

    private int getSubjectID(Connection connection, String subjectName) throws SQLException {
        String query = "SELECT SubjectID FROM subjects WHERE SubjectName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, subjectName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("SubjectID");
                }
            }
        }
        throw new SQLException("Subject not found");
    }

    private int getStaffID(Connection connection, String staffName) throws SQLException {
        String query = "SELECT StaffID FROM staffs WHERE CONCAT(FirstName, ' ', LastName) = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, staffName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("StaffID");
                }
            }
        }
        throw new SQLException("Staff not found");
    }

    private void loadTimetableData() {
        ObservableList<Timetable> timetableList = FXCollections.observableArrayList();

        try (Connection connection = ConnectDB.connection()) {
            String query = "SELECT t.TimetableID, t.Time, c.ClassName, CONCAT(s.FirstName, ' ', s.LastName) AS Teacher, sub.SubjectName, t.Description, t.Date " +
                           "FROM timetable t " +
                           "JOIN classes c ON t.ClassID = c.ClassID " +
                           "JOIN staffs s ON t.StaffID = s.StaffID " +
                           "JOIN subjects sub ON t.SubjectID = sub.SubjectID";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Timetable timetable = new Timetable(
                            resultSet.getInt("TimetableID"),
                            resultSet.getString("Time"),
                            resultSet.getString("ClassName"),
                            resultSet.getString("Teacher"),
                            resultSet.getString("SubjectName"),
                            resultSet.getString("Description"),
                            resultSet.getDate("Date").toLocalDate() 
                    );
                    timetableList.add(timetable);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        timetableTableView.setItems(timetableList);
    }

    private void applyFilters() {
        String selectedClass = filterClassChoiceBox.getValue();
        LocalDate selectedDate = datePickerField.getValue();

        if (selectedDate == null) {
            // Nếu không có ngày nào được chọn, mặc định là ngày hôm nay
            selectedDate = LocalDate.now();
            datePickerField.setValue(selectedDate);
        }

        ObservableList<Timetable> filteredList = FXCollections.observableArrayList();

        try (Connection connection = ConnectDB.connection()) {
            StringBuilder queryBuilder = new StringBuilder(
                "SELECT t.TimetableID, t.Time, c.ClassName, CONCAT(s.FirstName, ' ', s.LastName) AS Teacher, sub.SubjectName, t.Description, t.Date " +
                "FROM timetable t " +
                "JOIN classes c ON t.ClassID = c.ClassID " +
                "JOIN staffs s ON t.StaffID = s.StaffID " +
                "JOIN subjects sub ON t.SubjectID = sub.SubjectID " +
                "WHERE t.Date = ?"
            );

            if (selectedClass != null && !selectedClass.equals("Select class")) {
                queryBuilder.append(" AND c.ClassName = ?");
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
                preparedStatement.setDate(1, Date.valueOf(selectedDate));
                if (selectedClass != null && !selectedClass.equals("Select class")) {
                    preparedStatement.setString(2, selectedClass);
                }

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Timetable timetable = new Timetable(
                            resultSet.getInt("TimetableID"),
                            resultSet.getString("Time"),
                            resultSet.getString("ClassName"),
                            resultSet.getString("Teacher"),
                            resultSet.getString("SubjectName"),
                            resultSet.getString("Description"),
                            resultSet.getDate("Date").toLocalDate()
                        );
                        filteredList.add(timetable);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        timetableTableView.setItems(filteredList);
    }

    private void resetForm() {
        teacherChoiceBox.setValue("Select teacher");
        subjectChoiceBox.setValue("Select subject");
        classChoiceBox.setValue("Select class"); 
        timeChoiceBox.setValue("Select time");
        datePickerField.setValue(LocalDate.now()); // Đặt lại ngày hôm nay
        descriptionField.clear();
        resetFilterClassChoiceBox(); // Đặt lại bộ lọc
    }

    /**
     * Phương thức đặt lại filterClassChoiceBox thành "Select class"
     */
    private void resetFilterClassChoiceBox() {
        filterClassChoiceBox.setValue("Select class");
        applyFilters(); // Áp dụng bộ lọc lại nếu cần
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void deleteTimetable(Timetable timetable) {
        String query = "DELETE FROM timetable WHERE TimetableID = ?";

        try (Connection connection = ConnectDB.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, timetable.getTimetableID());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Timetable entry has been deleted.");
                loadTimetableData(); // Làm mới dữ liệu sau khi xóa
                resetFilterClassChoiceBox(); // Đặt lại bộ lọc sau khi xóa
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Unable to delete timetable entry.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the timetable entry.");
        }
    }
}