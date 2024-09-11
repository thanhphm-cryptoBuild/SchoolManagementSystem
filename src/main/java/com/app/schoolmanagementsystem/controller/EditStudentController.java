package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.model.Student;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public class EditStudentController {

    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private DatePicker birthDatePicker;
    @FXML private TextField registrationNoField;
    @FXML private TextField batchField;
    @FXML private DatePicker admissionDatePicker;
    @FXML private TextField previousInstituteField;
    @FXML private TextField reasonForLeavingField;
    @FXML private TextField guardianContactField;
    @FXML private TextField classIDField;
    @FXML private ComboBox<String> sexComboBox;
    @FXML private ImageView avatarImageView;
    @FXML private Button chooseImageButton;

    private Student student;
    private boolean studentUpdated = false;
    private String newImagePath;

    @FXML
    private void initialize() {
        sexComboBox.getItems().addAll("male", "female");
        chooseImageButton.setOnAction(event -> chooseImage());
        setDefaultImage();
    }

    public void setStudent(Student student) {
        this.student = student;
        populateFields();
        loadStudentImage();
    }

    private void populateFields() {
        if (student != null) {
            nameField.setText(student.getFullName());
            addressField.setText(student.getAddress());
            phoneField.setText(student.getPhone());
            emailField.setText(student.getEmail());
            birthDatePicker.setValue(student.getBirthDate() != null ? 
                ((java.sql.Date) student.getBirthDate()).toLocalDate() : null);
            registrationNoField.setText(student.getRegistrationNo());
            batchField.setText(student.getBatch());
            admissionDatePicker.setValue(student.getAdmissionDate() != null ? 
                ((java.sql.Date) student.getAdmissionDate()).toLocalDate() : null);
            previousInstituteField.setText(student.getPreviousInstitute());
            reasonForLeavingField.setText(student.getReasonForLeaving());
            guardianContactField.setText(student.getGuardianContact());
            classIDField.setText(String.valueOf(student.getClassID()));
            sexComboBox.setValue(student.getSex());
        }
    }

    private void loadStudentImage() {
        if (student != null && student.getProfilePicture() != null && !student.getProfilePicture().isEmpty()) {
            try {
                File file = new File(student.getProfilePicture());
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString(), 100, 100, true, true);
                    avatarImageView.setImage(image);
                } else {
                    setDefaultImage();
                }
            } catch (Exception e) {
                System.err.println("Không thể tải ảnh học sinh: " + e.getMessage());
                setDefaultImage();
            }
        } else {
            setDefaultImage();
        }
    }

    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh đại diện");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(chooseImageButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString(), 100, 100, true, true);
                avatarImageView.setImage(image);
                newImagePath = selectedFile.getAbsolutePath();
            } catch (Exception e) {
                System.err.println("Không thể tải ảnh đã chọn: " + e.getMessage());
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải ảnh đã chọn.");
            }
        }
    }

    private void setDefaultImage() {
        try {
            Image defaultImage = new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/default-avatar.png"), 100, 100, true, true);
            avatarImageView.setImage(defaultImage);
        } catch (Exception e) {
            System.err.println("Không thể tải ảnh mặc định: " + e.getMessage());
        }
    }

    @FXML
    private void saveChanges() {
        if (validateInputs()) {
            updateStudentFromFields();
            if (updateStudentInDatabase()) {
                studentUpdated = true;
                closeWindow();
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể cập nhật thông tin sinh viên.");
            }
        }
    }

    private boolean validateInputs() {
        StringBuilder errorMessage = new StringBuilder();

        // Kiểm tra các trường bắt buộc
        if (isNullOrEmpty(nameField.getText())) errorMessage.append("Họ và tên không được để trống.\n");
        if (isNullOrEmpty(addressField.getText())) errorMessage.append("Địa chỉ không được để trống.\n");
        if (isNullOrEmpty(phoneField.getText())) errorMessage.append("Số điện thoại không được để trống.\n");
        if (isNullOrEmpty(emailField.getText())) errorMessage.append("Email không được để trống.\n");
        if (birthDatePicker.getValue() == null) errorMessage.append("Ngày sinh không được để trống.\n");
        if (isNullOrEmpty(registrationNoField.getText())) errorMessage.append("Số đăng ký không được để trống.\n");
        if (isNullOrEmpty(batchField.getText())) errorMessage.append("Lớp không được để trống.\n");
        if (admissionDatePicker.getValue() == null) errorMessage.append("Ngày nhập học không được để trống.\n");
        if (sexComboBox.getValue() == null) errorMessage.append("Giới tính không được để trống.\n");

        // Kiểm tra định dạng email
        if (!isValidEmail(emailField.getText())) errorMessage.append("Email không hợp lệ.\n");

        // Kiểm tra số điện thoại
        if (!isValidPhoneNumber(phoneField.getText())) errorMessage.append("Số điện thoại không hợp lệ.\n");

        // Kiểm tra ngày sinh và ngày nhập học
        LocalDate birthDate = birthDatePicker.getValue();
        LocalDate admissionDate = admissionDatePicker.getValue();
        LocalDate now = LocalDate.now();

        if (birthDate != null) {
            // Kiểm tra tuổi học sinh (giả sử học sinh cấp 3 từ 14 đến 19 tuổi)
            LocalDate minBirthDate = now.minusYears(19);
            LocalDate maxBirthDate = now.minusYears(14);
            if (birthDate.isBefore(minBirthDate) || birthDate.isAfter(maxBirthDate)) {
                errorMessage.append("Ngày sinh không hợp lệ. Học sinh phải từ 14 đến 19 tuổi.\n");
            }
        }

        if (admissionDate != null) {
            // Kiểm tra ngày nhập học (giả sử không quá 3 năm trước và không trong tương lai)
            LocalDate minAdmissionDate = now.minusYears(3);
            if (admissionDate.isBefore(minAdmissionDate) || admissionDate.isAfter(now)) {
                errorMessage.append("Ngày nhập học không hợp lệ. Phải trong vòng 3 năm gần đây và không trong tương lai.\n");
            }
        }

        if (birthDate != null && admissionDate != null) {
            // Kiểm tra tuổi nhập học (giả sử học sinh nhập học từ 14 đến 18 tuổi)
            long ageAtAdmission = ChronoUnit.YEARS.between(birthDate, admissionDate);
            if (ageAtAdmission < 14 || ageAtAdmission > 18) {
                errorMessage.append("Tuổi nhập học không hợp lệ. Học sinh phải từ 14 đến 18 tuổi khi nhập học.\n");
            }
        }

        // Kiểm tra ClassID
        try {
            int classID = Integer.parseInt(classIDField.getText());
            if (classID <= 0) errorMessage.append("ClassID phải là số nguyên dương.\n");
        } catch (NumberFormatException e) {
            errorMessage.append("ClassID phải là số nguyên.\n");
        }

        if (errorMessage.length() > 0) {
            showAlert(Alert.AlertType.ERROR, "Lỗi Nhập Liệu", errorMessage.toString());
            return false;
        }

        return true;
    }

    private void updateStudentFromFields() {
        student.setFullName(nameField.getText());
        student.setAddress(addressField.getText());
        student.setPhone(phoneField.getText());
        student.setEmail(emailField.getText());
        student.setBirthDate(birthDatePicker.getValue() != null ? Date.valueOf(birthDatePicker.getValue()) : null);
        student.setRegistrationNo(registrationNoField.getText());
        student.setBatch(batchField.getText());
        student.setAdmissionDate(admissionDatePicker.getValue() != null ? Date.valueOf(admissionDatePicker.getValue()) : null);
        student.setPreviousInstitute(previousInstituteField.getText());
        student.setReasonForLeaving(reasonForLeavingField.getText());
        student.setGuardianContact(guardianContactField.getText());
        if (newImagePath != null) {
            student.setProfilePicture(newImagePath);
        }
        student.setClassID(Integer.parseInt(classIDField.getText()));
        student.setSex(sexComboBox.getValue());
    }

    private boolean updateStudentInDatabase() {
        String sql = "UPDATE student SET fullName = ?, address = ?, phone = ?, email = ?, birthDate = ?, " +
                "registrationNo = ?, batch = ?, admissionDate = ?, previousInstitute = ?, " +
                "reasonForLeaving = ?, guardianContact = ?, profilePicture = ?, classID = ?, sex = ? " +
                "WHERE studentID = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getFullName());
            pstmt.setString(2, student.getAddress());
            pstmt.setString(3, student.getPhone());
            pstmt.setString(4, student.getEmail());
            pstmt.setDate(5, student.getBirthDate() != null ? new java.sql.Date(student.getBirthDate().getTime()) : null);
            pstmt.setString(6, student.getRegistrationNo());
            pstmt.setString(7, student.getBatch());
            pstmt.setDate(8, student.getAdmissionDate() != null ? new java.sql.Date(student.getAdmissionDate().getTime()) : null);
            pstmt.setString(9, student.getPreviousInstitute());
            pstmt.setString(10, student.getReasonForLeaving());
            pstmt.setString(11, student.getGuardianContact());
            pstmt.setString(12, student.getProfilePicture());
            pstmt.setInt(13, student.getClassID());
            pstmt.setString(14, student.getSex());
            pstmt.setInt(15, student.getStudentID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    public boolean isStudentUpdated() {
        return studentUpdated;
    }

    public Student getUpdatedStudent() {
        return student;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phone) {
        // Giả sử số điện thoại chỉ chứa số và có độ dài từ 10 đến 11 chữ số
        return phone.matches("\\d{10,11}");
    }
}