package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.model.StaffModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
    private TableColumn<Staff, String> fullNameColumn;
    @FXML
    private TableColumn<Staff, String> addressColumn;
    @FXML
    private TableColumn<Staff, String> phoneColumn;
    @FXML
    private TableColumn<Staff, String> emailColumn;
    @FXML
    private TableColumn<Staff, Date> birthDateColumn;
    @FXML
    private TableColumn<Staff, String> departmentColumn;
    @FXML
    private TableColumn<Staff, String> positionColumn;
    @FXML
    private TableColumn<Staff, Date> hireDateColumn;
    @FXML
    private TableColumn<Staff, BigDecimal> salaryColumn;
    @FXML
    private TableColumn<Staff, Date> lastLoginColumn;
    @FXML
    private TableColumn<Staff, String> profilePictureColumn;
    @FXML
    private TableColumn<Staff, String> sexColumn;
    @FXML
    private TableColumn<Staff, String> statusColumn;
    @FXML
    private TableColumn<Staff, Void> actionColumn;

    private StaffModel staffModel = new StaffModel();
    private ObservableList<Staff> staffList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configure columns
        staffIDColumn.setCellValueFactory(new PropertyValueFactory<>("staffID"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        lastLoginColumn.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));
        profilePictureColumn.setCellValueFactory(new PropertyValueFactory<>("profilePicture"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Configure column alignments
        configureColumnAlignment(staffIDColumn);
        configureColumnAlignment(fullNameColumn);
        configureColumnAlignment(addressColumn);
        configureColumnAlignment(phoneColumn);
        configureColumnAlignment(emailColumn);
        configureColumnAlignment(birthDateColumn);
        configureColumnAlignment(departmentColumn);
        configureColumnAlignment(positionColumn);
        configureColumnAlignment(hireDateColumn);
        configureColumnAlignment(salaryColumn);
        configureColumnAlignment(lastLoginColumn);
        configureColumnAlignment(profilePictureColumn);
        configureColumnAlignment(sexColumn);
        configureColumnAlignment(statusColumn);

        // Cấu hình cột profilePicture để hiển thị hình ảnh
        configureProfilePictureColumn();

        // Configure action column
        configureActionColumn();

        // Load staff data
        loadStaffData();
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

    private void configureProfilePictureColumn() {
        profilePictureColumn.setCellFactory(column -> new TableCell<Staff, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null || imagePath.isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        // Load the image from the provided path
                        Image image = new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/detail.png"));
                        imageView.setImage(image);
                        imageView.setFitHeight(24);  // Set the height of the image
                        imageView.setFitWidth(24);   // Set the width of the image
                        imageView.setPreserveRatio(true); // Maintain aspect ratio
                        setGraphic(imageView);
                        setAlignment(Pos.CENTER); // Căn giữa trong TableCell
                    } catch (Exception e) {
                        setGraphic(null);  // In case of error, do not show anything
                    }
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
        // Tạo danh sách mới từ mô hình
        List<Staff> newStaffList = staffModel.getAllStaff();
        staffList.clear();
        staffList.addAll(newStaffList);
        staffTableView.setItems(staffList);
        updateActionColumn();
    }
}
