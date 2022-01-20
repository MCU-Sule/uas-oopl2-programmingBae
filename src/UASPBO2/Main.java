package UASPBO2;
/**
 * @author - AbednegoSteven 1972009
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle resources = ResourceBundle.getBundle("resourceBundle");
        Parent root = FXMLLoader.load(getClass().getResource("views/login-view.fxml"),resources);
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
