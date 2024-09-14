package com.app.schoolmanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EditStudentController implements Initializable {

    @FXML
    private StackPane formEditStudent;

    @FXML
    private StackPane pageStudent;

    @FXML
    private AnchorPane moveBG;

    public void setPageStudent(StackPane pageStudent) {
        this.pageStudent = pageStudent;
    }

    public void setBGPageStudent(AnchorPane moveBG) {
        this.moveBG = moveBG;
    }

    @FXML
    void closeFormEditStudent(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
