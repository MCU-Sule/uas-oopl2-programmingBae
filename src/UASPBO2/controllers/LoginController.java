package UASPBO2.controllers;
/**
 * @author - AbednegoSteven 1972009
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    public TextField txtId;
    @FXML
    public PasswordField txtPassword;

    public void loginAction(ActionEvent actionEvent) throws IOException {
        if (txtId.getText().isEmpty() && txtPassword.getText().isEmpty()){
            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
            alertInformation.setContentText("Please fill in all the field");
            alertInformation.show();
        } else {
            if (txtId.getText().equals("1972009") && txtPassword.getText().equals("abed1234")){
                ResourceBundle resources = ResourceBundle.getBundle("resourceBundle");
                Stage newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/main-view.fxml"));
                loader.setResources(resources);
                Parent root = loader.load();
                MainController controller2 = loader.getController();
                controller2.setLoginController(this);
                Scene newScene = new Scene(root);
                newStage.setScene(newScene);
                newStage.initOwner(txtId.getScene().getWindow());
                newStage.initModality(Modality.WINDOW_MODAL);
                newStage.setTitle("Main Form");
                newStage.show();
            } else {
                Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
                alertInformation.setContentText("Id atau Password salah");
                alertInformation.show();
            }
        }

    }
}
