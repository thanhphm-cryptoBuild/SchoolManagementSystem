package com.app.schoolmanagementsystem.entities;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TuitionFee {
    private int tuitionFeeID;
    private int studentID;
    private BigDecimal amount;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private String status;
    private String description;

    public TuitionFee(int tuitionFeeID, int studentID, BigDecimal amount, LocalDate dueDate, LocalDate paidDate, String status, String description) {
        this.tuitionFeeID = tuitionFeeID;
        this.studentID = studentID;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paidDate = paidDate;
        this.status = status;
        this.description = description;
    }
    // Getters and Setters
    public int getTuitionFeeID() {
        return tuitionFeeID;
    }

    public void setTuitionFeeID(int tuitionFeeID) {
        this.tuitionFeeID = tuitionFeeID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}