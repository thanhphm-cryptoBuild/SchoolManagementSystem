package com.app.schoolmanagementsystem.controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClassesController implements Initializable {


    @FXML
    private StackPane pageClass;

    @FXML
    void addClassBTN(MouseEvent event) throws IOException {
        StackPane pageAddClass = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/PageAddClass.fxml"));

        pageAddClass.setTranslateX(2000);
        pageAddClass.setTranslateY(10);

//        pageStudent.getChildren().removeAll();
        pageClass.getChildren().add(pageAddClass);

//        GaussianBlur blur = new GaussianBlur(10);
//        bgRefPane.setEffect(blur);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(pageAddClass);
        translateTransition.setFromX(2000);
        translateTransition.setToY(9);
        translateTransition.setToX(500);

        translateTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
