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
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.GaussianBlur;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
// Định nghĩa cho các trường và cột
public class StudentController implements Initializable {


    public AnchorPane moveBG;
    @FXML
    private StackPane pageStudent;

    @FXML
    private TableView<StudentModel> studentTable;

    @FXML
    private TableColumn<StudentModel, Integer> colSTT;

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
    private TableColumn<StudentModel, String> colStatus; // Giữ khai báo này

    @FXML
    private TableColumn<StudentModel, Void> colAction;

    @Override
    //Chạy các hàm đã tạo
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        loadStudentData();
    }
//Hàm thiết lập các cột
    private void setupTableColumns() {
        colSTT.setCellValueFactory(column ->
            new SimpleIntegerProperty(studentTable.getItems().indexOf(column.getValue()) + 1).asObject());

        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEnrollmentDate.setCellValueFactory(new PropertyValueFactory<>("enrollmentDate"));
        colClassID.setCellValueFactory(new PropertyValueFactory<>("classID"));
        // Không thiết lập colStatus ở đây

        //Hàm tạo biểu tượng trong cột active
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button();

            {
                Image recycleImage = new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/recycle.png"));
                ImageView imageView = new ImageView(recycleImage);
                imageView.setFitHeight(20);
                imageView.setFitWidth(20);
                deleteButton.setGraphic(imageView);
                deleteButton.setOnAction(event -> {
                    StudentModel student = getTableView().getItems().get(getIndex());
                    deleteStudent(student);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
        //Update dữ liệu boolean thành text Nam, Nữ
        colGender.setCellFactory(column -> new TableCell<StudentModel, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Male" : "Female");
                }
            }
        });
    }
//Hàm xóa Student
    private void deleteStudent(StudentModel student) {
        String query = "UPDATE students SET Status = 'inactive' WHERE StudentID = " + student.getStudentID();

        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement()) {
            int result = stmt.executeUpdate(query);
            if (result > 0) {
                student.setStatus("inactive");
                studentTable.refresh();
                loadStudentData(); // Tải lại dữ liệu để ẩn các sinh viên không hoạt động
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
// Hàm hiện data lên table
    private void loadStudentData() {
        ObservableList<StudentModel> studentData = FXCollections.observableArrayList();
        String query = "SELECT * FROM students WHERE Status = 'active'";

        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                StudentModel student = new StudentModel(
                        rs.getInt("StudentID"),  // Vẫn giữ StudentID trong data
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getDate("DateOfBirth"),
                        rs.getBoolean("Gender"),
                        rs.getString("Address"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Email"),
                        rs.getDate("EnrollmentDate"),
                        rs.getInt("ClassID"),
                        rs.getString("Status") // Vẫn giữ Status trong data
                );
                studentData.add(student);
            }

            studentTable.setItems(studentData);
            studentTable.refresh(); // Cập nhật bảng để hiển thị STT mới
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addStudentBTN(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/schoolmanagementsystem/views/PageAddStudent.fxml"));
        StackPane pageAddStudent = loader.load();

        AddStudentController addStudentController = loader.getController();
        addStudentController.setPageStudent(pageStudent);
        addStudentController.setBGPageStudent(moveBG);

        pageAddStudent.setTranslateX(2000);
        pageAddStudent.setTranslateY(10);

        pageStudent.getChildren().add(pageAddStudent);

        GaussianBlur gaussianBlur = new GaussianBlur(10);
        moveBG.setEffect(gaussianBlur);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(pageAddStudent);
        translateTransition.setFromX(2000);
        translateTransition.setToY(6);
        translateTransition.setToX(420);
        translateTransition.play();
    }
}
