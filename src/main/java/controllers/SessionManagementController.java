package controllers;

import bo.BOFactory;
import bo.custom.PatientBO;
import bo.custom.ProgramBO;
import bo.custom.SessionBO;
import bo.custom.TherapistBO;
import dto.PatientDto;
import dto.ProgramDto;
import dto.Therapy_SessionDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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
    public TableView<Therapy_SessionDto> tblSessions;
    public TableColumn<?,?> colSessionID;
    public TableColumn<?,?> colTherapistID;
    public TableColumn<?,?> colProgramID;
    public TableColumn<?,?> colPatientID;
    public TableColumn<?,?> colProgramName;
    public TableColumn<?,?> colFee;
    public TableColumn<?,?> colSessionDate;
    public TextField txtPatientName;
    public TextField txtPatientContact;
    public TextField txtProgramName;
    public TextField txtProgramfee;
    public TextField txtAmountPaid;
    public ComboBox<String> cmbPayment;

    SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);
    ProgramBO programBO = (ProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.PROGRAM);
    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);

    public void initialize() throws SQLException, ClassNotFoundException {
        cmbPayment.getItems().addAll("Full Payment", "Installment Payment");
        txtPatientName.setDisable(true);
        txtPatientContact.setDisable(true);
        txtProgramName.setDisable(true);
        txtProgramfee.setDisable(true);
        clear();
        setCmbProgramID();
        setCmbTherapistID();
        setCmbPatientID();
//        getAllSessions();
    }

    public void clear() {
        txtPatientName.clear();
        txtPatientContact.clear();
        txtProgramfee.clear();
        txtProgramName.clear();
        cmbTherapistID.getSelectionModel().clearSelection();
        cmbProgramID.getSelectionModel().clearSelection();
        cmbPatientID.getSelectionModel().clearSelection();
        cmbPayment.getSelectionModel().clearSelection();
        dateSession.setValue(null);
    }
//
//    public void getAllSessions() throws SQLException, ClassNotFoundException {
//        ObservableList<Therapy_SessionDto> therapySessionDtos = sessionBO.getAllSessions();
//    }
    public void setCmbPatientID() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        List<PatientDto> patientIds = patientBO.getAllPatients();
        for (PatientDto patientDto : patientIds) {
            observableList.add(patientDto.getId());
        }
        cmbPatientID.setItems(observableList);

    }
    public void setCmbProgramID() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        List<ProgramDto> programIds = programBO.getAllPrograms();
        for (ProgramDto programDto : programIds) {
            observableList.add(programDto.getProgramId());
        }
        cmbProgramID.setItems(observableList);
        clear();
    }
    public void setCmbTherapistID() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<String> therapistIds = therapistBO.getAvailableTherapistIds();
        observableList.addAll(therapistIds);
        cmbTherapistID.setItems(observableList);

    }

    public void loadtherapistId(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String programId = cmbProgramID.getSelectionModel().getSelectedItem();

        ProgramDto program = programBO.searchProgram(programId);
        txtProgramName.setText(program.getName());
        txtProgramfee.setText(program.getFee());

        System.out.println("programId = " + programId);
        if (programId != null) {
            String id = sessionBO.search(programId);

            System.out.println(id);
            if (id != null) {
                cmbTherapistID.setValue(id);
                cmbTherapistID.setDisable(true);
            }else {
                setCmbTherapistID();
                cmbTherapistID.setPromptText("Select Therapist");
                cmbTherapistID.setDisable(false);
            }
        }else {
                setCmbTherapistID();
                cmbTherapistID.setPromptText("Select Therapist");
                cmbTherapistID.setDisable(false);
            }
    }

    public void addSession(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        long sessionId = Long.parseLong(txtSessionID.getText());
        String patientId = cmbPatientID.getValue();
        String programId = cmbProgramID.getValue();
        String therapistId = cmbTherapistID.getValue();
        String sessionDate = dateSession.getValue().toString();

        Therapy_SessionDto therapySessionDto = new Therapy_SessionDto(sessionId,patientId,programId,therapistId,sessionDate);

            boolean isAdded = sessionBO.addSession(therapySessionDto);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION,"Successfully Added");
//                getAllSessions();
                clear();

            }else {
                new Alert(Alert.AlertType.ERROR,"Add Failed");
            }
    }

    public void updateSession(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        long sessionId = Long.parseLong(txtSessionID.getText());
        String patientId = cmbPatientID.getValue();
        String programId = cmbProgramID.getValue();
        String therapistId = cmbTherapistID.getValue();
        String sessionDate = dateSession.getValue().toString();

        Therapy_SessionDto therapySessionDto = new Therapy_SessionDto(sessionId,patientId,programId,therapistId,sessionDate);

        boolean isUpdated = sessionBO.updateSession(therapySessionDto);
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Updated");
//            getAllSessions();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Update Failed");
        }
    }

    public void deleteSession(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        long sessionId = Long.parseLong(txtSessionID.getText());
        boolean isDeleted = sessionBO.deleteSession(sessionId);
        if (isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Deleted");
//            getAllSessions();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Delete Failed");
        }
    }

//    public void sessionTblClicked(MouseEvent mouseEvent) {
//        Therapy_SessionDto therapySessionDto = tblSessions.getSelectionModel().getSelectedItem();
//
//        if (therapySessionDto != null) {
//            txtSessionID.setText(String.valueOf(therapySessionDto.getSessionId()));
//            cmbPatientID.setValue(therapySessionDto.getPatientId());
//            cmbProgramID.setValue(therapySessionDto.getProgramId());
//            cmbTherapistID.setValue(therapySessionDto.getTherapistId());
//            dateSession.setValue(LocalDate.parse(therapySessionDto.getSessionDate()));
//        }
//    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) txtSessionID.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Page");
    }

    public void patientCmbOnAction(ActionEvent actionEvent) {
        String patientId = cmbPatientID.getSelectionModel().getSelectedItem();

        PatientDto patient = patientBO.searchPatient(patientId);

        txtPatientName.setText(patient.getName());
        txtPatientContact.setText(patient.getTel());

    }
}
