package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.utils.ConnectDB;
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
import java.util.List;

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
    private Label studentCountLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataPieChart();
        loadDataLineChart();
    }

    void loadDataPieChart() {
        int maleStudents = ConnectDB.countStudentsByGender(true);
        int femaleStudents = ConnectDB.countStudentsByGender(false);
        int maleStaff = ConnectDB.countStaffByGender(true);
        int femaleStaff = ConnectDB.countStaffByGender(false);

        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data("Male Student", maleStudents),
                new PieChart.Data("Female Student", femaleStudents),
                new PieChart.Data("Male Staff", maleStaff),
                new PieChart.Data("Female Staff", femaleStaff)
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

        // Fetch staff data dynamically
        List<Integer> staffData = ConnectDB.getStaffData();
        for (int i = 0; i < staffData.size(); i++) {
            lineChartOne.getData().add(new XYChart.Data<>(String.valueOf(i + 1), staffData.get(i)));
        }

        XYChart.Series lineChartTwo = new XYChart.Series<>();
        lineChartTwo.setName("Student");

        // Fetch student data dynamically
        List<Integer> studentData = ConnectDB.getStudentData();
        for (int i = 0; i < studentData.size(); i++) {
            lineChartTwo.getData().add(new XYChart.Data<>(String.valueOf(i + 1), studentData.get(i)));
        }

        lineview.getData().addAll(lineChartOne, lineChartTwo);

        lineview.lookup(".chart-legend").setStyle("-fx-font-size: 15px; -fx-font-family: Sitka Text;");
    }
                    
}