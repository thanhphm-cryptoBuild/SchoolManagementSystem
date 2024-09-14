package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.application.Application;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable{

    @FXML
    private AnchorPane pageSignIn;

    @FXML
    private StackPane changePWPane;

    @FXML
    private StackPane forgotPWPane;

    @FXML
    private StackPane formForgotChange;

    private boolean isSubmit = false;

    @FXML
    void loginSubmit(MouseEvent event) throws IOException {

        Stage stage = (Stage) pageSignIn.getScene().getWindow();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/app/schoolmanagementsystem/views/Dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1366, 780);
            stage.setResizable(false);
            stage.setTitle("School Management System");
            stage.setScene(scene);
            stage.centerOnScreen();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void closeFromForgotChange(MouseEvent event) {

            formForgotChange.setTranslateY(0);
//            formForgotChange.setVisible(false);
            forgotPWPane.setVisible(false);
            changePWPane.setVisible(false);

            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(Duration.seconds(0.4));
            transition.setNode(formForgotChange);
            transition.setFromY(0);
            transition.setToY(750);
            transition.play();



            forgotPWPane.setTranslateX(-50);
            forgotPWPane.setVisible(false);
            changePWPane.setVisible(false);

            TranslateTransition backtransition = new TranslateTransition();
            backtransition.setDuration(Duration.seconds(0.3));
            backtransition.setNode(forgotPWPane);
            backtransition.setFromX(-50);
            backtransition.setToX(0);
            backtransition.play();
    }

    @FXML
    void openFormForgotChange(MouseEvent event) {

        formForgotChange.setTranslateY(500);
        formForgotChange.setVisible(true);
        forgotPWPane.setVisible(true);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(formForgotChange);

        transition.setFromY(750);
        transition.setToY(0);

        transition.play();
    }

    @FXML
    void sendCodeBTN(MouseEvent event) {

        forgotPWPane.setTranslateX(500);
        forgotPWPane.setVisible(true);
        changePWPane.setVisible(true);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(changePWPane);

        transition.setFromX(500);
        transition.setToX(0);

        transition.play();
    }

    @FXML
    void submitFormForgotChange(MouseEvent event) {
        closeFromForgotChange(event);
    }



    @FXML
    void backToForgotPW(MouseEvent event) {

        forgotPWPane.setTranslateX(-50);
        forgotPWPane.setVisible(true);
        changePWPane.setVisible(false);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(forgotPWPane);

        transition.setFromX(-50);
        transition.setToX(0);

        transition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
