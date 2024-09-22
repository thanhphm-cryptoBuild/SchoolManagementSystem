package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Classes;
import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.model.ClassModel;
import com.app.schoolmanagementsystem.model.StaffModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClassesController implements Initializable {

    @FXML
    private ChoiceBox<String> classNo;

    @FXML
    private DatePicker completePicker;

    @FXML
    private DatePicker enrollmentPicker;

    @FXML
    private StackPane pageClass;

    @FXML
    private TextField sectionClass;

    @FXML
    private TableView<ClassModel> tableViewClass;

    @FXML
    private TableColumn<ClassModel, LocalDate> colCompleteDate;

    @FXML
    private TableColumn<ClassModel, LocalDate> colEnrollmentDate;

    @FXML
    private TableColumn<ClassModel, Void> colAction;

    @FXML
    private TableColumn<ClassModel, Integer> colClassID;

    @FXML
    private TableColumn<ClassModel, String> colClassName;

    @FXML
    private TableColumn<ClassModel, String> colPhoneNumberTeacher;

    @FXML
    private TableColumn<ClassModel, String> colSection;

    @FXML
    private TableColumn<ClassModel, String> colTeacherName;

    @FXML
    private ChoiceBox<Integer> selectTeacherID;

    @FXML
    private ChoiceBox<Staff> selectTeacherName;

    @FXML
    private Label validateClass;

    @FXML
    private Label validateEndDate;

    @FXML
    private Label validateID;

    @FXML
    private Label validateName;

    @FXML
    private Label validateSection;

    @FXML
    private Label validateStartDate;

    private final Staff selectTeacherPlaceholder = new Staff(-1, "", "", "", "", "");

    @FXML
    void addFormClass(MouseEvent event) {
        if (!validateFields()) {
            return;
        }

        String selectedClass = classNo.getValue();
        String section = sectionClass.getText();
        LocalDate enrollmentDate = enrollmentPicker.getValue();
        LocalDate completeDate = completePicker.getValue();

        int staffID = selectTeacherID.getValue();

        try {
            ClassModel newClass = new ClassModel(0, selectedClass, section, staffID, enrollmentDate, completeDate);

            Classes classes = new Classes(0, selectedClass, section, staffID, enrollmentDate, completeDate);
            boolean isSaved = classes.saveClass(newClass);

            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Class added successfully");
                loadClassesData();
                resetFormClass(null);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add class");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while adding the class");
        }
    }

    private boolean validateFields() {
        boolean isValid = true;

        if (classNo.getValue() == null || classNo.getValue().equals("Select Class")) {
            validateClass.setText("Class is required.");
            isValid = false;
        } else {
            validateClass.setText("");
        }

        String section = sectionClass.getText();
        if (section.isEmpty()) {
            validateSection.setText("Section is required.");
            isValid = false;
        } else {
            validateSection.setText("");
        }

        LocalDate enrollmentDate = enrollmentPicker.getValue();
        if (enrollmentDate == null) {
            validateStartDate.setText("Enrollment date is required.");
            isValid = false;
        } else {
            validateStartDate.setText("");
        }

        LocalDate completeDate = completePicker.getValue();
        if (completeDate == null) {
            validateEndDate.setText("Completion date is required.");
            isValid = false;
        } else {
            validateEndDate.setText("");
        }

        if (selectTeacherName.getValue() == null || selectTeacherName.getValue() == selectTeacherPlaceholder) {
            validateName.setText("Teacher name is required.");
            isValid = false;
        } else {
            validateName.setText("");
        }

        return isValid;
    }

    private void setDatePickerLimits(LocalDate enrollmentDate) {
        LocalDate minCompleteDate = enrollmentDate.plusYears(1);

        completePicker.setValue(null);
        completePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isBefore(minCompleteDate));
            }
        });
    }

    private void loadClassesData() {
        Classes classes = new Classes();
        List<ClassModel> classesData = classes.getAllClasses();
        ObservableList<ClassModel> classList = FXCollections.observableArrayList(classesData);
        tableViewClass.setItems(classList);
    }

    @FXML
    void resetFormClass(MouseEvent event) {
        classNo.getSelectionModel().selectFirst();
        sectionClass.clear();
        enrollmentPicker.setValue(null);
        completePicker.setValue(null);
        selectTeacherID.getSelectionModel().clearSelection();
        selectTeacherName.getSelectionModel().select(selectTeacherPlaceholder);

        validateClass.setText("");
        validateEndDate.setText("");
        validateID.setText("");
        validateName.setText("");
        validateSection.setText("");
        validateStartDate.setText("");
    }

    void populateChoiceBoxes() {
        StaffModel staffModel = new StaffModel();
        List<Staff> staffList = staffModel.selectName();

        List<Staff> uniqueStaffList = new ArrayList<>();
        for (Staff staff : staffList) {
            boolean exists = uniqueStaffList.stream()
                    .anyMatch(s -> s.getFirstName().equals(staff.getFirstName())
                            && s.getLastName().equals(staff.getLastName())
                            && s.getPositionName().equals(staff.getPositionName()));
            if (!exists) {
                uniqueStaffList.add(staff);
            }
        }

        uniqueStaffList.add(0, selectTeacherPlaceholder);

        ObservableList<Staff> observableStaffList = FXCollections.observableArrayList(uniqueStaffList);

        selectTeacherName.setItems(observableStaffList);

        selectTeacherName.getSelectionModel().select(selectTeacherPlaceholder);

        selectTeacherName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Staff>() {
            @Override
            public void changed(ObservableValue<? extends Staff> observable, Staff oldValue, Staff newValue) {
                if (newValue != null) {
                    String selectedFirstName = newValue.getFirstName();
                    String selectedLastName = newValue.getLastName();
                    String selectedPosition = newValue.getPositionName();

                    List<Integer> teacherIDs = new ArrayList<>();
                    for (Staff staff : staffList) {
                        if (staff.getFirstName().equals(selectedFirstName) &&
                                staff.getLastName().equals(selectedLastName) &&
                                staff.getPositionName().equals(selectedPosition)) {
                            teacherIDs.add(staff.getStaffID());
                        }
                    }

                    selectTeacherID.setItems(FXCollections.observableArrayList(teacherIDs));
                    if (!teacherIDs.isEmpty()) {
                        selectTeacherID.getSelectionModel().selectFirst();
                    }
                }
            }
        });
    }

    void setupTable() {
        colClassID.setCellValueFactory(new PropertyValueFactory<>("classID"));
        colClassID.setStyle("-fx-alignment: CENTER;");
        colClassName.setCellValueFactory(new PropertyValueFactory<>("className"));
        colClassName.setStyle("-fx-alignment: CENTER;");
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));
        colSection.setStyle("-fx-alignment: CENTER;");
        colTeacherName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        colTeacherName.setStyle("-fx-alignment: CENTER;");
        colPhoneNumberTeacher.setCellValueFactory(new PropertyValueFactory<>("teacherPhoneNumber"));
        colPhoneNumberTeacher.setStyle("-fx-alignment: CENTER;");
        colEnrollmentDate.setCellValueFactory(new PropertyValueFactory<>("enrollmentDate"));
        colEnrollmentDate.setStyle("-fx-alignment: CENTER;");
        colCompleteDate.setCellValueFactory(new PropertyValueFactory<>("completeDate"));
        colCompleteDate.setStyle("-fx-alignment: CENTER;");

        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button();

            {
                Image editImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/edit.png")));
                ImageView editImageView = new ImageView(editImage);
                editImageView.setFitHeight(16);
                editImageView.setFitWidth(16);
                editButton.setGraphic(editImageView);
                editButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-color: #A3B5ED; -fx-border-radius: 5px; -fx-background-radius: 5px;");
//                editButton.setOnAction(event -> {
//
//                });
            };

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
//                    HBox actionButton = new HBox(5, editButton);
                    setGraphic(editButton);
                }
            }
        });

        colAction.setStyle("-fx-alignment: CENTER;");
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void initializeClassNo() {
        ObservableList<String> classOptions = FXCollections.observableArrayList(
                "Select Class", "10", "11", "12"
        );
        classNo.setItems(classOptions);
        classNo.getSelectionModel().selectFirst(); // Đặt giá trị mặc định là "Select Class"
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        populateChoiceBoxes();
        loadClassesData();
        initializeClassNo();

        enrollmentPicker.setOnAction(event -> {
            LocalDate enrollmentDate = enrollmentPicker.getValue();
            if (enrollmentDate != null) {
                setDatePickerLimits(enrollmentDate);
            }
        });
    }
}
