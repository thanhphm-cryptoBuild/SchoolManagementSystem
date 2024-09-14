package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.model.StudentModel;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

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
    private TableColumn<StudentModel, Void> colAction;

    @FXML
    private TableColumn<StudentModel, String> colAvatar;

    @FXML
    private TableColumn<StudentModel, String> colClassName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        loadStudentData();
    }

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

        colAvatar.setCellFactory(column -> new TableCell<StudentModel, String>() {
            private final ImageView imageView = new ImageView();
            private final String defaultAvatarPath = "file:/C:/Users/ADMIN/IdeaProjects/School/src/main/resources/com/app/schoolmanagementsystem/images/default_avatar.png";

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    try {
                        // Kiểm tra và in ra đường dẫn avatar
                        System.out.println("Avatar Path: " + item);
                        Image image;
                        if (item.isEmpty()) {
                            image = new Image(defaultAvatarPath);
                        } else {
                            image = new Image(item);
                        }
                        imageView.setImage(image);
                        imageView.setFitHeight(50);
                        imageView.setFitWidth(50);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        e.printStackTrace();
                        setGraphic(null);
                    }
                }
            }
        });

        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button();
            private final Button editButton = new Button();

            {
                // Nút xóa
                Image deleteImage = new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/cross.png"));
                ImageView deleteImageView = new ImageView(deleteImage);
                deleteImageView.setFitHeight(20);
                deleteImageView.setFitWidth(20);
                deleteButton.setGraphic(deleteImageView);
                deleteButton.setOnAction(event -> {
                    StudentModel student = getTableView().getItems().get(getIndex());
                    deleteStudent(student);
                });

                // Nút chỉnh sửa
                Image gearImage = new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/gear.png"));
                ImageView gearImageView = new ImageView(gearImage);
                gearImageView.setFitHeight(20);
                gearImageView.setFitWidth(20);
                editButton.setGraphic(gearImageView);
                editButton.setOnAction(event -> {
                    StudentModel student = getTableView().getItems().get(getIndex());
                    openEditStudentPage(student);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actionButtons = new HBox(editButton, deleteButton);
                    setGraphic(actionButtons);
                }
            }
        });

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

        colClassName.setCellValueFactory(cellData -> {
            int classID = cellData.getValue().getClassID();
            String className = getClassNameById(classID);
            return new SimpleStringProperty(className);
        });
    }

    private String getClassNameById(int classID) {
        String className = "";
        String query = "SELECT ClassName FROM classes WHERE ClassID = " + classID;

        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                className = rs.getString("ClassName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return className;
    }

    private void deleteStudent(StudentModel student) {
        String query = "UPDATE students SET Status = 'inactive' WHERE StudentID = " + student.getStudentID();

        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement()) {
            int result = stmt.executeUpdate(query);
            if (result > 0) {
                student.setStatus("inactive");
                studentTable.refresh();
                loadStudentData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openEditStudentPage(StudentModel student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/schoolmanagementsystem/views/pageeditstudent.fxml"));
            StackPane pageEditStudent = loader.load();

            EditStudentController editStudentController = loader.getController();
            editStudentController.setPageStudent(pageStudent);
            editStudentController.setBGPageStudent(moveBG);
            editStudentController.setStudentData(student); // Truyền dữ liệu sinh viên vào controller

            pageEditStudent.setTranslateX(2000);
            pageEditStudent.setTranslateY(10);

            pageStudent.getChildren().add(pageEditStudent);

            GaussianBlur gaussianBlur = new GaussianBlur(10);
            moveBG.setEffect(gaussianBlur);

            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(0.2));
            translateTransition.setNode(pageEditStudent);
            translateTransition.setFromX(2000);
            translateTransition.setToY(6);
            translateTransition.setToX(420);
            translateTransition.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStudentData() {
        ObservableList<StudentModel> studentData = FXCollections.observableArrayList();
        String query = "SELECT * FROM students WHERE Status = 'active'";

        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String avatarPath = rs.getString("avatar");
                if (avatarPath == null || avatarPath.isEmpty()) {
                    avatarPath = "file:src/main/resources/com/app/schoolmanagementsystem/images/default_avatar.png";
                }
                System.out.println("Avatar Path: " + avatarPath); // In ra đường dẫn avatar để kiểm tra

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
                        rs.getString("Status"),
                        avatarPath
                );
                studentData.add(student);
            }

            studentTable.setItems(studentData);
            studentTable.refresh();
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

    @FXML
    void refreshData(MouseEvent event) {
        loadStudentData();
        // Đã xóa đoạn mã hiển thị thông báo
    }
}

