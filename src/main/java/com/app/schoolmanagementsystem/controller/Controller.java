package com.app.schoolmanagementsystem.controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

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
    private LineChart<?, ?> lineview;

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
    private StackPane panepie;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorSideBar.setTranslateX(0);
        anchorSideBar.toFront();
        open.setVisible(false);
        close.setVisible(true);
        loadDataPieChart();
        loadDataLineChart();
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

    void loadDataPieChart() {

        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data("Male Staff", 20),
                new PieChart.Data("Female Staff", 60),
                new PieChart.Data("Male Student", 75),
                new PieChart.Data("Female Student", 90)
        );

        PieChart pieChart = new PieChart(list);
        pieChart.setMaxWidth(500);
        pieChart.setMaxHeight(350);
        pieChart.setClockwise(true);
        pieChart.setLabelsVisible(true);
        pieChart.setStyle("-fx-font-weight: regular; -fx-font-family: Sitka Text; -fx-font-size: 14px;");
        pieChart.setLabelLineLength(10);


        panepie.getChildren().add(pieChart);

    }

    void loadDataLineChart() {
        XYChart.Series lineChartOne = new XYChart.Series<>();
        lineChartOne.setName("Staff");

        lineChartOne.getData().add(new XYChart.Data<>("1", 2));
        lineChartOne.getData().add(new XYChart.Data<>("2", 10));
        lineChartOne.getData().add(new XYChart.Data<>("3", 9));
        lineChartOne.getData().add(new XYChart.Data<>("4", 11));
        lineChartOne.getData().add(new XYChart.Data<>("5", 15));
        lineChartOne.getData().add(new XYChart.Data<>("6", 10));
        lineChartOne.getData().add(new XYChart.Data<>("7", 14));
        lineChartOne.getData().add(new XYChart.Data<>("8", 20));
        lineChartOne.getData().add(new XYChart.Data<>("9", 21));
        lineChartOne.getData().add(new XYChart.Data<>("10", 22));
        lineChartOne.getData().add(new XYChart.Data<>("11", 34));
        lineChartOne.getData().add(new XYChart.Data<>("12", 50));

        XYChart.Series lineChartTwo = new XYChart.Series<>();
        lineChartTwo.setName("Teacher    ");

        lineChartTwo.getData().add(new XYChart.Data<>("1", 5));
        lineChartTwo.getData().add(new XYChart.Data<>("2", 2));
        lineChartTwo.getData().add(new XYChart.Data<>("3", 15));
        lineChartTwo.getData().add(new XYChart.Data<>("4", 7));
        lineChartTwo.getData().add(new XYChart.Data<>("5", 20));
        lineChartTwo.getData().add(new XYChart.Data<>("6", 15));
        lineChartTwo.getData().add(new XYChart.Data<>("7", 19));
        lineChartTwo.getData().add(new XYChart.Data<>("8", 36));
        lineChartTwo.getData().add(new XYChart.Data<>("9", 50));
        lineChartTwo.getData().add(new XYChart.Data<>("10", 62));
        lineChartTwo.getData().add(new XYChart.Data<>("11", 70));
        lineChartTwo.getData().add(new XYChart.Data<>("12", 85));

        XYChart.Series lineChartThree = new XYChart.Series<>();
        lineChartThree.setName(" Student");

        lineChartThree.getData().add(new XYChart.Data<>("1", 50));
        lineChartThree.getData().add(new XYChart.Data<>("2", 30));
        lineChartThree.getData().add(new XYChart.Data<>("3", 10));
        lineChartThree.getData().add(new XYChart.Data<>("4", 25));
        lineChartThree.getData().add(new XYChart.Data<>("5", 12));
        lineChartThree.getData().add(new XYChart.Data<>("6", 15));
        lineChartThree.getData().add(new XYChart.Data<>("7", 60));
        lineChartThree.getData().add(new XYChart.Data<>("8", 62));
        lineChartThree.getData().add(new XYChart.Data<>("9", 68));
        lineChartThree.getData().add(new XYChart.Data<>("10", 89));
        lineChartThree.getData().add(new XYChart.Data<>("11", 80));
        lineChartThree.getData().add(new XYChart.Data<>("12", 100));

        lineview.getData().addAll(lineChartOne, lineChartTwo, lineChartThree);
    }

}
