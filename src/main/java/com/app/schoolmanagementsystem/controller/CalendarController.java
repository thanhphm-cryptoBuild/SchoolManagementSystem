package com.app.schoolmanagementsystem.controller;
import com.app.schoolmanagementsystem.entities.CalendarWork;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;

public class CalendarController implements Initializable {

    @FXML
    private StackPane datePicker;

    @FXML
    private VBox showDate;

    @FXML
    private TableView<CalendarWork> calendarTable;

    @FXML
    private TableColumn<CalendarWork, String> timeColumn;

    @FXML
    private TableColumn<CalendarWork, String> classNameColumn;

    @FXML
    private TableColumn<CalendarWork, String> teacherColumn;

    @FXML
    private TableColumn<CalendarWork, String> subjectColumn;

    @FXML
    private TableColumn<CalendarWork, String> descriptionColumn;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");

    @FXML
    private ChoiceBox<String> teacherChoiceBox;

    @FXML
    private ChoiceBox<String> subjectChoiceBox;

    @FXML
    private ChoiceBox<String> classNameChoiceBox;

    void calendarPicker() {
        DatePicker datePickerControl = new DatePicker(LocalDate.now());
        updateShowDate(LocalDate.now());

        DatePickerSkin datePickerSkin = new DatePickerSkin(datePickerControl);
        Node datePickerContent = datePickerSkin.getPopupContent();

        datePickerContent.setStyle("-fx-pref-width: 500px; -fx-pref-height: 500px;");
        datePicker.getChildren().add(datePickerContent);

        datePickerControl.setOnAction(event -> {
            LocalDate selectedDate = datePickerControl.getValue();
            updateShowDate(selectedDate);
            loadCalendarWork(selectedDate);
        });
    }

    private void updateShowDate(LocalDate date) {
        showDate.getChildren().clear();
        Label dateLabel = new Label(date.format(dateFormatter));
        dateLabel.setStyle("-fx-font-family: 'Sitka Text'; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #AB8905;");
        showDate.getChildren().add(dateLabel);
    }

    private void loadCalendarWork(LocalDate date) {
        calendarTable.getItems().clear();
        String query = "SELECT timetable.Time, classes.ClassName, staffs.Firstname, Lastname AS Teacher, subjects.SubjectName, timetable.Description " +
                       "FROM timetable " +
                       "JOIN classes ON timetable.ClassID = classes.ClassID " +
                       "JOIN staffs ON timetable.StaffID = staffs.StaffID " +
                       "JOIN subjects ON timetable.SubjectID = subjects.SubjectID " +
                       "WHERE timetable.Date = '" + date + "'";
        try (Connection conn = ConnectDB.connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                CalendarWork work = new CalendarWork(
                        rs.getString("Time"),
                        rs.getString("ClassName"),
                        rs.getString("Teacher"),
                        rs.getString("SubjectName"),
                        rs.getString("Description")
                );
                calendarTable.getItems().add(work);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        calendarPicker();
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        loadCalendarWork(LocalDate.now());
        
        // Populate teacherChoiceBox with staff names from the database
        ObservableList<String> teacherList = FXCollections.observableArrayList(getStaffNames());
        teacherChoiceBox.setItems(teacherList);
    }

    private List<String> getStaffNames() {
        List<String> staffNames = new ArrayList<>();
        String query = "SELECT FirstName, LastName FROM staffs";
        try (Connection conn = ConnectDB.connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String fullName = rs.getString("FirstName") + " " + rs.getString("LastName");
                staffNames.add(fullName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffNames;
    }
}

