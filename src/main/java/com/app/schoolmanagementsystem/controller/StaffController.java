package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.model.StaffModel;
import com.app.schoolmanagementsystem.model.StudentModel;
import com.app.schoolmanagementsystem.services.AuthService;
import com.app.schoolmanagementsystem.session.UserSession;
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
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private TableColumn<Staff, String> positionNameColumn;

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
    private ImageView reloadButton;


    private StaffModel staffModel = new StaffModel();
    private ObservableList<Staff> staffList = FXCollections.observableArrayList();

    @FXML
    private Label label_StaffID;

    private Integer IDStaff;

    private Image defaultAvatar;
    private final Map<String, Image> avatarCache = new HashMap<>();



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
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load PageAddStaff.fxml");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staffIDColumn.setCellValueFactory(new PropertyValueFactory<>("staffID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        genderColumn.setCellValueFactory(cellData -> {
            byte genderValue = cellData.getValue().getGender();
            String genderText = (genderValue == 1) ? "Male" : "Female";
            return new SimpleStringProperty(genderText);
        });
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));

        positionNameColumn.setCellValueFactory(new PropertyValueFactory<>("positionName"));

        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        educationBackgroundColumn.setCellValueFactory(new PropertyValueFactory<>("educationBackground"));
        experienceColumn.setCellValueFactory(new PropertyValueFactory<>("experience"));

        avatarColumn.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        configureColumnAlignment(staffIDColumn);
        configureColumnAlignment(firstNameColumn);
        configureColumnAlignment(lastNameColumn);
        configureColumnAlignment(dateOfBirthColumn);
        configureColumnAlignment(genderColumn);
        configureColumnAlignment(addressColumn);
        configureColumnAlignment(phoneNumberColumn);
        configureColumnAlignment(emailColumn);
        configureColumnAlignment(hireDateColumn);
        configureColumnAlignment(positionNameColumn);
        configureColumnAlignment(salaryColumn);
        configureColumnAlignment(educationBackgroundColumn);
        configureColumnAlignment(experienceColumn);
        configureColumnAlignment(avatarColumn);
        configureColumnAlignment(statusColumn);

        configureActionColumn();
        configureAvatarColumn();

        reloadButton.setOnMouseClicked(event -> reloadPage());

        ObservableList<String> searchOptions = FXCollections.observableArrayList("Filter", "Gender", "ID");
        selectBox.setItems(searchOptions);
        selectBox.setValue("Filter");

        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchStaff());
        selectBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> searchStaff());
        extraSearchField.textProperty().addListener((observable, oldValue, newValue) -> searchStaff());

        staffModel = new StaffModel();

        loadStaffDataByLogic(null, null, null, null);

        loadStaffData();
    }

    private void searchStaff() {
        String searchText = searchField.getText().trim();
        String extraSearchText = extraSearchField.getText().trim();

        String firstName = null;
        String email = null;

        if (searchText.contains("@")) {
            email = searchText;
        } else if (!searchText.isEmpty()) {
            firstName = searchText;
        }

        Integer id = null;
        Byte gender = null;

        String selectedOption = selectBox.getValue();
        if ("ID".equals(selectedOption)) {
            try {
                id = Integer.parseInt(extraSearchText);
            } catch (NumberFormatException e) {
                id = null;
            }
        } else if ("Gender".equals(selectedOption)) {
            if ("Male".equalsIgnoreCase(extraSearchText)) {
                gender = (byte) 1;
            } else if ("Female".equalsIgnoreCase(extraSearchText)) {
                gender = (byte) 0;
            }
        }

        updateActionColumn();

        loadStaffDataByLogic(firstName, email, gender, id);
    }


    private void resetSearchFields() {
        searchField.setText("");
        extraSearchField.setText("");
        selectBox.setValue("");
    }

    private void loadStaffDataByLogic(String firstName, String email, Byte gender, Integer id) {
        staffList.clear();
        List<Staff> staffs = staffModel.searchStaff(firstName, email, gender, id);
        staffList.addAll(staffs);
        staffTableView.setItems(staffList);
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
                    Staff staff = getTableView().getItems().get(getIndex());
                    String fullAvatarPath = staff.getFullAvatarPath();

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
                imageView.setFitHeight(32);
                imageView.setFitWidth(32);
                imageView.setPreserveRatio(true);

                imageView.setClip(new Circle(16, 16, 16));
                setGraphic(empty ? null : imageView);
                HBox hBox = new HBox(imageView);
                hBox.setAlignment(Pos.CENTER);
                setGraphic(hBox);
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
                        detailImageView.setFitHeight(24);
                        detailImageView.setFitWidth(24);
                        editImageView.setFitHeight(24);
                        editImageView.setFitWidth(24);
                        deleteImageView.setFitHeight(24);
                        deleteImageView.setFitWidth(24);

                        HBox hBox = new HBox(10, detailImageView, editImageView, deleteImageView);
                        hBox.setAlignment(Pos.CENTER);

                        detailImageView.setOnMouseClicked(e -> showDetails(getIndex()));

                        editImageView.setOnMouseClicked(e -> {
                            Staff staff = getTableRow() != null ? getTableRow().getItem() : null;
                            if (staff != null) {
                                System.out.println("Editing staff with ID: " + staff.getStaffID());
                                editStaff(staff.getStaffID());
                            } else {
                                System.err.println("No staff data available.");
                            }
                        });

                        deleteImageView.setOnMouseClicked(e -> deleteStaff(getIndex()));

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
                        detailImageView.setFitHeight(24);
                        detailImageView.setFitWidth(24);
                        editImageView.setFitHeight(24);
                        editImageView.setFitWidth(24);
                        deleteImageView.setFitHeight(24);
                        deleteImageView.setFitWidth(24);

                        HBox hBox = new HBox(10, detailImageView, editImageView, deleteImageView);
                        hBox.setAlignment(Pos.CENTER);

                        detailImageView.setOnMouseClicked(e -> showDetails(getIndex()));
                        editImageView.setOnMouseClicked(e -> {
                            Staff staff = getTableRow().getItem();
                            if (staff != null) {
                                editStaff(staff.getStaffID());
                            }
                        });
                        deleteImageView.setOnMouseClicked(e -> deleteStaff(getIndex()));
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

    private AuthService authService = new AuthService();

    public void loginUser(String email, String password) {
        if (authService.login(email, password)) {
            String roleName = authService.getRoleName(email);
            UserSession.setCurrentRoleName(roleName);
            System.out.println("Logged in with role: " + roleName);
        } else {
            System.out.println("Login failed.");
        }
    }

    public String getCurrentRoleName() {
        return UserSession.getCurrentRoleName();
    }

    private boolean canPerformAction(int staffID) {
        String staffRoleName = getStaffRoleNameByStaffID(staffID);
        String userRoleName = getCurrentRoleName();

        System.out.println("User roleName: " + userRoleName);
        System.out.println("Staff roleName: " + staffRoleName);

        if ("Admin Master".equals(userRoleName)) {
            return true;
        } else if ("Manager".equals(userRoleName) && "Teacher".equals(staffRoleName)) {
            return true;
        }
        return false;
    }

    private String getStaffRoleNameByStaffID(int staffID) {
        return staffModel.getRoleByStaffID(staffID);
    }

    private void showDetails(int index) {
        Staff staff = staffTableView.getItems().get(index);

        String userRoleName = UserSession.getCurrentRoleName();
        String staffRoleName = getStaffRoleNameByStaffID(staff.getStaffID());

        System.out.println("User roleName: " + userRoleName);
        System.out.println("Staff roleName: " + staffRoleName);

        if (canPerformAction(staff.getStaffID())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Staff Details");
            alert.setHeaderText("Details of Staff ID: " + staff.getStaffID());
            alert.setContentText("Name: " + staff.getStaffID() + "\nRole: " + staffRoleName);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Permission Denied");
            alert.setHeaderText("Access Denied");
            alert.setContentText("You do not have permission to view details of this staff member.");
            alert.showAndWait();
        }

        resetSearchFields();
    }

    private void editStaff(int staffId) {
        System.out.println("Attempting to edit staff with ID: " + staffId);

        try {
            Staff staff = staffModel.getStaffByID(staffId);

            if (staff == null) {
                System.err.println("Staff with ID " + staffId + " not found.");
                throw new IllegalArgumentException("Staff with ID " + staffId + " not found.");
            }

            System.out.println("Editing staff: " + staff);

            String userRoleName = UserSession.getCurrentRoleName();
            String staffRoleName = getStaffRoleNameByStaffID(staffId);

            System.out.println("User roleName: " + userRoleName);
            System.out.println("Staff roleName: " + staffRoleName);

            if (canPerformAction(staffId)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/schoolmanagementsystem/views/PageEditStaff.fxml"));
                StackPane pageEditStaff = loader.load();

                EditStaffController editStaffController = loader.getController();
                editStaffController.setStaffData(staff);
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
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Permission Denied");
                alert.setHeaderText("Access Denied");
                alert.setContentText("You do not have permission to edit this staff member.");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load PageEditStaff.fxml");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            resetSearchFields();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Staff Not Found");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void deleteStaff(int index) {
        Staff staff = staffTableView.getItems().get(index);

        String userRoleName = UserSession.getCurrentRoleName();
        String staffRoleName = getStaffRoleNameByStaffID(staff.getStaffID());

        System.out.println("User roleName: " + userRoleName);
        System.out.println("Staff roleName: " + staffRoleName);

        if (canPerformAction(staff.getStaffID())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Staff");
            alert.setHeaderText("Are you sure you want to delete Staff ID: " + staff.getStaffID() + "?");
            if (alert.showAndWait().get().getButtonData().isDefaultButton()) {
                staffModel.deleteStaff(staff.getStaffID());
                loadStaffData();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Permission Denied");
            alert.setHeaderText("Access Denied");
            alert.setContentText("You do not have permission to delete this staff member.");
            alert.showAndWait();
        }

        resetSearchFields();
    }



    private void loadStaffData() {
        List<Staff> newStaffList = staffModel.getActiveStaff();
        staffList.clear();
        staffList.addAll(newStaffList);
        staffTableView.setItems(staffList);
        updateActionColumn();
        configureAvatarColumn();
        loadStaffDataByLogic(null, null, null, null);
    }

    private void reloadPage() {
        resetSearchFields();
        loadStaffData();
    }
}