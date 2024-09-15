package com.app.schoolmanagementsystem.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane anchorSideBar;

    @FXML
    private ImageView close;

    @FXML
    private VBox iconAdvancedSB;

    @FXML
    private VBox iconCalendarSB;

    @FXML
    private VBox iconClassSB;

    @FXML
    private VBox iconDashboardSB;

    @FXML
    private VBox iconEventSB;

    @FXML
    private VBox iconStaffSB;

    @FXML
    private VBox iconStudentSB;

    @FXML
    private ImageView iconGifCar;

    @FXML
    private VBox nameAdvancedSB;

    @FXML
    private VBox nameCalendarSB;

    @FXML
    private VBox nameClassSB;

    @FXML
    private VBox nameDashboardSB;

    @FXML
    private VBox nameEventSB;

    @FXML
    private VBox nameStaffSB;

    @FXML
    private VBox nameStudentSB;

    @FXML
    private Pane navigateSideBar;

    @FXML
    private VBox logo;

    @FXML
    private VBox logo_ref;

    @FXML
    private ImageView open;

    @FXML
    private StackPane stackLoadPage;

    @FXML
    private Label roleLabel; // Thêm fx:id cho Label
    private String roleName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            anchorSideBar.setTranslateX(0);
            anchorSideBar.toFront();
            open.setVisible(false);
            close.setVisible(true);

            Parent fxml = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/PageDashboard.fxml"));
            stackLoadPage.getChildren().removeAll();
            stackLoadPage.getChildren().setAll(fxml);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
        roleLabel.setText(roleName);
    }

    @FXML
    void closeSideBar(MouseEvent event) {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.05));
        slide.setNode(anchorSideBar);
        slide.setToX(-130);
        slide.play();

//        TranslateTransition iconSlideDashboard = new TranslateTransition(Duration.seconds(0.2), iconDashboardSB);
//        iconSlideDashboard.setToX(130);
//        nameDashboardSB.setVisible(false);
//        iconSlideDashboard.play();
//
//        TranslateTransition iconSlideClass = new TranslateTransition(Duration.seconds(0.2), iconClassSB);
//        iconSlideClass.setToX(130);
//        nameClassSB.setVisible(false);
//        iconSlideClass.play();
//
//        TranslateTransition iconSlideStudent = new TranslateTransition(Duration.seconds(0.2), iconStudentSB);
//        iconSlideStudent.setToX(130);
//        nameStudentSB.setVisible(false);
//        iconSlideStudent.play();
//
//        TranslateTransition iconSlideStaff = new TranslateTransition(Duration.seconds(0.2), iconStaffSB);
//        iconSlideStaff.setToX(130);
//        nameStaffSB.setVisible(false);
//        iconSlideStaff.play();
//
//        TranslateTransition iconSlideEvent = new TranslateTransition(Duration.seconds(0.2), iconEventSB);
//        iconSlideEvent.setToX(130);
//        nameEventSB.setVisible(false);
//        iconSlideEvent.play();
//
//        TranslateTransition iconSlideAdvanced = new TranslateTransition(Duration.seconds(0.2), iconAdvancedSB);
//        iconSlideAdvanced.setToX(130);
//        nameAdvancedSB.setVisible(false);
//        iconSlideAdvanced.play();
//
//        TranslateTransition iconSlideCalendar = new TranslateTransition(Duration.seconds(0.2), iconCalendarSB);
//        iconSlideCalendar.setToX(130);
//        nameCalendarSB.setVisible(false);
//        iconSlideCalendar.play();
//
//        TranslateTransition navigateSB = new TranslateTransition(Duration.seconds(0.2), navigateSideBar);
//        navigateSB.setToX(65);
//        navigateSB.play();

        hideIcons();

        slide.setOnFinished((ActionEvent e) -> {
            close.setVisible(false);
            open.setVisible(true);
        });
    }

    @FXML
    void openSideBar(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.05));
        slide.setNode(anchorSideBar);

        slide.setToX(0);
        slide.play();

