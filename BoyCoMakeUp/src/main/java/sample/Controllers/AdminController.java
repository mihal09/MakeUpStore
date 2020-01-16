package main.java.sample.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.sample.Database;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;


public class AdminController {

    @FXML
    Button buttonAddUser, buttonRemoveUser, buttonChangePermission, buttonSaveBackup, buttonLoadBackup, buttonShowUsers;
    @FXML
    TextField fieldLogin, fieldPassword, fieldUserIDRemoveUser, fieldUserIDChangePermission;
    @FXML
    ChoiceBox fieldRole, fieldChangePermission;


    public AdminController(Parent root) {

        buttonAddUser = (Button) root.lookup("#buttonAddUser");
        buttonRemoveUser = (Button) root.lookup("#buttonRemoveUser");
        buttonChangePermission = (Button) root.lookup("#buttonChangePermission");
        buttonSaveBackup = (Button) root.lookup("#buttonSaveBackup");
        buttonLoadBackup = (Button) root.lookup("#buttonLoadBackup");

        fieldLogin = (TextField) root.lookup("#fieldLogin");
        fieldPassword = (TextField) root.lookup("#fieldPassword");
        fieldUserIDRemoveUser = (TextField) root.lookup("#fieldUserIDRemoveUser");
        fieldUserIDChangePermission = (TextField) root.lookup("#fieldUserIDChangePermission");



        fieldRole = (ChoiceBox) root.lookup("#fieldRole");

        buttonAddUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                add_user();
            }
        });
        buttonSaveBackup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                save_backup();
            }
        });
        buttonLoadBackup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                load_backup();
            }
        });
        buttonRemoveUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                remove_user();
            }
        });

        //Stage stage = Main.stage;
        Stage stage = new Stage();
        stage.setTitle("Manage delivery");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void save_backup() {
        Database.getInstance().save_backup();
    }

    private void load_backup() {
        Database.getInstance().load_backup();
    }

    private void add_user() {
        try (
                Connection conn = Database.getInstance().getConnection()
        ) {
            String login = fieldLogin.getText();
            String password = fieldPassword.getText();
            String role = fieldRole.getValue().toString();

            String query = "{CALL add_user(?,?,?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.setString(3, role);
            ResultSet rs = stmt.executeQuery();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("User added!");
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

    private void remove_user() {
        try (
                Connection conn = Database.getInstance().getConnection()
        ) {
            int userID = Integer.parseInt(fieldUserIDRemoveUser.getText());

            String query = "{CALL remove_user(?)}";
            CallableStatement stmt = conn.prepareCall(query);
            stmt.setInt(1, userID);

            ResultSet rs = stmt.executeQuery();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("User removed!");
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
}
