package controllers;

import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    public Label userNameLabel;
    public StackPane contentArea;
    public Label therapistCountLabel;
    public Label patientCountLabel;
    public Label programCountLabel;
    public Label sessionCountLabel;

    static UserDto userDto;
    public Button btnuser;
    public Button btntherapist;
    public Button btntherapyProgram;
    public Button btnpatient;
    public Button btnsession;
    public Button btnpayment;
    public Button btnreport;
    public Button btnlogout;
    public HBox role;

    public void initialize() throws IOException {
        userDto = LoginFormController.getUserDto();
        checkrole(userDto);
        setLabel();
    }
    public void setLabel(){
        if (userDto != null) {
            if (userDto.getRole().equals("admin")) {
                userNameLabel.setText("Admin User");
            } else if (userDto.getRole().equals("receptionist")) {
                userNameLabel.setText("Receptionist User");
            } else {
                userNameLabel.setText("Unknown Role");
            }
        }
    }
    public void handleLogout(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) btnlogout.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Page");
    }

    public void checkrole(UserDto userDto) throws IOException {
        System.out.println(userDto.getRole()+"checkrole");
        if (userDto.getRole().equals("admin")){
            btnpayment.setDisable(true);
            btnreport.setDisable(true);
            btnsession.setDisable(true);
        }else{
            btnuser.setDisable(true);
            btntherapyProgram.setDisable(true);
            btntherapist.setDisable(true);
        }
    }
    public void handleUserManagement(ActionEvent actionEvent) throws IOException {
        AnchorPane userManagementPage = FXMLLoader.load(getClass().getResource("/view/userManagement.fxml"));
        contentArea.getChildren().clear();
        contentArea.getChildren().add(userManagementPage);
    }


    public void handleTherapyProgramManagement(ActionEvent actionEvent) throws IOException {
        AnchorPane userManagementPage = FXMLLoader.load(getClass().getResource("/view/userManagement.fxml"));
        contentArea.getChildren().clear();
        contentArea.getChildren().add(userManagementPage);
    }

    public void handleTherapistManagement(ActionEvent actionEvent) throws IOException {
        AnchorPane userManagementPage = FXMLLoader.load(getClass().getResource("/view/userManagement.fxml"));
        contentArea.getChildren().clear();
        contentArea.getChildren().add(userManagementPage);
    }

    public void handlePatientManagement(ActionEvent actionEvent) throws IOException {
        AnchorPane userManagementPage = FXMLLoader.load(getClass().getResource("/view/PatientManagement.fxml"));
        contentArea.getChildren().clear();
        contentArea.getChildren().add(userManagementPage);
    }

    public void handleTherapySessionScheduling(ActionEvent actionEvent) throws IOException {
        AnchorPane userManagementPage = FXMLLoader.load(getClass().getResource("/view/userManagement.fxml"));
        contentArea.getChildren().clear();
        contentArea.getChildren().add(userManagementPage);
    }

    public void handlePaymentManagement(ActionEvent actionEvent) throws IOException {
        AnchorPane userManagementPage = FXMLLoader.load(getClass().getResource("/view/userManagement.fxml"));
        contentArea.getChildren().clear();
        contentArea.getChildren().add(userManagementPage);
    }

    public void handleReportingAnalytics(ActionEvent actionEvent) throws IOException {
        AnchorPane userManagementPage = FXMLLoader.load(getClass().getResource("/view/userManagement.fxml"));
        contentArea.getChildren().clear();
        contentArea.getChildren().add(userManagementPage);
    }
}
