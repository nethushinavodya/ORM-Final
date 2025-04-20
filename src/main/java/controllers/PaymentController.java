package controllers;

import bo.BOFactory;
import bo.custom.PatientBO;
import bo.custom.PaymentBO;
import bo.custom.ProgramBO;
import bo.custom.SessionBO;
import dto.PatientDto;
import dto.PaymentDTO;
import dto.ProgramDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PaymentController {
    public TextField txtPaymentID;
    public TextField txtAmount;
    public DatePicker date;
    public TextField txtProgramName;
    public TextField txtProgramFee;
    public Button backbit;
    public ComboBox<String> cmbPatient;
    public Button btnPay;
    public Button btnClear;
    public ComboBox<String> cmbProgram;
    public TextField txtPayingAmount;

    ProgramBO programBO = (ProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.PROGRAM);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);

    public void initialize() throws SQLException, ClassNotFoundException {
        setPatientId();
    }

    public void clear(){
        txtPaymentID.clear();
        txtAmount.clear();
        date.getEditor().clear();
        txtProgramName.clear();
        txtProgramFee.clear();
        txtPayingAmount.clear();
    }
    private void setPatientId() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<String> patientIds = sessionBO.getPatientIdsFromTherapySessions();
        observableList.addAll(patientIds);
        cmbPatient.setItems(observableList);
    }

    public void patientCmbOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String patientId = cmbPatient.getSelectionModel().getSelectedItem();
        setProgramId(patientId);
    }

    private void setProgramId(String patientId) throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<String> programIds = sessionBO.getProgramIds(patientId);
        observableList.addAll(programIds);
        cmbProgram.setItems(observableList);
    }

    public void programCmbOnAction(ActionEvent actionEvent) {
        String programId = cmbProgram.getSelectionModel().getSelectedItem();
        ProgramDto program = programBO.searchProgram(programId);
        String patiendId = cmbPatient.getSelectionModel().getSelectedItem();

        String sessionId = String.valueOf(sessionBO.searchSessionId(patiendId, programId));
        System.out.println(sessionId);
        PaymentDTO payment = paymentBO.searchPayment(sessionId);
        txtPaymentID.setText(String.valueOf(payment.getPaymentId()));
        txtAmount.setText(String.valueOf(payment.getRemainingAmount()));
        txtProgramName.setDisable(true);
        txtProgramFee.setDisable(true);
        txtProgramName.setText(program.getName());
        txtProgramFee.setText(program.getFee());
    }

    public void payAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentID.getText();
        String payingAmount = txtPayingAmount.getText();

        boolean isPaid = paymentBO.pay(paymentId, payingAmount);
        if (isPaid) {
            new Alert(Alert.AlertType.CONFIRMATION, "Payment successful").show();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR, "Payment failed").show();
        }
    }

    public void clearAction(ActionEvent actionEvent) {
        cmbPatient.getSelectionModel().clearSelection();
        cmbProgram.getSelectionModel().clearSelection();
        txtPaymentID.clear();
        txtAmount.clear();
        date.getEditor().clear();
        txtProgramName.clear();
        txtProgramFee.clear();
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) txtPaymentID.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Page");
    }
}
