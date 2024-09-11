package com.app.schoolmanagementsystem.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class

Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/app/schoolmanagementsystem/views/Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1028, 768);
        stage.setMinWidth(1028);
        stage.setMinHeight(768);
        stage.setTitle("School Management System");
        stage.setScene(scene);
//        stage.setFullScreen(true);
        stage.centerOnScreen();
// hhh
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
