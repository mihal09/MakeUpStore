package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import sun.security.ssl.Debug;

import java.io.IOException;
import java.lang.reflect.Field;


public class MascaraController {

    @FXML
    Button buttonSubmit;

    public MascaraController(Parent root) {

        buttonSubmit = (Button) root.lookup("#buttonSubmit");

        buttonSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        //Stage stage = Main.stage;
        Stage stage = new Stage();
        stage.setTitle("Add concealer");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
