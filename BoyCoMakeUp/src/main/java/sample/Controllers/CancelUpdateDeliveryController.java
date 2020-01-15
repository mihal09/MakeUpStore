package main.java.sample.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.sample.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;


public class CancelUpdateDeliveryController {

    @FXML
    Button buttonUpdateDelivery, buttonCancelDelivery;
    @FXML
    TextField fieldDeliveryID;

    public CancelUpdateDeliveryController(Parent root) {

        buttonUpdateDelivery = (Button) root.lookup("#buttonUpdateDelivery");
        buttonCancelDelivery = (Button) root.lookup("#buttonCancelDelivery");

        fieldDeliveryID = (TextField) root.lookup("#fieldDeliveryID");

        buttonUpdateDelivery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                update_delivery();
            }
        });
        buttonCancelDelivery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cancel_delivery();
            }
        });


        //Stage stage = Main.stage;
        Stage stage = new Stage();
        stage.setTitle("Manage delivery");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void cancel_delivery() {
        try (
                Connection conn = Database.getInstance().getConnection()
        ) {
            String query = "{CALL cancel_delivery(?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(fieldDeliveryID.getText()));
            ResultSet rs = stmt.executeQuery();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Delivery canceled!");
            alert.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText("Error");
            alert.setContentText("Something went wrong, make sure id is valid!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    private void update_delivery() {
        try (
                Connection conn = Database.getInstance().getConnection()
        ) {
            String query = "{CALL update_delivery(?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(fieldDeliveryID.getText()));
            ResultSet rs = stmt.executeQuery();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Delivery updated!");
            alert.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText("Error");
            alert.setContentText("Something went wrong, make sure id is valid and delivery wasn't already confirmed!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
}
