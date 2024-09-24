package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.entities.Student;
import com.app.schoolmanagementsystem.model.AdvancedModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdvancedController implements Initializable {

    @FXML
    private VBox layoutStaff;

    @FXML
    private VBox layoutStudent;

    @FXML
    private AnchorPane moveBG;

    @FXML
    private StackPane pageAdvanced;

    //column staff
    @FXML
    private TableView<Staff> staffTableView;

    @FXML
    private TableColumn<Staff, String> avatarStaffColumn;

    @FXML
    private TableColumn<Staff, Integer> idStaffColumn;

    @FXML
    private TableColumn<Staff, String> firstNameStaffColumn;

    @FXML
    private TableColumn<Staff, String> lastNameStaffColumn;

    @FXML
    private TableColumn<Staff, String> emailStaffColumn;

    @FXML
    private TableColumn<Staff, String> genderStaffColumn;

    @FXML
    private TableColumn<Staff, String> phoneStaffColumn;

    @FXML
    private TableColumn<Staff, String> positionNameStaffColumn;

    @FXML
    private TableColumn<Staff, String> statusStaffColumn;

    @FXML
    private TableColumn<Staff, Void> actionStaffColumn;

    //column student
    @FXML
    private TableView<Student> studentTableView;

    @FXML
    private TableColumn<Student, String> avatarStudentColumn;

    @FXML
    private TableColumn<Student, Integer> idStudentColumn;

    @FXML
    private TableColumn<Student, String> firstNameStudentColumn;

    @FXML
    private TableColumn<Student, String> lastNameStudentColumn;

    @FXML
    private TableColumn<Student, String> emailStudentColumn;

    @FXML
    private TableColumn<Student, String> genderStudentColumn;

    @FXML
    private TableColumn<Student, String> phoneStudentColumn;

    @FXML
    private TableColumn<Student, String> statusStudentColumn;

    @FXML
    private TableColumn<Student, Void> actionStudentColumn;

    @FXML
    private TextField staffSearchField;
    @FXML
    private ChoiceBox <String> selectBoxStaff;
    @FXML
    private TextField extraSearchFieldStaff;

    @FXML
    private TextField studentSearchField;
    @FXML
    private ChoiceBox <String> selectBoxStudent;
    @FXML
    private TextField extraSearchFieldStudent;

    @FXML
    private ImageView reloadStaffButton;

    @FXML
    private ImageView reloadStudentButton;

    private AdvancedModel advancedModel; // Khai báo biến

    private ObservableList<Staff> staffList = FXCollections.observableArrayList();
    private ObservableList<Student> studentList = FXCollections.observableArrayList();



    @FXML
    void openTableStaff(MouseEvent event) {
        layoutStaff.setVisible(true);
        layoutStudent.setVisible(false);
    }

    @FXML
    void openTableStudent(MouseEvent event) {
        layoutStaff.setVisible(false);
        layoutStudent.setVisible(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Khởi tạo model
        advancedModel = new AdvancedModel();

        // Định nghĩa cột cho Staff TableView
        setupStaffTableColumns();

        // Định nghĩa cột cho Student TableView
        setupStudentTableColumns();


        //can le staff
        configureColumnAlignmentStaff(idStaffColumn);
        configureColumnAlignmentStaff(firstNameStaffColumn);
        configureColumnAlignmentStaff(lastNameStaffColumn);
        configureColumnAlignmentStaff(emailStaffColumn);
        configureColumnAlignmentStaff(genderStaffColumn);
        configureColumnAlignmentStaff(phoneStaffColumn);
        configureColumnAlignmentStaff(positionNameStaffColumn);
        configureColumnAlignmentStaff(statusStaffColumn);

        //can le student
        configureColumnAlignmentStudent(idStudentColumn);
        configureColumnAlignmentStudent(firstNameStudentColumn);
        configureColumnAlignmentStudent(lastNameStudentColumn);
        configureColumnAlignmentStudent(emailStudentColumn);
        configureColumnAlignmentStudent(genderStudentColumn);
        configureColumnAlignmentStudent(phoneStudentColumn);
        configureColumnAlignmentStudent(statusStudentColumn);

        reloadStaffButton.setOnMouseClicked(event -> reloadPageStaff());
        reloadStudentButton.setOnMouseClicked(event -> reloadPageStudent());

        // Cấu hình ChoiceBox cho tìm kiếm
        setupChoiceBox();

        // Tạo sự kiện khi người dùng nhập vào ô tìm kiếm hoặc chọn lựa chọn
        setupSearchListeners();

        configureActionStaffColumn();
        configureActionStudentColumn();

        // Tải dữ liệu vào TableView
        loadInactiveStaff();
        loadInactiveStudents();

    }

    private void setupChoiceBox() {
        // Cấu hình ChoiceBox cho Staff
        ObservableList<String> searchOptionStaff = FXCollections.observableArrayList("Equal", "Gender", "ID");
        selectBoxStaff.setItems(searchOptionStaff);
        selectBoxStaff.setValue("Equal"); // Đặt giá trị mặc định

        // Cấu hình ChoiceBox cho Student
        ObservableList<String> searchOptionStudent = FXCollections.observableArrayList("Equal", "Gender", "ID");
        selectBoxStudent.setItems(searchOptionStudent);
        selectBoxStudent.setValue("Equal"); // Đặt giá trị mặc định
    }

    private void setupSearchListeners() {
        // Sự kiện tìm kiếm cho Staff
        staffSearchField.textProperty().addListener((observable, oldValue, newValue) -> searchStaff());
        selectBoxStaff.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> searchStaff());
        extraSearchFieldStaff.textProperty().addListener((observable, oldValue, newValue) -> searchStaff());

        // Sự kiện tìm kiếm cho Student
        studentSearchField.textProperty().addListener((observable, oldValue, newValue) -> searchStudent());
        selectBoxStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> searchStudent());
        extraSearchFieldStudent.textProperty().addListener((observable, oldValue, newValue) -> searchStudent());
    }


    private void setupStaffTableColumns() {
        // Avatar Staff column với hình ảnh
        avatarStaffColumn.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        avatarStaffColumn.setCellFactory(new Callback<TableColumn<Staff, String>, TableCell<Staff, String>>() {
            @Override
            public TableCell<Staff, String> call(TableColumn<Staff, String> param) {
                return new TableCell<Staff, String>() {
                    @Override
                    protected void updateItem(String imagePath, boolean empty) {
                        super.updateItem(imagePath, empty);
                        if (empty || imagePath == null) {
                            setGraphic(null);
                        } else {
                            ImageView imageView = new ImageView(new Image(imagePath));
                            imageView.setFitWidth(24);
                            imageView.setFitHeight(24);
                            Circle clip = new Circle(12, 12, 12);
                            imageView.setClip(clip);

                            // Tạo HBox và căn giữa
                            HBox hBox = new HBox(imageView);
                            hBox.setAlignment(Pos.CENTER); // Căn giữa hình ảnh trong HBox
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });


        // Các cột khác
        idStaffColumn.setCellValueFactory(new PropertyValueFactory<>("staffID"));
        firstNameStaffColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameStaffColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailStaffColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderStaffColumn.setCellValueFactory(cellData -> {
            String gender = cellData.getValue().getGender() == 1 ? "Male" : "Female";
            return new javafx.beans.property.SimpleStringProperty(gender);
        });
        phoneStaffColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        positionNameStaffColumn.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        statusStaffColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


    }

    private void setupStudentTableColumns() {
        // Avatar student column với hình ảnh
        avatarStudentColumn.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        avatarStudentColumn.setCellFactory(new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {
            @Override
            public TableCell<Student, String> call(TableColumn<Student, String> param) {
                return new TableCell<Student, String>() {
                    @Override
                    protected void updateItem(String imagePath, boolean empty) {
                        super.updateItem(imagePath, empty);
                        if (empty || imagePath == null) {
                            setGraphic(null);
                        } else {
                            ImageView imageView = new ImageView(new Image(imagePath));
                            imageView.setFitWidth(24);
                            imageView.setFitHeight(24);
                            Circle clip = new Circle(12, 12, 12);
                            imageView.setClip(clip);

                            // Tạo HBox và căn giữa
                            HBox hBox = new HBox(imageView);
                            hBox.setAlignment(Pos.CENTER); // Căn giữa hình ảnh trong HBox
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });

        // Các cột khác
        idStudentColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        firstNameStudentColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameStudentColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailStudentColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderStudentColumn.setCellValueFactory(cellData -> {
            String gender = cellData.getValue().getGender() == 1 ? "Male" : "Female";
            return new javafx.beans.property.SimpleStringProperty(gender);
        });
        phoneStudentColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        statusStudentColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private <T> void configureColumnAlignmentStaff(TableColumn<Staff, T> tableColumn) {
        tableColumn.setCellFactory(column -> new TableCell<Staff, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item == null ? "" : item.toString());
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    private <T> void configureColumnAlignmentStudent(TableColumn<Student, T> tableColumn) {
        tableColumn.setCellFactory(column -> new TableCell<Student, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item == null ? "" : item.toString());
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    // Cấu hình cột action cho Staff
    private void configureActionStaffColumn() {
        actionStaffColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Staff, Void> call(TableColumn<Staff, Void> param) {
                return new TableCell<>() {
                    private final ImageView resetIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/reset.png")));

                    private final Button resetButton = new Button("", resetIcon);

                    {
                        // Thiết lập kích thước hình ảnh
                        resetIcon.setFitHeight(20);
                        resetIcon.setFitWidth(20);

                        // Thêm sự kiện khi nhấp vào hình ảnh reset
                        resetButton.setOnMouseClicked((MouseEvent event) -> {
                            Staff staff = getTableView().getItems().get(getIndex());
                            handleResetStaff(staff);
                        });

                        resetButton.setOnMouseEntered(e -> resetButton.setCursor(Cursor.HAND));
                        resetButton.setOnMouseExited(e -> resetButton.setCursor(Cursor.DEFAULT));
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(resetButton);

                            // Tạo HBox và căn giữa
                            HBox hBox = new HBox(resetButton);
                            hBox.setAlignment(Pos.CENTER); // Căn giữa hình ảnh trong HBox
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });
    }


    private void configureActionStudentColumn() {
        actionStudentColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Student, Void> call(TableColumn<Student, Void> param) {
                return new TableCell<>() {
                    private final ImageView resetIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/reset.png")));

                    private final Button resetButton = new Button("", resetIcon);

                    {
                        // Thiết lập kích thước hình ảnh
                        resetIcon.setFitHeight(20);
                        resetIcon.setFitWidth(20);

                        // Thêm sự kiện khi nhấp vào hình ảnh reset
                        resetButton.setOnMouseClicked((MouseEvent event) -> {
                            Student student = getTableView().getItems().get(getIndex());
                            handleResetStudent(student);
                        });

                        resetButton.setOnMouseEntered(e -> resetButton.setCursor(Cursor.HAND));
                        resetButton.setOnMouseExited(e -> resetButton.setCursor(Cursor.DEFAULT));

                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(resetButton);

                            // Tạo HBox và căn giữa
                            HBox hBox = new HBox(resetButton);
                            hBox.setAlignment(Pos.CENTER); // Căn giữa hình ảnh trong HBox
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });
    }





    private void loadInactiveStaff() {
        List<Staff> inactiveStaffList = advancedModel.getInactiveStaff();
        ObservableList<Staff> staffObservableList = FXCollections.observableArrayList(inactiveStaffList);
        staffTableView.setItems(staffObservableList);
    }

    private void loadInactiveStudents() {
        List<Student> inactiveStudentList = advancedModel.getInactiveStudents();
        ObservableList<Student> studentObservableList = FXCollections.observableArrayList(inactiveStudentList);
        studentTableView.setItems(studentObservableList);
    }

    private void searchStaff() {
        // Lấy giá trị từ các ô tìm kiếm
        String searchText = staffSearchField.getText().trim(); // Tìm kiếm theo firstName hoặc email
        String extraSearchText = extraSearchFieldStaff.getText().trim(); // Tìm kiếm theo ID hoặc Gender từ ChoiceBox

        // Tách phần tìm kiếm cho firstName, email từ searchField
        String firstName = null;
        String email = null;

        // Xử lý tìm kiếm theo searchField
        if (searchText.contains("@")) {
            email = searchText;
        } else if (!searchText.isEmpty()) {
            firstName = searchText;
        }

        // Tách phần tìm kiếm cho ID, gender từ extraSearchField
        Integer id = null;
        Byte gender = null;

        // Xử lý tìm kiếm theo lựa chọn từ ChoiceBox
        String selectedOption = selectBoxStaff.getValue();
        if ("ID".equals(selectedOption)) {
            // Nếu lựa chọn là ID, tìm kiếm theo ID
            try {
                id = Integer.parseInt(extraSearchText);
            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu extraSearchField không phải là số
                id = null;
            }
        } else if ("Gender".equals(selectedOption)) {
            // Nếu lựa chọn là Gender, tìm kiếm theo Gender
            if ("Male".equalsIgnoreCase(extraSearchText)) {
                gender = (byte) 1;
            } else if ("Female".equalsIgnoreCase(extraSearchText)) {
                gender = (byte) 0;
            }
        }

        loadStaffDataByLogic(firstName, email, gender, id);
    }

    private void searchStudent() {
        // Lấy giá trị từ các ô tìm kiếm
        String searchText = studentSearchField.getText().trim(); // Tìm kiếm theo firstName hoặc email
        String extraSearchText = extraSearchFieldStudent.getText().trim(); // Tìm kiếm theo ID hoặc Gender từ ChoiceBox

        // Tách phần tìm kiếm cho firstName, email từ searchField
        String firstName = null;
        String email = null;

        // Xử lý tìm kiếm theo searchField
        if (searchText.contains("@")) {
            email = searchText;
        } else if (!searchText.isEmpty()) {
            firstName = searchText;
        }

        // Tách phần tìm kiếm cho ID, gender từ extraSearchField
        Integer id = null;
        Byte gender = null;

        // Xử lý tìm kiếm theo lựa chọn từ ChoiceBox
        String selectedOption = selectBoxStudent.getValue();
        if ("ID".equals(selectedOption)) {
            // Nếu lựa chọn là ID, tìm kiếm theo ID
            try {
                id = Integer.parseInt(extraSearchText);
            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu extraSearchField không phải là số
                id = null;
            }
        } else if ("Gender".equals(selectedOption)) {
            // Nếu lựa chọn là Gender, tìm kiếm theo Gender
            if ("Male".equalsIgnoreCase(extraSearchText)) {
                gender = (byte) 1;
            } else if ("Female".equalsIgnoreCase(extraSearchText)) {
                gender = (byte) 0;
            }
        }

        loadStudentDataByLogic(firstName, email, gender, id);
    }

    private void loadStaffDataByLogic(String firstName, String email, Byte gender, Integer id) {
        staffList.clear(); // Xóa dữ liệu cũ từ ObservableList
        List<Staff> staffs = advancedModel.searchInactiveStaff(firstName, email, gender, id); // Gọi phương thức tìm kiếm trong AdvancedModel
        staffList.addAll(staffs); // Thêm kết quả tìm kiếm vào ObservableList
        staffTableView.setItems(staffList); // Cập nhật TableView với staffList
    }

    private void loadStudentDataByLogic(String firstName, String email, Byte gender, Integer id) {
        studentList.clear(); // Xóa dữ liệu cũ từ ObservableList
        List<Student> students = advancedModel.searchInactiveStudent(firstName, email, gender, id); // Gọi phương thức tìm kiếm trong AdvancedModel
        studentList.addAll(students); // Thêm kết quả tìm kiếm vào ObservableList
        studentTableView.setItems(studentList); // Cập nhật TableView với studentList
    }



    // Phương thức xử lý Reset cho Staff
    @FXML
    void handleResetStaff(Staff selectedStaff) {
        if (selectedStaff != null) {
            // Xác nhận trước khi reset
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Xác nhận");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Are you sure you want to restore staff the ID agent: " + selectedStaff.getStaffID() + "?");

            confirmation.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    boolean success = advancedModel.restoreStaffStatus(selectedStaff.getStaffID());
                    if (success) {
                        showSuccess("Status restored for ID agents: " + selectedStaff.getStaffID());
                        loadInactiveStaff(); // Cập nhật lại TableView
                    } else {
                        showError("Status can't be restored to an employee.");
                    }
                }
            });
        } else {
            showError("Please select an employee to restore the status.");
        }
    }



    // Phương thức xử lý Reset cho Student
    @FXML
    void handleResetStudent(Student selectedStudent) {
        if (selectedStudent != null) {
            // Xác nhận trước khi reset
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Xác nhận");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Are you sure you want to restore student the ID agent: " + selectedStudent.getStudentID() + "?");

            confirmation.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    boolean success = advancedModel.restoreStudentStatus(selectedStudent.getStudentID());
                    if (success) {
                        showSuccess("Status restored for student ID: " + selectedStudent.getStudentID());
                        loadInactiveStudents(); // Cập nhật lại TableView
                    } else {
                        showError("The student's status cannot be restored.");
                    }
                }
            });
        } else {
            showError("Please select a student to restore the status.");
        }

    }




    // Các phương thức hiển thị thông báo lỗi và thành công
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void reloadPageStaff() {

        // Tải dữ liệu nhân viên
        loadInactiveStaff();

    }

    private void reloadPageStudent() {

        // Tải dữ liệu nhân viên
        loadInactiveStudents();

    }



}