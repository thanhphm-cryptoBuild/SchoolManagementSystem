package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.text.SimpleDateFormat;

public class StudentDetailController {

    @FXML private Label idLabel;
    @FXML private Label nameLabel;
    @FXML private Label addressLabel;
    @FXML private Label phoneLabel;
    @FXML private Label emailLabel;
    @FXML private Label birthDateLabel;
    @FXML private Label registrationNoLabel;
    @FXML private Label batchLabel;
    @FXML private Label admissionDateLabel;
    @FXML private Label previousInstituteLabel;
    @FXML private Label reasonForLeavingLabel;
    @FXML private Label guardianContactLabel;
    @FXML private Label classIDLabel;
    @FXML private Label sexLabel;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public void setStudent(Student student) {
        idLabel.setText(String.valueOf(student.getStudentID()));
        nameLabel.setText(student.getFullName());
        addressLabel.setText(student.getAddress());
        phoneLabel.setText(student.getPhone());
        emailLabel.setText(student.getEmail());
        birthDateLabel.setText(student.getBirthDate() != null ? dateFormat.format(student.getBirthDate()) : "N/A");
        registrationNoLabel.setText(student.getRegistrationNo());
        batchLabel.setText(student.getBatch());
        admissionDateLabel.setText(student.getAdmissionDate() != null ? dateFormat.format(student.getAdmissionDate()) : "N/A");
        previousInstituteLabel.setText(student.getPreviousInstitute());
        reasonForLeavingLabel.setText(student.getReasonForLeaving());
        guardianContactLabel.setText(student.getGuardianContact());
        classIDLabel.setText(String.valueOf(student.getClassID()));
        sexLabel.setText(student.getSex());
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) idLabel.getScene().getWindow();
        stage.close();
    }
}