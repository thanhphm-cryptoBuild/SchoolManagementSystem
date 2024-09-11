package com.app.schoolmanagementsystem.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/app/schoolmanagementsystem/views/SignIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 550);
        stage.setResizable(false);
        stage.setTitle("School Management System");
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
