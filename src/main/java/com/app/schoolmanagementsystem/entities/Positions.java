package com.app.schoolmanagementsystem.entities;

public class Positions {
    private int PositionID;
    private String PositionName;
    private String Description;

    public Positions() {
    }

    public Positions(int positionID, String positionName, String description) {
        PositionID = positionID;
        PositionName = positionName;
        Description = description;
    }


    public int getPositionID() {
        return PositionID;
    }

    public void setPositionID(int positionID) {
        PositionID = positionID;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String positionName) {
        PositionName = positionName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}