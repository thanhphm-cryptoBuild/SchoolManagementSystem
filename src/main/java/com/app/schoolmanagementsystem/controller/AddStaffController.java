package com.app.schoolmanagementsystem.controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AddStaffController implements Initializable {

    @FXML
    private StackPane formAddStaff;

    private StackPane pageStaff;

    @FXML
    private AnchorPane moveBG;

    public void setPageStaff(StackPane pageStaff) {
        this.pageStaff = pageStaff;
    }

    public void setBGPageStaff(AnchorPane moveBG) {
        this.moveBG = moveBG;
    }

    @FXML
    void closeFormAdd(MouseEvent event) {

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(formAddStaff);
        translateTransition.setToX(2000);

        translateTransition.play();

        translateTransition.setOnFinished(actionEvent -> {
            pageStaff.getChildren().remove(formAddStaff);
            moveBG.setEffect(null);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
