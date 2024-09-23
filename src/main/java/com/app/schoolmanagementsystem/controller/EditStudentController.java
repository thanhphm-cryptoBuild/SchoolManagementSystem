package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.model.ClassModel;
import com.app.schoolmanagementsystem.model.StudentFamilyModel;
import com.app.schoolmanagementsystem.model.StudentModel;
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
import javafx.util.StringConverter;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
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
    private ChoiceBox<String> classNameField;
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
    @FXML
    private ChoiceBox<String> academicYearField;
    @FXML
    private Label academicYearErrorLabel;

    @FXML
    private Label studentIdValueLabel; // New Label to display Student ID

    private String avatarPath = "useravatar.png"; // Default avatar
    private StudentModel student;
    private StudentFamilyModel studentFamily;
    private Map<String, Map<String, ClassModel>> classMap = new HashMap<>();

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
        dobField.setValue(student.getDateOfBirth() != null ? convertToLocalDateViaSqlDate(student.getDateOfBirth()) : null);
        genderField.setValue(student.getGender() ? "Male" : "Female");
        addressField.setText(student.getAddress());

        ClassModel studentClass = getClassModelById(student.getClassID());
        classNameField.setValue(studentClass.getClassName() + studentClass.getSection()); // Chọn lớp hiện tại
        updateAcademicYear(studentClass.getClassName() + studentClass.getSection());

        enrollmentDateField.setValue(student.getEnrollmentDate() != null ? convertToLocalDateViaSqlDate(student.getEnrollmentDate()) : null);
        previousSchoolField.setText(student.getPreviousSchool());
        reasonForLeavingField.setText(student.getReasonForLeaving());
        avatarPath = student.getAvatar();

        // Kiểm tra và đặt hình ảnh đại diện
        try {
            Image avatarImage = new Image(avatarPath);
            avatarImageView.setImage(avatarImage);
        } catch (IllegalArgumentException e) {
            // Nếu không tìm thấy hình ảnh, sử dụng hình ảnh mặc định
            avatarImageView.setImage(new Image("file:src/main/resources/com/app/schoolmanagementsystem/images/useravatar.png"));
        }

        // Lấy thông tin gia đình từ cơ sở dữ liệu
        loadStudentFamilyData(student.getStudentID());

        // Set Student ID
        studentIdValueLabel.setText(String.valueOf(student.getStudentID())); // Set the student ID
    }

    private LocalDate convertToLocalDateViaSqlDate(java.util.Date dateToConvert) {
        return dateToConvert != null ? new java.sql.Date(dateToConvert.getTime()).toLocalDate() : null;
    }

    private void loadStudentFamilyData(int studentID) {
        String query = "SELECT * FROM studentfamily WHERE StudentID = ?";
        try (Connection connection = ConnectDB.connection();
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
        String query = "SELECT * FROM classes WHERE classID = ?";
        try (Connection connection = ConnectDB.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, classID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String className = resultSet.getString("className");
                String section = resultSet.getString("section");
                LocalDate enrollmentDate = resultSet.getDate("enrollmentDate").toLocalDate();
                LocalDate completeDate = resultSet.getDate("completeDate").toLocalDate();
                return new ClassModel(classID, className, section, enrollmentDate, completeDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy
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
                alert.setContentText("Your image must be 2x3, 3x4, or 4x6 ratio.");
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
    private void updateStudent(MouseEvent event) {
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
        fatherNameErrorLabel.setVisible(false); // Reset father name error label
        fatherPhoneNumberErrorLabel.setVisible(false); // Reset father phone number error label
        motherNameErrorLabel.setVisible(false); // Reset mother name error label
        motherPhoneNumberErrorLabel.setVisible(false); // Reset mother phone number error label
    
        boolean hasError = false;
    
        // Validate fields
        if (lastNameField.getText() == null || lastNameField.getText().isEmpty() || !Character.isUpperCase(lastNameField.getText().charAt(0))) {
            lastNameErrorLabel.setText("Last name must start with an uppercase letter");
            lastNameErrorLabel.setVisible(true);
            hasError = true;
        }
        if (firstNameField.getText() == null || firstNameField.getText().isEmpty() || !Character.isUpperCase(firstNameField.getText().charAt(0))) {
            firstNameErrorLabel.setText("First name must start with an uppercase letter");
            firstNameErrorLabel.setVisible(true);
            hasError = true;
        }
        if (!isValidEmail(emailField.getText())) {
            emailErrorLabel.setText("Invalid email format");
            emailErrorLabel.setVisible(true);
            hasError = true;
        } else if (isEmailExists(emailField.getText(), student.getStudentID())) { // Check for duplicate email
            emailErrorLabel.setText("Email already exists");
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
        if (addressField.getText() == null || addressField.getText().isEmpty()) {
            addressErrorLabel.setText("Address is required");
            addressErrorLabel.setVisible(true);
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
    
        // Validate father and mother fields
        if (fatherNameField.getText() != null && !fatherNameField.getText().isEmpty()) {
            if (!Character.isUpperCase(fatherNameField.getText().charAt(0))) {
                fatherNameErrorLabel.setText("Father's name must start with an uppercase letter");
                fatherNameErrorLabel.setVisible(true);
                hasError = true;
            }
            if (fatherPhoneNumberField.getText() == null || fatherPhoneNumberField.getText().isEmpty()) {
                fatherPhoneNumberErrorLabel.setText("Father's phone number is required");
                fatherPhoneNumberErrorLabel.setVisible(true);
                hasError = true;
            } else if (!isValidPhoneNumber(fatherPhoneNumberField.getText())) {
                fatherPhoneNumberErrorLabel.setText("Invalid father's phone number");
                fatherPhoneNumberErrorLabel.setVisible(true);
                hasError = true;
            }
        }
    
        if (motherNameField.getText() != null && !motherNameField.getText().isEmpty()) {
            if (!Character.isUpperCase(motherNameField.getText().charAt(0))) {
                motherNameErrorLabel.setText("Mother's name must start with an uppercase letter");
                motherNameErrorLabel.setVisible(true);
                hasError = true;
            }
            if (motherPhoneNumberField.getText() == null || motherPhoneNumberField.getText().isEmpty()) {
                motherPhoneNumberErrorLabel.setText("Mother's phone number is required");
                motherPhoneNumberErrorLabel.setVisible(true);
                hasError = true;
            } else if (!isValidPhoneNumber(motherPhoneNumberField.getText())) {
                motherPhoneNumberErrorLabel.setText("Invalid mother's phone number");
                motherPhoneNumberErrorLabel.setVisible(true);
                hasError = true;
            }
        }
    
        // Ensure at least one of the parent names is filled
        if ((fatherNameField.getText() == null || fatherNameField.getText().isEmpty()) && 
            (motherNameField.getText() == null || motherNameField.getText().isEmpty())) {
            fatherNameErrorLabel.setText("At least one parent name is required");
            fatherNameErrorLabel.setVisible(true);
            motherNameErrorLabel.setText("At least one parent name is required");
            motherNameErrorLabel.setVisible(true);
            hasError = true;
        }
    
        if (!hasError) {
            // Proceed with updating the student
            String lastName = lastNameField.getText();
            String firstName = firstNameField.getText();
            String email = emailField.getText();
            String dob = dobField.getValue() != null ? dobField.getValue().toString() : null;
            String phoneNumber = phoneNumberField.getText();
            String gender = genderField.getValue();
            String address = addressField.getText();
            String selectedClassName = classNameField.getValue();
            String selectedAcademicYear = academicYearField.getValue();
            ClassModel selectedClass = classMap.get(selectedClassName).get(selectedAcademicYear);
            String enrollmentDate = enrollmentDateField.getValue() != null ? enrollmentDateField.getValue().toString() : null;
            String fatherName = fatherNameField.getText();
            String fatherPhoneNumber = fatherPhoneNumberField.getText();
            String motherName = motherNameField.getText();
            String motherPhoneNumber = motherPhoneNumberField.getText();
            String previousSchool = previousSchoolField.getText();
            String reasonForLeaving = reasonForLeavingField.getText();
    
            // Connect and update data in the database
            try (Connection connection = ConnectDB.connection()) {
                // Update student information
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
    
                // Update student family information
                String familyQuery = "UPDATE studentfamily SET FatherName = ?, FatherPhoneNumber = ?, MotherName = ?, MotherPhoneNumber = ? WHERE StudentID = ?";
                PreparedStatement familyPreparedStatement = connection.prepareStatement(familyQuery);
                familyPreparedStatement.setString(1, fatherName.isEmpty() ? null : fatherName);
                familyPreparedStatement.setString(2, fatherPhoneNumber.isEmpty() ? null : fatherPhoneNumber);
                familyPreparedStatement.setString(3, motherName.isEmpty() ? null : motherName);
                familyPreparedStatement.setString(4, motherPhoneNumber.isEmpty() ? null : motherPhoneNumber);
                familyPreparedStatement.setInt(5, student.getStudentID());
    
                familyPreparedStatement.executeUpdate();
    
                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Student updated successfully!");
                alert.showAndWait();
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isEmailExists(String email, int currentStudentId) {
        try (Connection connection = ConnectDB.connection()) {
            String query = "SELECT COUNT(*) FROM students WHERE email = ? AND StudentID != ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, currentStudentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Return true if email exists for another student
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
        academicYearField.setValue(null);
        enrollmentDateField.setValue(null);
        fatherNameField.clear();
        fatherPhoneNumberField.clear();
        motherNameField.clear();
        motherPhoneNumberField.clear();
        previousSchoolField.clear();
        reasonForLeavingField.clear();
        avatarImageView.setImage(new Image("file:src/main/resources/com/app/schoolmanagementsystem/images/useravatar.png"));
        avatarPath = "file:src/main/resources/com/app/schoolmanagementsystem/images/useravatar.png";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderField.getItems().addAll("Male", "Female");
        loadClassNames();

        // Thiết lập StringConverter cho classNameField
        classNameField.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String className) {
                return className;
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        });

        // Set DatePicker constraints for Date of Birth
        setDatePickerConstraints();

        // Add listener to classNameField to update academic year
        classNameField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateAcademicYear(newValue);
            }
        });
    }

    private void setDatePickerConstraints() {
        // Define the maximum selectable date as December 31, 2010
        final LocalDate MAX_DATE = LocalDate.of(2011, 1, 1).minusDays(1);

        // Set the default date to January 1, 2010
        dobField.setValue(LocalDate.of(2010, 1, 1));

        // Apply DayCellFactory to disable dates after MAX_DATE
        dobField.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isAfter(MAX_DATE)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Optional: Highlight disabled dates
                }
            }
        });

        // Optionally, set the maximum date to MAX_DATE to prevent manual entry
        dobField.setEditable(false);
    }

    private void loadClassNames() {
        try (Connection connection = ConnectDB.connection()) {
            String query = "SELECT classID, className, section, enrollmentDate, completeDate FROM classes";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int classID = resultSet.getInt("classID");
                String className = resultSet.getString("className");
                String section = resultSet.getString("section");
                LocalDate enrollmentDate = resultSet.getDate("enrollmentDate").toLocalDate();
                LocalDate completeDate = resultSet.getDate("completeDate").toLocalDate();
                String displayName = className + section;
                String academicYear = enrollmentDate.getYear() + " - " + completeDate.getYear();

                // Add to classMap if not already present
                classMap.putIfAbsent(displayName, new HashMap<>());
                classMap.get(displayName).put(academicYear, new ClassModel(classID, className, section, enrollmentDate, completeDate));
                
                // Add to classNameField if not already present
                if (!classNameField.getItems().contains(displayName)) {
                    classNameField.getItems().add(displayName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateAcademicYear(String className) {
        // Clear previous items
        academicYearField.getItems().clear();

        // Collect all academic years for the selected class name
        Map<String, ClassModel> classModels = classMap.get(className);
        if (classModels != null) {
            for (String academicYear : classModels.keySet()) {
                academicYearField.getItems().add(academicYear);
            }
        }

        // Set the first academic year as the default value
        if (!academicYearField.getItems().isEmpty()) {
            academicYearField.setValue(academicYearField.getItems().get(0));
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