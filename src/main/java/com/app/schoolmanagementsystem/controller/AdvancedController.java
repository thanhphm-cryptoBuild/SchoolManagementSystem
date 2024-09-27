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

    private AdvancedModel advancedModel;

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
        advancedModel = new AdvancedModel();

        setupStaffTableColumns();

        setupStudentTableColumns();

        configureColumnAlignmentStaff(idStaffColumn);
        configureColumnAlignmentStaff(firstNameStaffColumn);
        configureColumnAlignmentStaff(lastNameStaffColumn);
        configureColumnAlignmentStaff(emailStaffColumn);
        configureColumnAlignmentStaff(genderStaffColumn);
        configureColumnAlignmentStaff(phoneStaffColumn);
        configureColumnAlignmentStaff(positionNameStaffColumn);
        configureColumnAlignmentStaff(statusStaffColumn);

        configureColumnAlignmentStudent(idStudentColumn);
        configureColumnAlignmentStudent(firstNameStudentColumn);
        configureColumnAlignmentStudent(lastNameStudentColumn);
        configureColumnAlignmentStudent(emailStudentColumn);
        configureColumnAlignmentStudent(genderStudentColumn);
        configureColumnAlignmentStudent(phoneStudentColumn);
        configureColumnAlignmentStudent(statusStudentColumn);

        reloadStaffButton.setOnMouseClicked(event -> reloadPageStaff());
        reloadStudentButton.setOnMouseClicked(event -> reloadPageStudent());

        setupChoiceBox();

        setupSearchListeners();

        configureActionStaffColumn();
        configureActionStudentColumn();

        loadInactiveStaff();
        loadInactiveStudents();

    }

    private void setupChoiceBox() {
        ObservableList<String> searchOptionStaff = FXCollections.observableArrayList("Equal", "Gender", "ID");
        selectBoxStaff.setItems(searchOptionStaff);
        selectBoxStaff.setValue("Equal");

        ObservableList<String> searchOptionStudent = FXCollections.observableArrayList("Equal", "Gender", "ID");
        selectBoxStudent.setItems(searchOptionStudent);
        selectBoxStudent.setValue("Equal");
    }

    private void setupSearchListeners() {
        staffSearchField.textProperty().addListener((observable, oldValue, newValue) -> searchStaff());
        selectBoxStaff.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> searchStaff());
        extraSearchFieldStaff.textProperty().addListener((observable, oldValue, newValue) -> searchStaff());

        studentSearchField.textProperty().addListener((observable, oldValue, newValue) -> searchStudent());
        selectBoxStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> searchStudent());
        extraSearchFieldStudent.textProperty().addListener((observable, oldValue, newValue) -> searchStudent());
    }


    private void setupStaffTableColumns() {
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
                            try {
                                ImageView imageView = new ImageView(new Image(imagePath));
                                imageView.setFitWidth(24);
                                imageView.setFitHeight(24);
                                Circle clip = new Circle(12, 12, 12);
                                imageView.setClip(clip);

                                HBox hBox = new HBox(imageView);
                                hBox.setAlignment(Pos.CENTER);
                                setGraphic(hBox);
                            } catch (IllegalArgumentException e) {
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        });

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
        avatarStudentColumn.setCellValueFactory(new PropertyValueFactory<>("avatar.png"));
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
                            try {
                                ImageView imageView = new ImageView(new Image(imagePath));
                                imageView.setFitWidth(24);
                                imageView.setFitHeight(24);
                                Circle clip = new Circle(12, 12, 12);
                                imageView.setClip(clip);

                                HBox hBox = new HBox(imageView);
                                hBox.setAlignment(Pos.CENTER);
                                setGraphic(hBox);
                            } catch (IllegalArgumentException e) {
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        });

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

    private void configureActionStaffColumn() {
        actionStaffColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Staff, Void> call(TableColumn<Staff, Void> param) {
                return new TableCell<>() {
                    private final ImageView resetIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/reset.png")));

                    private final Button resetButton = new Button("", resetIcon);

                    {
                        resetIcon.setFitHeight(20);
                        resetIcon.setFitWidth(20);

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

                            HBox hBox = new HBox(resetButton);
                            hBox.setAlignment(Pos.CENTER);
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
                        resetIcon.setFitHeight(20);
                        resetIcon.setFitWidth(20);

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
                            HBox hBox = new HBox(resetButton);
                            hBox.setAlignment(Pos.CENTER);
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
        String searchText = staffSearchField.getText().trim();
        String extraSearchText = extraSearchFieldStaff.getText().trim();

        String firstName = null;
        String email = null;

        if (searchText.contains("@")) {
            email = searchText;
        } else if (!searchText.isEmpty()) {
            firstName = searchText;
        }

        Integer id = null;
        Byte gender = null;

        String selectedOption = selectBoxStaff.getValue();
        if ("ID".equals(selectedOption)) {
            try {
                id = Integer.parseInt(extraSearchText);
            } catch (NumberFormatException e) {
                id = null;
            }
        } else if ("Gender".equals(selectedOption)) {
            if ("Male".equalsIgnoreCase(extraSearchText)) {
                gender = (byte) 1;
            } else if ("Female".equalsIgnoreCase(extraSearchText)) {
                gender = (byte) 0;
            }
        }

        loadStaffDataByLogic(firstName, email, gender, id);
    }

    private void searchStudent() {
        String searchText = studentSearchField.getText().trim();
        String extraSearchText = extraSearchFieldStudent.getText().trim();

        String firstName = null;
        String email = null;

        if (searchText.contains("@")) {
            email = searchText;
        } else if (!searchText.isEmpty()) {
            firstName = searchText;
        }

        Integer id = null;
        Byte gender = null;

        String selectedOption = selectBoxStudent.getValue();
        if ("ID".equals(selectedOption)) {
            try {
                id = Integer.parseInt(extraSearchText);
            } catch (NumberFormatException e) {
                id = null;
            }
        } else if ("Gender".equals(selectedOption)) {
            if ("Male".equalsIgnoreCase(extraSearchText)) {
                gender = (byte) 1;
            } else if ("Female".equalsIgnoreCase(extraSearchText)) {
                gender = (byte) 0;
            }
        }

        loadStudentDataByLogic(firstName, email, gender, id);
    }

    private void loadStaffDataByLogic(String firstName, String email, Byte gender, Integer id) {
        staffList.clear();
        List<Staff> staffs = advancedModel.searchInactiveStaff(firstName, email, gender, id);
        staffList.addAll(staffs);
        staffTableView.setItems(staffList);
    }

    private void loadStudentDataByLogic(String firstName, String email, Byte gender, Integer id) {
        studentList.clear();
        List<Student> students = advancedModel.searchInactiveStudent(firstName, email, gender, id);
        studentList.addAll(students);
        studentTableView.setItems(studentList);
    }

    @FXML
    void handleResetStaff(Staff selectedStaff) {
        if (selectedStaff != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Xác nhận");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Are you sure you want to restore staff the ID agent: " + selectedStaff.getStaffID() + "?");

            confirmation.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    boolean success = advancedModel.restoreStaffStatus(selectedStaff.getStaffID());
                    if (success) {
                        showSuccess("Status restored for ID agents: " + selectedStaff.getStaffID());
                        loadInactiveStaff();
                    } else {
                        showError("Status can't be restored to an employee.");
                    }
                }
            });
        } else {
            showError("Please select an employee to restore the status.");
        }
    }

    @FXML
    void handleResetStudent(Student selectedStudent) {
        if (selectedStudent != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Are you sure you want to restore student the ID agent: " + selectedStudent.getStudentID() + "?");

            confirmation.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    boolean success = advancedModel.restoreStudentStatus(selectedStudent.getStudentID());
                    if (success) {
                        showSuccess("Status restored for student ID: " + selectedStudent.getStudentID());
                        loadInactiveStudents();
                    } else {
                        showError("The student's status cannot be restored.");
                    }
                }
            });
        } else {
            showError("Please select a student to restore the status.");
        }

    }

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
        loadInactiveStaff();
    }

    private void reloadPageStudent() {
        loadInactiveStudents();
    }
}