//        TranslateTransition iconSlideDashboard = new TranslateTransition(Duration.seconds(0.2), iconDashboardSB);
//        iconSlideDashboard.setToX(0);
//        nameDashboardSB.setVisible(true);
//        iconSlideDashboard.play();
//
//        TranslateTransition iconSlideClass = new TranslateTransition(Duration.seconds(0.2), iconClassSB);
//        iconSlideClass.setToX(0);
//        nameClassSB.setVisible(true);
//        iconSlideClass.play();
//
//        TranslateTransition iconSlideStudent = new TranslateTransition(Duration.seconds(0.2), iconStudentSB);
//        iconSlideStudent.setToX(0);
//        nameStudentSB.setVisible(true);
//        iconSlideStudent.play();
//
//        TranslateTransition iconSlideStaff = new TranslateTransition(Duration.seconds(0.2), iconStaffSB);
//        iconSlideStaff.setToX(0);
//        nameStaffSB.setVisible(true);
//        iconSlideStaff.play();
//
//        TranslateTransition iconSlideEvent = new TranslateTransition(Duration.seconds(0.2), iconEventSB);
//        iconSlideEvent.setToX(0);
//        nameEventSB.setVisible(true);
//        iconSlideEvent.play();
//
//        TranslateTransition iconSlideAdvanced = new TranslateTransition(Duration.seconds(0.2), iconAdvancedSB);
//        iconSlideAdvanced.setToX(0);
//        nameAdvancedSB.setVisible(true);
//        iconSlideAdvanced.play();
//
//        TranslateTransition iconSlideCalendar = new TranslateTransition(Duration.seconds(0.2), iconCalendarSB);
//        iconSlideCalendar.setToX(0);
//        nameCalendarSB.setVisible(true);
//        iconSlideCalendar.play();
//
//        TranslateTransition navigateSB = new TranslateTransition(Duration.seconds(0.2), navigateSideBar);
//        navigateSB.setToX(0);
//        navigateSB.play();
        showIcons();
        slide.setOnFinished((ActionEvent e) -> {
            close.setVisible(true);
            open.setVisible(false);
        });
    }

    private void hideIcons() {
        nameDashboardSB.setVisible(false);
        nameClassSB.setVisible(false);
        nameStudentSB.setVisible(false);
        nameStaffSB.setVisible(false);
        nameEventSB.setVisible(false);
        nameAdvancedSB.setVisible(false);
        nameCalendarSB.setVisible(false);
        logo.setVisible(false);
        logo_ref.setVisible(true);

        iconDashboardSB.setTranslateX(130);
        iconClassSB.setTranslateX(130);
        iconStudentSB.setTranslateX(130);
        iconStaffSB.setTranslateX(130);
        iconEventSB.setTranslateX(130);
        iconAdvancedSB.setTranslateX(130);
        iconCalendarSB.setTranslateX(130);
        navigateSideBar.setTranslateX(65);
        iconGifCar.setTranslateX(130);

    }

    private void showIcons() {
        nameDashboardSB.setVisible(true);
        nameClassSB.setVisible(true);
        nameStudentSB.setVisible(true);
        nameStaffSB.setVisible(true);
        nameEventSB.setVisible(true);
        nameAdvancedSB.setVisible(true);
        nameCalendarSB.setVisible(true);
        logo.setVisible(true);
        logo_ref.setVisible(false);

        iconDashboardSB.setTranslateX(0);
        iconClassSB.setTranslateX(0);
        iconStudentSB.setTranslateX(0);
        iconStaffSB.setTranslateX(0);
        iconEventSB.setTranslateX(0);
        iconAdvancedSB.setTranslateX(0);
        iconCalendarSB.setTranslateX(0);
        navigateSideBar.setTranslateX(0);
        iconGifCar.setTranslateX(0);
    }

    @FXML
    void buttonAdvanced(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/PageAdvanced.fxml"));
        stackLoadPage.getChildren().removeAll();
        stackLoadPage.getChildren().setAll(fxml);
    }

    @FXML
    void buttonCalendar(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/PageCalendar.fxml"));
        stackLoadPage.getChildren().removeAll();
        stackLoadPage.getChildren().setAll(fxml);
    }

    @FXML
    void buttonClass(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/PageClass.fxml"));
        stackLoadPage.getChildren().removeAll();
        stackLoadPage.getChildren().setAll(fxml);
    }

    @FXML
    void buttonEvent(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/PageEvent.fxml"));
        stackLoadPage.getChildren().removeAll();
        stackLoadPage.getChildren().setAll(fxml);
    }

    @FXML
    void buttonHome(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/PageDashboard.fxml"));
        stackLoadPage.getChildren().removeAll();
        stackLoadPage.getChildren().setAll(fxml);
    }

    @FXML
    void buttonStaff(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/PageStaff.fxml"));
        stackLoadPage.getChildren().removeAll();
        stackLoadPage.getChildren().setAll(fxml);
    }

    @FXML
    void buttonStudent(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/PageStudent.fxml"));
        stackLoadPage.getChildren().removeAll();
        stackLoadPage.getChildren().setAll(fxml);
    }


}