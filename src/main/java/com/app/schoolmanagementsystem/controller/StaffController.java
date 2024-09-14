package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.model.StaffModel;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class StaffController implements Initializable {

    @FXML
    private TableView<Staff> staffTableView;

    @FXML
    private TableColumn<Staff, Integer> staffIDColumn;
    @FXML
    private TableColumn<Staff, String> firstNameColumn;
    @FXML
    private TableColumn<Staff, String> lastNameColumn;
    @FXML
    private TableColumn<Staff, Date> dateOfBirthColumn;
    @FXML
    private TableColumn<Staff, String> genderColumn;
    @FXML
    private TableColumn<Staff, String> addressColumn;
    @FXML
    private TableColumn<Staff, String> phoneNumberColumn;
    @FXML
    private TableColumn<Staff, String> emailColumn;
    @FXML
    private TableColumn<Staff, Date> hireDateColumn;
    @FXML
    private TableColumn<Staff, Integer> positionIDColumn;
    @FXML
    private TableColumn<Staff, Double> salaryColumn;
    @FXML
    private TableColumn<Staff, String> educationBackgroundColumn;
    @FXML
    private TableColumn<Staff, String> experienceColumn;
    @FXML
    private TableColumn<Staff, String> avatarColumn;
    @FXML
    private TableColumn<Staff, String> statusColumn;
    @FXML
    private TableColumn<Staff, Void> actionColumn;

    private StaffModel staffModel = new StaffModel();
    private ObservableList<Staff> staffList = FXCollections.observableArrayList();

    @FXML
    private StackPane pageStaff;

    @FXML
    void addStaffBTN(MouseEvent event) throws IOException {
        StackPane pageAddStaff = FXMLLoader.load(getClass().getResource("/com/app/schoolmanagementsystem/views/PageAddStaff.fxml"));

        pageAddStaff.setTranslateX(2000);
        pageAddStaff.setTranslateY(6);

//        pageStudent.getChildren().removeAll();
        pageStaff.getChildren().add(pageAddStaff);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(pageAddStaff);
        translateTransition.setFromX(2000);
        translateTransition.setToY(6);
        translateTransition.setToX(500);

        translateTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configure columns
        staffIDColumn.setCellValueFactory(new PropertyValueFactory<>("staffID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColumn.setCellValueFactory(cellData -> {
            byte genderValue = cellData.getValue().getGender();  // Get gender as byte
            String genderText = (genderValue == 1) ? "Male" : "Female";  // Convert to text
            return new SimpleStringProperty(genderText);  // Return the gender text as StringProperty
        });
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        positionIDColumn.setCellValueFactory(new PropertyValueFactory<>("positionID"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        educationBackgroundColumn.setCellValueFactory(new PropertyValueFactory<>("educationBackground"));
        experienceColumn.setCellValueFactory(new PropertyValueFactory<>("experience"));
        // Configure avatar column
        avatarColumn.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Configure column alignments
        configureColumnAlignment(staffIDColumn);
        configureColumnAlignment(firstNameColumn);
        configureColumnAlignment(lastNameColumn);
        configureColumnAlignment(dateOfBirthColumn);
        configureColumnAlignment(genderColumn);
        configureColumnAlignment(addressColumn);
        configureColumnAlignment(phoneNumberColumn);
        configureColumnAlignment(emailColumn);
        configureColumnAlignment(hireDateColumn);
        configureColumnAlignment(positionIDColumn);
        configureColumnAlignment(salaryColumn);
        configureColumnAlignment(educationBackgroundColumn);
        configureColumnAlignment(experienceColumn);
        configureColumnAlignment(avatarColumn);
        configureColumnAlignment(statusColumn);

        // Configure action column
        configureActionColumn();
        // Configure avatar column
        configureAvatarColumn();

        // Load staff data
        loadStaffData();
    }

    private void configureAvatarColumn() {
        avatarColumn.setCellFactory(column -> new TableCell<Staff, String>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitHeight(24); // Điều chỉnh kích thước hình ảnh nếu cần
                imageView.setFitWidth(24);
            }

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null || imagePath.isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        // Load image from the resources folder
                        Image image = new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/" + "add.png"));
                        imageView.setImage(image);
                        setGraphic(imageView);
                        setAlignment(Pos.CENTER);
                    } catch (Exception e) {
                        e.printStackTrace(); // In lỗi nếu không thể tải hình ảnh
                        setGraphic(null);
                    }
                }
            }
        });
    }


    private <T> void configureColumnAlignment(TableColumn<Staff, T> tableColumn) {
        tableColumn.setCellFactory(column -> new TableCell<Staff, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item == null ? "" : item.toString());
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    private void configureActionColumn() {
        actionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Staff, Void> call(TableColumn<Staff, Void> param) {
                return new TableCell<>() {
                    private final ImageView detailImageView = new ImageView(new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/detail.png")));
                    private final ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/edit.png")));
                    private final ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/delete.png")));

                    {
                        // Set image sizes
                        detailImageView.setFitHeight(24);
                        detailImageView.setFitWidth(24);
                        editImageView.setFitHeight(24);
                        editImageView.setFitWidth(24);
                        deleteImageView.setFitHeight(24);
                        deleteImageView.setFitWidth(24);

                        // Create HBox and configure alignment
                        HBox hBox = new HBox(10, detailImageView, editImageView, deleteImageView);
                        hBox.setAlignment(Pos.CENTER);

                        // Add event handlers for clicks
                        detailImageView.setOnMouseClicked(e -> showDetails(getIndex()));
                        editImageView.setOnMouseClicked(e -> editStaff(getIndex()));
                        deleteImageView.setOnMouseClicked(e -> deleteStaff(getIndex()));

                        // Add mouse enter and exit handlers to change cursor
                        detailImageView.setOnMouseEntered(e -> detailImageView.setCursor(Cursor.HAND));
                        detailImageView.setOnMouseExited(e -> detailImageView.setCursor(Cursor.DEFAULT));
                        editImageView.setOnMouseEntered(e -> editImageView.setCursor(Cursor.HAND));
                        editImageView.setOnMouseExited(e -> editImageView.setCursor(Cursor.DEFAULT));
                        deleteImageView.setOnMouseEntered(e -> deleteImageView.setCursor(Cursor.HAND));
                        deleteImageView.setOnMouseExited(e -> deleteImageView.setCursor(Cursor.DEFAULT));

                        setGraphic(hBox);
                        setText(null);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(getGraphic());
                        }
                    }
                };
            }
        });
    }

    private void updateActionColumn() {
        actionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Staff, Void> call(TableColumn<Staff, Void> param) {
                return new TableCell<>() {
                    private final ImageView detailImageView = new ImageView(new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/detail.png")));
                    private final ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/edit.png")));
                    private final ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/delete.png")));

                    {
                        // Set image sizes
                        detailImageView.setFitHeight(24);
                        detailImageView.setFitWidth(24);
                        editImageView.setFitHeight(24);
                        editImageView.setFitWidth(24);
                        deleteImageView.setFitHeight(24);
                        deleteImageView.setFitWidth(24);

                        // Create HBox and configure alignment
                        HBox hBox = new HBox(10, detailImageView, editImageView, deleteImageView);
                        hBox.setAlignment(Pos.CENTER);

                        // Add event handlers for clicks
                        detailImageView.setOnMouseClicked(e -> showDetails(getIndex()));
                        editImageView.setOnMouseClicked(e -> editStaff(getIndex()));
                        deleteImageView.setOnMouseClicked(e -> deleteStaff(getIndex()));

                        // Add mouse enter and exit handlers to change cursor
                        detailImageView.setOnMouseEntered(e -> detailImageView.setCursor(Cursor.HAND));
                        detailImageView.setOnMouseExited(e -> detailImageView.setCursor(Cursor.DEFAULT));
                        editImageView.setOnMouseEntered(e -> editImageView.setCursor(Cursor.HAND));
                        editImageView.setOnMouseExited(e -> editImageView.setCursor(Cursor.DEFAULT));
                        deleteImageView.setOnMouseEntered(e -> deleteImageView.setCursor(Cursor.HAND));
                        deleteImageView.setOnMouseExited(e -> deleteImageView.setCursor(Cursor.DEFAULT));

                        setGraphic(hBox);
                        setText(null);
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(getGraphic());
                        }
                    }
                };
            }
        });
    }

    private void showDetails(int index) {
        Staff staff = staffTableView.getItems().get(index);
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Staff Details");
        alert.setHeaderText("Details of Staff ID: " + staff.getStaffID());
        alert.setContentText(staff.toString()); // Customize this to show more details
        alert.showAndWait();
    }

    private void editStaff(int index) {
        Staff staff = staffTableView.getItems().get(index);
        // Implement edit logic here
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Edit Staff");
        alert.setHeaderText("Edit Staff ID: " + staff.getStaffID());
        alert.setContentText("Edit functionality is not implemented yet.");
        alert.showAndWait();
    }

    private void deleteStaff(int index) {
        Staff staff = staffTableView.getItems().get(index);
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Staff");
        alert.setHeaderText("Are you sure you want to delete Staff ID: " + staff.getStaffID() + "?");
        if (alert.showAndWait().get().getButtonData().isDefaultButton()) {
            // Implement delete logic here
            staffModel.deleteStaff(staff.getStaffID());
            loadStaffData(); // Refresh the list
        }
    }

    private void loadStaffData() {
        // Create a new list from the model
        List<Staff> newStaffList = staffModel.getActiveStaff();
        staffList.clear();
        staffList.addAll(newStaffList);
        staffTableView.setItems(staffList);
        updateActionColumn();
    }
}
