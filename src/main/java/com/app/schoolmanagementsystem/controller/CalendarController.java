package com.app.schoolmanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    @FXML
    private Pane cardAddCalendar;

    @FXML
    private StackPane datePicker;

    @FXML
    private StackPane pageCalendar;

    void calendarPicker() {
        DatePicker datePickerControl = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePickerControl);
        Node datePickerContent = datePickerSkin.getPopupContent();

        datePickerContent.setStyle("-fx-pref-width: 500px; -fx-pref-height: 500px;");

        datePicker.getChildren().add(datePickerContent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calendarPicker();
    }
}
