package com.app.schoolmanagementsystem.controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private StackPane pageStudent;

    @FXML
    void addStudentBTN(MouseEvent event) throws IOException {
        StackPane pageAddStudent = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/PageAddStudent.fxml"));

        pageAddStudent.setTranslateX(2000);
        pageAddStudent.setTranslateY(10);

//        pageStudent.getChildren().removeAll();
        pageStudent.getChildren().add(pageAddStudent);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(pageAddStudent);
        translateTransition.setFromX(2000);
        translateTransition.setToY(9);
        translateTransition.setToX(500);

        translateTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
