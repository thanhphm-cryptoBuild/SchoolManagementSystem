package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.model.StudentModel;
import com.app.schoolmanagementsystem.entities.Classes;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Period;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    public AnchorPane moveBG;
    @FXML
    private StackPane pageStudent;

    @FXML
    private TableView<StudentModel> studentTable;

    @FXML
    private TableColumn<StudentModel, Integer> colStudentID; // Add this line

    @FXML
    private TableColumn<StudentModel, String> colFirstName;

    @FXML
    private TableColumn<StudentModel, String> colLastName;

    @FXML
    private TableColumn<StudentModel, java.sql.Date> colDateOfBirth;

    @FXML
    private TableColumn<StudentModel, Boolean> colGender;

    @FXML
    private TableColumn<StudentModel, String> colAddress;

    @FXML
    private TableColumn<StudentModel, String> colPhoneNumber;

    @FXML
    private TableColumn<StudentModel, String> colEmail;

    @FXML
    private TableColumn<StudentModel, java.sql.Date> colEnrollmentDate;

    @FXML
    private TableColumn<StudentModel, Void> colAction;

    @FXML
    private TableColumn<StudentModel, String> colClassName;

    @FXML
    private TableColumn<StudentModel, String> colAvatar;

    @FXML
    private TableColumn<StudentModel, String> colAcademicSchool; // New Column

    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox<String> searchChoiceBox;

    @FXML
    private TextField filterField;

    private final ObservableList<StudentModel> studentData = FXCollections.observableArrayList();

    private boolean isSearchIconClicked = false;
    private Image defaultAvatar;
    private final Map<String, Image> avatarCache = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultAvatar = new Image(Objects.requireNonNull(getClass().getResource("/com/app/schoolmanagementsystem/images/default_avatar.png")).toExternalForm());
        setupTableColumns();
        loadStudentData();
        setupSearchChoiceBox();
        searchChoiceBox.getItems().add("Filter"); // Add "Filter" as the first item
        searchChoiceBox.setValue("Filter"); // Set default value to "Filter"
    }

    private void setupSearchChoiceBox() {
        searchChoiceBox.setItems(FXCollections.observableArrayList("Student ID", "Gender", "Class")); // Updated line
    }

    @FXML
    private void onSearchIconClicked() {
        isSearchIconClicked = true;
        if (searchField.getText().isEmpty() && filterField.getText().isEmpty()) {
            loadStudentData(); // Refresh the page if search fields are empty
            searchChoiceBox.setValue("Filter");
            isSearchIconClicked = false; // Reset the flag after refresh
        } else {
            if (!searchField.getText().isEmpty() && filterField.getText().isEmpty()) {
                searchChoiceBox.setValue("Filter");
            }
            onSearchKeyReleased();
        }
    }

    @FXML
    private void onSearchKeyReleased() {
        if (!isSearchIconClicked) {
            return;
        }

        String searchText = searchField.getText().toLowerCase();
        String filterText = filterField.getText().toLowerCase();
        String searchChoice = searchChoiceBox.getValue();
        ObservableList<StudentModel> filteredData = FXCollections.observableArrayList();

        for (StudentModel student : studentData) {
            boolean matches = false;
            if (student.getFirstName().toLowerCase().contains(searchText) || student.getEmail().toLowerCase().contains(searchText)) {
                if ("Student ID".equals(searchChoice) && String.valueOf(student.getStudentID()).contains(filterText)) { // Updated line
                    matches = true;
                } else if ("Gender".equals(searchChoice) && (student.getGender() ? "male" : "female").equalsIgnoreCase(filterText)) { // Updated line
                    matches = true;
                } else if ("Class".equals(searchChoice) && getClassNameById(student.getClassID()).toLowerCase().contains(filterText)) {
                    matches = true;
                } else if (filterText.isEmpty()) {
                    matches = true;
                }
            }

            if (matches) {
                filteredData.add(student);
            }
        }

        studentTable.setItems(filteredData);
        isSearchIconClicked = false; // Reset the flag after search

        // Reset ChoiceBox to "Filter" after search
//        searchChoiceBox.setValue("Filter");

    }

    @FXML
    void clearText(MouseEvent event) {
        searchField.clear();
    }

    @FXML
    void clearFilterText(MouseEvent event) {
        filterField.clear();
    }

    private void setupTableColumns() {
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID")); // Set the cell value factory for Student ID
        colStudentID.setStyle("-fx-alignment: CENTER;"); // Center align Student ID column

        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colFirstName.setStyle("-fx-alignment: CENTER;"); // Center align First Name column

        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colLastName.setStyle("-fx-alignment: CENTER;"); // Center align Last Name column

        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colDateOfBirth.setStyle("-fx-alignment: CENTER;"); // Center align Date of Birth column

        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colGender.setStyle("-fx-alignment: CENTER;"); // Center align Gender column

        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAddress.setStyle("-fx-alignment: CENTER;"); // Center align Address column

        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colPhoneNumber.setStyle("-fx-alignment: CENTER;"); // Center align Phone Number column

        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setStyle("-fx-alignment: CENTER;"); // Center align Email column

        colEnrollmentDate.setCellValueFactory(new PropertyValueFactory<>("enrollmentDate"));
        colEnrollmentDate.setStyle("-fx-alignment: CENTER;"); // Center align Enrollment Date column

        // Configure Avatar column
        colAvatar.setCellFactory(column -> new TableCell<StudentModel, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String avatarPath, boolean empty) {
                super.updateItem(avatarPath, empty);
                if (empty || avatarPath == null || avatarPath.isEmpty()) {
                    imageView.setImage(defaultAvatar);
                } else {
                    // Get the current StudentModel object
                    StudentModel student = getTableView().getItems().get(getIndex());
                    String fullAvatarPath = student.getFullAvatarPath();

                    Image avatarImage = avatarCache.get(fullAvatarPath);
                    if (avatarImage == null) {
                        try {
                            avatarImage = new Image(student.isExternalAvatar() ? fullAvatarPath : Objects.requireNonNull(getClass().getResource(fullAvatarPath)).toExternalForm());
                            avatarCache.put(fullAvatarPath, avatarImage);
                        } catch (Exception e) {
                            avatarImage = defaultAvatar;
                        }
                    }
                    imageView.setImage(avatarImage);
                }
                imageView.setFitHeight(32); // Set fixed height for the avatar
                imageView.setFitWidth(32); // Set fixed width for the avatar
                imageView.setPreserveRatio(true); // Maintain aspect ratio

                // Clip the image into a circle
                imageView.setClip(new Circle(16, 16, 16)); // Create a circular clip
                setGraphic(empty ? null : imageView);
            }
        });

        colAvatar.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        colAvatar.setStyle("-fx-alignment: CENTER;"); // Center align Avatar column

        // Configure Academic School column
        colAcademicSchool.setCellValueFactory(cellData -> {
            int classId = cellData.getValue().getClassID();
            Classes cls = getClassById(classId);
            if (cls != null && cls.getEnrollmentDate() != null && cls.getCompleteDate() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dateRange = cls.getEnrollmentDate().format(formatter) + " -> " + cls.getCompleteDate().format(formatter);
                return new SimpleStringProperty(dateRange);
            } else {
                return new SimpleStringProperty("N/A");
            }
        });
        colAcademicSchool.setStyle("-fx-alignment: CENTER;"); // Center align Academic School column

        // Configure Action column
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button();
            private final Button editButton = new Button();
            private final Button gradeButton = new Button();

            {
                // Delete button
                Image deleteImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/cross.png")));
                ImageView deleteImageView = new ImageView(deleteImage);
                deleteImageView.setFitHeight(20);
                deleteImageView.setFitWidth(20);
                deleteButton.setGraphic(deleteImageView);
                deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                deleteButton.setOnAction(event -> {
                    StudentModel student = getTableView().getItems().get(getIndex());

                    // Show confirmation dialog
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Delete Confirmation");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to delete this student?");

                    Optional<ButtonType> result = confirmationAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        deleteStudent(student);
                    }
                });

                // Edit button
                Image gearImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/edit.png")));
                ImageView gearImageView = new ImageView(gearImage);
                gearImageView.setFitHeight(20);
                gearImageView.setFitWidth(20);
                editButton.setGraphic(gearImageView);
                editButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                editButton.setOnAction(event -> {
                    StudentModel student = getTableView().getItems().get(getIndex());
                    openEditStudentPage(student);
                });

                // Grade button
                Image gradeImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/score.png")));
                ImageView gradeImageView = new ImageView(gradeImage);
                gradeImageView.setFitHeight(20);
                gradeImageView.setFitWidth(20);
                gradeButton.setGraphic(gradeImageView);
                gradeButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                gradeButton.setOnAction(event -> {
                    StudentModel student = getTableView().getItems().get(getIndex());
                    openGradeStudentPage(student);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionButtons = new HBox(5, editButton, deleteButton, gradeButton);
                    setGraphic(actionButtons);
                }
            }
        });

        // Configure Gender column
        colGender.setCellFactory(column -> new TableCell<StudentModel, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Male" : "Female");
                }
            }
        });

        // Configure ClassName column
        colClassName.setCellValueFactory(cellData -> {
            int classID = cellData.getValue().getClassID();
            String className = getClassNameById(classID);
            return new SimpleStringProperty(className);
        });
        colClassName.setStyle("-fx-alignment: CENTER;"); // Center align ClassName column
    }

    /**
     * Formats the Period into a readable string.
     *
     * @param period The Period to format.
     * @return A string representation of the period.
     */

    /**
     * Retrieves the Classes object by ClassID.
     *
     * @param classID The ClassID to search for.
     * @return The Classes object if found, otherwise null.
     */
    private Classes getClassById(int classID) {
        String query = "SELECT * FROM classes WHERE ClassID = " + classID;
        try (Connection conn = ConnectDB.connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                int id = rs.getInt("ClassID");
                String className = rs.getString("ClassName");
                String section = rs.getString("Section");
                int staffID = rs.getInt("StaffID");
                LocalDate enrollmentDate = rs.getDate("EnrollmentDate").toLocalDate();
                LocalDate completeDate = rs.getDate("CompleteDate").toLocalDate();

                return new Classes(id, className, section, staffID, enrollmentDate, completeDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getClassNameById(int classID) {
        String className = "";
        String query = "SELECT ClassName FROM classes WHERE ClassID = " + classID;

        try (Connection conn = ConnectDB.connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                className = rs.getString("ClassName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return className;
    }

    private void deleteStudent(StudentModel student) {
        String query = "UPDATE students SET Status = 'inactive' WHERE StudentID = " + student.getStudentID();

        try (Connection conn = ConnectDB.connection();
             Statement stmt = conn.createStatement()) {
            int result = stmt.executeUpdate(query);
            if (result > 0) {
                student.setStatus("inactive");
                studentTable.refresh();
                loadStudentData();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student has been deleted.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Unable to delete student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the student.");
        }
    }

    private void openEditStudentPage(StudentModel student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/schoolmanagementsystem/views/pageeditstudent.fxml"));
            StackPane pageEditStudent = loader.load();

            EditStudentController editStudentController = loader.getController();
            editStudentController.setPageStudent(pageStudent);
            editStudentController.setBGPageStudent(moveBG);
            editStudentController.setStudentData(student); // Pass student data to the controller

            pageEditStudent.setTranslateX(2000);
            pageEditStudent.setTranslateY(10);

            pageStudent.getChildren().add(pageEditStudent);

            GaussianBlur gaussianBlur = new GaussianBlur(10);
            moveBG.setEffect(gaussianBlur);

            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(0.2));
            translateTransition.setNode(pageEditStudent);
            translateTransition.setFromX(2000);
            translateTransition.setToX(420);
            translateTransition.setToY(6);
            translateTransition.play();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open the edit student page.");
        }
    }

    private void openGradeStudentPage(StudentModel student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/schoolmanagementsystem/views/PageGrade.fxml"));
            StackPane pageGradeStudent = loader.load();

            GradeController gradeController = loader.getController();
            gradeController.setPageStudent(pageStudent);
            gradeController.setBGPageStudent(moveBG);

            pageGradeStudent.setTranslateX(2000);
            pageGradeStudent.setTranslateY(10);

            pageStudent.getChildren().add(pageGradeStudent);

            GaussianBlur gaussianBlur = new GaussianBlur(10);
            moveBG.setEffect(gaussianBlur);

            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(0.2));
            translateTransition.setNode(pageGradeStudent);
            translateTransition.setFromX(2000);
            translateTransition.setToX(115);
            translateTransition.setToY(6);
            translateTransition.play();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open the grade student page.");
        }
    }

    private void loadStudentData() {
        studentData.clear();
        String query = "SELECT * FROM students WHERE Status = 'active'";

        try (Connection conn = ConnectDB.connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                StudentModel student =  new StudentModel(
                        rs.getInt("StudentID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getDate("DateOfBirth"),
                        rs.getBoolean("Gender"),
                        rs.getString("Address"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Email"),
                        rs.getDate("EnrollmentDate"),
                        rs.getInt("ClassID"),
                        rs.getString("Status"),
                        rs.getString("Avatar") // Added avatar parameter
                );
                studentData.add(student);
            }

            studentTable.setItems(studentData);
            studentTable.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading student data.");
        }
    }

    @FXML
    void addStudentBTN(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/schoolmanagementsystem/views/PageAddStudent.fxml"));
        StackPane pageAddStudent = loader.load();

        AddStudentController addStudentController = loader.getController();
        addStudentController.setPageStudent(pageStudent);
        addStudentController.setBGPageStudent(moveBG);

        pageAddStudent.setTranslateX(2000);
        pageAddStudent.setTranslateY(10);

        pageStudent.getChildren().add(pageAddStudent);

        GaussianBlur gaussianBlur = new GaussianBlur(10);
        moveBG.setEffect(gaussianBlur);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(pageAddStudent);
        translateTransition.setFromX(2000);
        translateTransition.setToX(420);
        translateTransition.setToY(6);
        translateTransition.play();
    }

    @FXML
    void refreshData(MouseEvent event) {
        loadStudentData();
//        showAlert(Alert.AlertType.INFORMATION, "Success", "Data has been refreshed.");
    }

    // Method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onStudentSelected() {
        StudentModel selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            openGradeStudentPage(selectedStudent);
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a student.");
        }
    }
}