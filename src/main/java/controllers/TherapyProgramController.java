package controllers;

import bo.BOFactory;
import bo.custom.ProgramBO;
import dto.ProgramDto;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;

public class TherapyProgramController {
    public Button backBtn;
    public TextField txtProgramID;
    public TextField txtProgramName;
    public TextField txtProgramDuration;
    public TextField txtProgramFee;
    public Button btnAddProgram;
    public Button btnUpdateProgram;
    public Button btnDeleteProgram;
    public TableView<ProgramDto> tblPrograms;
    public TableColumn<?,?> colProgramID;
    public TableColumn<?,?> colProgramName;
    public TableColumn<?,?> colDuration;
    public TableColumn<?,?> colFee;

    ProgramBO programBO = (ProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.PROGRAM);

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        getAllProgram();
    }

    private void setCellValueFactory() {
        colProgramID.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    public void getAllProgram() throws SQLException, ClassNotFoundException {
        ObservableList<ProgramDto> programDtos = programBO.getAllPrograms();
        tblPrograms.setItems(programDtos);
    }
    public void addProgram(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String programId = txtProgramID.getText();
        String name = txtProgramName.getText();
        String duration = txtProgramDuration.getText();
        String fee = txtProgramFee.getText();

        ProgramDto programDto = new ProgramDto(programId,name,duration,fee);
        boolean isSaved = programBO.addProgram(programDto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Program Saved");
                getAllProgram();
            }else {
                new Alert(Alert.AlertType.ERROR,"Program Not Saved");
            }
    }

    public void updateProgram(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String programId = txtProgramID.getText();
        String name = txtProgramName.getText();
        String duration = txtProgramDuration.getText();
        String fee = txtProgramFee.getText();

        ProgramDto programDto = new ProgramDto(programId,name,duration,fee);
        boolean isUpdate = programBO.updateProgram(programDto);
        if (isUpdate){
            new Alert(Alert.AlertType.INFORMATION,"Update successful");
            getAllProgram();
        }else {
            new Alert(Alert.AlertType.ERROR,"Update failed");
        }
    }

    public void deleteProgram(ActionEvent actionEvent) {
    }

    public void backOnAction(ActionEvent actionEvent) {
    }

    public void programIdOnKeyReleased(KeyEvent keyEvent) {
    }

    public void programNameOnKeyReleased(KeyEvent keyEvent) {
    }

    public void programDurationOnKeyReleased(KeyEvent keyEvent) {
    }

    public void programFeeOnKeyReleased(KeyEvent keyEvent) {
    }

}
