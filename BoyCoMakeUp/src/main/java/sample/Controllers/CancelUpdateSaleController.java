package main.java.sample.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.sample.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;


public class CancelUpdateSaleController {

    @FXML
    Button buttonUpdateSale, buttonCancelSale;
    @FXML
    TextField fieldSaleID;

    public CancelUpdateSaleController(Parent root) {

        buttonUpdateSale = (Button) root.lookup("#buttonUpdateSale");
        buttonCancelSale = (Button) root.lookup("#buttonCancelSale");

        fieldSaleID = (TextField) root.lookup("#fieldSaleID");

        buttonUpdateSale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                update_sale();
            }
        });
        buttonCancelSale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cancel_sale();
            }
        });


        //Stage stage = Main.stage;
        Stage stage = new Stage();
        stage.setTitle("Manage sale");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void cancel_sale() {
        try (
                Connection conn = Database.getInstance().getConnection()
        ) {
            String query = "{CALL cancel_sale(?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(fieldSaleID.getText()));
            ResultSet rs = stmt.executeQuery();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Sale canceled!");
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

    private void update_sale() {
        try (
                Connection conn = Database.getInstance().getConnection()
        ) {
            String query = "{CALL update_sale(?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(fieldSaleID.getText()));
            ResultSet rs = stmt.executeQuery();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Sale updated!");
            alert.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText("Error");
            alert.setContentText("Something went wrong, make sure id is valid and sale wasn't already confirmed!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
}
