package controllers;

import bo.BOFactory;
import bo.custom.PatientBO;
import dto.PatientDto;
import dto.UserDto;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PatientManagementController {

    public TextField txtPatientID;
    public TextField txtPatientName;
    public TextField txtPatientEmail;
    public TextField txtPatientAddress;
    public TextField txtPatientTel;
    public Button btnAddPatient;
    public Button btnUpdatePatient;
    public Button btnDeletePatient;
    public Button btnSearchPatient;
    public TableView<PatientDto> tblPatients;
    public TableColumn<?,?> colID;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colEmail;
    public TableColumn<?,?> colAddress;
    public TableColumn<?,?> colTel;
    public Button backbit;

    static UserDto userDto;
    PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);

    public void initialize() throws SQLException, ClassNotFoundException {
        userDto = LoginFormController.getUserDto();
        setcellvaluefactory();
        getallPatient();
        checkrole(userDto);
        clear();
    }

    public void clear() {
        txtPatientID.clear();
        txtPatientName.clear();
        txtPatientEmail.clear();
        txtPatientAddress.clear();
        txtPatientTel.clear();
    }
    private void checkrole(UserDto userDto) {
        System.out.println(userDto.getRole());

        if (userDto.getRole().equals("admin")){
            btnAddPatient.setDisable(true);
            btnDeletePatient.setDisable(true);
            btnUpdatePatient.setDisable(true);
        }
    }

    public void setcellvaluefactory(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }
    public void getallPatient() throws SQLException, ClassNotFoundException {
        ObservableList<PatientDto> patientDtos = patientBO.getAllPatients();
        tblPatients.setItems(patientDtos);
    }

    public void addPatient(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtPatientID.getText();
        String name = txtPatientName.getText();
        String email = txtPatientEmail.getText();
        String address = txtPatientAddress.getText();
        String tel = txtPatientTel.getText();


            PatientDto patientDto = new PatientDto(id,name,email,address,tel);
            boolean isSaved = patientBO.addPatient(patientDto , userDto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Patient Saved");
                getallPatient();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Patient Not Saved");
            }
    }

    public void updatePatient(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtPatientID.getText();
        String name = txtPatientName.getText();
        String email = txtPatientEmail.getText();
        String address = txtPatientAddress.getText();
        String tel = txtPatientTel.getText();

        PatientDto patientDto = new PatientDto(id,name,email,address,tel);
        boolean isUpdate = patientBO.updatePatient(patientDto);
        if (isUpdate){
            new Alert(Alert.AlertType.INFORMATION,"Update successful");
            getallPatient();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Update failed");
        }
    }

    public void deletePatient(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtPatientID.getText();

        boolean isDelete = patientBO.deletePatient(id);
        if (isDelete){
            new Alert(Alert.AlertType.INFORMATION,"Delete successful");
            getallPatient();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Delete failed");
        }

    }

    public void searchPatient(ActionEvent actionEvent) {
        String id = txtPatientID.getText();

        PatientDto patientDto = patientBO.searchPatient(id);
        if (patientDto != null){
            txtPatientName.setText(patientDto.getName());
            txtPatientEmail.setText(patientDto.getEmail());
            txtPatientAddress.setText(patientDto.getAddress());
            txtPatientTel.setText(patientDto.getTel());
        }else {
            new Alert(Alert.AlertType.ERROR,"Patient not found");
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) txtPatientID.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Page");
    }


    public void patientOnClicked(MouseEvent mouseEvent) {
        PatientDto patientDto = tblPatients.getSelectionModel().getSelectedItem();

        if (patientDto != null){
            txtPatientID.setText(patientDto.getId());
            txtPatientName.setText(patientDto.getName());
            txtPatientEmail.setText(patientDto.getEmail());
            txtPatientAddress.setText(patientDto.getAddress());
            txtPatientTel.setText(patientDto.getTel());
        }
    }
    public void PatientIdOnreleasedOnAction(KeyEvent keyEvent) {
    }

    public void PatientNameOnActionReleased(KeyEvent keyEvent) {
    }

    public void PatientEmailOnActionReleased(KeyEvent keyEvent) {
    }

    public void PatientAddressOnActionReleased(KeyEvent keyEvent) {
    }

    public void PatientTelOnActionReleased(KeyEvent keyEvent) {
    }

}
