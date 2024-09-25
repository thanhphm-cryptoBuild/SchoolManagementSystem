package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Classes;
import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.model.ClassModel;
import com.app.schoolmanagementsystem.model.StaffModel;
import com.app.schoolmanagementsystem.session.UserSession;
import javafx.animation.TranslateTransition;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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
    private TextField descriptionClass;

    @FXML
    private TableView<ClassModel> tableViewClass;

    @FXML
    private TableColumn<ClassModel, LocalDate> colCompleteDate;

    @FXML
    private TableColumn<ClassModel, LocalDate> colEnrollmentDate;

    @FXML
    private TableColumn<ClassModel, Void> colAction;

    @FXML
    private TableColumn<ClassModel, String> colDescription;

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
    private VBox formAddClass;

    @FXML
    private VBox formEditClass;

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

    @FXML
    private Label showEditClassID;

    @FXML
    private ChoiceBox<String> showEditClassNo;

    @FXML
    private DatePicker showEditEndDate;

    @FXML
    private TextField showEditSectionClass;

    @FXML
    private DatePicker showEditStartDate;

    @FXML
    private ChoiceBox<Integer> showEditTeacherID;

    @FXML
    private ChoiceBox<Staff> showEditTeacherName;

    @FXML
    private TextField showEditDescription;

    @FXML
    private Label validateEditClassNo;

    @FXML
    private Label validateEditEndDate;

    @FXML
    private Label validateEditSectionClass;

    @FXML
    private Label validateEditStartDate;

    @FXML
    private Label validateEditTeacherID;

    @FXML
    private Label validateEditTeacherName;



    private final Staff selectTeacherPlaceholder = new Staff(-1, "", "", "", "", "");

    @FXML
    void addFormClass(MouseEvent event) {
        if (!validateFields()) {
            return;
        }

        String selectedClass = classNo.getValue();
        String section = sectionClass.getText();
        String description = descriptionClass.getText();
        LocalDate enrollmentDate = enrollmentPicker.getValue();
        LocalDate completeDate = completePicker.getValue();

        int staffID = selectTeacherID.getValue();

        try {
            ClassModel newClass = new ClassModel(0, selectedClass, section, staffID, enrollmentDate, completeDate, description);

            Classes classes = new Classes(0, selectedClass, section, staffID, enrollmentDate, completeDate, description);

            boolean isStaffUnique = classes.isStaffIDUnique(staffID, enrollmentDate.getYear(), completeDate.getYear());
            if (!isStaffUnique) {
                showAlert(Alert.AlertType.ERROR, "Error", "The teacher was homeroom teacher in another class in the same school year.");
                return;
            }

            boolean isAcademicYearValid = classes.isAcademicYearValid(selectedClass, section, enrollmentDate.getYear(), completeDate.getYear());
            if (!isAcademicYearValid) {
                showAlert(Alert.AlertType.ERROR, "Error", "The school year of the new class coincides with the school year of the other class.");
                return;
            }

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

    @FXML
    void updateFormEditClass(MouseEvent event) {
        if (!validateEditFields()) {
            return;
        }

        int classID = Integer.parseInt(showEditClassID.getText());
        String selectedClass = showEditClassNo.getValue();
        String section = showEditSectionClass.getText();
        String description = showEditDescription.getText();
        LocalDate enrollmentDate = showEditStartDate.getValue();
        LocalDate completeDate = showEditEndDate.getValue();
        Integer staffID = showEditTeacherID.getValue();

        if (staffID == null || staffID == -1) {
            showAlert(Alert.AlertType.ERROR, "Error", "Staff ID is required.");
            return;
        }

        try {
            ClassModel updatedClass = new ClassModel(classID, selectedClass, section, staffID, enrollmentDate, completeDate, description);

            Classes classes = new Classes();

            boolean isStaffUniqueEdit = classes.isStaffIDUniqueEdit(staffID, enrollmentDate.getYear(), completeDate.getYear(), classID);
            if (!isStaffUniqueEdit) {
                showAlert(Alert.AlertType.ERROR, "Error", "The teacher was homeroom teacher in another class in the same school year.");
                return;
            }

            boolean isAcademicYearValidEdit = classes.isAcademicYearValidEdit(selectedClass, section, enrollmentDate.getYear(), completeDate.getYear(), classID);
            if (!isAcademicYearValidEdit) {
                showAlert(Alert.AlertType.ERROR, "Error", "The school year of the edited class coincides with the school year of another class.");
                return;
            }

            boolean isUpdated = classes.updateClass(updatedClass);

            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Class updated successfully");
                loadClassesData();
                closeFormEditClass(null);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update class");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating the class");
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

        Integer staffID = selectTeacherID.getValue();
        if (staffID == null) {
            validateID.setText("Staff ID is required.");
            isValid = false;
        } else {
            validateID.setText("");
        }

        return isValid;
    }

    private boolean validateEditFields() {
        boolean isValid = true;

        validateEditClassNo.setText("");
        validateEditSectionClass.setText("");
        validateEditStartDate.setText("");
        validateEditEndDate.setText("");
        validateEditTeacherName.setText("");
        validateEditTeacherID.setText("");

        if (showEditClassNo.getValue() == null || showEditClassNo.getValue().equals("Select Class")) {
            validateEditClassNo.setText("Class is required.");
            isValid = false;
        } else {
            validateEditClassNo.setText("");
        }

        String section = showEditSectionClass.getText();
        if (section.isEmpty()) {
            validateEditSectionClass.setText("Section is required.");
            isValid = false;
        } else {
            validateEditSectionClass.setText("");
        }

        LocalDate enrollmentDate = showEditStartDate.getValue();
        if (enrollmentDate == null) {
            validateEditStartDate.setText("Enrollment date is required.");
            isValid = false;
        } else {
            validateEditStartDate.setText("");
        }

        LocalDate completeDate = showEditEndDate.getValue();
        if (completeDate == null) {
            validateEditEndDate.setText("Completion date is required.");
            isValid = false;
        } else {
            validateEditEndDate.setText("");
        }

        Staff selectedTeacher = showEditTeacherName.getValue();
        if (selectedTeacher == null || selectedTeacher == selectTeacherPlaceholder) {
            validateEditTeacherName.setText("Teacher name is required.");
            isValid = false;
        } else {
            validateEditTeacherName.setText("");
        }

        Integer staffID = showEditTeacherID.getValue();
        if (staffID == null || staffID == -1) {
            validateEditTeacherID.setText("Staff ID is required.");
            isValid = false;
        } else {
            validateEditTeacherID.setText("");
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

    private void setDatePickerLimitsEdit(LocalDate enrollmentDate) {
        LocalDate minCompleteDate = enrollmentDate.plusYears(1);

        showEditEndDate.setValue(null);
        showEditEndDate.setDayCellFactory(picker -> new DateCell() {
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

        System.out.println("Loaded Classes Data:");
        for (ClassModel classModel : classesData) {
            System.out.println(classModel);
        }

        ObservableList<ClassModel> classList = FXCollections.observableArrayList(classesData);
        tableViewClass.setItems(classList);

    }

    @FXML
    void refreshTable(MouseEvent event) {
        loadClassesData();
        showAlert(Alert.AlertType.INFORMATION, "Confirmed", "Refresh Success");
    }

    @FXML
    void resetFormClass(MouseEvent event) {
        classNo.getSelectionModel().selectFirst();
        sectionClass.clear();
        descriptionClass.clear();
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

    private void populateEditTeacherChoiceBoxes(int staffID) {
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
        showEditTeacherName.setItems(observableStaffList);
        showEditTeacherName.getSelectionModel().selectFirst();

        showEditTeacherName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Staff>() {
            @Override
            public void changed(ObservableValue<? extends Staff> observable, Staff oldValue, Staff newValue) {
                if (newValue != null && newValue != selectTeacherPlaceholder) {
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

//                    List<Integer> teacherIDsWithPlaceholder = new ArrayList<>();
//                    teacherIDsWithPlaceholder.add(-1);
//                    teacherIDsWithPlaceholder.addAll(teacherIDs);

                    showEditTeacherID.setItems(FXCollections.observableArrayList(teacherIDs));
//                    showEditTeacherID.getSelectionModel().selectFirst();

                    if (teacherIDs.contains(staffID)) {
                        showEditTeacherID.getSelectionModel().select(Integer.valueOf(staffID));
                    }
                } else {
                    showEditTeacherID.getItems().clear();
                }
            }
        });

        for (Staff staff : staffList) {
            if (staff.getStaffID() == staffID) {
                showEditTeacherName.setValue(staff);
                break;
            }
        }
    }


    void setupTable() {
        colClassID.setCellValueFactory(new PropertyValueFactory<>("classID"));
        colClassID.setStyle("-fx-alignment: CENTER;");
        colClassName.setCellValueFactory(new PropertyValueFactory<>("className"));
        colClassName.setStyle("-fx-alignment: CENTER;");
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));
        colSection.setStyle("-fx-alignment: CENTER;");
        colTeacherName.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getFormattedTeacherName()));
        colTeacherName.setStyle("-fx-alignment: CENTER;");
        colPhoneNumberTeacher.setCellValueFactory(new PropertyValueFactory<>("teacherPhoneNumber"));
        colPhoneNumberTeacher.setStyle("-fx-alignment: CENTER;");
        colEnrollmentDate.setCellValueFactory(new PropertyValueFactory<>("enrollmentDate"));
        colEnrollmentDate.setStyle("-fx-alignment: CENTER;");
        colCompleteDate.setCellValueFactory(new PropertyValueFactory<>("completeDate"));
        colCompleteDate.setStyle("-fx-alignment: CENTER;");
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDescription.setStyle("-fx-alignment: CENTER;");

        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button();

            {
                Image editImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/edit.png")));
                ImageView editImageView = new ImageView(editImage);
                editImageView.setFitHeight(16);
                editImageView.setFitWidth(16);
                editButton.setGraphic(editImageView);
                editButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-color: #A3B5ED; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                editButton.setOnAction(event -> {
                    ClassModel classToEdit = getTableView().getItems().get(getIndex());
                    openFormEdit(classToEdit);
                });
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


    @FXML
    void closeFormEditClass(MouseEvent event) {
        formEditClass.setTranslateX(50);
        formEditClass.setVisible(false);
        formAddClass.setVisible(true);

        TranslateTransition closeTransition = new TranslateTransition(Duration.seconds(0.2));
        closeTransition.setNode(formAddClass);
        closeTransition.setFromX(50);
        closeTransition.setToX(0);
        closeTransition.play();

        closeTransition.setOnFinished(event1 -> {
            formAddClass.setVisible(true);
            formEditClass.setVisible(false);
        });

        validateEditClassNo.setText("");
        validateEditSectionClass.setText("");
        validateEditStartDate.setText("");
        validateEditEndDate.setText("");
        validateEditTeacherName.setText("");
        validateEditTeacherID.setText("");
    }



    private void openFormEdit(ClassModel classModel) {
        formEditClass.setTranslateX(-50);
        formEditClass.setVisible(true);
        formAddClass.setVisible(false);


        TranslateTransition openTransition = new TranslateTransition(Duration.seconds(0.2));
        openTransition.setNode(formEditClass);
        openTransition.setFromX(-50);
        openTransition.setToX(0);
        openTransition.play();

        openTransition.setOnFinished(event -> {
            formAddClass.setVisible(false);
            formEditClass.setVisible(true);
        });

        showEditClassID.setText(String.valueOf(classModel.getClassID()));
        showEditClassNo.setValue(classModel.getClassName());
        showEditSectionClass.setText(classModel.getSection());
        showEditStartDate.setValue(classModel.getEnrollmentDate());
        showEditEndDate.setValue(classModel.getCompleteDate());
        showEditDescription.setText(classModel.getDescription());

        populateEditTeacherChoiceBoxes(classModel.getStaffID());

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
        classNo.getSelectionModel().selectFirst();
    }

    private void initializeShowClassNo() {
        ObservableList<String> classOptions = FXCollections.observableArrayList(
                "Select Class", "10", "11", "12"
        );
        showEditClassNo.setItems(classOptions);
        showEditClassNo.getSelectionModel().selectFirst();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        populateChoiceBoxes();
        loadClassesData();
        initializeClassNo();
        initializeShowClassNo();
        disableformAddClass();
        hideActionClass();

        enrollmentPicker.setOnAction(event -> {
            LocalDate enrollmentDate = enrollmentPicker.getValue();
            if (enrollmentDate != null) {
                setDatePickerLimits(enrollmentDate);
            }
        });

        showEditStartDate.setOnAction(event -> {
            LocalDate enrollmentDate = showEditStartDate.getValue();
            if (enrollmentDate != null) {
                setDatePickerLimitsEdit(enrollmentDate);
            }
        });

    }

    private void disableformAddClass() {
        // Kiểm tra vai trò của người dùng hiện tại
        String currentRole = getCurrentRoleName(); // Phương thức này trả về vai trò của người dùng hiện tại

        // Nếu vai trò là "Teacher", ẩn cột action
        if ("Teacher".equals(currentRole)) {
            formAddClass.setDisable(true); // an add class
        }
    }

    private void hideActionClass() {
        // Kiểm tra vai trò của người dùng hiện tại
        String currentRole = getCurrentRoleName(); // Phương thức này trả về vai trò của người dùng hiện tại

        // Nếu vai trò là "Teacher", ẩn cột action
        if ("Teacher".equals(currentRole)) {
            colAction.setVisible(false); // Ẩn cột hành động nếu là Teacher
        }
    }

    // Phương thức lấy roleName hiện tại
    private String getCurrentRoleName() {
        return UserSession.getCurrentRoleName(); // Lấy roleName từ UserSession
    }
}
