package controllers;

import bo.BOFactory;
import bo.custom.UserBO;
import dto.PatientDto;
import dto.UserDto;
import javafx.beans.Observable;
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
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserManagementController {
    public TextField txtUserID;
    public TextField txtUsername;
    public TextField txtEmail;
    public ComboBox<String> roleCmb;
    public TextField passwordtxt;
    public Button btnAddUser;
    public Button btnUpdateUser;
    public Button btnDeleteUser;
    public Button btnSearchUser;
    public TableView<UserDto> tblUsers;
    public TableColumn<?,?> colUserID;
    public TableColumn<?,?> colUsername;
    public TableColumn<?,?> colEmail;
    public TableColumn<?,?> colRole;
    public Button backbit;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public void initialize() throws SQLException, ClassNotFoundException {
        roleCmb.getItems().addAll("admin", "receptionist");
        setCellValue();
        loadAllData();
        clear();
    }
    public void clear() {
        txtUserID.clear();
        txtUsername.clear();
        txtEmail.clear();
        passwordtxt.clear();
        roleCmb.setValue(null);
    }
    public void setCellValue() {
        colUserID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }
    public void loadAllData() throws SQLException, ClassNotFoundException {
        ObservableList<UserDto> oblist = FXCollections.observableArrayList();

        List<UserDto> dtos = userBO.getAllUsers();

        for (UserDto dto : dtos) {
            oblist.add(dto);
        }
        tblUsers.setItems(oblist);
    }

    public void addUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtUserID.getText();
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = passwordtxt.getText();
        String role = roleCmb.getValue();

        if (!isValid()) {
            new Alert(Alert.AlertType.ERROR, "Please input valid data").show();
        } else {
            String bcryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            UserDto userDto = new UserDto(username, email, bcryptedPassword, role);
            boolean isSaved = userBO.addUser(userDto);
            if (isSaved) {
                loadAllData();
                new Alert(Alert.AlertType.CONFIRMATION, "User Saved").show();
                clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "User Not Saved").show();
            }
        }
    }

    public void updateUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int id = Integer.parseInt(txtUserID.getText());
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = passwordtxt.getText();
        String role = roleCmb.getValue();

        if (id == 0 || username.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Please fill all the fields");
        }else {
            UserDto userDto = new UserDto(id , username , email, password, role);
            boolean isUpdate = userBO.updateUser(userDto);
            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Update successful");
                loadAllData();
                clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"Update failed");
            }
        }
    }

    public void deleteUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtUserID.getText();
        if (id.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please fill all the fields");
        }
        boolean isDelete = userBO.deleteUser(id);
        if (isDelete){
            new Alert(Alert.AlertType.INFORMATION,"Delete successful");
            loadAllData();
            clear();
        }else {
            new Alert(Alert.AlertType.ERROR,"Delete failed");
        }
    }

    public void userTblClicked(MouseEvent mouseEvent) {
        UserDto userDto = tblUsers.getSelectionModel().getSelectedItem();

        if (userDto != null){
            txtUserID.setText(String.valueOf(userDto.getId()));
            txtUsername.setText(userDto.getUsername());
            txtEmail.setText(userDto.getEmail());
            passwordtxt.setText(userDto.getPassword());
            roleCmb.setValue(userDto.getRole());
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) txtUserID.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Page");
    }

    public void passwordChangeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/passwordChange.fxml"))));
        stage.setTitle("Change Password Page");
        stage.show();
    }


    public void searchUser(ActionEvent actionEvent) {
        String id = txtUserID.getText();

        UserDto userDto = userBO.searchUser(id);
        if (userDto != null){
            txtUsername.setText(userDto.getUsername());
            txtEmail.setText(userDto.getEmail());
            passwordtxt.setText(userDto.getPassword());
            roleCmb.setValue(userDto.getRole());
        }else {
            new Alert(Alert.AlertType.ERROR,"Patient not found");
        }
    }
    public boolean isValid(){
        String id = txtUserID.getText();
        String username = txtUsername.getText();
        String email = txtEmail.getText();
        String password = passwordtxt.getText();
        String role = (String) roleCmb.getValue();

        if (
                username.matches("[a-zA-Z0-9]{4,}") &&
                        email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$") &&
                        password.matches("[a-zA-Z0-9]{6,}")
        ) {
            return true;
        } else {
            return false;
        }

    }

    public void usernameOnKeyReleased(KeyEvent keyEvent) {
        if (txtUsername.getText().matches("[a-zA-Z0-9]{4,}")) {
            txtUsername.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }else {
            txtUsername.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
    }

    public void emailOnKeyReleased(KeyEvent keyEvent) {
        if (txtEmail.getText().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
            txtEmail.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }else {
            txtEmail.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
    }

    public void passwordOnKeyReleased(KeyEvent keyEvent) {
        if (passwordtxt.getText().matches("[a-zA-Z0-9]{6,}")) {
            passwordtxt.setStyle("-fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }else {
            passwordtxt.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
        }
    }
}
