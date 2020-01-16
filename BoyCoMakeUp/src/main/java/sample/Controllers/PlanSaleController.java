package main.java.sample.Controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
import jdk.internal.org.objectweb.asm.Type;
import main.java.sample.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;


public class PlanSaleController {

    @FXML
    Button buttonAddProduct, buttonAcceptSale;
    @FXML
    TextField fieldProductID, fieldQuantity, fieldClientID;

    DatePicker fieldSaleDate;

    ArrayList<Pair<Integer,Integer>> productsList;

    public PlanSaleController(Parent root) {
        productsList = new ArrayList<>();
        buttonAddProduct = (Button) root.lookup("#buttonAddProduct");
        buttonAcceptSale = (Button) root.lookup("#buttonAcceptSale");

        fieldProductID = (TextField) root.lookup("#fieldProductID");
        fieldQuantity = (TextField) root.lookup("#fieldQuantity");
        fieldClientID = (TextField) root.lookup("#fieldClientID");
        fieldSaleDate = (DatePicker) root.lookup("#fieldSaleDate");

        buttonAddProduct.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                add_product();
            }
        });
        buttonAcceptSale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accept();
            }
        });


        //Stage stage = Main.stage;
        Stage stage = new Stage();
        stage.setTitle("Plan new sale");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void add_product() {
        int productId = Integer.parseInt(fieldProductID.getText());
        int quantity = Integer.parseInt(fieldQuantity.getText());
        productsList.add(new Pair<>(productId, quantity));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Added product with id " + productId + ", amount: "+quantity);
        alert.showAndWait();
    }

    private void accept() {
        String request = "";
        for (Pair<Integer,Integer> product : productsList) {
            request += product.getKey() + "," + product.getValue() + ";" ;
        }

        try (
                Connection conn = Database.getInstance().getConnection()
        ) {
            String query = "{CALL plan_sale(?,?,?,?,?)}";
            CallableStatement stmt = conn.prepareCall(query);

            Date date =  Date.valueOf(fieldSaleDate.getValue());
            stmt.registerOutParameter(1, Type.BOOLEAN);
            stmt.registerOutParameter(2, Type.INT);
            stmt.setInt(3, Integer.parseInt(fieldClientID.getText()));
            stmt.setDate(4, date);
            stmt.setString(5,request);
            ResultSet rs = stmt.executeQuery();
            Boolean isSuccess = stmt.getBoolean(1);
            int resultID = stmt.getInt(2);

            if(!isSuccess){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(null);
                alert.setHeaderText("Error");
                alert.setContentText("Not enough products!");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("New sale with id "+resultID+" added!");
                alert.showAndWait();
            }
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
