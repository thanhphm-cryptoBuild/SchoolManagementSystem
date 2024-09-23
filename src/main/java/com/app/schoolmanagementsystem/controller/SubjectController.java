package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.entities.Subject;
import com.app.schoolmanagementsystem.entities.SubjectClass;
import com.app.schoolmanagementsystem.model.StaffModel;
import com.app.schoolmanagementsystem.model.SubjectClassModel;
import com.app.schoolmanagementsystem.model.SubjectModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SubjectController implements Initializable {

    @FXML
    private StackPane pageSubject;

    @FXML
    private ChoiceBox<?> selectClassNo;

    @FXML
    private ChoiceBox<?> selectSectionClass;

    @FXML
    private ChoiceBox<Subject> selectSubject;

    @FXML
    private ChoiceBox<Integer> selectTeacherID;

    @FXML
    private ChoiceBox<Staff> selectTeacherName;

    @FXML
    private TextField textDescription;

    private final Staff selectTeacherPlaceholder = new Staff(-1, "", "", "", "", "");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSubjects();
        populateChoiceBoxes();
    }

    @FXML
    void addFormSubject(MouseEvent event) {
    }

    @FXML
    void resetFormSubject(MouseEvent event) {

    }

    private void loadSubjects() {
        SubjectModel subjectModel = new SubjectModel();
        List<Subject> subjects = subjectModel.selectName();

        ObservableList<Subject> observableSubjectList = FXCollections.observableArrayList(subjects);

        selectSubject.setItems(observableSubjectList);
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

}
