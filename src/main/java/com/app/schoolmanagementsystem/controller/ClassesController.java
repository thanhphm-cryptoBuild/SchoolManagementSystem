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
    private AnchorPane moveBG;

    @FXML
    private StackPane pageClass;

    @FXML
    void addClassBTN(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/schoolmanagementsystem/views/PageAddClass.fxml"));
        StackPane pageAddClass = loader.load();

        AddClassController addClassController = loader.getController();
        addClassController.setPageClass(pageClass);
        addClassController.setBGPageClass(moveBG);

        pageAddClass.setTranslateX(2000);
        pageAddClass.setTranslateY(10);

//        pageStudent.getChildren().removeAll();
        pageClass.getChildren().add(pageAddClass);

        GaussianBlur gaussianBlur = new GaussianBlur(10);
        moveBG.setEffect(gaussianBlur);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(pageAddClass);
        translateTransition.setFromX(2000);
        translateTransition.setToY(6);
        translateTransition.setToX(440);

        translateTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
