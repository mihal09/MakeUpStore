package main.java.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;


public class Main extends Application {

    public static Stage stage;
    @FXML
    Button buttonAddLipstick, buttonAddConcealer, buttonAddMascara;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Database.getInstance().setConnection("root", "haslo1");

        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/admin.fxml"));
        buttonAddLipstick = (Button) root.lookup("#buttonAddLipstick");
        buttonAddConcealer = (Button) root.lookup("#buttonAddConcealer");
        buttonAddMascara = (Button) root.lookup("#buttonAddMascara");

        buttonAddLipstick.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/LipstickPanel.fxml"));
                    new LipstickController(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonAddConcealer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/ConcealerPanel.fxml"));
                    new ConcealerController(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonAddMascara.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/MascaraPanel.fxml"));
                    new MascaraController(root);
                } catch (IOException e) {
                    e.printStackTrace();
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
