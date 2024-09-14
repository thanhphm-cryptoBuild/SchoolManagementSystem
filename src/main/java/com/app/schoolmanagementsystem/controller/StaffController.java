package com.app.schoolmanagementsystem.controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StaffController implements Initializable {

    @FXML
    private AnchorPane moveBG;

    @FXML
    private StackPane pageStaff;

    @FXML
    void addStaffBTN(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/schoolmanagementsystem/views/PageAddStaff.fxml"));
        StackPane pageAddStaff = loader.load();

        AddStaffController addStaffController = loader.getController();
        addStaffController.setPageStaff(pageStaff);
        addStaffController.setBGPageStaff(moveBG);

        pageAddStaff.setTranslateX(2000);
        pageAddStaff.setTranslateY(6);

        pageStaff.getChildren().add(pageAddStaff);

        GaussianBlur gaussianBlur = new GaussianBlur(10);
        moveBG.setEffect(gaussianBlur);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(pageAddStaff);
        translateTransition.setFromX(2000);
        translateTransition.setToY(6);
        translateTransition.setToX(115);

        translateTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
