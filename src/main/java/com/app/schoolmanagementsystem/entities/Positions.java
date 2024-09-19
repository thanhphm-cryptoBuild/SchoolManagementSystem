package com.app.schoolmanagementsystem.entities;

public class Positions {

    private int positionID;

    private String positionName;

    private String description;

    public Positions(int positionID, String positionName, String description) {
        this.positionID = positionID;
        this.positionName = positionName;
        this.description = description;
    }

    public Positions() {
    }

    public int getPositionID() {
        return positionID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
