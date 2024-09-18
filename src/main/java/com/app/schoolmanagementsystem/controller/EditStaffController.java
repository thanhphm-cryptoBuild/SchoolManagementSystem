package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.entities.StaffFamily;
import com.app.schoolmanagementsystem.entities.StaffRoles;
import com.app.schoolmanagementsystem.model.StaffModel;
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
    private Button updateButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView profileImageView;

    private StaffModel staffService;

    private Staff currentStaff;

    public void setStaffData(Staff staff) {
        this.currentStaff = staff;
        loadStaffData();
    }

    public EditStaffController() {
        staffService = new StaffModel(); // Initialize your service here
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
        // Load necessary data for the form
        loadStaffData();
        // Make fields non-editable
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


            String gender = currentStaff.getGender() == 1 ? "Male" : "Female";
            genderChoiceBox.setValue(gender);
            ObservableList<String> genderOptions = FXCollections.observableArrayList("Male", "Female");
            genderChoiceBox.setItems(genderOptions);

            salaryChoiceBox.setValue(String.valueOf(currentStaff.getSalary()));
            ObservableList<String> salaryOptions = FXCollections.observableArrayList(
                    "30000", "40000", "50000", "60000" // Thay thế bằng các giá trị thực tế
            );
            salaryChoiceBox.setItems(salaryOptions);

            educationChoiceBox.setValue(currentStaff.getEducationBackground());
            // Các tùy chọn cho Education Background
            ObservableList<String> educationOptions = FXCollections.observableArrayList(
                    "High School", "Associate Degree", "Bachelor's Degree", "Master's Degree" // Thay thế bằng các giá trị thực tế
            );
            educationChoiceBox.setItems(educationOptions);

            experienceChoiceBox.setValue(currentStaff.getExperience());
            // Các tùy chọn cho Experience
            ObservableList<String> experienceOptions = FXCollections.observableArrayList(
                    "0-1 years", "1-3 years", "3-5 years", "5-10 years", "10+ years" // Thay thế bằng các giá trị thực tế
            );
            experienceChoiceBox.setItems(experienceOptions);


            // Load profile image
            // Đảm bảo rằng hình ảnh được đóng gói vào classpath của dự án
            if (currentStaff.getAvatar() != null && !currentStaff.getAvatar().isEmpty()) {
                try {
                    // Đối với tài nguyên nằm trong thư mục resources
                    String resourcePath = "/com/app/schoolmanagementsystem/images/" + currentStaff.getAvatar();
                    Image profileImage = new Image(getClass().getResourceAsStream(resourcePath));
                    profileImageView.setImage(profileImage);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Hiển thị hình ảnh mặc định nếu có lỗi
                    profileImageView.setImage(new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/avatar-ex.jpg")));
                }
            } else {
                // Hiển thị hình ảnh mặc định nếu không có avatar
                profileImageView.setImage(new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/avatar-ex.jpg")));
            }

            // Giả sử bạn đã có đối tượng StaffModel và currentStaff
            StaffModel staffModel = new StaffModel(currentStaff, connection()); // Khởi tạo StaffModel với currentStaff và Connection

            // Lấy dữ liệu roleName từ StaffModel
            String roleName = staffModel.getRoleName(); // Gọi phương thức getRoleName của StaffModel

            // Đặt giá trị vào roleChoiceBox nếu có
            if (roleName != null) {
                roleChoiceBox.setValue(roleName);
            } else {
                roleChoiceBox.setValue(""); // Hoặc một giá trị mặc định nếu roleName là null
            }

            // Đảm bảo roleChoiceBox có các giá trị để chọn
            ObservableList<String> roleOptions = FXCollections.observableArrayList("Admin Master", "Manager", "Teacher");
            roleChoiceBox.setItems(roleOptions);

            // Lấy và hiển thị thông tin gia đình
            loadFamilyData(currentStaff.getStaffID());

        }
    }

    private void loadFamilyData(int staffID) {
        // Khởi tạo StaffModel với currentStaff và Connection
        StaffModel staffModel = new StaffModel(currentStaff, connection());

        // Lấy danh sách thành viên gia đình từ StaffModel
        List<StaffFamily> familyMembers = staffModel.getFamilyMembers(staffID);

        // Đảm bảo rằng các ChoiceBox đã được cấu hình với các giá trị tùy chọn
        ObservableList<String> relationshipOptions = FXCollections.observableArrayList(
                "Parent", "Sibling", "Spouse", "Child", "Other" // Thay thế bằng các mối quan hệ thực tế
        );

        // Giữ lại dữ liệu cũ và chỉ cập nhật khi có thông tin mới
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

        // Đặt các giá trị tùy chọn cho các ChoiceBox
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


        // Xóa thông báo lỗi hiện tại
        clearErrorMessages();

        // Giả sử bạn đã có staffId của nhân viên hiện tại
        int staffId = currentStaff.getStaffID(); // Lấy ID của nhân viên hiện tại

        // Lấy nhân viên từ cơ sở dữ liệu
        Staff staffFromDatabase = staffService.getStaffByID(staffId);

        // Lấy avatar cũ từ cơ sở dữ liệu
        String oldAvatar = staffFromDatabase.getAvatar();

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
        LocalDate dob = dobDatePicker.getValue();
        LocalDate hireDate = hireDatePicker.getValue();
        // Lấy tên hình ảnh từ profileImageView nếu có
        String newAvatar = (profileImageView.getImage() != null && profileImageView.getImage().getUrl() != null)
                ? new File(profileImageView.getImage().getUrl()).getName()
                : "";

        // Kiểm tra và cập nhật avatar
        String avatarToSet = newAvatar.isEmpty() ? oldAvatar : newAvatar;

        boolean hasError = false;
        boolean isValid = true;



        // Validate tên
        if (firstName.isEmpty()) {
            firstNameErrorLabel.setText("Tên không được để trống.");
            firstNameErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else if (!Character.isUpperCase(firstName.charAt(0))) {
            firstNameErrorLabel.setText("Tên phải bắt đầu bằng chữ cái hoa.");
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
        } else if (!Character.isUpperCase(lastName.charAt(0))) {
            lastNameErrorLabel.setText("Họ phải bắt đầu bằng chữ cái hoa.");
            lastNameErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            lastNameErrorLabel.setVisible(false);
        }

        // Validate email
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

        // Validate ngày sinh
        if (dob == null) {
            dobErrorLabel.setText("Ngày sinh không được để trống.");
            dobErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            LocalDate limitDate = LocalDate.of(2000, 1, 1);
            if (dob.isAfter(limitDate)) {
                dobErrorLabel.setText("Ngày sinh phải trước năm 2010.");
                dobErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                dobErrorLabel.setVisible(false);
            }
        }

        // Validate mật khẩu và xác nhận mật khẩu
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

        // Validate số điện thoại
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

        // Validate ngày tuyển dụng
        if (hireDate == null) {
            hireDateErrorLabel.setText("Ngày tuyển dụng không được để trống.");
            hireDateErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            hireDateErrorLabel.setVisible(false);
        }

        // Validate vai trò
        if (role == null) {
            roleErrorLabel.setText("Vai trò không được để trống.");
            roleErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            roleErrorLabel.setVisible(false);
        }

        // Validate giới tính
        if (gender == null) {
            genderErrorLabel.setText("Giới tính không được để trống.");
            genderErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            genderErrorLabel.setVisible(false);
        }

        // Validate lương
        if (salary == null) {
            salaryErrorLabel.setText("Lương không được để trống.");
            salaryErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            salaryErrorLabel.setVisible(false);
        }

        // Validate trình độ học vấn
        if (educationBackground == null) {
            educationErrorLabel.setText("Trình độ học vấn không được để trống.");
            educationErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            educationErrorLabel.setVisible(false);
        }

        // Validate kinh nghiệm
        if (experience == null) {
            experienceErrorLabel.setText("Kinh nghiệm không được để trống.");
            experienceErrorLabel.setVisible(true);
            hasError = true;
            isValid = false;
        } else {
            experienceErrorLabel.setVisible(false);
        }

        // Kiểm tra định dạng của hình đại diện
        if (!newAvatar.isEmpty()) {
            // Danh sách các định dạng hợp lệ
            List<String> validFormats = Arrays.asList("png", "jpg", "jpeg");

            // Lấy phần mở rộng của tệp từ đường dẫn
            String fileExtension = "";
            try {
                String fileName = new File(newAvatar).getName(); // Lấy tên tệp từ đường dẫn
                int dotIndex = fileName.lastIndexOf('.'); // Tìm vị trí dấu chấm
                if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                    fileExtension = fileName.substring(dotIndex + 1).toLowerCase(); // Lấy phần mở rộng
                }
            } catch (Exception e) {
                fileExtension = ""; // Đặt mặc định nếu có lỗi
            }

            System.out.println("Avatar path: " + newAvatar); // Debugging line
            System.out.println("File extension: " + fileExtension); // Debugging line

            // Kiểm tra định dạng
            if (!validFormats.contains(fileExtension)) {
                chooseFileErrorLabel.setText("Hình đại diện phải có định dạng: png, jpg, hoặc jpeg.");
                chooseFileErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                chooseFileErrorLabel.setVisible(false);
            }
        }

        if (!isValid) {
            showError("Vui lòng kiểm tra và sửa các lỗi bên trên.");
            return; // Nếu có lỗi, không thực hiện cập nhật

        }


        // Validate thông tin thành viên gia đình
        boolean hasFamilyMember = false;

        String familyMemberName1 = familyMemberNameField1.getText();
        String familyMemberRelationship1 = relationshipChoiceBox1.getValue();
        String familyMemberContact1 = contactNumberField1.getText();

        String familyMemberName2 = familyMemberNameField2.getText();
        String familyMemberRelationship2 = relationshipChoiceBox2.getValue();
        String familyMemberContact2 = contactNumberField2.getText();

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
                familyMemberName2ErrorLabel.setText("Tên thành viên gia đình 2 không được để trống.");
                familyMemberName2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                familyMemberName2ErrorLabel.setVisible(false);
            }

            if (familyMemberContact2.isEmpty()) {
                contactNumber2ErrorLabel.setText("Số liên lạc 2 không được để trống.");
                contactNumber2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else if (!familyMemberContact2.matches("\\d{10}")) {
                contactNumber2ErrorLabel.setText("Số liên lạc phải là số hợp lệ.");
                contactNumber2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                contactNumber2ErrorLabel.setVisible(false);
            }

            if (familyMemberRelationship2 == null) {
                relationship2ErrorLabel.setText("Mối quan hệ 2 không được để trống.");
                relationship2ErrorLabel.setVisible(true);
                hasError = true;
                isValid = false;
            } else {
                relationship2ErrorLabel.setVisible(false);
            }
        }

        // Hiển thị thông báo tổng hợp lỗi nếu có lỗi
        if (hasError) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi xác thực");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng kiểm tra lại các thông tin đã nhập.");
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
        currentStaff.setAvatar(avatarToSet);  // Gán tên hình ảnh cho currentStaff
        currentStaff.setPassword(password);

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

    }


    private void updateStaff() {

    }


    @FXML
    private void handleChooseFileButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Kiểm tra tính hợp lệ của hình ảnh
            if (isValidImage(selectedFile)) {
                try {
                    // Hiển thị hình ảnh đã chọn
                    Image profileImage = new Image(selectedFile.toURI().toString());
                    profileImageView.setImage(profileImage);

                    // Lưu hình ảnh vào thư mục resources
                    String imageName = selectedFile.getName();
                    saveImageToResources(new FileInputStream(selectedFile), imageName);

                    // Cập nhật tên hình ảnh vào cơ sở dữ liệu nếu cần
                } catch (IOException e) {
                    e.printStackTrace();
                    showError("Có lỗi xảy ra khi lưu hình ảnh.");
                }
            } else {
                // Hiển thị thông báo lỗi nếu hình ảnh không hợp lệ
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Image");
                alert.setHeaderText(null);
                alert.setContentText("Hình ảnh của bạn phải có tỷ lệ 2x3, 3x4 hoặc 4x6.");
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

    private void saveImageToResources(InputStream inputStream, String imageName) throws IOException {
        // Đường dẫn đến thư mục lưu trữ hình ảnh trong thư mục resources
        String path = "src/main/resources/com/app/schoolmanagementsystem/images/" + imageName;
        File file = new File(path);

        // Tạo thư mục nếu chưa tồn tại
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        // Lưu hình ảnh vào thư mục
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
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