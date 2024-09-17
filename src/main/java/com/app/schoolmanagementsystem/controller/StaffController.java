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
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
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


    @FXML
    void addStaffBTN(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/schoolmanagementsystem/views/PageAddStaff.fxml"));
            StackPane pageAddStaff = loader.load();

            AddStaffController addStaffController = loader.getController();
            addStaffController.setPageStaff(pageStaff);
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
        // Cấu hình các cột
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
        // Cấu hình cột avatar
        avatarColumn.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Cấu hình căn chỉnh cột
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

        // Cấu hình cột hành động
        configureActionColumn();
        // Cấu hình cột avatar
        configureAvatarColumn();

        reloadButton.setOnMouseClicked(event -> reloadPage());



        // Tạo sự kiện khi người dùng nhập vào ô tìm kiếm
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchStaff());
        selectBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> searchStaff());
        extraSearchField.textProperty().addListener((observable, oldValue, newValue) -> searchStaff());

        // Khởi tạo StaffModel
        staffModel = new StaffModel();

        // Cấu hình ChoiceBox cho giới tính
        ObservableList<String> genderList = FXCollections.observableArrayList("","Male", "Female");
        selectBox.setItems(genderList);
        selectBox.setValue(""); // Đặt giá trị mặc định là "Male"
        // Tải dữ liệu ban đầu
        loadStaffDataByLogic(null, null, null, null);

        // Tải dữ liệu nhân viên
        loadStaffData();
    }

    private void searchStaff() {
        // Lấy giá trị từ các ô tìm kiếm
        String searchText = searchField.getText().trim();
        String extraSearchText = extraSearchField.getText().trim();

        // Tách phần tìm kiếm cho firstName, email từ searchField
        String firstName = null;
        String email = null;

        // Xử lý tìm kiếm theo searchField
        if (searchText.contains("@")) {
            email = searchText;
        } else {
            firstName = searchText;
        }

        // Tách phần tìm kiếm cho ID, gender từ extraSearchField
        Integer id = null;
        Byte gender = null;

        // Nếu có số thì giả định đó là ID
        try {
            id = Integer.parseInt(extraSearchText);
        } catch (NumberFormatException e) {
            // Không xử lý lỗi vì gender được kiểm tra sau
        }

        // Lấy giới tính từ ChoiceBox nếu có
        if (selectBox.getValue() != null && !selectBox.getValue().isEmpty()) {
            gender = (byte) (selectBox.getValue().equalsIgnoreCase("Male") ? 1 : 0); // Giả sử Male là 1 và Female là 0
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
        List<Staff> staffs = staffModel.searchStaff(firstName, email, gender, id); // Gọi phương thức tìm kiếm trong StaffModel
        staffList.addAll(staffs); // Thêm kết quả tìm kiếm vào ObservableList
        staffTableView.setItems(staffList); // Cập nhật TableView
    }

    private void configureAvatarColumn() {
        avatarColumn.setCellFactory(column -> new TableCell<Staff, String>() {
            private final ImageView imageView = new ImageView();
            private final Rectangle clip = new Rectangle();

            {
                imageView.setFitHeight(24); // Điều chỉnh kích thước hình ảnh nếu cần
                imageView.setFitWidth(24);
                imageView.setPreserveRatio(true);

                // Tạo hình dạng bo tròn cho hình ảnh
                clip.setArcWidth(24); // Đặt kích thước bo tròn (thay đổi nếu cần)
                clip.setArcHeight(24); // Đặt kích thước bo tròn (thay đổi nếu cần)
                imageView.setClip(clip);
            }

            @Override
            protected void updateItem(String imageName, boolean empty) {
                super.updateItem(imageName, empty);
                if (empty || imageName == null || imageName.isEmpty()) {
                    setGraphic(null); // Không hiển thị gì nếu không có hình ảnh
                } else {
                    try {
                        // Nạp hình ảnh từ thư mục resources
                        Image image = new Image(getClass().getResourceAsStream("/com/app/schoolmanagementsystem/images/" + imageName));
                        imageView.setImage(image);

                        // Đặt kích thước hình dạng bo tròn
                        clip.setWidth(imageView.getFitWidth());
                        clip.setHeight(imageView.getFitHeight());

                        setGraphic(imageView);
                        setAlignment(Pos.CENTER);
                    } catch (Exception e) {
                        e.printStackTrace();
                        setGraphic(null); // Xử lý lỗi nếu không thể nạp hình ảnh
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Staff Details");
        alert.setHeaderText("Details of Staff ID: " + staff.getStaffID());
        alert.setContentText(staff.toString()); // Customize this to show more details
        alert.showAndWait();

        resetSearchFields();
    }

    private void editStaff(int index) {
        Staff staff = staffTableView.getItems().get(index);
        // Implement edit logic here
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Staff");
        alert.setHeaderText("Edit Staff ID: " + staff.getStaffID());
        alert.setContentText("Edit functionality is not implemented yet.");
        alert.showAndWait();

        resetSearchFields();
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