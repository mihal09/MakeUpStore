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
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/makeup", "root", "haslo1");
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
//            Statement stmt = conn.createStatement();
//            String testquery = "Select * from colors";
//            ResultSet res = stmt.executeQuery(testquery);
//            int rowCount = 0;
//            while(res.next()){
//                String name  = res.getString("name");
//                String id  = res.getString("color_id");
//                ++rowCount;
//                System.out.println(id + " : " + name);
//            }
    } catch(SQLException ex) {ex.printStackTrace();}
    }
}
