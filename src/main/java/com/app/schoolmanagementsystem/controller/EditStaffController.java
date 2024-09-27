package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.entities.StaffFamily;
import com.app.schoolmanagementsystem.entities.StaffRoles;
import com.app.schoolmanagementsystem.model.StaffModel;
import com.app.schoolmanagementsystem.session.UserSession;
import com.app.schoolmanagementsystem.utils.PasswordUtil;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.app.schoolmanagementsystem.utils.ConnectDB.connection;

public class EditStaffController implements Initializable {

    @FXML
    private StackPane formEditStaff;

    private StackPane pageStaff;

    @FXML
    private AnchorPane moveBG;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private DatePicker dobDatePicker;
    @FXML
    private DatePicker hireDatePicker;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField addressField;
    @FXML
    private ChoiceBox<String> roleChoiceBox;
    @FXML
    private ChoiceBox<String> genderChoiceBox;
    @FXML
    private ChoiceBox<String> salaryChoiceBox;
    @FXML
    private ChoiceBox<String> educationChoiceBox;
    @FXML
    private ChoiceBox<String> experienceChoiceBox;
    @FXML
    private ChoiceBox<String> relationshipChoiceBox1;
    @FXML
    private ChoiceBox<String> relationshipChoiceBox2;
    @FXML
    private ChoiceBox<String> positionNameChoiceBox;

    @FXML
    private TextField familyMemberNameField1;
    @FXML
    private TextField familyMemberNameField2;
    @FXML
    private TextField contactNumberField1;
    @FXML
    private TextField contactNumberField2;

    @FXML
    private Label firstNameErrorLabel;
    @FXML
    private Label lastNameErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label confirmPasswordErrorLabel;
    @FXML
    private Label dobErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label phoneErrorLabel;
    @FXML
    private Label addressErrorLabel;
    @FXML
    private Label hireDateErrorLabel;
    @FXML
    private Label roleErrorLabel;
    @FXML
    private Label genderErrorLabel;
    @FXML
    private Label salaryErrorLabel;
    @FXML
    private Label educationErrorLabel;
    @FXML
    private Label experienceErrorLabel;
    @FXML
    private Label relationship1ErrorLabel;
    @FXML
    private Label relationship2ErrorLabel;
    @FXML
    private Label familyMemberName1ErrorLabel;
    @FXML
    private Label familyMemberName2ErrorLabel;
    @FXML
    private Label contactNumber1ErrorLabel;
    @FXML
    private Label contactNumber2ErrorLabel;
    @FXML
    private Label chooseFileErrorLabel;
    @FXML
    private Label positionNameErrorLabel;

    @FXML
    private Button chooseFileButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label label_StaffID;

    @FXML
    private ImageView profileImageView;

    private String avatarPath = "useravatar.png";

    private StaffModel staffService;

    private Staff currentStaff;

    public void setStaffData(Staff staff) {
        this.currentStaff = staff;
        loadStaffData();
    }

    public EditStaffController() {
        staffService = new StaffModel();
    }

    public void setPageStaff(StackPane pageStaff) {
        this.pageStaff = pageStaff;
    }

    public void setBGPageStaff(AnchorPane moveBG) {
        this.moveBG = moveBG;
    }

    @FXML
    void closeFormEdit(MouseEvent event) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(formEditStaff);
        translateTransition.setToX(2000);

        translateTransition.play();

