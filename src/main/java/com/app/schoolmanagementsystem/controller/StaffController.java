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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @FXML
    private AnchorPane moveBG;

    @FXML
    private StackPane pageStaff;

    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox<String> selectBox;

    @FXML
    private TextField extraSearchField;

    @FXML
    private ImageView reloadButton; // Gán ID của ImageView


    private StaffModel staffModel = new StaffModel();
    private ObservableList<Staff> staffList = FXCollections.observableArrayList();

    // Add default avatar and avatar cache
    private Image defaultAvatar;
    private final Map<String, Image> avatarCache = new HashMap<>();


    @FXML
    void addStaffBTN(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/schoolmanagementsystem/views/PageAddStaff.fxml"));
            StackPane pageAddStaff = loader.load();

            AddStaffController addStaffController = loader.getController();
            addStaffController.setPageStaff(pageAddStaff);
            addStaffController.setBGPageStaff(moveBG);

            pageAddStaff.setTranslateX(2000);
            pageAddStaff.setTranslateY(6);

            pageStaff.getChildren().add(pageAddStaff);

            GaussianBlur gaussianBlur = new GaussianBlur(10);
            moveBG.setEffect(gaussianBlur);

            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(0.2));
            translateTransition.setNode(pageAddStaff);
            translateTransition.setFromX(2000);
            translateTransition.setToY(6);
            translateTransition.setToX(115);

            translateTransition.play();
        } catch (IOException e) {
            e.printStackTrace(); // In chi tiết lỗi ra console
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load PageAddStaff.fxml");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize default avatar
        defaultAvatar = new Image(Objects.requireNonNull(getClass().getResource("/com/app/schoolmanagementsystem/images/default_avatar.png")).toExternalForm());

        // Configure columns
        staffIDColumn.setCellValueFactory(new PropertyValueFactory<>("staffID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColumn.setCellValueFactory(cellData -> {
            byte genderValue = cellData.getValue().getGender();  // Lấy giới tính dưới dạng byte
            String genderText = (genderValue == 1) ? "Male" : "Female";  // Chuyển đổi thành văn bản
            return new SimpleStringProperty(genderText);  // Trả về văn bản giới tính dưới dạng StringProperty
        });
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        educationBackgroundColumn.setCellValueFactory(new PropertyValueFactory<>("educationBackground"));
        experienceColumn.setCellValueFactory(new PropertyValueFactory<>("experience"));
        avatarColumn.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Configure column alignment
        configureColumnAlignment(staffIDColumn);
        configureColumnAlignment(firstNameColumn);
        configureColumnAlignment(lastNameColumn);
        configureColumnAlignment(dateOfBirthColumn);
        configureColumnAlignment(genderColumn);
        configureColumnAlignment(addressColumn);
        configureColumnAlignment(phoneNumberColumn);
        configureColumnAlignment(emailColumn);
        configureColumnAlignment(hireDateColumn);
        configureColumnAlignment(salaryColumn);
        configureColumnAlignment(educationBackgroundColumn);
        configureColumnAlignment(experienceColumn);
        configureColumnAlignment(avatarColumn);
        configureColumnAlignment(statusColumn);

        // Configure action column
        configureActionColumn();
        // Configure avatar column
        configureAvatarColumn();

        reloadButton.setOnMouseClicked(event -> reloadPage());

        // Configure ChoiceBox for Gender and ID
        ObservableList<String> searchOptions = FXCollections.observableArrayList("Equal", "Gender", "ID");
        selectBox.setItems(searchOptions);
        selectBox.setValue("Equal"); // Đặt giá trị mặc định là "" (không chọn gì)

        // Add search listeners
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchStaff());
        selectBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> searchStaff());
        extraSearchField.textProperty().addListener((observable, oldValue, newValue) -> searchStaff());

        // Initialize StaffModel
        staffModel = new StaffModel();

        // Load initial data
        loadStaffDataByLogic(null, null, null, null);
        loadStaffData();
    }

    private void searchStaff() {
        // Lấy giá trị từ các ô tìm kiếm
        String searchText = searchField.getText().trim(); // Tìm kiếm theo firstName hoặc email
        String extraSearchText = extraSearchField.getText().trim(); // Tìm kiếm theo ID hoặc Gender từ ChoiceBox

        // Tách phần tìm kiếm cho firstName, email từ searchField
        String firstName = null;
        String email = null;

        // Xử lý tìm kiếm theo searchField
        if (searchText.contains("@")) {
            email = searchText;
        } else if (!searchText.isEmpty()) {
            firstName = searchText;
        }

        // Tách phần tìm kiếm cho ID, gender từ extraSearchField
        Integer id = null;
        Byte gender = null;

        // Xử lý tìm kiếm theo lựa chọn từ ChoiceBox
        String selectedOption = selectBox.getValue();
        if ("ID".equals(selectedOption)) {
            // Nếu lựa chọn là ID, tìm kiếm theo ID
            try {
                id = Integer.parseInt(extraSearchText);
            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu extraSearchField không phải là số
                id = null;
            }
        } else if ("Gender".equals(selectedOption)) {
            // Nếu lựa chọn là Gender, tìm kiếm theo Gender
            if ("Male".equalsIgnoreCase(extraSearchText)) {
                gender = (byte) 1;
            } else if ("Female".equalsIgnoreCase(extraSearchText)) {
                gender = (byte) 0;
            }
        }

        // Cập nhật cột hành động
        updateActionColumn();

        // Tải dữ liệu nhân viên theo các tiêu chí tìm kiếm
        loadStaffDataByLogic(firstName, email, gender, id);
    }


    private void resetSearchFields() {
        searchField.setText(""); // Đặt giá trị ô tìm kiếm chính là rỗng
        extraSearchField.setText(""); // Đặt giá trị ô tìm kiếm phụ là rỗng
        selectBox.setValue(""); // Đặt giá trị ChoiceBox về giá trị mặc định
    }

    private void loadStaffDataByLogic(String firstName, String email, Byte gender, Integer id) {
        staffList.clear(); // Xóa dữ liệu cũ
        List<Staff> staff = staffModel.searchStaff(firstName, email, gender, id); // Gọi phương thức tìm kiếm trong StaffModel
        staffList.addAll(staff); // Thêm kết quả tìm kiếm vào ObservableList
        staffTableView.setItems(staffList); // Cập nhật TableView
    }

    private void configureAvatarColumn() {
        avatarColumn.setCellFactory(column -> new TableCell<Staff, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String avatarPath, boolean empty) {
                super.updateItem(avatarPath, empty);
                if (empty || avatarPath == null || avatarPath.isEmpty()) {
                    imageView.setImage(defaultAvatar);
                } else {
                    // Get the current Staff object
                    Staff staff = getTableView().getItems().get(getIndex());
                    String fullAvatarPath = staff.getAvatar();

                    Image avatarImage = avatarCache.get(fullAvatarPath);
                    if (avatarImage == null) {
                        try {
                            avatarImage = new Image(staff.isExternalAvatar() ? fullAvatarPath : getClass().getResource(fullAvatarPath).toExternalForm());
                            avatarCache.put(fullAvatarPath, avatarImage);
                        } catch (Exception e) {
                            avatarImage = defaultAvatar;
                        }
                    }
                    imageView.setImage(avatarImage);
                }
                imageView.setFitHeight(32); // Set fixed height for the avatar
                imageView.setFitWidth(32); // Set fixed width for the avatar
                imageView.setPreserveRatio(true); // Maintain aspect ratio

                // Clip the image to a circle
                imageView.setClip(new Circle(16, 16, 16)); // Create a circular clip
                setGraphic(empty ? null : imageView);
            }
        });
        avatarColumn.setStyle("-fx-alignment: CENTER;"); // Center align Avatar column
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

                        editImageView.setOnMouseClicked(e -> {
                            Staff staff = getTableRow() != null ? getTableRow().getItem() : null;
                            if (staff != null) {
                                System.out.println("Editing staff with ID: " + staff.getStaffID());
                                editStaff(staff.getStaffID()); // Chuyển ID nhân viên để chỉnh sửa
                            } else {
                                System.err.println("No staff data available.");
                            }
                        });

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
                        editImageView.setOnMouseClicked(e -> {
                            Staff staff = getTableRow().getItem();
                            if (staff != null) {
                                editStaff(staff.getStaffID()); // Gọi phương thức editStaff với ID của nhân viên
                            }
                        });
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Staff Details");
        alert.setHeaderText("Details of Staff ID: " + staff.getStaffID());
        alert.setContentText(staff.toString()); // Customize this to show more details
        alert.showAndWait();

        resetSearchFields();
    }


    // Cập nhật phương thức editStaff để nhận đối tượng Staff
    private void editStaff(int staffId) {
        System.out.println("Attempting to edit staff with ID: " + staffId); // Log ID nhân viên

        try {
            // Truy xuất thông tin nhân viên dựa trên ID từ dịch vụ hoặc lớp xử lý dữ liệu
            Staff staff = staffModel.getStaffByID(staffId);

            if (staff == null) {
                System.err.println("Staff with ID " + staffId + " not found."); // Log lỗi nếu không tìm thấy nhân viên
                throw new IllegalArgumentException("Staff with ID " + staffId + " not found.");
            }

            System.out.println("Editing staff: " + staff); // Log thông tin nhân viên

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/schoolmanagementsystem/views/PageEditStaff.fxml"));
            StackPane pageEditStaff = loader.load();

            EditStaffController editStaffController = loader.getController();
            editStaffController.setStaffData(staff); // Gửi dữ liệu nhân viên vào controller
            editStaffController.setPageStaff(pageStaff);
            editStaffController.setBGPageStaff(moveBG);

            pageEditStaff.setTranslateX(2000);
            pageEditStaff.setTranslateY(6);

            pageStaff.getChildren().add(pageEditStaff);

            GaussianBlur gaussianBlur = new GaussianBlur(10);
            moveBG.setEffect(gaussianBlur);

            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(0.2));
            translateTransition.setNode(pageEditStaff);
            translateTransition.setFromX(2000);
            translateTransition.setToY(6);
            translateTransition.setToX(115);

            translateTransition.play();
        } catch (IOException e) {
            e.printStackTrace(); // In chi tiết lỗi ra console
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load PageEditStaff.fxml");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            resetSearchFields();
        } catch (IllegalArgumentException e) {
            e.printStackTrace(); // In chi tiết lỗi ra console
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Staff Not Found");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }







    private void deleteStaff(int index) {
        Staff staff = staffTableView.getItems().get(index);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Staff");
        alert.setHeaderText("Are you sure you want to delete Staff ID: " + staff.getStaffID() + "?");
        if (alert.showAndWait().get().getButtonData().isDefaultButton()) {
            // Implement delete logic here
            staffModel.deleteStaff(staff.getStaffID());
            loadStaffData(); // Refresh the list
            resetSearchFields();
        }

    }

    private void loadStaffData() {
        // Create a new list from the model
        List<Staff> newStaffList = staffModel.getActiveStaff();
        staffList.clear();
        staffList.addAll(newStaffList);
        staffTableView.setItems(staffList);
        updateActionColumn();
        configureAvatarColumn(); // Cấu hình lại cột avatar
        loadStaffDataByLogic(null, null, null, null); // Tải dữ liệu toàn bộ nếu không có điều kiện tìm kiếm
    }

    private void reloadPage() {
        // Logic để tải lại trang
        // ...

        // Đặt lại các trường tìm kiếm
        resetSearchFields();

        // Tải dữ liệu nhân viên
        loadStaffData();
    }
}