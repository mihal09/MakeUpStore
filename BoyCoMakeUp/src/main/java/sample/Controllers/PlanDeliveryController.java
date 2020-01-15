package main.java.sample.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.java.sample.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;


public class PlanDeliveryController {

    @FXML
    Button buttonAddProduct, buttonAcceptDelivery;
    @FXML
    TextField fieldProductID, fieldQuantity;

    DatePicker fieldDeliveryDate;

    ArrayList<Pair<Integer,Integer>> productsList;

    public PlanDeliveryController(Parent root) {
        productsList = new ArrayList<>();
        buttonAddProduct = (Button) root.lookup("#buttonAddProduct");
        buttonAcceptDelivery = (Button) root.lookup("#buttonAcceptDelivery");

        fieldProductID = (TextField) root.lookup("#fieldProductID");
        fieldQuantity = (TextField) root.lookup("#fieldQuantity");
        fieldDeliveryDate = (DatePicker) root.lookup("#fieldDeliveryDate");

        buttonAddProduct.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                add_product();
            }
        });
        buttonAcceptDelivery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accept();
            }
        });


        //Stage stage = Main.stage;
        Stage stage = new Stage();
        stage.setTitle("Plan new delivery");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void add_product() {
        int productId = Integer.parseInt(fieldProductID.getText());
        int quantity = Integer.parseInt(fieldQuantity.getText());
        productsList.add(new Pair<>(productId, quantity));
    }

    private void accept() {
        String request = "";
        for (Pair<Integer,Integer> product : productsList) {
            request += product.getKey() + "," + product.getValue() + ";" ;
        }

        try (
                Connection conn = Database.getInstance().getConnection()
        ) {
            String query = "{CALL plan_delivery(?,?)}";
            CallableStatement stmt = conn.prepareCall(query);

            Date date =  Date.valueOf(fieldDeliveryDate.getValue());
            stmt.setDate(1, date);
            stmt.setString(2,request);
            ResultSet rs = stmt.executeQuery();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("New delivery added!");
            alert.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText("Error");
            alert.setContentText("Something went wrong, make sure all fields are valid!");
            alert.showAndWait();
            e.printStackTrace();
        }

        productsList = new ArrayList<>();
    }

}
