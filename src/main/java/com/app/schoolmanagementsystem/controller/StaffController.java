package com.app.schoolmanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class StaffController implements Initializable {


    @FXML
    private Button btn_add;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_salary;

    @FXML
    private Button btn_promotion;

    @FXML
    private TextField txt_search_idStaff;

    @FXML
    private TableView<?> table_list_Staff;

    @FXML
    private Pagination pag_list_Staff;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Thiết lập hành động cho các nút khi khởi tạo
//        btn_add.setOnMouseClicked(this::handleAddAction);
//        btn_update.setOnMouseClicked(this::handleUpdateAction);
//        btn_delete.setOnMouseClicked(this::handleDeleteAction);
//        btn_salary.setOnMouseClicked(this::handleSalaryAction);
//        btn_promotion.setOnMouseClicked(this::handlePromotionAction);
    }

//    // Hàm xử lý khi nhấn nút "Add"
//    private void handleAddAction(MouseEvent event) {
//        System.out.println("Adding new staff...");
//        // Thêm logic thêm nhân viên tại đây
//    }
//
//    // Hàm xử lý khi nhấn nút "Update"
//    private void handleUpdateAction(MouseEvent event) {
//        System.out.println("Updating staff information...");
//        // Thêm logic cập nhật thông tin nhân viên tại đây
//    }
//
//    // Hàm xử lý khi nhấn nút "Delete"
//    private void handleDeleteAction(MouseEvent event) {
//        System.out.println("Deleting staff...");
//        // Thêm logic xóa nhân viên tại đây
//    }
//
//    // Hàm xử lý khi nhấn nút "Salary"
//    private void handleSalaryAction(MouseEvent event) {
//        System.out.println("Managing staff salary...");
//        // Thêm logic quản lý lương nhân viên tại đây
//    }
//
//    // Hàm xử lý khi nhấn nút "Promotion"
//    private void handlePromotionAction(MouseEvent event) {
//        System.out.println("Promoting staff...");
//        // Thêm logic thăng chức nhân viên tại đây
//    }
//
//    // Hàm tìm kiếm nhân viên theo ID
//    private void searchStaffById() {
////        String staffId = txt_search_idStaff.getText();
////        System.out.println("Searching for staff with ID: " + staffId);
////        // Thêm logic tìm kiếm nhân viên theo ID tại đây
//    }
}
