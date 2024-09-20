package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Grades;
import com.app.schoolmanagementsystem.entities.Subject;
import com.app.schoolmanagementsystem.model.GradesModel;
import com.app.schoolmanagementsystem.model.SubjectModel;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GradeController implements Initializable {

    @FXML
    private StackPane formGradeStudent;

    @FXML
    private StackPane pageStudent;

    @FXML
    private AnchorPane moveBG;

    @FXML
    private TableColumn<Grades, String> collumGrades;

    @FXML
    private TableColumn<Grades, String> collumSubject;

    @FXML
    private TableView<Grades> tableSubjectGrades;

    private GradesModel gradesModel;


    public void setPageStudent(StackPane pageStudent) {
        this.pageStudent = pageStudent;
    }

    public void setBGPageStudent(AnchorPane moveBG) {
        this.moveBG = moveBG;
    }

    @FXML
    void closeFormGradeStudent(MouseEvent event) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.2));
        translateTransition.setNode(formGradeStudent);
        translateTransition.setToX(2000);

        translateTransition.play();

        translateTransition.setOnFinished(actionEvent -> {
            pageStudent.getChildren().remove(formGradeStudent);
            moveBG.setEffect(null);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
