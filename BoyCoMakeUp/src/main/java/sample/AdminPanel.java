package main.java.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminPanel extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/LipstickPanel.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("BoyCo MakeUp");
        stage.show();
    }
}
