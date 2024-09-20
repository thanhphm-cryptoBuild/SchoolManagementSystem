package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.application.Application;
import com.app.schoolmanagementsystem.entities.Staff;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane appDashBoard;

    @FXML
    private AnchorPane anchorSideBar;

    @FXML
    private ImageView close;

    @FXML
    private AnchorPane dropdownPane;

    @FXML
    private Pane navbarMain;

    @FXML
    private VBox iconAdvancedSB;

    @FXML
    private VBox iconTuitionSB;

    @FXML
    private VBox iconCalendarSB;

    @FXML
    private VBox iconClassSB;

    @FXML
    private VBox iconDashboardSB;

    @FXML
    private VBox iconSubjectSB;

    @FXML
    private ImageView iconGifCar;

    @FXML
    private VBox iconStaffSB;

    @FXML
    private VBox iconStudentSB;

    @FXML
    private VBox logo;

    @FXML
    private VBox logo_ref;

    @FXML
    private VBox nameAdvancedSB;

    @FXML
    private VBox nameTuitionSB;

    @FXML
    private VBox nameCalendarSB;

    @FXML
    private VBox nameClassSB;

    @FXML
    private VBox nameDashboardSB;

    @FXML
    private VBox nameSubjectSB;

    @FXML
    private VBox nameStaffSB;

    @FXML
    private VBox nameStudentSB;

    @FXML
    private Pane navigateSideBar;

    @FXML
    private ImageView open;

    @FXML
    private StackPane stackLoadPage;

    private boolean isDropdownOpen = false;

    private boolean isSideBarOpen = false;

    @FXML
    private Label roleLabel; // Thêm fx:id cho Label
    private String roleName;

    @FXML
    private ImageView img_avatar;

    @FXML ImageView img_logout;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            anchorSideBar.setTranslateX(0);
            anchorSideBar.toFront();
            navbarMain.toFront();
            stackLoadPage.toBack();
            open.setVisible(false);
            close.setVisible(true);

            loadPage("/com/app/schoolmanagementsystem/views/PageDashboard.fxml");


            dropdownPane.setViewOrder(10);
            navbarMain.setViewOrder(0);

        } catch(Exception e) {
            e.printStackTrace();
        }


    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
        roleLabel.setText(roleName);
    }


    private void loadPage(String page) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource(page));
        stackLoadPage.getChildren().removeAll();
        stackLoadPage.getChildren().setAll(fxml);

        anchorSideBar.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (dropdownPane.isVisible()) {
                closeDropdownPane();
            }
        });
    }


    @FXML
    void closeSideBar(MouseEvent event) {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode(anchorSideBar);
        slide.setToX(-130);
        slide.play();

        hideIcons();

        isSideBarOpen = false;

        slide.setOnFinished((ActionEvent e) -> {
            close.setVisible(false);
            open.setVisible(true);
        });
    }



    @FXML
    void openSideBar(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode(anchorSideBar);

        slide.setToX(0);
        slide.play();

        showIcons();

        isSideBarOpen = true;

        slide.setOnFinished((ActionEvent e) -> {
            close.setVisible(true);
            open.setVisible(false);
        });
    }


    @FXML
    void anchorSideBarExited(MouseEvent event) {

        if (isSideBarOpen) {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.2));
            slide.setNode(anchorSideBar);
            slide.setToX(-130);
            slide.play();

            hideIcons();

            isSideBarOpen = false;

            slide.setOnFinished((ActionEvent e) -> {
                close.setVisible(false);
                open.setVisible(true);
            });
        }
    }

    @FXML
    void anchorSideBarEntered(MouseEvent event) {

        if(isSideBarOpen == false) {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.2));
            slide.setNode(anchorSideBar);

            slide.setToX(0);
            slide.play();

            showIcons();

            isSideBarOpen = true;

            slide.setOnFinished((ActionEvent e) -> {
                close.setVisible(true);
                open.setVisible(false);
            });
        }
    }


    @FXML
    void dropdownBTN(MouseEvent event) {

        if (!isDropdownOpen) {
            dropdownPane.setTranslateY(-10);
            dropdownPane.setVisible(true);

            GaussianBlur gaussianBlur = new GaussianBlur(10);
            stackLoadPage.setEffect(gaussianBlur);

            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(0.2));
            translateTransition.setNode(dropdownPane);
            translateTransition.setFromY(-10);
            translateTransition.setToY(50);

            translateTransition.play();
            isDropdownOpen = true;
        } else {
            closeDropdownPane();
        }

    }

    private void closeDropdownPane() {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.1));
        translateTransition.setNode(dropdownPane);
        translateTransition.setFromY(50);
        translateTransition.setToY(-10);

        translateTransition.setOnFinished(event -> {
            dropdownPane.setVisible(false);
            stackLoadPage.setEffect(null);
        });

        translateTransition.play();

        isDropdownOpen = false;
    }

    private void hideIcons() {
        nameDashboardSB.setVisible(false);
        nameClassSB.setVisible(false);
        nameStudentSB.setVisible(false);
        nameStaffSB.setVisible(false);
        nameSubjectSB.setVisible(false);
        nameAdvancedSB.setVisible(false);
        nameTuitionSB.setVisible(false);
        nameCalendarSB.setVisible(false);
        logo.setVisible(false);
        logo_ref.setVisible(true);

        iconDashboardSB.setTranslateX(130);
        iconClassSB.setTranslateX(130);
        iconStudentSB.setTranslateX(130);
        iconStaffSB.setTranslateX(130);
        iconSubjectSB.setTranslateX(130);
        iconAdvancedSB.setTranslateX(130);
        iconTuitionSB.setTranslateX(130);
        iconCalendarSB.setTranslateX(130);
        navigateSideBar.setTranslateX(65);
        iconGifCar.setTranslateX(130);

    }

    private void showIcons() {
        nameDashboardSB.setVisible(true);
        nameClassSB.setVisible(true);
        nameStudentSB.setVisible(true);
        nameStaffSB.setVisible(true);
        nameSubjectSB.setVisible(true);
        nameAdvancedSB.setVisible(true);
        nameTuitionSB.setVisible(true);
        nameCalendarSB.setVisible(true);
        logo.setVisible(true);
        logo_ref.setVisible(false);

        iconDashboardSB.setTranslateX(0);
        iconClassSB.setTranslateX(0);
        iconStudentSB.setTranslateX(0);
        iconStaffSB.setTranslateX(0);
        iconSubjectSB.setTranslateX(0);
        iconAdvancedSB.setTranslateX(0);
        iconTuitionSB.setTranslateX(0);
        iconCalendarSB.setTranslateX(0);
        navigateSideBar.setTranslateX(0);
        iconGifCar.setTranslateX(0);
    }


    @FXML
    void buttonAdvanced(MouseEvent event) throws IOException {
        loadPage("/com/app/schoolmanagementsystem/views/PageAdvanced.fxml");
    }


    @FXML
    void buttonTuition(MouseEvent event) throws IOException {
        loadPage("/com/app/schoolmanagementsystem/views/PageTuition.fxml");
    }

    @FXML
    void buttonCalendar(MouseEvent event) throws IOException {
        loadPage("/com/app/schoolmanagementsystem/views/PageCalendar.fxml");
    }


    @FXML
    void buttonClass(MouseEvent event) throws IOException {
        loadPage("/com/app/schoolmanagementsystem/views/PageClass.fxml");
    }

    @FXML
    void buttonSubject(MouseEvent event) throws IOException {
        loadPage("/com/app/schoolmanagementsystem/views/PageSubject.fxml");
    }

    @FXML
    void buttonHome(MouseEvent event) throws IOException {
        loadPage("/com/app/schoolmanagementsystem/views/PageDashboard.fxml");
    }

    @FXML
    void buttonStaff(MouseEvent event) throws IOException {
        loadPage("/com/app/schoolmanagementsystem/views/PageStaff.fxml");
    }


    @FXML
    void buttonStudent(MouseEvent event) throws IOException {
        loadPage("/com/app/schoolmanagementsystem/views/PageStudent.fxml");
    }

    @FXML
    void handle_Logout(MouseEvent event) throws IOException {
        // Tải trang đăng nhập
        Parent loginPage = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/SignIn.fxml"));

        // Lấy cửa sổ hiện tại
        Stage currentStage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();

        // Đặt trang đăng nhập làm trang chính
        Scene scene = new Scene(loginPage);
        currentStage.setScene(scene);
        currentStage.show();
    }


}