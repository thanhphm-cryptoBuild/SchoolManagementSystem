package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Classes;
import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.entities.Subject;
import com.app.schoolmanagementsystem.entities.SubjectClass;
import com.app.schoolmanagementsystem.model.ClassModel;
import com.app.schoolmanagementsystem.model.StaffModel;
import com.app.schoolmanagementsystem.model.SubjectClassModel;
import com.app.schoolmanagementsystem.model.SubjectModel;
import com.app.schoolmanagementsystem.services.AuthService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

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
        hideActionSubject();
        hideformAddSubjectClass();
    }

    @FXML
    void addFormSubject(MouseEvent event) {
        ClassModel selectedClass = selectClassName.getValue();
        Subject selectedSubject = selectSubject.getValue();
        int selectedTeacherID = selectTeacherID.getValue();

        if (selectedClass != null && selectedSubject != null && selectedTeacherID != -1) {
            int classID = selectedClass.getClassID();
            int subjectID = selectedSubject.getSubjectID();

            SubjectClassModel subjectClassModel = new SubjectClassModel();
            subjectClassModel.addClassSubject(classID, subjectID, selectedTeacherID);

            loadClassSubjects();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Subject detail added successfully.");
            resetFormSubject(event);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add subject detail.");
        }
    }


    @FXML
    void updateFormEditSubject(MouseEvent event) {
        try {
            int classSubjectID = Integer.parseInt(showEditClassSubjectID.getText());
            ClassModel selectedClass = showEditClassName.getValue();
            Subject selectedSubject = showEditSubject.getValue();
            int selectedTeacherID = showEditTeacherID.getValue();

            if (selectedClass != null && selectedSubject != null && selectedTeacherID != -1) {
                int classID = selectedClass.getClassID();
                int subjectID = selectedSubject.getSubjectID();

                SubjectClassModel subjectClassModel = new SubjectClassModel();
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
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
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

    private void hideformAddSubjectClass() {
        // Kiểm tra vai trò của người dùng hiện tại
        String currentRole = getCurrentRoleName(); // Phương thức này trả về vai trò của người dùng hiện tại

        // Nếu vai trò là "Teacher", ẩn cột action
        if ("Teacher".equals(currentRole)) {
            formAddSubjectClass.setDisable(true); // Ẩn cột hành động nếu là Teacher
        }
    }

    private void hideActionSubject() {
        // Kiểm tra vai trò của người dùng hiện tại
        String currentRole = getCurrentRoleName(); // Phương thức này trả về vai trò của người dùng hiện tại

        // Nếu vai trò là "Teacher", ẩn cột action
        if ("Teacher".equals(currentRole)) {
            colAction.setVisible(false); // Ẩn cột hành động nếu là Teacher
        }
    }

    private AuthService authService = new AuthService(); // Thay thế bằng cách tiêm phụ thuộc nếu cần

    // Phương thức để lấy roleName hiện tại
    public String getCurrentRoleName() {
        return UserSession.getCurrentRoleName(); // Sử dụng UserSession để lấy vai trò
    }

}
