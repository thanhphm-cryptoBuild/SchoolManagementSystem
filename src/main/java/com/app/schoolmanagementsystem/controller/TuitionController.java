package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.TuitionFee;
import com.app.schoolmanagementsystem.utils.ConnectDB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TuitionController implements Initializable {

    @FXML
    private TableView<TuitionFee> tuitionTable;

    @FXML
    private TableColumn<TuitionFee, Integer> columnTuitionFeeID;

    @FXML
    private TableColumn<TuitionFee, Integer> columnStudentID;

    @FXML
    private TableColumn<TuitionFee, BigDecimal> columnAmount;

    @FXML
    private TableColumn<TuitionFee, LocalDate> columnDueDate;

    @FXML
    private TableColumn<TuitionFee, LocalDate> columnPaidDate;

    @FXML
    private TableColumn<TuitionFee, String> columnStatus;

    @FXML
    private TableColumn<TuitionFee, String> columnDescription;

    private ObservableList<TuitionFee> tuitionFeeList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tuitionFeeList = FXCollections.observableArrayList();

        // Set up the columns in the table
        columnTuitionFeeID.setCellValueFactory(new PropertyValueFactory<>("tuitionFeeID"));
        columnStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        columnDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        columnPaidDate.setCellValueFactory(new PropertyValueFactory<>("paidDate"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Load data from database (this is just a placeholder, replace with actual database call)
        loadTuitionFees();
        
        // Add data to the table
        tuitionTable.setItems(tuitionFeeList);
    }

    private void loadTuitionFees() {
        String query = "SELECT * FROM tuition_fees"; // Update with your actual table name

        try (Connection conn = ConnectDB.connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int tuitionFeeID = rs.getInt("tuitionFeeID");
                int studentID = rs.getInt("studentID");
                BigDecimal amount = rs.getBigDecimal("amount");
                LocalDate dueDate = rs.getDate("dueDate").toLocalDate();
                LocalDate paidDate = rs.getDate("paidDate").toLocalDate();
                String status = rs.getString("status");
                String description = rs.getString("description");

                tuitionFeeList.add(new TuitionFee(tuitionFeeID, studentID, amount, dueDate, paidDate, status, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
}
