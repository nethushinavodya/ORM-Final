package controllers;

import bo.BOFactory;
import bo.custom.PatientBO;
import bo.custom.PaymentBO;
import bo.custom.SessionBO;
import dto.PatientDto;
import dto.TherapyProgramHistoryDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PatientHistoryController {
    public Button btnSearch;
    public Label lblPatientName;
    public Label lblContactNumber;
    public Label lblEmail;
    public Label lblRegistrationDate;
    public Label lblAddress;
    public TableView<TherapyProgramHistoryDTO> tblTherapyPrograms;
    public TableColumn<TherapyProgramHistoryDTO, String> colProgramId;
    public TableColumn<TherapyProgramHistoryDTO, String> colProgramName;
    public TableColumn<TherapyProgramHistoryDTO, String> colFee;
    public TableColumn<TherapyProgramHistoryDTO, Double> colRemainAmount;
    public ComboBox<String> cmbPatientId;
    public TableColumn<TherapyProgramHistoryDTO, String> colTherapistName;
    public TableColumn<TherapyProgramHistoryDTO, String> colPaymentDate;

    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);

    public void initialize() throws SQLException, ClassNotFoundException {
        setCmbPatientId();
        setCellValue();
    }

    public void setCellValue() {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colTherapistName.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colRemainAmount.setCellValueFactory(new PropertyValueFactory<>("remainingAmount"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
    }

    public void searchPatient(ActionEvent actionEvent) {
        String patientId = cmbPatientId.getSelectionModel().getSelectedItem();
        if (patientId == null) return;

        // Load patient basic information
        PatientDto patient = patientBO.searchPatient(patientId);
        if (patient != null) {
            lblPatientName.setText(patient.getName());
            lblContactNumber.setText(patient.getTel());
            lblEmail.setText(patient.getEmail());
            lblRegistrationDate.setText(patient.getRegisterDate());
            lblAddress.setText(patient.getAddress());

            // Load therapy program history for this patient
            loadTherapyProgramHistory(patientId);
        }
    }

    private void loadTherapyProgramHistory(String patientId) {
        try {
            List<TherapyProgramHistoryDTO> historyList = sessionBO.getPatientTherapyHistory(patientId);
            ObservableList<TherapyProgramHistoryDTO> observableList = FXCollections.observableArrayList(historyList);
            tblTherapyPrograms.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load therapy history: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void setCmbPatientId() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList = FXCollections.observableArrayList();

        List<PatientDto> patientIds = patientBO.getAllPatients();
        for (PatientDto patientDto : patientIds) {
            observableList.add(patientDto.getId());
        }
        cmbPatientId.setItems(observableList);
    }

    public void cmbPatientId(ActionEvent actionEvent) {
    }
}