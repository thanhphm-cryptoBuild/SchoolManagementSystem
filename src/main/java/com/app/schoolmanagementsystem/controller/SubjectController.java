package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.entities.Subject;
import com.app.schoolmanagementsystem.entities.SubjectClass;
import com.app.schoolmanagementsystem.model.SubjectClassModel;
import com.app.schoolmanagementsystem.model.SubjectModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SubjectController implements Initializable {

    @FXML
    private StackPane pageSubject;

    @FXML
    private ChoiceBox<?> selectClassNo;

    @FXML
    private ChoiceBox<?> selectSectionClass;

    @FXML
    private ChoiceBox<Subject> selectSubject;

    @FXML
    private ChoiceBox<?> selectTeacherID;

    @FXML
    private ChoiceBox<?> selectTeacherName;

    @FXML
    private TextField textDescription;


    @FXML
    void addFormSubject(MouseEvent event) {
    }

    @FXML
    void resetFormSubject(MouseEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
            loadSubjects();
    }

    private void loadSubjects() {
        SubjectModel subjectModel = new SubjectModel();
        List<Subject> subjects = subjectModel.selectName();

        ObservableList<Subject> observableSubjectList = FXCollections.observableArrayList(subjects);

        selectSubject.setItems(observableSubjectList);
        selectSubject.setConverter(new javafx.util.StringConverter<Subject>() {
            @Override
            public String toString(Subject subject) {
                return subject != null ? subject.getSubjectName() : "";
            }

            @Override
            public Subject fromString(String string) {
                return null;
            }
        });
    }

}
