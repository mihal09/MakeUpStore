package main.java.sample.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.sample.Database;

import java.sql.*;


public class ConcealerController {

    @FXML
    Button buttonSubmit;
    @FXML
    TextField fieldName, fieldPrice, fieldBrandID, fieldColorID, fieldFillWeight;
    @FXML
    ChoiceBox fieldUndertone, fieldCoverage;

    public ConcealerController(Parent root) {

        buttonSubmit = (Button) root.lookup("#buttonSubmit");
        fieldUndertone = (ChoiceBox) root.lookup("#fieldUndertone");
        fieldCoverage = (ChoiceBox) root.lookup("#fieldCoverage");

        fieldName = (TextField) root.lookup("#fieldName");
        fieldPrice = (TextField) root.lookup("#fieldPrice");
        fieldBrandID = (TextField) root.lookup("#fieldBrandID");
        fieldColorID = (TextField) root.lookup("#fieldColorID");
        fieldFillWeight = (TextField) root.lookup("#fieldFillWeight");


        buttonSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                add_concealer();
            }
        });

        //Stage stage = Main.stage;
        Stage stage = new Stage();
        stage.setTitle("Add concealer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void add_concealer(){
        try (
                Connection conn = Database.getInstance().getConnection()
        ) {
            String query = "{CALL add_concealer(?,?,?,?,?,?,?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(fieldColorID.getText()));
            stmt.setString(2, fieldUndertone.getValue().toString());
            stmt.setString(3, fieldCoverage.getValue().toString());
            stmt.setInt(4,Integer.parseInt(fieldFillWeight.getText()));
            stmt.setInt(5, Integer.parseInt(fieldBrandID.getText()));
            stmt.setString(6, fieldName.getText());
            stmt.setFloat(7, Float.parseFloat(fieldPrice.getText()));

            ResultSet rs = stmt.executeQuery();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Concealer added!");
            alert.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText("Error");
            alert.setContentText("Something went wrong, make sure all fields are valid!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
}
