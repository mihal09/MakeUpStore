package main.java.sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;


public class LipstickController {

    @FXML
    Button buttonSubmit;
    @FXML
    TextField fieldName, fieldPrice, fieldBrandID, fieldColorID, fieldFillWeight;
    @FXML
    ChoiceBox fieldFormula, fieldFinish;

    public LipstickController(Parent root) {

        buttonSubmit = (Button) root.lookup("#buttonSubmit");
        fieldFormula = (ChoiceBox) root.lookup("#fieldFormula");
        fieldFinish = (ChoiceBox) root.lookup("#fieldFinish");

        fieldName = (TextField) root.lookup("#fieldName");
        fieldPrice = (TextField) root.lookup("#fieldPrice");
        fieldBrandID = (TextField) root.lookup("#fieldBrandID");
        fieldColorID = (TextField) root.lookup("#fieldColorID");
        fieldFillWeight = (TextField) root.lookup("#fieldFillWeight");


        buttonSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                System.out.println(fieldFormula.getValue());
                System.out.println(fieldFinish.getValue());

                add_lipstick();
            }
        });

        //Stage stage = Main.stage;
        Stage stage = new Stage();
        stage.setTitle("Add lipstick");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void add_lipstick(){
        try (
                Connection conn = Database.getInstance().getConnection()
        ) {
            String query = "{CALL add_lipstick(?,?,?,?,?,?,?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setInt(1, Integer.parseInt(fieldColorID.getText()));
            stmt.setString(2, fieldFormula.getValue().toString());
            stmt.setString(3, fieldFinish.getValue().toString());
            stmt.setInt(4,Integer.parseInt(fieldFillWeight.getText()));
            stmt.setInt(5, Integer.parseInt(fieldBrandID.getText()));
            stmt.setString(6, fieldName.getText());
            stmt.setFloat(7, Float.parseFloat(fieldPrice.getText()));

            ResultSet rs = stmt.executeQuery();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Lipstick added!");
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
