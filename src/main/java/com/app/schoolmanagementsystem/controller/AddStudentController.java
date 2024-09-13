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

public class AddStudentController implements Initializable {

    @FXML
    private StackPane formAddStudent;

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
    void closeFormAddStudent(MouseEvent event) {

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(formAddStudent);
        translateTransition.setToX(2000);

        translateTransition.play();

        translateTransition.setOnFinished(actionEvent -> {
            pageStudent.getChildren().remove(formAddStudent);
            moveBG.setEffect(null);
        });

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
