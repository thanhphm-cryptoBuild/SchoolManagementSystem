package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Classes;
import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.model.ClassModel;
import com.app.schoolmanagementsystem.model.StaffModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClassesController implements Initializable {

    @FXML
    private TextField classNo;

    @FXML
    private DatePicker completePicker;

    @FXML
    private DatePicker enrollmentPicker;

    @FXML
    private StackPane pageClass;

    @FXML
    private TextField sectionClass;

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

    @FXML
    void addFormClass(MouseEvent event) {
        String className = classNo.getText();
        String section = sectionClass.getText();
        LocalDate enrollmentDate = enrollmentPicker.getValue();
        LocalDate completeDate = completePicker.getValue();

        if (selectTeacherID.getValue() == null) {
            System.out.println("No teacher ID selected.");
            return;
        }

        int staffID = selectTeacherID.getValue();

        ClassModel newClass = new ClassModel(0, className, section, staffID, java.sql.Date.valueOf(enrollmentDate), java.sql.Date.valueOf(completeDate));

        Classes classes = new Classes(0, className, section, staffID, enrollmentDate, completeDate);
        boolean isSaved = classes.saveClass(newClass);

        if (isSaved) {
            resetFormClass(null);
        }
    }

    @FXML
    void resetFormClass(MouseEvent event) {
        classNo.clear();
        sectionClass.clear();
        enrollmentPicker.setValue(null);
        completePicker.setValue(null);
        selectTeacherID.getSelectionModel().clearSelection();
        selectTeacherName.getSelectionModel().clearSelection();

        validateClass.setText("");
        validateEndDate.setText("");
        validateID.setText("");
        validateName.setText("");
        validateSection.setText("");
        validateStartDate.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        ObservableList<Staff> observableStaffList = FXCollections.observableArrayList(uniqueStaffList);

        selectTeacherName.setItems(observableStaffList);

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
