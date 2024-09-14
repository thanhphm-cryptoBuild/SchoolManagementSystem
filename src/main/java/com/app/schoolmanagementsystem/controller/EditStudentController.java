package com.app.schoolmanagementsystem.controller;
import com.app.schoolmanagementsystem.model.StudentFamilyModel;

import com.app.schoolmanagementsystem.model.ClassModel;
import com.app.schoolmanagementsystem.model.StudentModel;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
public class EditStudentController implements Initializable {

    @FXML
    private StackPane formEditStudent;

    @FXML
    private StackPane pageStudent;

    @FXML
    private AnchorPane moveBG;

    @FXML
    private TextField lastNameField;
    @FXML
    private Label lastNameErrorLabel;
    @FXML
    private TextField emailField;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private Label phoneNumberErrorLabel;
    @FXML
    private TextField firstNameField;
    @FXML
    private Label firstNameErrorLabel;
    @FXML
    private DatePicker dobField;
    @FXML
    private Label dobErrorLabel;
    @FXML
    private ChoiceBox<String> genderField;
    @FXML
    private Label genderErrorLabel;
    @FXML
    private TextField addressField;
    @FXML
    private Label addressErrorLabel;
    @FXML
    private ChoiceBox<ClassModel> classNameField;
    @FXML
    private Label classNameErrorLabel;
    @FXML
    private DatePicker enrollmentDateField;
    @FXML
    private Label enrollmentDateErrorLabel;
    @FXML
    private TextField fatherNameField;
    @FXML
    private Label fatherNameErrorLabel;
    @FXML
    private TextField fatherPhoneNumberField;
    @FXML
    private Label fatherPhoneNumberErrorLabel;
    @FXML
    private TextField motherNameField;
    @FXML
    private Label motherNameErrorLabel;
    @FXML
    private TextField motherPhoneNumberField;
    @FXML
    private Label motherPhoneNumberErrorLabel;
    @FXML
    private TextField previousSchoolField;
    @FXML
    private Label previousSchoolErrorLabel;
    @FXML
    private TextField reasonForLeavingField;
    @FXML
    private Label reasonForLeavingErrorLabel;
    @FXML
    private ImageView avatarImageView;

    private String avatarPath = "default_avatar.png"; // Default avatar
    private StudentModel student;
    private StudentFamilyModel studentFamily;

    public void setPageStudent(StackPane pageStudent) {
        this.pageStudent = pageStudent;
    }

    public void setBGPageStudent(AnchorPane moveBG) {
        this.moveBG = moveBG;
    }

    public void setStudentData(StudentModel student) {
        this.student = student;
        lastNameField.setText(student.getLastName());
        firstNameField.setText(student.getFirstName());
        emailField.setText(student.getEmail());
        phoneNumberField.setText(student.getPhoneNumber());
        dobField.setValue(student.getDateOfBirth() != null ? convertToLocalDateViaSqlDate(student.getDateOfBirth()) : null); // Chuyển đổi java.sql.Date sang java.time.LocalDate
        genderField.setValue(student.getGender() ? "Male" : "Female");
        addressField.setText(student.getAddress());
        classNameField.setValue(getClassModelById(student.getClassID()));
        enrollmentDateField.setValue(student.getEnrollmentDate() != null ? convertToLocalDateViaSqlDate(student.getEnrollmentDate()) : null); // Chuyển đổi java.sql.Date sang java.time.LocalDate
        previousSchoolField.setText(student.getPreviousSchool());
        reasonForLeavingField.setText(student.getReasonForLeaving());
        avatarPath = student.getAvatar();
        
        // Kiểm tra và đặt hình ảnh đại diện
        try {
            Image avatarImage = new Image(avatarPath);
            avatarImageView.setImage(avatarImage);
        } catch (IllegalArgumentException e) {
            // Nếu không tìm thấy hình ảnh, sử dụng hình ảnh mặc định
            avatarImageView.setImage(new Image("file:src/main/resources/com/app/schoolmanagementsystem/images/default_avatar.png"));
        }

        // Lấy thông tin gia đình từ cơ sở dữ liệu
        loadStudentFamilyData(student.getStudentID());
    }

    private LocalDate convertToLocalDateViaSqlDate(java.util.Date dateToConvert) {
        return dateToConvert != null ? new java.sql.Date(dateToConvert.getTime()).toLocalDate() : null;
    }

