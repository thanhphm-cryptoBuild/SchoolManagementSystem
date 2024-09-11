package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.model.Student;
import com.app.schoolmanagementsystem.utils.ConnectDB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Integer> idColumn;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> addressColumn;
    @FXML private TableColumn<Student, String> phoneColumn;
    @FXML private TableColumn<Student, String> emailColumn;
    @FXML private TableColumn<Student, Date> birthDateColumn;
    @FXML private TableColumn<Student, String> registrationNoColumn;
    @FXML private TableColumn<Student, String> batchColumn;
    @FXML private TableColumn<Student, Date> admissionDateColumn;
    @FXML private TableColumn<Student, String> previousInstituteColumn;
    @FXML private TableColumn<Student, String> actionColumn;
    @FXML private TableColumn<Student, String> avatarColumn;

    @FXML private StackPane addButtonPane;
    @FXML private StackPane refreshButtonPane;
    @FXML private StackPane printButtonPane;
    @FXML private TextField searchField;

    private ObservableList<Student> allStudents = FXCollections.observableArrayList();
    private FilteredList<Student> filteredStudents;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initializeColumns();
            loadStudentData();
            setupActionColumn();
            setupAvatarColumn();
            setupButtonActions();
            setupSearch();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Initialization Error", "An error occurred while initializing the page: " + e.getMessage());
        }
    }

    private void setupButtonActions() {
        addButtonPane.setOnMouseClicked(event -> addStudent());
        refreshButtonPane.setOnMouseClicked(event -> refreshStudentData());
        printButtonPane.setOnMouseClicked(event -> printStudentData());
    }

    private void setupSearch() {
        filteredStudents = new FilteredList<>(allStudents, p -> true);
        studentTable.setItems(filteredStudents);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredStudents.setPredicate(student -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (student.getFullName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (student.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (student.getPhone().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (student.getRegistrationNo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    @FXML
    private void addStudent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/schoolmanagementsystem/views/AddStudentView.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Add New Student");
            stage.setScene(new Scene(root));
            
            stage.showAndWait();
            
            loadStudentData();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open add student window: " + e.getMessage());
        }
    }

    private void refreshStudentData() {
        loadStudentData();
        showAlert(Alert.AlertType.INFORMATION, "Refresh", "Student data has been refreshed.");
    }

    private void printStudentData() {
        showAlert(Alert.AlertType.INFORMATION, "Print", "Printing student data...");
    }

    private void initializeColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        registrationNoColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNo"));
        batchColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        admissionDateColumn.setCellValueFactory(new PropertyValueFactory<>("admissionDate"));
        previousInstituteColumn.setCellValueFactory(new PropertyValueFactory<>("previousInstitute"));
    }

    private void loadStudentData() {
        allStudents.clear();
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT s.*, c.className FROM student s LEFT JOIN classes c ON s.classID = c.classID")) {

            while (rs.next()) {
                String profilePicture = rs.getString("profilePicture");
                if (profilePicture != null && !profilePicture.isEmpty()) {
                    if (!profilePicture.startsWith("/") && !profilePicture.contains(":")) {
                        profilePicture = "/" + profilePicture;
                    }
                    File file = new File(profilePicture);
                    if (!file.exists()) {
                        profilePicture = null;
                    }
                } else {
                    profilePicture = null;
                }
                Student student = new Student(
                    rs.getInt("studentID"),
                    rs.getString("fullName"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getDate("birthDate"),
                    rs.getString("registrationNo"),
                    rs.getString("batch"),
                    rs.getDate("admissionDate"),
                    rs.getString("previousInstitute"),
                    rs.getString("reasonForLeaving"),
                    rs.getString("guardianContact"),
                    profilePicture,
                    rs.getInt("classID"),
                    rs.getString("sex")
                );
                student.setClassName(rs.getString("className"));
                allStudents.add(student);
            }

            if (filteredStudents == null) {
                filteredStudents = new FilteredList<>(allStudents);
            } else {
                filteredStudents.setPredicate(filteredStudents.getPredicate());
            }
            studentTable.setItems(filteredStudents);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Data Error", "Unable to load student data: " + e.getMessage());
        }
    }

    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button viewBtn = createIconButton("/com/app/schoolmanagementsystem/images/view.jpg", "View", "view-button");
            private final Button editBtn = createIconButton("/com/app/schoolmanagementsystem/images/edit.jpg", "Edit", "edit-button");
            private final Button deleteBtn = createIconButton("/com/app/schoolmanagementsystem/images/delete.png", "Delete", "delete-button");

            {
                deleteBtn.setOnAction(event -> deleteStudent(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, viewBtn, editBtn, deleteBtn);
                    buttons.setAlignment(javafx.geometry.Pos.CENTER);
                    setGraphic(buttons);
                }
            }
        });
    }

    private Button createIconButton(String imagePath, String tooltip, String styleClass) {
        Button button = new Button();
        try {
            java.io.InputStream inputStream = StudentController.class.getResourceAsStream(imagePath);
            if (inputStream != null) {
                javafx.scene.image.Image image = new javafx.scene.image.Image(inputStream);
                javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
                imageView.setFitHeight(20);
                imageView.setFitWidth(20);
                imageView.getStyleClass().add("image-view");
                button.setGraphic(imageView);
            } else {
                System.err.println("Unable to find image file: " + imagePath);
                button.setText(tooltip);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + imagePath);
            e.printStackTrace();
            button.setText(tooltip);
        }
        button.setTooltip(new Tooltip(tooltip));
        button.getStyleClass().add(styleClass);
        return button;
    }

    private void deleteStudent(Student student) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Deletion");
        confirmDialog.setHeaderText(null);
        confirmDialog.setContentText("Are you sure you want to delete student " + student.getFullName() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try (Connection conn = ConnectDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM student WHERE studentID = ?")) {

                stmt.setInt(1, student.getStudentID());
                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    allStudents.remove(student);
                    filteredStudents.remove(student);
                    
                    studentTable.setItems(filteredStudents);
                    studentTable.refresh();
                    
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Student deleted successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Unable to delete student. Please try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting student: " + e.getMessage());
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void setupAvatarColumn() {
        avatarColumn.setCellFactory(column -> new TableCell<Student, String>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                imageView.getStyleClass().add("avatar-image-view");
                this.getStyleClass().add("avatar-cell");
                setGraphic(imageView);
            }

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null || imagePath.isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        File file = new File(imagePath);
                        javafx.scene.image.Image image;
                        if (file.exists()) {
                            image = new javafx.scene.image.Image(file.toURI().toString(), 50, 50, true, true);
                        } else {
                            image = new javafx.scene.image.Image(getClass().getResourceAsStream(imagePath), 50, 50, true, true);
                        }
                        if (image.isError()) {
                            setDefaultImage();
                        } else {
                            imageView.setImage(image);
                            setGraphic(imageView);
                        }
                    } catch (Exception e) {
                        System.err.println("Unable to load image: " + imagePath);
                        setDefaultImage();
                    }
                }
            }

            private void setDefaultImage() {
                try {
                    javafx.scene.image.Image defaultImage = new javafx.scene.image.Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/default-avatar.png"), 50, 50, true, true);
                    imageView.setImage(defaultImage);
                    setGraphic(imageView);
                } catch (Exception e) {
                    System.err.println("Unable to load default image");
                    setGraphic(null);
                }
            }
        });
        avatarColumn.getStyleClass().add("avatar-column");
        avatarColumn.setCellValueFactory(new PropertyValueFactory<>("profilePicture"));
    }
}
