package controllers;

import bo.BOFactory;
import bo.custom.ProgramBO;
import bo.custom.TherapistBO;
import dto.ProgramDto;
import dto.TherapistDto;
import javafx.collections.FXCollections;
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
import java.util.List;

public class TherapistManagementController {
    public Button backBtn;
    public TextField txtTherapistID;
    public TextField txtName;
    public TextField txtSpecialization;
    public TextField txtContactInfo;
    public Button btnAddTherapist;
    public Button btnUpdateTherapist;
    public Button btnDeleteTherapist;
    public Button btnSearchTherapist;
    public TableView<TherapistDto> tblTherapists;
    public TableColumn<?,?> colTherapistID;
    public TableColumn<?,?> colSpecialization;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colContactInfo;
    public TableColumn<?,?> colPrograms;
    public TableColumn<?,?> colStatus;
    public ComboBox<String> therapyProgramCmb;

    TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    ProgramBO programBO = (ProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.PROGRAM);

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValue();
        getAllTherapists();
        setProgramId();
        clear();
    }
    public void setCellValue(){
        colTherapistID.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colContactInfo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    public void clear(){
        txtTherapistID.clear();
        txtName.clear();
        txtSpecialization.clear();
        txtContactInfo.clear();
    }
    public void getAllTherapists() throws SQLException, ClassNotFoundException {
        ObservableList<TherapistDto> therapistDtos = therapistBO.getAllTherapists();
        tblTherapists.setItems(therapistDtos);
    }

    public void setProgramId() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<ProgramDto> programIds = programBO.getAllPrograms();
        for (ProgramDto programDto : programIds) {
            observableList.add(programDto.getProgramId());
        }
        therapyProgramCmb.setItems(observableList);
    }
    public void addTherapist(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String therapistId = txtTherapistID.getText();
        String name = txtName.getText();
        String specialization = txtSpecialization.getText();
        String contactInfo = txtContactInfo.getText();
        String programId = therapyProgramCmb.getValue();

        if (isValid()){
        TherapistDto therapistDTO = new TherapistDto(therapistId,name,specialization,contactInfo,programId);

        if (programId == null){
            therapistDTO = new TherapistDto(therapistId,name,specialization,contactInfo,"Available");
            getAllTherapists();
        }else {
            therapistDTO = new TherapistDto(therapistId,name,specialization,contactInfo,"Not Available");
            getAllTherapists();
        }
        therapistBO.addTherapist(therapistDTO, programId);
        getAllTherapists();
        }
    }

    public void deleteTherapist(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtTherapistID.getText();

        boolean isDeleted = therapistBO.deleteTherapist(id);

        if (isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Deleted");
            getAllTherapists();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Delete Failed");
        }
    }

    public void updateTherapist(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String therapistId = txtTherapistID.getText();
        String name = txtName.getText();
        String specialization = txtSpecialization.getText();
        String contactInfo = txtContactInfo.getText();
        String programId = therapyProgramCmb.getValue();

        TherapistDto therapistDTO = new TherapistDto(therapistId, name, specialization, contactInfo, programId);

        TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
        boolean isUpdated = therapistBO.updateTherapist(therapistDTO, programId);

        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Updated");
            getAllTherapists();
            clear();
            if (programId != null) {
                therapistDTO.setStatus("Not Available");
            } else {
                therapistDTO.setStatus("Available");
            }
            tblTherapists.refresh();
        } else {
            new Alert(Alert.AlertType.ERROR, "Update Failed");
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) txtTherapistID.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Page");
    }

    public void therapistTblClicked(MouseEvent mouseEvent) {
        TherapistDto therapist = tblTherapists.getSelectionModel().getSelectedItem();

        if (therapist != null) {
            txtTherapistID.setText(therapist.getTherapistId());
            txtName.setText(therapist.getName());
            txtSpecialization.setText(therapist.getSpecialization());
            txtContactInfo.setText(therapist.getContactNo());
        }
    }

    public void searchTherapist(ActionEvent actionEvent) {
    }

    public boolean isValid(){
        String contactInfo = txtContactInfo.getText();

        if (contactInfo.matches("^(?:7|0|(?:\\+94))[0-9]{9,10}$")) {
            return true;
        } else {
            return false;
        }
    }
    public void therapistContactOnKeyReleased(KeyEvent keyEvent) {
        if (txtContactInfo.getText().matches("^(?:7|0|(?:\\+94))[0-9]{9,10}$")) {
            txtContactInfo.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 5px;");
        } else {
            txtContactInfo.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
    }
}
