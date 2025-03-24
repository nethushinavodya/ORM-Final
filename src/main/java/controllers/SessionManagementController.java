package controllers;

import dto.SessionDto;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class SessionManagementController {
    public Button backBtn;
    public TextField txtSessionID;
    public ComboBox<String> cmbTherapistID;
    public ComboBox<String> cmbProgramID;
    public ComboBox<String> cmbPatientID;
    public DatePicker dateSession;
    public Button btnAddSession;
    public Button btnUpdateSession;
    public Button btnDeleteSession;
    public Button btnSearchSession;
    public TableView<SessionDto> tblSessions;
    public TableColumn<?,?> colSessionID;
    public TableColumn<?,?> colTherapistID;
    public TableColumn<?,?> colProgramID;
    public TableColumn<?,?> colPatientID;
    public TableColumn<?,?> colSessionDate;


    public void backOnAction(ActionEvent actionEvent) {
    }

    public void addSession(ActionEvent actionEvent) {
    }

    public void updateSession(ActionEvent actionEvent) {
    }

    public void deleteSession(ActionEvent actionEvent) {
    }

    public void searchSession(ActionEvent actionEvent) {
    }

    public void sessionTblClicked(MouseEvent mouseEvent) {
    }
}