        translateTransition.setOnFinished(actionEvent -> {
            pageStaff.getChildren().remove(formEditStaff);
            moveBG.setEffect(null);
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadStaffData();
        makeFieldsNonEditable();
    }

    private void loadStaffData() {

        if (currentStaff != null) {
            firstNameField.setText(currentStaff.getFirstName());
            lastNameField.setText(currentStaff.getLastName());
            dobDatePicker.setValue(currentStaff.getDateOfBirth().toLocalDate());
            hireDatePicker.setValue(currentStaff.getHireDate().toLocalDate());
            emailField.setText(currentStaff.getEmail());
            phoneNumberField.setText(currentStaff.getPhoneNumber());
            addressField.setText(currentStaff.getAddress());
            label_StaffID.setText(String.valueOf(currentStaff.getStaffID()));

            String gender = currentStaff.getGender() == 1 ? "Male" : "Female";
            genderChoiceBox.setValue(gender);
            ObservableList<String> genderOptions = FXCollections.observableArrayList("Male", "Female");
            genderChoiceBox.setItems(genderOptions);

            salaryChoiceBox.setValue(String.valueOf(currentStaff.getSalary()));
            ObservableList<String> salaryOptions = FXCollections.observableArrayList(
                    "5000", "6000", "7000", "8000", "other"
            );
            salaryChoiceBox.setItems(salaryOptions);

            educationChoiceBox.setValue(currentStaff.getEducationBackground());
            ObservableList<String> educationOptions = FXCollections.observableArrayList(
                    "Intermediate", "College", "University", "Master's", "Ph.D."
            );
            educationChoiceBox.setItems(educationOptions);

            experienceChoiceBox.setValue(currentStaff.getExperience());
            ObservableList<String> experienceOptions = FXCollections.observableArrayList(
                    "0-1 years", "1-3 years", "3-5 years", "5-10 years", "10+ years"
            );
            experienceChoiceBox.setItems(experienceOptions);

            positionNameChoiceBox.setValue(currentStaff.getPositionName());
            ObservableList<String> positionOptions = FXCollections.observableArrayList(
                    "Admin Master", "Manager", "Biology Teacher", "Computer Science Teacher", "Chemistry Teacher", "English Teacher", "Geography Teacher", "History Teacher", "Mathematics Teacher", "Physical Education Teacher", "Physics Teacher", "Science Teacher"
            );
            positionNameChoiceBox.setItems(positionOptions);

            avatarPath = currentStaff.getAvatar();

            try {
                Image avatarImage = new Image(avatarPath);
                profileImageView.setImage(avatarImage);
            } catch (IllegalArgumentException e) {
                profileImageView.setImage(new Image("file:src/main/resources/com/app/schoolmanagementsystem/images/useravatar.png"));
            }

            StaffModel staffModel = new StaffModel(currentStaff, connection());

            String roleName = staffModel.getRoleName();

            if (roleName != null) {
                roleChoiceBox.setValue(roleName);
            } else {
                roleChoiceBox.setValue("");
            }

            ObservableList<String> roleOptions = FXCollections.observableArrayList("Admin Master", "Manager", "Teacher");
            String currentRole = getCurrentRoleName();

            if ("Manager".equals(currentRole)) {
                roleOptions.clear();
                roleOptions.add("Teacher");
            }

            roleChoiceBox.setItems(roleOptions);

            loadFamilyData(currentStaff.getStaffID());
        }
    }

    public String getCurrentRoleName() {
        return UserSession.getCurrentRoleName();
    }

    private void loadFamilyData(int staffID) {
        StaffModel staffModel = new StaffModel(currentStaff, connection());
        List<StaffFamily> familyMembers = staffModel.getFamilyMembers(staffID);
        ObservableList<String> relationshipOptions = FXCollections.observableArrayList(
                "Father", "Mother", "Sibling", "Spouse", "Child"
        );

        if (familyMembers.size() > 0) {
            StaffFamily member1 = familyMembers.get(0);
            familyMemberNameField1.setText(member1.getFamilyMemberName());
            relationshipChoiceBox1.setValue(member1.getRelationshipType());
            contactNumberField1.setText(member1.getContactNumber());
        }

        if (familyMembers.size() > 1) {
            StaffFamily member2 = familyMembers.get(1);
            familyMemberNameField2.setText(member2.getFamilyMemberName());
            relationshipChoiceBox2.setValue(member2.getRelationshipType());
            contactNumberField2.setText(member2.getContactNumber());
        }

        relationshipChoiceBox1.setItems(relationshipOptions);
        relationshipChoiceBox2.setItems(relationshipOptions);
    }

    private void makeFieldsNonEditable() {
        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        dobDatePicker.setDisable(true);
        hireDatePicker.setDisable(true);
        genderChoiceBox.setDisable(true);
    }

    @FXML
    private void handleUpdateButtonAction() {
        clearErrorMessages();

        int staffId = currentStaff.getStaffID();

        Staff staffFromDatabase = staffService.getStaffByID(staffId);

        String oldAvatar = staffFromDatabase.getAvatar();

        String oldEmail = staffFromDatabase.getEmail();
        String oldPassword = staffFromDatabase.getPassword();

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        String address = addressField.getText();
        String role = roleChoiceBox.getValue();
        String gender = genderChoiceBox.getValue();
        String salary = salaryChoiceBox.getValue();
        String position = positionNameChoiceBox.getValue();
        String educationBackground = educationChoiceBox.getValue();
        String experience = experienceChoiceBox.getValue();
        LocalDate dob = dobDatePicker.getValue();
        LocalDate hireDate = hireDatePicker.getValue();
        String newAvatar = avatarPath != null ? avatarPath : "";

        String avatarToSet = newAvatar.isEmpty() ? oldAvatar : newAvatar;

        boolean hasError = false;
        boolean isValid = true;

        if (firstName.isEmpty()) {
            firstNameErrorLabel.setText("Name cannot be blank.");
            firstNameErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else if (!Character.isUpperCase(firstName.charAt(0))) {
            firstNameErrorLabel.setText("Name must start with a capital letter.");
            firstNameErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            firstNameErrorLabel.setVisible(false);
        }

        if (lastName.isEmpty()) {
            lastNameErrorLabel.setText("They cannot be left blank.");
            lastNameErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else if (!Character.isUpperCase(lastName.charAt(0))) {
            lastNameErrorLabel.setText("Last name must start with a capital letter.");
            lastNameErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            lastNameErrorLabel.setVisible(false);
        }

        if (email.isEmpty()) {
            emailErrorLabel.setText("Email cannot be blank.");
            emailErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            if (!email.matches(emailRegex)) {
                emailErrorLabel.setText("Email không hợp lệ.");
                emailErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                emailErrorLabel.setVisible(false);
            }
        }

        if (dob == null) {
            dobErrorLabel.setText("Date of birth cannot be left blank.");
            dobErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            LocalDate limitDate = LocalDate.of(2000, 1, 1);
            if (dob.isAfter(limitDate)) {
                dobErrorLabel.setText("Date of birth must be before 2010.");
                dobErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                dobErrorLabel.setVisible(false);
            }
        }

        if (phoneNumber.isEmpty()) {
            phoneErrorLabel.setText("Phone number cannot be left blank.");
            phoneErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else if (!phoneNumber.matches("\\d{10}")) {
            phoneErrorLabel.setText("Phone number must be 10 digits.");
            phoneErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            phoneErrorLabel.setVisible(false);
        }

        if (address.isEmpty()) {
            addressErrorLabel.setText("Address cannot be left blank.");
            addressErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            addressErrorLabel.setVisible(false);
        }

        if (hireDate == null) {
            hireDateErrorLabel.setText("Recruitment date cannot be left blank.");
            hireDateErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            hireDateErrorLabel.setVisible(false);
        }

        if (position == null) {
            positionNameErrorLabel.setText("Position cannot be left blank.");
            positionNameErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            positionNameErrorLabel.setVisible(false);
        }

        if (role == null) {
            roleErrorLabel.setText("Role cannot be left empty.");
            roleErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            roleErrorLabel.setVisible(false);
        }

        if (gender == null) {
            genderErrorLabel.setText("Gender cannot be left blank.");
            genderErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            genderErrorLabel.setVisible(false);
        }

        if (salary == null) {
            salaryErrorLabel.setText("Salary cannot be left blank.");
            salaryErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            salaryErrorLabel.setVisible(false);
        }

        if (educationBackground == null) {
            educationErrorLabel.setText("Education level cannot be left blank.");
            educationErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            educationErrorLabel.setVisible(false);
        }

        if (experience == null) {
            experienceErrorLabel.setText("Experience cannot be left blank.");
            experienceErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            experienceErrorLabel.setVisible(false);
        }

        if (!newAvatar.isEmpty()) {
            List<String> validFormats = Arrays.asList("png", "jpg", "jpeg");

            String fileExtension = "";
            try {
                String fileName = new File(newAvatar).getName();
                int dotIndex = fileName.lastIndexOf('.');
                if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                    fileExtension = fileName.substring(dotIndex + 1).toLowerCase();
                }
            } catch (Exception e) {
                fileExtension = "";
            }

            System.out.println("Avatar path: " + newAvatar);
            System.out.println("File extension: " + fileExtension);

            if (!validFormats.contains(fileExtension)) {
                chooseFileErrorLabel.setText("Avatar must be in format: png, jpg, hoặc jpeg.");
                chooseFileErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                chooseFileErrorLabel.setVisible(false);
            }
        }

        boolean isPasswordChanged = password != null && !password.trim().isEmpty();

        if (isPasswordChanged) {
            System.out.println("Mật khẩu mới: " + password);
            if (password.length() < 5) {
                passwordErrorLabel.setText("Password must be at least 5 characters.");
                passwordErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                passwordErrorLabel.setVisible(false);
            }

            if (!password.equals(confirmPassword)) {
                confirmPasswordErrorLabel.setText("Confirmation password does not match.");
                confirmPasswordErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                confirmPasswordErrorLabel.setVisible(false);
            }

            if (isValid) {
                System.out.println("The new password has been validated.");

            }

        } else {
            confirmPasswordErrorLabel.setVisible(false);
        }

        if (!isValid) {
            showError("Please check and fix the errors above.");
            return;

        }

        boolean hasFamilyMember = false;

        String familyMemberName1 = familyMemberNameField1.getText();
        String familyMemberRelationship1 = relationshipChoiceBox1.getValue();
        String familyMemberContact1 = contactNumberField1.getText();

        String familyMemberName2 = familyMemberNameField2.getText();
        String familyMemberRelationship2 = relationshipChoiceBox2.getValue();
        String familyMemberContact2 = contactNumberField2.getText();

        if (!familyMemberName1.isEmpty() || !familyMemberContact1.isEmpty() || familyMemberRelationship1 != null) {
            hasFamilyMember = true;

            if (familyMemberName1.isEmpty()) {
                familyMemberName1ErrorLabel.setText("Family member 1 name cannot be left blank.");
                familyMemberName1ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                familyMemberName1ErrorLabel.setVisible(false);
            }

            if (familyMemberContact1.isEmpty()) {
                contactNumber1ErrorLabel.setText("Contact 1 cannot be blank.");
                contactNumber1ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else if (!familyMemberContact1.matches("\\d{10}")) {
                contactNumber1ErrorLabel.setText("Contact number must be valid.");
                contactNumber1ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                contactNumber1ErrorLabel.setVisible(false);
            }

            if (familyMemberRelationship1 == null) {
                relationship1ErrorLabel.setText("Relationship 1 cannot be left blank.");
                relationship1ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                relationship1ErrorLabel.setVisible(false);
            }
        }

        if (!familyMemberName2.isEmpty() || !familyMemberContact2.isEmpty() || familyMemberRelationship2 != null) {
            hasFamilyMember = true;

            if (familyMemberName2.isEmpty()) {
                familyMemberName2ErrorLabel.setText("Family member 2 name cannot be left blank.");
                familyMemberName2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                familyMemberName2ErrorLabel.setVisible(false);
            }

            if (familyMemberContact2.isEmpty()) {
                contactNumber2ErrorLabel.setText("Contact 2 cannot be blank.");
                contactNumber2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else if (!familyMemberContact2.matches("\\d{10}")) {
                contactNumber2ErrorLabel.setText("Contact number must be valid.");
                contactNumber2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                contactNumber2ErrorLabel.setVisible(false);
            }

            if (familyMemberRelationship2 == null) {
                relationship2ErrorLabel.setText("Relationship 2 cannot be left blank.");
                relationship2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                relationship2ErrorLabel.setVisible(false);
            }
        }

        if (hasError) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi xác thực");
            alert.setHeaderText(null);
            alert.setContentText("Please double check the information entered.");
            alert.showAndWait();
        }

        currentStaff.setFirstName(firstName);
        currentStaff.setLastName(lastName);
        currentStaff.setDateOfBirth(Date.valueOf(dob));
        currentStaff.setHireDate(Date.valueOf(hireDate));
        currentStaff.setEmail(email);
        currentStaff.setPhoneNumber(phoneNumber);
        currentStaff.setAddress(address);
        currentStaff.setGender(gender.equals("Male") ? (byte) 1 : (byte) 0);
        currentStaff.setAvatar(avatarToSet);
        currentStaff.setPassword(password);
        currentStaff.setPositionName(position);

        StaffRoles staffRole = new StaffRoles(role);

        try {
            double salaryValue = Double.parseDouble(salary);
            currentStaff.setSalary(salaryValue);
        } catch (NumberFormatException e) {
            showError("Invalid salary value. Please enter a valid number.");
            return;
        }

        currentStaff.setEducationBackground(educationBackground);
        currentStaff.setExperience(experience);

        List<StaffFamily> familyMembers = new ArrayList<>();
        if (familyMemberName1 != null && !familyMemberName1.isEmpty()) {
            StaffFamily familyMember1 = new StaffFamily(familyMemberRelationship1, familyMemberName1, familyMemberContact1);
            familyMembers.add(familyMember1);
        }
        if (familyMemberName2 != null && !familyMemberName2.isEmpty()) {
            StaffFamily familyMember2 = new StaffFamily(familyMemberRelationship2, familyMemberName2, familyMemberContact2);
            familyMembers.add(familyMember2);
        }

        boolean updated = staffService.updateStaff(currentStaff, familyMembers, staffRole);

        if (updated) {
            showSuccess("Staff updated successfully.");
        } else {
            showError("Failed to update staff.");
            emailErrorLabel.setText("Email already exists in the database.");
            emailErrorLabel.setVisible(true);
        }
    }

    private void clearErrorMessages() {
        firstNameErrorLabel.setVisible(false);
        firstNameErrorLabel.setText("");

        lastNameErrorLabel.setVisible(false);
        lastNameErrorLabel.setText("");

        passwordErrorLabel.setVisible(false);
        passwordErrorLabel.setText("");

        confirmPasswordErrorLabel.setVisible(false);
        confirmPasswordErrorLabel.setText("");

        dobErrorLabel.setVisible(false);
        dobErrorLabel.setText("");

        emailErrorLabel.setVisible(false);
        emailErrorLabel.setText("");

        phoneErrorLabel.setVisible(false);
        phoneErrorLabel.setText("");

        addressErrorLabel.setVisible(false);
        addressErrorLabel.setText("");

        roleErrorLabel.setVisible(false);
        roleErrorLabel.setText("");

        genderErrorLabel.setVisible(false);
        genderErrorLabel.setText("");

        salaryErrorLabel.setVisible(false);
        salaryErrorLabel.setText("");

        educationErrorLabel.setVisible(false);
        educationErrorLabel.setText("");

        experienceErrorLabel.setVisible(false);
        experienceErrorLabel.setText("");

        relationship1ErrorLabel.setVisible(false);
        relationship1ErrorLabel.setText("");

        relationship2ErrorLabel.setVisible(false);
        relationship2ErrorLabel.setText("");

        familyMemberName1ErrorLabel.setVisible(false);
        familyMemberName1ErrorLabel.setText("");

        familyMemberName2ErrorLabel.setVisible(false);
        familyMemberName2ErrorLabel.setText("");

        contactNumber1ErrorLabel.setVisible(false);
        contactNumber1ErrorLabel.setText("");

        contactNumber2ErrorLabel.setVisible(false);
        contactNumber2ErrorLabel.setText("");

        chooseFileErrorLabel.setVisible(false);
        chooseFileErrorLabel.setText("");

        positionNameErrorLabel.setVisible(false);
        positionNameErrorLabel.setText("");

    }


    private void updateStaff() {

    }


    @FXML
    private void handleChooseFileButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Avatar Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(formEditStaff.getScene().getWindow());
        if (selectedFile != null) {
            if (isValidImage(selectedFile)) {
                Image avatarImage = new Image(selectedFile.toURI().toString());
                profileImageView.setImage(avatarImage);
                avatarPath = selectedFile.toURI().toString();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Image");
                alert.setHeaderText(null);
                alert.setContentText("Your image must be 2x3, 3x4, or 4x6 ratio.");
                alert.showAndWait();
            }
        }
    }
    private boolean isValidImage(File file) {
        try {
            Image image = new Image(file.toURI().toString());
            double width = image.getWidth();
            double height = image.getHeight();

            double aspectRatio = width / height;
            return (Math.abs(aspectRatio - (2.0 / 3.0)) < 0.1 ||
                    Math.abs(aspectRatio - (3.0 / 4.0)) < 0.1 ||
                    Math.abs(aspectRatio - (4.0 / 6.0)) < 0.1);
        } catch (Exception e) {
            return false;
        }
    }



    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}