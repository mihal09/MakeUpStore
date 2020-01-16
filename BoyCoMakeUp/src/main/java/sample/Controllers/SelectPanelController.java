package main.java.sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdk.internal.org.objectweb.asm.Type;
import main.java.sample.Database;

import javax.xml.soap.Text;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class SelectPanelController {

    @FXML
    Button buttonShowUsers;
    @FXML
    TextField fieldRole;
    @FXML
    ListView listViewUsers;
    private ObservableList<String> items;


    public SelectPanelController(Parent root) {
        buttonShowUsers = (Button) root.lookup("#buttonShowUsers");
        fieldRole = (TextField) root.lookup("#fieldRole");
        listViewUsers = (ListView) root.lookup("#listViewUsers");

        buttonShowUsers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                show_users();
            }
        });


        //Stage stage = Main.stage;
        Stage stage = new Stage();
        stage.setTitle("Select users");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void show_users(){
        items = FXCollections.observableArrayList ();

        try (
                Connection conn = Database.getInstance().getConnection()
        ) {
            String query = "SELECT login FROM users WHERE type= ? ";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, fieldRole.getText());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                items.add(rs.getString(1));
            }
            listViewUsers.setItems(items);
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText("Error");
            alert.setContentText("Entered invalid NIP!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
}
