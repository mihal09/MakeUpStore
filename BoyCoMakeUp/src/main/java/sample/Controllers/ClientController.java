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
import jdk.internal.org.objectweb.asm.Type;
import main.java.sample.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;


public class ClientController {

    @FXML
    Button buttonSubmit, buttonCheckNIP;
    @FXML
    TextField fieldName, fieldNIP, fieldAddress;


    public ClientController(Parent root) {

        buttonSubmit = (Button) root.lookup("#buttonSubmit");
        buttonCheckNIP = (Button) root.lookup("#buttonCheckNIP");

        fieldName = (TextField) root.lookup("#fieldName");
        fieldNIP = (TextField) root.lookup("#fieldNIP");
        fieldAddress = (TextField) root.lookup("#fieldAddress");


        buttonSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                add_client();
            }
        });

        buttonCheckNIP.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                check_nip();
            }
        });


        //Stage stage = Main.stage;
        Stage stage = new Stage();
        stage.setTitle("Add client");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void check_nip(){
        try (
                Connection conn = Database.getInstance().getConnection()
        ) {
            CallableStatement cStmt = conn.prepareCall("{? = call is_nip_valid(?)}");
            cStmt.registerOutParameter(1, Type.BOOLEAN);
            cStmt.setString(2, fieldNIP.getText());
            cStmt.execute();
            Boolean isValid = cStmt.getBoolean(1);

            if(isValid) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("NIP is valid!");
                alert.showAndWait();
            }
            else throw new Exception("invalid nip");
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText("Error");
            alert.setContentText("Entered invalid NIP!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    private void add_client(){
        try (
                Connection conn = Database.getInstance().getConnection()
        ) {
            String query = "{CALL add_client(?,?,?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setString(1, fieldName.getText());
            stmt.setString(2, fieldNIP.getText());
            stmt.setString(3, fieldAddress.getText());

            ResultSet rs = stmt.executeQuery();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Client added!");
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
