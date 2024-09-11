package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController {

    @FXML
    private AnchorPane pageSignIn;

    @FXML
    void loginSubmit(MouseEvent event) throws IOException {

        Stage stage = (Stage) pageSignIn.getScene().getWindow();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/app/schoolmanagementsystem/views/Dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1366, 780);
            stage.setResizable(false);
//          stage.setMinWidth(1366);
//          stage.setMinHeight(780);
//          stage.setMaxWidth(1440);
//          stage.setMaxHeight(780);
            stage.setTitle("School Management System");
            stage.setScene(scene);
            stage.centerOnScreen();

//          stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
