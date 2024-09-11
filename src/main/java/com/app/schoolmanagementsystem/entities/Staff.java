package com.app.schoolmanagementsystem.entities;

import java.math.BigDecimal;
import java.sql.Date;

public class Staff {
    private int staffID;
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private Date birthDate;
    private String department;
    private String position;
    private Date hireDate;
    private BigDecimal salary;
    private Date lastLogin;
    private String profilePicture;
    private String sex;
    private String status;

    public Staff(int staffID, String fullName, String address, String phone, String email, Date birthDate, String department, String position, Date hireDate, BigDecimal salary, Date lastLogin, String profilePicture, String sex, String status) {
        this.staffID = staffID;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
        this.department = department;
        this.position = position;
        this.hireDate = hireDate;
        this.salary = salary;
        this.lastLogin = lastLogin;
        this.profilePicture = profilePicture;
        this.sex = sex;
        this.status = status;
    }

    public Staff() {
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
