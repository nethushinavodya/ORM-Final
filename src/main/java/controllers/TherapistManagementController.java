package controllers;

import bo.BOFactory;
import bo.custom.ProgramBO;
import bo.custom.TherapistBO;
import dto.ProgramDto;
import dto.TherapistDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    public TableView<TherapistDTO> tblTherapists;
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
        ObservableList<TherapistDTO> therapistDtos = therapistBO.getAllTherapists();
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

        TherapistDTO therapistDTO = new TherapistDTO(therapistId,name,specialization,contactInfo,programId);

        if (programId == null){
            therapistDTO = new TherapistDTO(therapistId,name,specialization,contactInfo,"Available");
            getAllTherapists();
        }else {
            therapistDTO = new TherapistDTO(therapistId,name,specialization,contactInfo,"Not Available");
            getAllTherapists();
        }
        therapistBO.addTherapist(therapistDTO, programId);
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

        TherapistDTO therapistDTO = new TherapistDTO(therapistId,name,specialization,contactInfo,programId);
        boolean isUpdated = therapistBO.updateTherapist(therapistDTO);

        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Updated");
            getAllTherapists();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Update Failed");
        }
    }

    public void searchTherapist(ActionEvent actionEvent) {
    }

    public void backOnAction(ActionEvent actionEvent) {
    }

    public void therapistTblClicked(MouseEvent mouseEvent) {
    }

}
