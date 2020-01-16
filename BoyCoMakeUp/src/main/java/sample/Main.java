package main.java.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;


public class Main extends Application {

    public static Stage stage;
    @FXML
    Button buttonLogin;
    @FXML
    TextField fieldLogin, fieldPassword;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/LoginPanel.fxml"));
        fieldLogin = (TextField) root.lookup("#fieldLogin");
        fieldPassword = (TextField) root.lookup("#fieldPassword");
        buttonLogin = (Button) root.lookup("#buttonLogin");

        buttonLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int result = Database.getInstance().setConnection(fieldLogin.getText(), fieldPassword.getText());
                if(result != 0) {
                    try {
                        new MainController(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(null);
                    alert.setHeaderText("Error");
                    alert.setContentText("Invalid password!");
                    alert.showAndWait();
                }
            }
        });

        primaryStage.setTitle("BoyCo MakeUp");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
