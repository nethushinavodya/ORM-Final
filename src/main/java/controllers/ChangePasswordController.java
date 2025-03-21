package controllers;

import bo.BOFactory;
import bo.custom.UserBO;
import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

public class ChangePasswordController {
    public TextField txtUsername;
    public PasswordField txtNewPassword;
    public PasswordField txtConfirmPassword;
    public Button btnSubmit;
    public Button btnClear;
    public Button backbit;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    public void submitPasswordChange(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String username = txtUsername.getText();
        if (!username.isEmpty()) {
            UserDto userDto = userBO.getData(username);
            if (userDto != null) {
                if (txtNewPassword.getText().equals(txtConfirmPassword.getText())) {
                    String newPassword = txtNewPassword.getText();
                    String encryptedPassword = null;
                    try {
                        encryptedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                    } catch (Exception e) {
                        e.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, "Error while encrypting password").show();
                        return;
                    }
                    userDto.setPassword(encryptedPassword);
                    boolean isUpdated = userBO.updateUser(userDto);
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Password Changed").show();
                        clearFields(actionEvent);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Password Not Changed").show();
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
                    txtNewPassword.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "User Not Found").show();
                txtUsername.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            }

        }
    }

    public void clearFields(ActionEvent actionEvent) {
        txtNewPassword.clear();
        txtConfirmPassword.clear();
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        /*AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) txtNewPassword.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Registration Page");
*/
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
