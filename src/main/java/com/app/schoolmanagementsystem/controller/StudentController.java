package com.app.schoolmanagementsystem.controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private AnchorPane moveBG;

    @FXML
    private StackPane pageStudent;

    @FXML
    void addStudentBTN(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/schoolmanagementsystem/views/PageAddStudent.fxml"));
        StackPane pageAddStudent = loader.load();

        AddStudentController addStudentController = loader.getController();
        addStudentController.setPageStudent(pageStudent);
        addStudentController.setBGPageStudent(moveBG);

        pageAddStudent.setTranslateX(2000);
        pageAddStudent.setTranslateY(10);

//        pageStudent.getChildren().removeAll();
        pageStudent.getChildren().add(pageAddStudent);

        GaussianBlur gaussianBlur = new GaussianBlur(10);
        moveBG.setEffect(gaussianBlur);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(pageAddStudent);
        translateTransition.setFromX(2000);
        translateTransition.setToY(6);
        translateTransition.setToX(420);
        translateTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
