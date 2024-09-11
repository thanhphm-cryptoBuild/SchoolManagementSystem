package com.app.schoolmanagementsystem.model;

import java.util.Date;

public class Student {
    private int studentID;
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private Date birthDate;
    private String registrationNo;
    private String batch;
    private Date admissionDate;
    private String previousInstitute;
    private String reasonForLeaving;
    private String guardianContact;
    private String profilePicture;
    private int classID;
    private String sex;
    private String className;

    // Constructor đầy đủ
    public Student(int studentID, String fullName, String address, String phone, String email, Date birthDate,
                   String registrationNo, String batch, Date admissionDate, String previousInstitute,
                   String reasonForLeaving, String guardianContact, String profilePicture, int classID, String sex) {
        this.studentID = studentID;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
        this.registrationNo = registrationNo;
        this.batch = batch;
        this.admissionDate = admissionDate;
        this.previousInstitute = previousInstitute;
        this.reasonForLeaving = reasonForLeaving;
        this.guardianContact = guardianContact;
        this.profilePicture = profilePicture;
        this.classID = classID;
        this.sex = sex;
    }

    // Thêm constructor cho id và name
    public Student(String id, String name) {
        this.studentID = Integer.parseInt(id); // Chuyển đổi id sang int
        this.fullName = name;
        // Khởi tạo các trường khác nếu cần
    }

    // Getters and Setters

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getPreviousInstitute() {
        return previousInstitute;
    }

    public void setPreviousInstitute(String previousInstitute) {
        this.previousInstitute = previousInstitute;
    }

    public String getReasonForLeaving() {
        return reasonForLeaving;
    }

    public void setReasonForLeaving(String reasonForLeaving) {
        this.reasonForLeaving = reasonForLeaving;
    }

    public String getGuardianContact() {
        return guardianContact;
    }

    public void setGuardianContact(String guardianContact) {
        this.guardianContact = guardianContact;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getSex() {
        return sex != null ? sex : "";  // Trả về chuỗi rỗng nếu sex là null
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
