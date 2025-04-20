package controllers;

import bo.BOFactory;
import bo.custom.*;
import dto.PatientDto;
import dto.PaymentDTO;
import dto.ProgramDto;
import dto.Therapy_SessionDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SessionManagementController {
    public Button backBtn;
    public ComboBox<String> cmbTherapistID;
    public ComboBox<String> cmbProgramID;
    public ComboBox<String> cmbPatientID;
    public DatePicker dateSession;
    public Button btnAddSession;
    public Button btnUpdateSession;
    public Button btnDeleteSession;
    public TextField txtPatientName;
    public TextField txtPatientContact;
    public TextField txtProgramName;
    public TextField txtProgramfee;
    public TextField txtAmountPaid;
    public ComboBox<String> cmbPayment;
    public Button btnGenerateInvoice;

    SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);
    ProgramBO programBO = (ProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.PROGRAM);
    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

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
    }

    public void clear() {
        cmbTherapistID.getSelectionModel().clearSelection();
        cmbProgramID.getSelectionModel().clearSelection();
        cmbPatientID.getSelectionModel().clearSelection();
        cmbPayment.getSelectionModel().clearSelection();
        txtPatientName.clear();
        txtPatientContact.clear();
        txtProgramfee.clear();
        txtProgramName.clear();
        dateSession.setValue(null);
        cmbTherapistID.getSelectionModel().clearSelection();
        txtAmountPaid.clear();
    }

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
    }
    public void setCmbTherapistID() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<String> therapistIds = therapistBO.getAvailableTherapistIds();
        observableList.addAll(therapistIds);
        cmbTherapistID.setItems(observableList);
        cmbTherapistID.getSelectionModel().clearSelection();
    }

    public void loadtherapistId(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String programId = cmbProgramID.getSelectionModel().getSelectedItem();

        if (programId == null) {
            return;
        }

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
        String patientId = cmbPatientID.getValue();
        String programId = cmbProgramID.getValue();
        String therapistId = cmbTherapistID.getValue();
        String sessionDate = dateSession.getValue().toString();
        String programfee = txtProgramfee.getText();
        String amountPaid = txtAmountPaid.getText();
        String paymentdetails = cmbPayment.getValue();

        double paidAmount =Double.parseDouble(programfee) - Double.parseDouble(amountPaid);
        PaymentDTO paymentDTO = new PaymentDTO(paymentdetails,Double.parseDouble(programfee),paidAmount);
        Therapy_SessionDto therapySessionDto = new Therapy_SessionDto(patientId,programId,therapistId,sessionDate);

            boolean isAdded = sessionBO.addSession(therapySessionDto,paymentDTO);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION,"Successfully Added");
                clear();

            }else {
                new Alert(Alert.AlertType.ERROR,"Add Failed");
            }
    }

    public void updateSession(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String patientId = cmbPatientID.getValue();
        String programId = cmbProgramID.getValue();
        String therapistId = cmbTherapistID.getValue();
        String sessionDate = dateSession.getValue().toString();

        Therapy_SessionDto therapySessionDto = new Therapy_SessionDto(patientId,programId,therapistId,sessionDate);

        boolean isUpdated = sessionBO.updateSession(therapySessionDto);
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Updated");
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Update Failed");
        }
    }

    public void deleteSession(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String patientId = cmbPatientID.getValue();
        boolean isDeleted = sessionBO.deletePatient(patientId);
        if (isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Deleted");
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Delete Failed");
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage)backBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Page");
    }

    public void patientCmbOnAction(ActionEvent actionEvent) {
        String patientId = cmbPatientID.getSelectionModel().getSelectedItem();

        if (patientId == null) {
            return;
        }

        PatientDto patient = patientBO.searchPatient(patientId);

        txtPatientName.setText(patient.getName());
        txtPatientContact.setText(patient.getTel());

    }

    public void generateInvoice(ActionEvent actionEvent) {
        try {
            if (cmbPatientID.getValue() == null || cmbProgramID.getValue() == null ||
                    dateSession.getValue() == null || txtAmountPaid.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please complete all required fields").show();
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Invoice.fxml"));
            Parent root = loader.load();

            InvoiceController invoiceController = loader.getController();

            invoiceController.setInvoiceData(
                    cmbPatientID.getValue(),
                    txtPatientName.getText(),
                    txtPatientContact.getText(),
                    cmbProgramID.getValue(),
                    txtProgramName.getText(),
                    cmbTherapistID.getValue(),
                    dateSession.getValue().toString(),
                    txtProgramfee.getText(),
                    txtAmountPaid.getText(),
                    cmbPayment.getValue()
            );

            Stage invoiceStage = new Stage();
            Scene scene = new Scene(root);
            invoiceStage.setScene(scene);
            invoiceStage.setTitle("Therapy Session Invoice");

            invoiceController.setPrintAction(invoiceStage);

            invoiceStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate invoice: " + e.getMessage()).show();
        }
    }
}