    private void loadStudentFamilyData(int studentID) {
        String query = "SELECT * FROM studentfamily WHERE StudentID = ?";
        try (Connection connection = ConnectDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                studentFamily = new StudentFamilyModel(
                        resultSet.getInt("FamilyID"),
                        resultSet.getInt("StudentID"),
                        resultSet.getString("FatherName"),
                        resultSet.getString("FatherPhoneNumber"),
                        resultSet.getString("MotherName"),
                        resultSet.getString("MotherPhoneNumber")
                );
                fatherNameField.setText(studentFamily.getFatherName());
                fatherPhoneNumberField.setText(studentFamily.getFatherPhoneNumber());
                motherNameField.setText(studentFamily.getMotherName());
                motherPhoneNumberField.setText(studentFamily.getMotherPhoneNumber());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ClassModel getClassModelById(int classID) {
        // Implement method to get ClassModel by classID
        // This is a placeholder implementation
        return new ClassModel(classID, "ClassName", 2023, "A", 1);
    }

    @FXML
    void closeFormEditStudent(MouseEvent event) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(formEditStudent);
        translateTransition.setToX(2000);

        translateTransition.play();

        translateTransition.setOnFinished(actionEvent -> {
            pageStudent.getChildren().remove(formEditStudent);
            moveBG.setEffect(null);
        });
    }

    @FXML
    void chooseFile(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Avatar Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(formEditStudent.getScene().getWindow());
        if (selectedFile != null) {
            avatarPath = selectedFile.toURI().toString();
            Image avatarImage = new Image(avatarPath);
            avatarImageView.setImage(avatarImage);
        }
    }

    @FXML
    private void updateStudent(MouseEvent event) {
        String lastName = lastNameField.getText();
        String firstName = firstNameField.getText();
        String email = emailField.getText();
        String dob = dobField.getValue() != null ? dobField.getValue().toString() : null;
        String phoneNumber = phoneNumberField.getText();
        String gender = genderField.getValue();
        String address = addressField.getText();
        ClassModel selectedClass = classNameField.getValue();
        String enrollmentDate = enrollmentDateField.getValue() != null ? enrollmentDateField.getValue().toString() : null;
        String fatherName = fatherNameField.getText();
        String fatherPhoneNumber = fatherPhoneNumberField.getText();
        String motherName = motherNameField.getText();
        String motherPhoneNumber = motherPhoneNumberField.getText();
        String previousSchool = previousSchoolField.getText();
        String reasonForLeaving = reasonForLeavingField.getText();

        // Kết nối và cập nhật dữ liệu vào database
        try (Connection connection = ConnectDB.getConnection()) {
            // Cập nhật thông tin sinh viên
            String studentQuery = "UPDATE students SET lastName = ?, firstName = ?, email = ?, dateOfBirth = ?, phoneNumber = ?, gender = ?, address = ?, classID = ?, enrollmentDate = ?, previousSchool = ?, reasonForLeaving = ?, avatar = ? WHERE StudentID = ?";
            PreparedStatement studentPreparedStatement = connection.prepareStatement(studentQuery);
            studentPreparedStatement.setString(1, lastName);
            studentPreparedStatement.setString(2, firstName);
            studentPreparedStatement.setString(3, email);
            studentPreparedStatement.setString(4, dob);
            studentPreparedStatement.setString(5, phoneNumber);
            studentPreparedStatement.setBoolean(6, gender.equals("Male"));
            studentPreparedStatement.setString(7, address);
            studentPreparedStatement.setInt(8, selectedClass.getClassID());
            studentPreparedStatement.setString(9, enrollmentDate);
            studentPreparedStatement.setString(10, previousSchool);
            studentPreparedStatement.setString(11, reasonForLeaving);
            studentPreparedStatement.setString(12, avatarPath);
            studentPreparedStatement.setInt(13, student.getStudentID());

            studentPreparedStatement.executeUpdate();

            // Cập nhật thông tin gia đình
            String familyQuery = "UPDATE studentfamily SET FatherName = ?, FatherPhoneNumber = ?, MotherName = ?, MotherPhoneNumber = ? WHERE StudentID = ?";
            PreparedStatement familyPreparedStatement = connection.prepareStatement(familyQuery);
            familyPreparedStatement.setString(1, fatherName);
            familyPreparedStatement.setString(2, fatherPhoneNumber);
            familyPreparedStatement.setString(3, motherName);
            familyPreparedStatement.setString(4, motherPhoneNumber);
            familyPreparedStatement.setInt(5, student.getStudentID());

            familyPreparedStatement.executeUpdate();

            // Hiển thị thông báo thành công
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Student updated successfully!");
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancel(MouseEvent event) {
        lastNameField.clear();
        emailField.clear();
        phoneNumberField.clear();
        firstNameField.clear();
        dobField.setValue(null);
        genderField.setValue(null);
        addressField.clear();
        classNameField.setValue(null);
        enrollmentDateField.setValue(null);
        fatherNameField.clear();
        fatherPhoneNumberField.clear();
        motherNameField.clear();
        motherPhoneNumberField.clear();
        previousSchoolField.clear();
        reasonForLeavingField.clear();
        avatarImageView.setImage(new Image("file:src/main/resources/com/app/schoolmanagementsystem/images/default_avatar.png"));
        avatarPath = "file:src/main/resources/com/app/schoolmanagementsystem/images/default_avatar.png";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderField.getItems().addAll("Male", "Female");
        loadClassNames();
    }

    private void loadClassNames() {
        try (Connection connection = ConnectDB.getConnection()) {
            String query = "SELECT classID, className, year, section, teacherID FROM classes";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int classID = resultSet.getInt("classID");
                String className = resultSet.getString("className");
                int year = resultSet.getInt("year");
                String section = resultSet.getString("section");
                int teacherID = resultSet.getInt("teacherID");
                classNameField.getItems().add(new ClassModel(classID, className, year, section, teacherID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "\\d{10}";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phoneNumber).matches();
    }

    private boolean isValidDOB(LocalDate dob) {
        LocalDate today = LocalDate.now();
        return Period.between(dob, today).getYears() >= 12;
    }
}