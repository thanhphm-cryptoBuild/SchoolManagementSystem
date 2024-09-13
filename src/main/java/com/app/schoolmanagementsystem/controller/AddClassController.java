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

public class AddClassController implements Initializable {

    @FXML
    private StackPane formAddClass;

    private StackPane pageClass;

    @FXML
    private AnchorPane moveBG;

    public void setPageClass(StackPane pageClass) {
        this.pageClass = pageClass;
    }

    public void setBGPageClass(AnchorPane moveBG) {
        this.moveBG = moveBG;
    }

    @FXML
    void closeFormAddClass(MouseEvent event) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(formAddClass);
        translateTransition.setToX(2000);

        translateTransition.play();

        translateTransition.setOnFinished(actionEvent -> {
            pageClass.getChildren().remove(formAddClass);
            moveBG.setEffect(null);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
