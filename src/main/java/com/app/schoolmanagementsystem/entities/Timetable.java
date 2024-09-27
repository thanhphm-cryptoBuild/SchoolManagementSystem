package com.app.schoolmanagementsystem.entities;

import java.time.LocalDate;

public class Timetable {
    private int timetableID;
    private String time;
    private String className;
    private String teacher;
    private String subject;
    private String description;
    private LocalDate date;

    public Timetable(int timetableID, String time, String className, String teacher, String subject, String description, LocalDate date) {
        this.timetableID = timetableID;
        this.time = time;
        this.className = className;
        this.teacher = teacher;
        this.subject = subject;
        this.description = description;
        this.date = date;
    }

    public int getTimetableID() {
        return timetableID;
    }

    public void setTimetableID(int timetableID) {
        this.timetableID = timetableID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date; 
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}