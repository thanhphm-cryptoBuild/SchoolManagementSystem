package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Classes;
import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.entities.Subject;
import com.app.schoolmanagementsystem.entities.SubjectClass;
import com.app.schoolmanagementsystem.model.*;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class SubjectController implements Initializable {

    @FXML
    private StackPane pageSubject;

    @FXML
    private VBox formAddSubjectClass;

    @FXML
    private VBox formEditSubjectClass;

    @FXML
    private ChoiceBox<ClassModel> selectClassName;

    @FXML
    private ChoiceBox<Subject> selectSubject;

    @FXML
    private ChoiceBox<Integer> selectTeacherID;

    @FXML
    private ChoiceBox<Staff> selectTeacherName;

    @FXML
    private Label validateClassName;

    @FXML
    private Label validateSubject;

    @FXML
    private Label validateTeacherID;

    @FXML
    private Label validateTeacherName;

    @FXML
    private ChoiceBox<ClassModel> showEditClassName;

    @FXML
    private Label showEditClassSubjectID;

    @FXML
    private ChoiceBox<Subject> showEditSubject;

    @FXML
    private ChoiceBox<Integer> showEditTeacherID;

    @FXML
    private ChoiceBox<Staff> showEditTeacherName;

    @FXML
    private Label validateEditClassName;

    @FXML
    private Label validateEditSubject;

    @FXML
    private Label validateEditTeacherID;

    @FXML
    private Label validateEditTeacherName;

    @FXML
    private TableView<SubjectClass> tableViewClassSubject;

    @FXML
    private TableColumn<SubjectClass, Void> colAction;

    @FXML
    private TableColumn<SubjectClass, String> colClassName;

    @FXML
    private TableColumn<SubjectClass, Integer> colClassSubjectID;

    @FXML
    private TableColumn<SubjectClass, String> colPhoneNumber;

    @FXML
    private TableColumn<SubjectClass, String> colSubject;

    @FXML
    private TableColumn<SubjectClass, String> colTeacherName;

    private final Staff selectTeacherPlaceholder = new Staff(-1, "", "", "", "", "");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        loadSubjects();
        populateChoiceBoxes();
        populateClassNames();
        loadClassSubjects();
    }

    @FXML
    void addFormSubject(MouseEvent event) {
        if (!validateFormAdd()) {
            return;
        }

        ClassModel selectedClass = selectClassName.getValue();
        Subject selectedSubject = selectSubject.getValue();
        int selectedTeacherID = selectTeacherID.getValue();

        if (selectedClass != null && selectedSubject != null && selectedTeacherID != -1) {
            int classID = selectedClass.getClassID();
            int subjectID = selectedSubject.getSubjectID();

            SubjectClassModel subjectClassModel = new SubjectClassModel();

            boolean isSubjectNameExists = subjectClassModel.isSubjectNameExists(subjectID, classID);
            if (isSubjectNameExists) {
                showAlert(Alert.AlertType.ERROR, "Error", "Subject already exists for this class.");
                return;
            }

            subjectClassModel.addClassSubject(classID, subjectID, selectedTeacherID);

            loadClassSubjects();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Subject detail added successfully.");
            resetFormSubject(event);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add subject detail.");
        }
    }

    private boolean validateFormAdd() {
        boolean isValid = true;

        if (selectClassName.getValue() == null) {
            validateClassName.setText("Class Name is required.");
            isValid = false;
        } else {
            validateClassName.setText("");
        }

        if (selectSubject.getValue() == null) {
            validateSubject.setText("Subject is required.");
            isValid = false;
        } else {
            validateSubject.setText("");
        }

        if (selectTeacherName.getValue() == null || selectTeacherName.getValue() == selectTeacherPlaceholder) {
            validateTeacherName.setText("Subject Teacher is required.");
            isValid = false;
        } else {
            validateTeacherName.setText("");
        }

        Integer staffID = selectTeacherID.getValue();
        if (staffID == null) {
            validateTeacherID.setText("Staff ID is required.");
            isValid = false;
        } else {
            validateTeacherID.setText("");
        }

        return isValid;
    }

    @FXML
    void updateFormEditSubject(MouseEvent event) {
        try {
            if (!validateFormEdit()) {
                return;
            }

            int classSubjectID = Integer.parseInt(showEditClassSubjectID.getText());
            ClassModel selectedClass = showEditClassName.getValue();
            Subject selectedSubject = showEditSubject.getValue();
            int selectedTeacherID = showEditTeacherID.getValue();

            if (selectedClass != null && selectedSubject != null && selectedTeacherID != -1) {
                int classID = selectedClass.getClassID();
                int subjectID = selectedSubject.getSubjectID();

                SubjectClassModel subjectClassModel = new SubjectClassModel();

                SubjectClass oldSubjectClass = subjectClassModel.getClassSubjectByID(classSubjectID);

                if (oldSubjectClass != null) {
                    boolean isSubjectNameExists = subjectClassModel.isSubjectNameEditExists(subjectID, classID, classSubjectID);
                    if (isSubjectNameExists) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Subject already exists in this class.");
                        return;
                    }
                }


                subjectClassModel.updateClassSubject(classSubjectID, classID, subjectID, selectedTeacherID);

                loadClassSubjects();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Subject detail updated successfully.");
                closeFormEditSubject(event);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update subject detail.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update subject detail.");
        }
    }

    private boolean validateFormEdit() {
        boolean isValid = true;

        validateEditSubject.setText("");
        validateEditClassName.setText("");
        validateEditTeacherName.setText("");
        validateEditTeacherID.setText("");

        if (showEditClassName.getValue() == null) {
            validateEditClassName.setText("Class Name is required.");
            isValid = false;
        } else {
            validateEditClassName.setText("");
        }

        if (showEditSubject.getValue() == null) {
            validateEditSubject.setText("Subject is required.");
            isValid = false;
        } else {
            validateEditSubject.setText("");
        }

        if (showEditTeacherName.getValue() == null || showEditTeacherName.getValue() == selectTeacherPlaceholder) {
            validateEditTeacherName.setText("Subject Teacher is required.");
            isValid = false;
        } else {
            validateTeacherName.setText("");
        }

        Integer staffID = showEditTeacherID.getValue();
        if (staffID == null) {
            validateEditTeacherID.setText("Staff ID is required.");
            isValid = false;
        } else {
            validateEditTeacherID.setText("");
        }

        return isValid;
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void resetFormSubject(MouseEvent event) {
        selectSubject.setValue(null);
        selectClassName.setValue(null);
        selectTeacherID.getSelectionModel().clearSelection();
        selectTeacherName.getSelectionModel().select(selectTeacherPlaceholder);

        validateSubject.setText("");
        validateClassName.setText("");
        validateTeacherName.setText("");
        validateTeacherID.setText("");
    }

    @FXML
    void refreshTableSubjectDetail(MouseEvent event) {
        loadClassSubjects();
        showAlert(Alert.AlertType.INFORMATION, "Confirmed", "Refresh Success");
    }

    @FXML
    void closeFormEditSubject(MouseEvent event) {
        formEditSubjectClass.setTranslateX(50);
        formEditSubjectClass.setVisible(false);
        formAddSubjectClass.setVisible(true);

        TranslateTransition closeTransition = new TranslateTransition(Duration.seconds(0.2));
        closeTransition.setNode(formAddSubjectClass);
        closeTransition.setFromX(50);
        closeTransition.setToX(0);
        closeTransition.play();

        closeTransition.setOnFinished(event1 -> {
            formAddSubjectClass.setVisible(true);
            formEditSubjectClass.setVisible(false);
        });
    }

    private void openFormEditSubjectClass(SubjectClass subjectClass) {
        formEditSubjectClass.setTranslateX(-50);
        formEditSubjectClass.setVisible(true);
        formAddSubjectClass.setVisible(false);


        TranslateTransition openTransition = new TranslateTransition(Duration.seconds(0.2));
        openTransition.setNode(formEditSubjectClass);
        openTransition.setFromX(-50);
        openTransition.setToX(0);
        openTransition.play();

        openTransition.setOnFinished(event -> {
            formAddSubjectClass.setVisible(false);
            formEditSubjectClass.setVisible(true);
        });

        showEditClassSubjectID.setText(String.valueOf(subjectClass.getClassSubjectID()));

        Subject subjectToEdit = findSubjectById(subjectClass.getSubjectID());
        if (subjectToEdit != null) {
            showEditSubject.getSelectionModel().select(subjectToEdit);
        }

        ClassModel classToEdit = findClassById(subjectClass.getClassID());
        if (classToEdit != null) {
            showEditClassName.getSelectionModel().select(classToEdit);
        }


        populateEditTeacherChoiceBoxes(subjectClass.getStaffID());

    }

    private void deleteClassSubject(SubjectClass subjectClassToDelete) {
        int classSubjectID = subjectClassToDelete.getClassSubjectID();

        try {
            SubjectClassModel subjectClassModel = new SubjectClassModel();
            subjectClassModel.deleteClassSubject(classSubjectID);

            loadClassSubjects();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Subject detail ID: " + classSubjectID + " deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete subject detail ID: " + classSubjectID + ".");
        }
    }

    private Subject findSubjectById(int subjectID) {
        for (Subject subject : selectSubject.getItems()) {
            if (subject.getSubjectID() == subjectID) {
                return subject;
            }
        }
        return null;
    }

    private ClassModel findClassById(int classID) {
        for (ClassModel classModel : selectClassName.getItems()) {
            if (classModel.getClassID() == classID) {
                return classModel;
            }
        }
        return null;
    }

    private void loadSubjects() {
        SubjectModel subjectModel = new SubjectModel();
        List<Subject> subjects = subjectModel.selectName();

        ObservableList<Subject> observableSubjectList = FXCollections.observableArrayList(subjects);

        selectSubject.setItems(observableSubjectList);
        showEditSubject.setItems(observableSubjectList);

        selectSubject.setConverter(new javafx.util.StringConverter<Subject>() {
            @Override
            public String toString(Subject subject) {
                return subject != null ? subject.getSubjectName() : "";
            }

            @Override
            public Subject fromString(String string) {
                return null;
            }
        });

        showEditSubject.setConverter(new javafx.util.StringConverter<Subject>() {
            @Override
            public String toString(Subject subject) {
                return subject != null ? subject.getSubjectName() : "";
            }

            @Override
            public Subject fromString(String string) {
                return null;
            }
        });
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

                    showEditTeacherID.setItems(FXCollections.observableArrayList(teacherIDs));

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

    private void populateClassNames() {
        Classes classes = new Classes();
        List<ClassModel> classList = classes.getAllClasses();

        ObservableList<ClassModel> observableClassList = FXCollections.observableArrayList(classList);

        selectClassName.setItems(observableClassList);

        selectClassName.setConverter(new javafx.util.StringConverter<ClassModel>() {
            @Override
            public String toString(ClassModel classModel) {
                return classModel != null ? classModel.getClassNameWithYears() : "";
            }

            @Override
            public ClassModel fromString(String string) {
                return null;
            }
        });

        showEditClassName.setItems(selectClassName.getItems());

        showEditClassName.setConverter(new javafx.util.StringConverter<ClassModel>() {
            @Override
            public String toString(ClassModel classModel) {
                return classModel != null ? classModel.getClassNameWithYears() : "";
            }

            @Override
            public ClassModel fromString(String string) {
                return null;
            }
        });
    }

    void setupTable() {
        colClassSubjectID.setCellValueFactory(new PropertyValueFactory<>("classSubjectID"));
        colClassSubjectID.setStyle("-fx-alignment: CENTER;");

        colClassName.setCellValueFactory(new PropertyValueFactory<>("classNameYear"));
        colClassName.setStyle("-fx-alignment: CENTER;");

        colSubject.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colSubject.setStyle("-fx-alignment: CENTER;");

        colTeacherName.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getFormattedTeacherName()));
        colTeacherName.setStyle("-fx-alignment: CENTER;");

        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("teacherPhoneNumber"));
        colPhoneNumber.setStyle("-fx-alignment: CENTER;");

        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button();
            private final Button deleteButton = new Button();

            {
                Image editImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/edit.png")));
                ImageView editImageView = new ImageView(editImage);
                editImageView.setFitHeight(16);
                editImageView.setFitWidth(16);
                editButton.setGraphic(editImageView);
                editButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-color: #A3B5ED; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                editButton.setOnAction(event -> {
                    SubjectClass SubjectClassToEdit = getTableView().getItems().get(getIndex());
                    openFormEditSubjectClass(SubjectClassToEdit);
                });

                Image deleteImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/cross.png")));
                ImageView deleteImageView = new ImageView(deleteImage);
                deleteImageView.setFitHeight(16);
                deleteImageView.setFitWidth(16);
                deleteButton.setGraphic(deleteImageView);
                deleteButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-color: #A3B5ED; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                deleteButton.setOnAction(event -> {
                    SubjectClass subjectClassToDelete = getTableView().getItems().get(getIndex());

                    int classSubjectID = subjectClassToDelete.getClassSubjectID();

                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Delete Confirmation");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to delete this subject detail ID: " + classSubjectID + "?");

                    Optional<ButtonType> result = confirmationAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        deleteClassSubject(subjectClassToDelete);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionButtons = new HBox(10, editButton, deleteButton);
                    actionButtons.setStyle("-fx-alignment: CENTER;");
                    setGraphic(actionButtons);
                }
            }
        });

        colAction.setStyle("-fx-alignment: CENTER;");
    }


    private void loadClassSubjects() {
        SubjectClassModel subjectClassModel = new SubjectClassModel();
        List<SubjectClass> subjectClassList = subjectClassModel.getAllClassSubject();

        ObservableList<SubjectClass> observableSubjectClassList = FXCollections.observableArrayList(subjectClassList);
        tableViewClassSubject.setItems(observableSubjectClassList);
    }

}
