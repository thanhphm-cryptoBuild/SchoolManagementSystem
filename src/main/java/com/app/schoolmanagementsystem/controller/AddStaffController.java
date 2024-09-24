package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.entities.StaffFamily;
import com.app.schoolmanagementsystem.entities.StaffRoles;
import com.app.schoolmanagementsystem.model.StaffModel;
import com.app.schoolmanagementsystem.session.UserSession;
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
    private ChoiceBox<String> salaryChoiceBox;
    @FXML
    private ChoiceBox<String> educationChoiceBox;
    @FXML
    private ChoiceBox<String> experienceChoiceBox;
    @FXML
    private ChoiceBox<String> positionNameChoiceBox;
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
    private Label salaryErrorLabel;
    @FXML
    private Label educationErrorLabel;
    @FXML
    private Label experienceErrorLabel;
    @FXML
    private Label positionNameErrorLabel;
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

    private String avatarPath = "useravatar.png"; // Biến để lưu đường dẫn hình ảnh

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
        // Initialize choice boxes with roles, genders, salaries, education backgrounds, and experiences
        // Đường dẫn đến hình ảnh trong thư mục resources
        String path = "useravatar.png";
        InputStream imageStream = getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/" + path);

        if (imageStream != null) {
            Image image = new Image(imageStream);
            profileImageView.setImage(image);
        } else {
            // Sử dụng hình ảnh mặc định nếu không tìm thấy hình ảnh
            Image defaultImage = new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/useravatar.png"));
            profileImageView.setImage(defaultImage);
        }

        // Lấy vai trò hiện tại
        String currentRoleName = getCurrentRoleName(); // Giả sử bạn có phương thức này để lấy roleName
        if ("Manager".equals(currentRoleName)) {
            // Nếu là Manager, chỉ thêm "Teacher"
            roleChoiceBox.getItems().add("Teacher");
        } else {
            // Nếu không, thêm tất cả các vai trò
            roleChoiceBox.getItems().addAll("Admin Master", "Manager", "Teacher");
        }

        genderChoiceBox.getItems().addAll("Male", "Female");
        salaryChoiceBox.getItems().addAll("50000", "60000", "70000", "80000");
        educationChoiceBox.getItems().addAll("Intermediate", "College", "University", "Master's", "Ph.D.");
        experienceChoiceBox.getItems().addAll("0-1 years", "1-3 years", "3-5 years", "5-10 years", "10+ years");
        positionNameChoiceBox.getItems().addAll("Math teacher", "Physics teacher", "Biology teacher, Music teacher");
        relationshipChoiceBox1.getItems().addAll("Father", "Mother", "Sibling", "Spouse", "Child");
        relationshipChoiceBox2.getItems().addAll("Father", "Mother", "Sibling", "Spouse", "Child");
        // Cấu hình DatePicker để chỉ cho phép chọn ngày từ năm 2010 trở về trước

        LocalDate maxDate = LocalDate.of(2000, 12, 31); // Ngày tối đa
        dobDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);

                        // Vô hiệu hóa các ngày không nằm trong khoảng giới hạn
                        if (date == null || date.isAfter(maxDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;"); // Tùy chọn kiểu dáng cho ngày bị vô hiệu hóa
                        } else {
                            setStyle(""); // Xóa kiểu dáng nếu ngày hợp lệ
                        }
                    }
                };
            }
        });
    }

    // Phương thức để lấy roleName hiện tại
    public String getCurrentRoleName() {
        return UserSession.getCurrentRoleName(); // Sử dụng UserSession để lấy vai trò
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
        String salary = salaryChoiceBox.getValue();
        String educationBackground = educationChoiceBox.getValue();
        String experience = experienceChoiceBox.getValue();
        String positionName = positionNameChoiceBox.getValue();
        LocalDate dob = dobDatePicker.getValue();
        LocalDate hireDate = hireDatePicker.getValue();
        String avatar = "";
        if (profileImageView.getImage() != null && profileImageView.getImage().getUrl() != null) {
            avatar = new File(profileImageView.getImage().getUrl()).getName();
        } else {
            System.out.println("No profile image selected.");
            // Hoặc hiển thị thông báo trong giao diện người dùng:
            // showAlert("No profile image selected.");
        }

        boolean hasError = false;
        boolean isValid = true;

        // Validate các trường
        if (firstName.isEmpty()) {
            firstNameErrorLabel.setText("Names must not be left blank.");
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


        // Validate ngày sinh
        if (dob == null) {
            dobErrorLabel.setText("Date of birth cannot be left blank.");
            dobErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            LocalDate limitDate = LocalDate.of(2000, 1, 1);
            if (dob.isAfter(limitDate)) {
                dobErrorLabel.setText("Date of birth must be before 2000.");
                dobErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                dobErrorLabel.setVisible(false);
            }
        }

        if (password.isEmpty()) {
            passwordErrorLabel.setText("Password cannot be blank.");
            passwordErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else if (password.length() < 5) {
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

        // Validate địa chỉ
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

        if (positionName == null) {
            positionNameErrorLabel.setText("positionName cannot be left blank.");
            positionNameErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            salaryErrorLabel.setVisible(false);
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


        // Validate định dạng hình đại diện
//        if (avatar.isEmpty()) {
//            chooseFileErrorLabel.setText("Avatar cannot be blank.");
//            chooseFileErrorLabel.setVisible(true);
//            hasError = true;
//            isValid = false;
//        } else {
//            // Danh sách các định dạng hợp lệ
//            List<String> validFormats = Arrays.asList("png", "jpg", "jpeg");
//
//            // Lấy phần mở rộng của tệp
//            String fileExtension = avatar.substring(avatar.lastIndexOf('.') + 1).toLowerCase();
//
//            // Kiểm tra định dạng
//            if (!validFormats.contains(fileExtension)) {
//                chooseFileErrorLabel.setText("Avatar must be in format: png, jpg, hoặc jpeg.");
//                chooseFileErrorLabel.setVisible(true);
//                hasError = true;
//                isValid = false;
//            } else {
//                chooseFileErrorLabel.setVisible(false);
//            }
//        }

        if (email.isEmpty()) {
            emailErrorLabel.setText("Email cannot be blank.");
            emailErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            if (!email.matches(emailRegex)) {
                emailErrorLabel.setText("Invalid email.");
                emailErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                emailErrorLabel.setVisible(false);

            }
        }


        // Nếu có lỗi, dừng việc lưu
        if (hasError) {
            System.out.println("Invalid input data.");
            return;
        }

        // Khai báo biến isValid


        boolean hasFamilyMember = false;

// Validate các trường thành viên gia đình
        String familyMemberName1 = familyMemberNameField1.getText().trim();
        String familyMemberRelationship1 = relationshipChoiceBox1.getValue();
        String familyMemberContact1 = contactNumberField1.getText().trim();

        String familyMemberName2 = familyMemberNameField2.getText().trim();
        String familyMemberRelationship2 = relationshipChoiceBox2.getValue();
        String familyMemberContact2 = contactNumberField2.getText().trim();

// Kiểm tra và xác thực thông tin thành viên gia đình 1
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
                contactNumber1ErrorLabel.setText("Số liên lạc 1 không được để trống.");
                contactNumber1ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else if (!familyMemberContact1.matches("\\d{10}")) {
                contactNumber1ErrorLabel.setText("Số liên lạc 1 phải là một số hợp lệ với 10 chữ số.");
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

// Kiểm tra và xác thực thông tin thành viên gia đình 2
        if (!familyMemberName2.isEmpty() || !familyMemberContact2.isEmpty() || familyMemberRelationship2 != null) {
            hasFamilyMember = true;

            if (familyMemberName2.isEmpty()) {
                familyMemberName2ErrorLabel.setText("Second family member name cannot be left blank.");
                familyMemberName2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                familyMemberName2ErrorLabel.setVisible(false);
            }

            if (familyMemberContact2.isEmpty()) {
                contactNumber2ErrorLabel.setText("Second family member contact number cannot be left blank.");
                contactNumber2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else if (!familyMemberContact2.matches("\\d{10}")) {
                contactNumber2ErrorLabel.setText("Số liên lạc thành viên gia đình thứ hai phải là một số hợp lệ với 10 chữ số.");

                contactNumber2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                contactNumber2ErrorLabel.setVisible(false);
            }

            if (familyMemberRelationship2 == null) {
                relationship2ErrorLabel.setText("Second family member relationship cannot be left blank.");
                relationship2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                relationship2ErrorLabel.setVisible(false);
            }
        }

// Nếu có lỗi trong việc xác thực thông tin thành viên gia đình, dừng quá trình và không tiếp tục
        if (!isValid) {
            return; // Hoặc có thể hiển thị một thông báo tổng quát nếu cần
        }

// Nếu không có thông tin cho ít nhất một thành viên gia đình
        if (!hasFamilyMember) {
            showAlert(Alert.AlertType.ERROR, "Error", "Enter information for at least one family member.");
            return;
        }

// Tạo danh sách thành viên gia đình
        List<StaffFamily> familyMembers = new ArrayList<>();

        if (!familyMemberName1.isEmpty() && familyMemberRelationship1 != null && !familyMemberContact1.isEmpty()) {
            StaffFamily familyMember1 = new StaffFamily(familyMemberName1, familyMemberRelationship1, familyMemberContact1);
            familyMembers.add(familyMember1);
        }

        if (!familyMemberName2.isEmpty() && familyMemberRelationship2 != null && !familyMemberContact2.isEmpty()) {
            StaffFamily familyMember2 = new StaffFamily(familyMemberName2, familyMemberRelationship2, familyMemberContact2);
            familyMembers.add(familyMember2);
        }

// Kiểm tra lại tính hợp lệ trước khi tiếp tục
        if (!isValid) {
            return;
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
        newStaff.setAvatar(avatarPath);
        newStaff.setStatus("active");
        newStaff.setSalary(Double.parseDouble(salary));
        newStaff.setEducationBackground(educationBackground);
        newStaff.setExperience(experience);
        newStaff.setDateOfBirth(Date.valueOf(dob));
        newStaff.setHireDate(Date.valueOf(hireDate));
        newStaff.setPositionName(positionName);

// Tạo đối tượng StaffRoles từ vai trò
        StaffRoles staffRole = new StaffRoles(role);

// Lưu nhân viên vào cơ sở dữ liệu chỉ nếu tất cả các thông tin đều hợp lệ
        boolean isAdded = staffService.addStaff(newStaff, familyMembers, staffRole);
        if (isAdded) {
            showConfirmation("Staff updated successfully.");
        } else {
            showError("Failed to update staff.");
            emailErrorLabel.setText("Email already exists in the database.");
            emailErrorLabel.setVisible(true);
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


    @FXML
    private void handleCancelButtonAction() {
        pageStaff.getChildren().remove(formAddStaff);
        moveBG.setEffect(null);
    }

    @FXML
    private void handleChooseFileButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Avatar Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(formAddStaff.getScene().getWindow());
        if (selectedFile != null) {
            // Validate the image before setting it
            if (isValidImage(selectedFile)) {
                // Set the image directly without resizing
                Image avatarImage = new Image(selectedFile.toURI().toString());
                profileImageView.setImage(avatarImage);
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
    private boolean isValidImage(File file) {
        try {
            Image image = new Image(file.toURI().toString());
            double width = image.getWidth();
            double height = image.getHeight();

            // Kiểm tra tỷ lệ khung hình (2x3, 3x4 hoặc 4x6)
            double aspectRatio = width / height;
            return (Math.abs(aspectRatio - (2.0 / 3.0)) < 0.1 ||  // Tỷ lệ 2:3
                    Math.abs(aspectRatio - (3.0 / 4.0)) < 0.1 ||  // Tỷ lệ 3:4
                    Math.abs(aspectRatio - (4.0 / 6.0)) < 0.1);   // Tỷ lệ 4:6
        } catch (Exception e) {
            return false;
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