package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Positions;
import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.entities.StaffFamily;
import com.app.schoolmanagementsystem.entities.StaffRoles;
import com.app.schoolmanagementsystem.model.StaffModel;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddStaffController implements Initializable {

    @FXML
    private StackPane formAddStaff;

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
    private ChoiceBox<String> positionChoiceBox;
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
    private TextField familyMemberNameField1;
    @FXML
    private TextField familyMemberNameField2;
    @FXML
    private TextField contactNumberField1;
    @FXML
    private TextField contactNumberField2;

    // Error labels
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
    private Label positionErrorLabel;
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
    private Button chooseFileButton;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView profileImageView;

    private StaffModel staffService;

    public AddStaffController() {
        staffService = new StaffModel(); // Initialize your service here
    }

    public void setPageStaff(StackPane pageStaff) {
        this.pageStaff = pageStaff;
    }

    public void setBGPageStaff(AnchorPane moveBG) {
        this.moveBG = moveBG;
    }

    @FXML
    void closeFormAdd(MouseEvent event) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(formAddStaff);
        translateTransition.setToX(2000);

        translateTransition.play();

        translateTransition.setOnFinished(actionEvent -> {
            pageStaff.getChildren().remove(formAddStaff);
            moveBG.setEffect(null);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize choice boxes with roles, genders, positions, salaries, education backgrounds, and experiences
        roleChoiceBox.getItems().addAll("Admin Master", "Manager", "Teacher");
        genderChoiceBox.getItems().addAll("Male", "Female");
        positionChoiceBox.getItems().addAll("1", "2", "3");
        salaryChoiceBox.getItems().addAll("50000", "60000", "70000", "80000");
        educationChoiceBox.getItems().addAll("High School", "Associate Degree", "Bachelor's Degree", "Master's Degree", "PhD");
        experienceChoiceBox.getItems().addAll("0-1 years", "1-3 years", "3-5 years", "5-10 years", "10+ years");
        relationshipChoiceBox1.getItems().addAll("Father", "Mother", "Sibling", "Spouse", "Child");
        relationshipChoiceBox2.getItems().addAll("Father", "Mother", "Sibling", "Spouse", "Child");
    }

    @FXML
    private void handleAddButtonAction() {
        clearErrorMessages(); // Xóa thông báo lỗi trước đó

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        String address = addressField.getText();
        String role = roleChoiceBox.getValue();
        String gender = genderChoiceBox.getValue();
        String position = positionChoiceBox.getValue();
        String salary = salaryChoiceBox.getValue();
        String educationBackground = educationChoiceBox.getValue();
        String experience = experienceChoiceBox.getValue();
        LocalDate dob = dobDatePicker.getValue();
        LocalDate hireDate = hireDatePicker.getValue();

        boolean hasError = false;
        boolean isValid = true;

        // Validate các trường
        if (firstName.isEmpty()) {
            firstNameErrorLabel.setText("Tên không được để trống.");
            firstNameErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            firstNameErrorLabel.setVisible(false);
        }

        if (lastName.isEmpty()) {
            lastNameErrorLabel.setText("Họ không được để trống.");
            lastNameErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            lastNameErrorLabel.setVisible(false);
        }

        if (email.isEmpty()) {
            emailErrorLabel.setText("Email không được để trống.");
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
            dobErrorLabel.setText("Ngày sinh không được để trống.");
            dobErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            dobErrorLabel.setVisible(false);
        }

        if (password.isEmpty()) {
            passwordErrorLabel.setText("Mật khẩu không được để trống.");
            passwordErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else if (password.length() < 5) {
            passwordErrorLabel.setText("Mật khẩu phải có ít nhất 5 ký tự.");
            passwordErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            passwordErrorLabel.setVisible(false);
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordErrorLabel.setText("Mật khẩu xác nhận không khớp.");
            confirmPasswordErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            confirmPasswordErrorLabel.setVisible(false);
        }

        if (phoneNumber.isEmpty()) {
            phoneErrorLabel.setText("Số điện thoại không được để trống.");
            phoneErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else if (!phoneNumber.matches("\\d{10}")) {
            phoneErrorLabel.setText("Số điện thoại phải là 10 chữ số.");
            phoneErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            phoneErrorLabel.setVisible(false);
        }

        // Validate địa chỉ
        if (address.isEmpty()) {
            addressErrorLabel.setText("Địa chỉ không được để trống.");
            addressErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            addressErrorLabel.setVisible(false);
        }

        if (hireDate == null) {
            hireDateErrorLabel.setText("Ngày tuyển dụng không được để trống.");
            hireDateErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            hireDateErrorLabel.setVisible(false);
        }

        if (role == null) {
            roleErrorLabel.setText("Vai trò không được để trống.");
            roleErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            roleErrorLabel.setVisible(false);
        }

        if (gender == null) {
            genderErrorLabel.setText("Giới tính không được để trống.");
            genderErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            genderErrorLabel.setVisible(false);
        }

        if (position == null) {
            positionErrorLabel.setText("Vị trí không được để trống.");
            positionErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            positionErrorLabel.setVisible(false);
        }

        if (salary == null) {
            salaryErrorLabel.setText("Lương không được để trống.");
            salaryErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            salaryErrorLabel.setVisible(false);
        }

        if (educationBackground == null) {
            educationErrorLabel.setText("Trình độ học vấn không được để trống.");
            educationErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            educationErrorLabel.setVisible(false);
        }

        if (experience == null) {
            experienceErrorLabel.setText("Kinh nghiệm không được để trống.");
            experienceErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            experienceErrorLabel.setVisible(false);
        }



        // Nếu có lỗi, dừng việc lưu
        if (hasError) {
            System.out.println("Dữ liệu nhập không hợp lệ.");
            return;
        }

        // Khai báo biến isValid


// Validate các trường thành viên gia đình
        String familyMemberName1 = familyMemberNameField1.getText();
        String familyMemberRelationship1 = relationshipChoiceBox1.getValue();
        String familyMemberContact1 = contactNumberField1.getText();

        String familyMemberName2 = familyMemberNameField2.getText();
        String familyMemberRelationship2 = relationshipChoiceBox2.getValue();
        String familyMemberContact2 = contactNumberField2.getText();

        boolean hasFamilyMember = false;

// Kiểm tra và xác thực thông tin thành viên gia đình 1
        if (!familyMemberName1.isEmpty() || !familyMemberContact1.isEmpty() || familyMemberRelationship1 != null) {
            hasFamilyMember = true;

            if (familyMemberName1.isEmpty()) {
                familyMemberName1ErrorLabel.setText("Tên thành viên gia đình 1 không được để trống.");
                familyMemberName1ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                familyMemberName1ErrorLabel.setVisible(false);
            }

            if (familyMemberContact1.isEmpty()) {
                contactNumber1ErrorLabel.setText("Số liên lạc 1 không được để trống.");
                contactNumber1ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else if (!familyMemberContact1.matches("\\d{10}")) {
                contactNumber1ErrorLabel.setText("Số liên lạc phải là số hợp lệ.");
                contactNumber1ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                contactNumber1ErrorLabel.setVisible(false);
            }

            if (familyMemberRelationship1 == null) {
                relationship1ErrorLabel.setText("Mối quan hệ 1 không được để trống.");
                relationship1ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                relationship1ErrorLabel.setVisible(false);
            }
        }


// Kiểm tra và xác thực thông tin thành viên gia đình 2
        if (!familyMemberName2.isEmpty() || !familyMemberContact2.isEmpty() || familyMemberRelationship2 != null) {
            hasFamilyMember = true;

            if (familyMemberName2.isEmpty()) {
                familyMemberName2ErrorLabel.setText("Tên thành viên gia đình thứ hai không được để trống.");
                familyMemberName2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                familyMemberName2ErrorLabel.setVisible(false);
            }

            if (familyMemberContact2.isEmpty()) {
                contactNumber2ErrorLabel.setText("Số liên lạc thành viên gia đình thứ hai không được để trống.");
                contactNumber2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else if (!familyMemberContact2.matches("\\d{10}")) {
                contactNumber2ErrorLabel.setText("Số liên lạc thành viên gia đình thứ hai phải là số hợp lệ.");
                contactNumber2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                contactNumber2ErrorLabel.setVisible(false);
            }

            if (familyMemberRelationship2 == null) {
                relationship2ErrorLabel.setText("Mối quan hệ thành viên gia đình thứ hai không được để trống.");
                relationship2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                relationship2ErrorLabel.setVisible(false);
            }


            if (isValid) {
                hasFamilyMember = true;
            }
        }

// Nếu không có thông tin cho ít nhất một thành viên gia đình
        if (!hasFamilyMember) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Nhập thông tin ít nhất một thành viên gia đình.");
            return;
        }

// Tạo danh sách thành viên gia đình
        List<StaffFamily> familyMembers = new ArrayList<>();

        if (isValid && !familyMemberName1.isEmpty() && familyMemberRelationship1 != null && !familyMemberContact1.isEmpty()) {
            StaffFamily familyMember1 = new StaffFamily(familyMemberName1, familyMemberRelationship1, familyMemberContact1);
            familyMembers.add(familyMember1);
        }

        if (isValid && !familyMemberName2.isEmpty() && familyMemberRelationship2 != null && !familyMemberContact2.isEmpty()) {
            StaffFamily familyMember2 = new StaffFamily(familyMemberName2, familyMemberRelationship2, familyMemberContact2);
            familyMembers.add(familyMember2);
        }

        // Tạo đối tượng Staff
        Staff newStaff = new Staff();
        newStaff.setFirstName(firstName);
        newStaff.setLastName(lastName);
        newStaff.setPassword(password); // Nếu cần mã hóa mật khẩu, thực hiện tại đây
        newStaff.setEmail(email);
        newStaff.setPhoneNumber(phoneNumber);
        newStaff.setAddress(address);
        newStaff.setGender(gender != null && gender.equals("Male") ? (byte) 1 : (byte) 0);
        newStaff.setAvatar(profileImageView.getImage() != null ? profileImageView.getImage().getUrl() : "");
        newStaff.setStatus("active");
        newStaff.setPositionID(getPositionID(position)); // Giả định getPositionID hoạt động chính xác
        newStaff.setSalary(Double.parseDouble(salary));
        newStaff.setEducationBackground(educationBackground);
        newStaff.setExperience(experience);
        newStaff.setDateOfBirth(Date.valueOf(dob));
        newStaff.setHireDate(Date.valueOf(hireDate));

        // Tạo đối tượng StaffRoles từ vai trò
        StaffRoles staffRole = new StaffRoles(role);

        // Tạo đối tượng Positions từ vị trí
        Positions positionEntity = new Positions(getPositionID(position), position, "");

        // Lưu nhân viên vào cơ sở dữ liệu
        boolean isAdded = staffService.addStaff(newStaff, familyMembers, staffRole, positionEntity);
        if (isAdded) {
            showConfirmation("Thêm nhân viên thành công!");
        } else {
            showError("Có lỗi xảy ra, vui lòng thử lại.");
        }
    }



    private void clearErrorMessages() {
        // Hide all error labels and clear text
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

        positionErrorLabel.setVisible(false);
        positionErrorLabel.setText("");

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
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private int getPositionID(String position) {
        // Implement this to return the ID of the position
        return 1; // Placeholder
    }


    @FXML
    private void handleCancelButtonAction() {
        pageStaff.getChildren().remove(formAddStaff);
        moveBG.setEffect(null);
    }

    @FXML
    private void handleChooseFileButtonAction() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            profileImageView.setImage(new Image(file.toURI().toString()));
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}
