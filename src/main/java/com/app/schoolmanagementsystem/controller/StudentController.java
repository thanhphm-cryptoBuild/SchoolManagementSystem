package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.model.StudentModel;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private StackPane pageStudent;

    @FXML
    private TableView<StudentModel> studentTable;

    @FXML
    private TableColumn<StudentModel, Integer> colStudentID;

    @FXML
    private TableColumn<StudentModel, String> colFirstName;

    @FXML
    private TableColumn<StudentModel, String> colLastName;

    @FXML
    private TableColumn<StudentModel, java.sql.Date> colDateOfBirth;

    @FXML
    private TableColumn<StudentModel, Boolean> colGender;

    @FXML
    private TableColumn<StudentModel, String> colAddress;

    @FXML
    private TableColumn<StudentModel, String> colPhoneNumber;

    @FXML
    private TableColumn<StudentModel, String> colEmail;

    @FXML
    private TableColumn<StudentModel, java.sql.Date> colEnrollmentDate;

    @FXML
    private TableColumn<StudentModel, Integer> colClassID;

    @FXML
    private TableColumn<StudentModel, String> colStatus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        loadStudentData();
    }

    private void setupTableColumns() {
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEnrollmentDate.setCellValueFactory(new PropertyValueFactory<>("enrollmentDate"));
        colClassID.setCellValueFactory(new PropertyValueFactory<>("classID"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadStudentData() {
        ObservableList<StudentModel> studentData = FXCollections.observableArrayList();
        String query = "SELECT * FROM students";
        
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                StudentModel student = new StudentModel(
                    rs.getInt("StudentID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getDate("DateOfBirth"),
                    rs.getBoolean("Gender"),
                    rs.getString("Address"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Email"),
                    rs.getDate("EnrollmentDate"),
                    rs.getInt("ClassID"),
                    rs.getString("Status")
                );
                studentData.add(student);
            }
            
            studentTable.setItems(studentData);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                ConnectDB.closeConnection(conn);
            }
        }
    }

    @FXML
    void addStudentBTN(MouseEvent event) throws IOException {
        StackPane pageAddStudent = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/PageAddStudent.fxml"));

        pageAddStudent.setTranslateX(2000);
        pageAddStudent.setTranslateY(10);

        pageStudent.getChildren().add(pageAddStudent);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(pageAddStudent);
        translateTransition.setFromX(2000);
        translateTransition.setToY(9);
        translateTransition.setToX(500);

        translateTransition.play();
    }
}
