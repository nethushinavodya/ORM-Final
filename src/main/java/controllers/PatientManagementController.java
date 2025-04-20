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
import java.time.LocalDate;

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
    public TableColumn<?,?> colDate;
    public DatePicker regDate;
    public Button backbit;

    static UserDto userDto;
    public Button btnPatientHistory;
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
        colDate.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
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
        String registerDate = regDate.getValue().toString();

        if (isValid()) {
            PatientDto patientDto = new PatientDto(id,name,email,address,tel,registerDate);
            boolean isSaved = patientBO.addPatient(patientDto , userDto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Patient Saved");
                getallPatient();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Patient Not Saved");
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Please input valid data");
        }
    }

    public void updatePatient(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtPatientID.getText();
        String name = txtPatientName.getText();
        String email = txtPatientEmail.getText();
        String address = txtPatientAddress.getText();
        String tel = txtPatientTel.getText();
        String registerDate = regDate.getValue().toString();

        if (isValid()){
            PatientDto patientDto = new PatientDto(id,name,email,address,tel,registerDate);
            boolean isUpdate = patientBO.updatePatient(patientDto);
            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Update successful");
                getallPatient();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Update failed");
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Please input valid data");
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
            regDate.setValue(LocalDate.parse(patientDto.getRegisterDate()));
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
            regDate.setValue(LocalDate.parse(patientDto.getRegisterDate()));
        }
    }


    public void patientHistory(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/patientHistory.fxml"))));
        stage.setTitle("Patient History Page");
        stage.show();
    }

    public boolean isValid(){
        String email = txtPatientEmail.getText();
        String tel = txtPatientTel.getText();

        if (
                email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$") &&
                        tel.matches("^(?:7|0|(?:\\+94))[0-9]{9,10}$")
        ) {
            return true;
        } else {
            return false;
        }
    }
    public void PatientIdOnreleasedOnAction(KeyEvent keyEvent) {
    }

    public void PatientNameOnActionReleased(KeyEvent keyEvent) {
    }

    public void PatientEmailOnActionReleased(KeyEvent keyEvent) {
        if (txtPatientEmail.getText().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
            txtPatientEmail.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }else {
            txtPatientEmail.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
    }

    public void PatientAddressOnActionReleased(KeyEvent keyEvent) {
    }

    public void PatientTelOnActionReleased(KeyEvent keyEvent) {
        if (txtPatientTel.getText().matches("^(?:7|0|(?:\\+94))[0-9]{9,10}$")) {
            txtPatientTel.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 5px;");
        } else {
            txtPatientTel.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
    }

}
