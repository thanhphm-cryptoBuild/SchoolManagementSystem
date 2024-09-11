package com.app.schoolmanagementsystem.controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StaffController implements Initializable {

    @FXML
    private StackPane pageStaff;

    @FXML
    void addStaffBTN(MouseEvent event) throws IOException {
        StackPane pageAddStaff = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/PageAddStaff.fxml"));

        pageAddStaff.setTranslateX(2000);
        pageAddStaff.setTranslateY(6);

//        pageStudent.getChildren().removeAll();
        pageStaff.getChildren().add(pageAddStaff);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(pageAddStaff);
        translateTransition.setFromX(2000);
        translateTransition.setToY(6);
        translateTransition.setToX(500);

        translateTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
