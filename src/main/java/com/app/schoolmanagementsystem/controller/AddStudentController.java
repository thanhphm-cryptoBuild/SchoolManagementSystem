package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.model.ClassModel;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
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
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddStudentController implements Initializable {

    @FXML
    private StackPane formAddStudent;

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

    private String avatarPath = "useravatar.png"; // Default avatar

    public void setPageStudent(StackPane pageStudent) {
        this.pageStudent = pageStudent;
    }

    public void setBGPageStudent(AnchorPane moveBG) {
        this.moveBG = moveBG;
    }

    @FXML
    void closeFormAddStudent(MouseEvent event) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(formAddStudent);
        translateTransition.setToX(2000);

        translateTransition.play();

        translateTransition.setOnFinished(actionEvent -> {
            pageStudent.getChildren().remove(formAddStudent);
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
        File selectedFile = fileChooser.showOpenDialog(formAddStudent.getScene().getWindow());
        if (selectedFile != null) {
            // Validate the image before setting it
            if (isValidImage(selectedFile)) {
                // Set the image directly without resizing
                Image avatarImage = new Image(selectedFile.toURI().toString());
                avatarImageView.setImage(avatarImage);
                avatarPath = selectedFile.toURI().toString();
            } else {
                // Show error message if the image is not valid
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Image");
                alert.setHeaderText(null);
                alert.setContentText("Your image must be 2x3, 3x4 or 4x6 ratio.");
                alert.showAndWait();
            }
        }
    }

    // Update this method to validate the image
    private boolean isValidImage(File file) {
        // Check if the image dimensions are approximately 2x3, 3x4, or 4x6 (width:height ratios)
        try {
            Image image = new Image(file.toURI().toString());
            double aspectRatio = image.getWidth() / image.getHeight();
            return (aspectRatio >= 0.67 && aspectRatio <= 0.75) || // Acceptable range for 2x3
                   (aspectRatio >= 0.75 && aspectRatio <= 0.85) || // Acceptable range for 3x4
                   (aspectRatio >= 0.5 && aspectRatio <= 0.75);   // Acceptable range for 4x6
        } catch (Exception e) {
            return false; // Return false if any error occurs
        }
    }

    @FXML
    void addStudent(MouseEvent event) {
        // Reset error labels
        lastNameErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
        phoneNumberErrorLabel.setVisible(false);
        firstNameErrorLabel.setVisible(false);
        dobErrorLabel.setVisible(false);
        genderErrorLabel.setVisible(false);
        addressErrorLabel.setVisible(false);
        classNameErrorLabel.setVisible(false);
        enrollmentDateErrorLabel.setVisible(false);
        fatherNameErrorLabel.setVisible(false);
        fatherPhoneNumberErrorLabel.setVisible(false);
        motherNameErrorLabel.setVisible(false);
        motherPhoneNumberErrorLabel.setVisible(false);
        previousSchoolErrorLabel.setVisible(false);
        reasonForLeavingErrorLabel.setVisible(false);

        boolean hasError = false;

        // Validate fields
        if (lastNameField.getText().isEmpty() || !Character.isUpperCase(lastNameField.getText().charAt(0))) {
            lastNameErrorLabel.setText("Last name must start with an uppercase letter");
            lastNameErrorLabel.setVisible(true);
            hasError = true;
        }
        if (firstNameField.getText().isEmpty() || !Character.isUpperCase(firstNameField.getText().charAt(0))) {
            firstNameErrorLabel.setText("First name must start with an uppercase letter");
            firstNameErrorLabel.setVisible(true);
            hasError = true;
        }
        if (!isValidEmail(emailField.getText())) {
            emailErrorLabel.setText("Invalid email format");
            emailErrorLabel.setVisible(true);
            hasError = true;
        }
        if (!isValidPhoneNumber(phoneNumberField.getText())) {
            phoneNumberErrorLabel.setText("Invalid phone number");
            phoneNumberErrorLabel.setVisible(true);
            hasError = true;
        }
        if (dobField.getValue() == null || !isValidDOB(dobField.getValue())) {
            dobErrorLabel.setText("Invalid date of birth");
            dobErrorLabel.setVisible(true);
            hasError = true;
        }
        if (enrollmentDateField.getValue() == null) {
            enrollmentDateErrorLabel.setText("Enrollment date is required");
            enrollmentDateErrorLabel.setVisible(true);
            hasError = true;
        }
        if (addressField.getText().isEmpty()) {
            addressErrorLabel.setText("Address is required");
            addressErrorLabel.setVisible(true);
            hasError = true;
        }
        if (fatherNameField.getText().isEmpty() && motherNameField.getText().isEmpty()) {
            fatherNameErrorLabel.setText("Either father name or mother name is required");
            fatherNameErrorLabel.setVisible(true);
            motherNameErrorLabel.setText("Either father name or mother name is required");
            motherNameErrorLabel.setVisible(true);
            hasError = true;
        }
        if (!fatherNameField.getText().isEmpty() && fatherPhoneNumberField.getText().isEmpty()) {
            fatherPhoneNumberErrorLabel.setText("Father's phone number is required if father name is provided");
            fatherPhoneNumberErrorLabel.setVisible(true);
            hasError = true;
        }
        if (!motherNameField.getText().isEmpty() && motherPhoneNumberField.getText().isEmpty()) {
            motherPhoneNumberErrorLabel.setText("Mother's phone number is required if mother name is provided");
            motherPhoneNumberErrorLabel.setVisible(true);
            hasError = true;
        }
        if (genderField.getValue() == null) {
            genderErrorLabel.setText("Gender is required");
            genderErrorLabel.setVisible(true);
            hasError = true;
        }
        if (classNameField.getValue() == null) {
            classNameErrorLabel.setText("Class is required");
            classNameErrorLabel.setVisible(true);
            hasError = true;
        }

        if (!hasError) {
            // Check for duplicate email
            if (isEmailExists(emailField.getText())) {
                emailErrorLabel.setText("Email already exists");
                emailErrorLabel.setVisible(true);
                hasError = true;
            }

            if (!hasError) {
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

                String status = "active"; // Default status

                // Kết nối và lưu dữ liệu vào database
                try (Connection connection = ConnectDB.getConnection()) {
                    // Lấy ID tiếp theo cho sinh viên
                    String getMaxStudentIdQuery = "SELECT COALESCE(MAX(StudentID), 0) + 1 AS nextID FROM students";
                    PreparedStatement getMaxStudentIdStatement = connection.prepareStatement(getMaxStudentIdQuery);
                    ResultSet studentResultSet = getMaxStudentIdStatement.executeQuery();
                    int nextStudentID = 0;
                    if (studentResultSet.next()) {
                        nextStudentID = studentResultSet.getInt("nextID");
                    }

                    // Lưu thông tin sinh viên
                    String studentQuery = "INSERT INTO students (StudentID, lastName, firstName, email, dateOfBirth, phoneNumber, gender, address, classID, enrollmentDate, previousSchool, reasonForLeaving, status, avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement studentPreparedStatement = connection.prepareStatement(studentQuery);
                    studentPreparedStatement.setInt(1, nextStudentID);
                    studentPreparedStatement.setString(2, lastName);
                    studentPreparedStatement.setString(3, firstName);
                    studentPreparedStatement.setString(4, email);
                    studentPreparedStatement.setString(5, dob);
                    studentPreparedStatement.setString(6, phoneNumber);
                    studentPreparedStatement.setBoolean(7, gender.equals("Male"));
                    studentPreparedStatement.setString(8, address);
                    studentPreparedStatement.setInt(9, selectedClass.getClassID());
                    studentPreparedStatement.setString(10, enrollmentDate);
                    studentPreparedStatement.setString(11, previousSchool);
                    studentPreparedStatement.setString(12, reasonForLeaving);
                    studentPreparedStatement.setString(13, status);
                    studentPreparedStatement.setString(14, avatarPath);

                    studentPreparedStatement.executeUpdate();

                    // Lấy ID tiếp theo cho gia đình
                    String getMaxFamilyIdQuery = "SELECT COALESCE(MAX(FamilyID), 0) + 1 AS nextID FROM studentfamily";
                    PreparedStatement getMaxFamilyIdStatement = connection.prepareStatement(getMaxFamilyIdQuery);
                    ResultSet familyResultSet = getMaxFamilyIdStatement.executeQuery();
                    int nextFamilyID = 0;
                    if (familyResultSet.next()) {
                        nextFamilyID = familyResultSet.getInt("nextID");
                    }

                    // Lưu thông tin gia đình
                    String familyQuery = "INSERT INTO studentfamily (FamilyID, StudentID, FatherName, FatherPhoneNumber, MotherName, MotherPhoneNumber) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement familyPreparedStatement = connection.prepareStatement(familyQuery);
                    familyPreparedStatement.setInt(1, nextFamilyID);
                    familyPreparedStatement.setInt(2, nextStudentID);
                    familyPreparedStatement.setString(3, fatherName);
                    familyPreparedStatement.setString(4, fatherPhoneNumber);
                    familyPreparedStatement.setString(5, motherName);
                    familyPreparedStatement.setString(6, motherPhoneNumber);

                    familyPreparedStatement.executeUpdate();

                    // Hiển thị thông báo thành công
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Student added successfully!");
                    alert.showAndWait();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isEmailExists(String email) {
        try (Connection connection = ConnectDB.getConnection()) {
            String query = "SELECT COUNT(*) FROM students WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Return true if email exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if no error occurs and email does not exist
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
        avatarImageView.setImage(new Image("useravatar.png"));
        avatarPath = "useravatar.png";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderField.getItems().addAll("Male", "Female");
        loadClassNames();
    }

    private void loadClassNames() {
        try (Connection connection = ConnectDB.getConnection()) {
            String query = "SELECT classID, className, section, staffID FROM classes";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int classID = resultSet.getInt("classID");
                String className = resultSet.getString("className");
                String section = resultSet.getString("section");
                int staffID = resultSet.getInt("staffID");
                classNameField.getItems().add(new ClassModel(classID, className, section, staffID));
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
        Period period = Period.between(dob, today);
        return period.getYears() >= 12;
    }
}
