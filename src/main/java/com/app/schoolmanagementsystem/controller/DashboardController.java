package com.app.schoolmanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private LineChart<?, ?> lineview;

    @FXML
    private AnchorPane pageDashboard;

    @FXML
    private StackPane panepie;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private Label label_totalStaff;

    private com.app.schoolmanagementsystem.model.StaffModel staffModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataPieChart();
        loadDataLineChart();
        staffModel = new com.app.schoolmanagementsystem.model.StaffModel();
        updateTotalStaff();
    }

    private void updateTotalStaff() {
        int totalStaff = staffModel.countStaff();
        label_totalStaff.setText(String.valueOf(totalStaff));
    }
    void loadDataPieChart() {

        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data("Male Staff", 20),
                new PieChart.Data("Female Staff", 60),
                new PieChart.Data("Male Student", 75),
                new PieChart.Data("Female Student", 90)
        );

        PieChart pieChart = new PieChart(list);
        pieChart.setMaxWidth(600);
        pieChart.setMaxHeight(400);
        pieChart.setClockwise(true);
        pieChart.setLabelsVisible(true);
        pieChart.setStyle("-fx-font-family: Sitka Text; -fx-font-size: 14px;");
        pieChart.setLabelLineLength(15);

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


        lineview.lookup(".chart-legend").setStyle("-fx-font-size: 15px; -fx-font-family: Sitka Text;");
    }
}
