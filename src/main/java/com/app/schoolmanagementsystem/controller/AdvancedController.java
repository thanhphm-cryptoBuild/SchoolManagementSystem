package com.app.schoolmanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AdvancedController implements Initializable {

    @FXML
    private VBox layoutStaff;

    @FXML
    private VBox layoutStudent;

    @FXML
    private AnchorPane moveBG;

    @FXML
    private StackPane pageAdvanced;



    @FXML
    void openTableStaff(MouseEvent event) {
        layoutStaff.setVisible(true);
        layoutStudent.setVisible(false);
    }

    @FXML
    void openTableStudent(MouseEvent event) {
        layoutStaff.setVisible(false);
        layoutStudent.setVisible(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
