package main.java.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import main.java.sample.Controllers.*;

import java.io.IOException;


public class Main extends Application {

    public static Stage stage;
    @FXML
    Button buttonAddLipstick, buttonAddConcealer, buttonAddMascara;
    @FXML
    Button buttonAddBrand, buttonAddClient;
    @FXML
    Button buttonCancelUpdateDelivery, buttonCancelUpdateSale;
    @FXML
    Button buttonPlanDelivery, buttonPlanSale;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Database.getInstance().setConnection("root", "haslo1");

        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/admin.fxml"));
        buttonAddLipstick = (Button) root.lookup("#buttonAddLipstick");
        buttonAddConcealer = (Button) root.lookup("#buttonAddConcealer");
        buttonAddMascara = (Button) root.lookup("#buttonAddMascara");

        buttonAddBrand = (Button) root.lookup("#buttonAddBrand");
        buttonAddClient = (Button) root.lookup("#buttonAddClient");

        buttonCancelUpdateDelivery = (Button) root.lookup("#buttonCancelUpdateDelivery");
        buttonCancelUpdateSale = (Button) root.lookup("#buttonCancelUpdateSale");

        buttonPlanDelivery = (Button) root.lookup("#buttonPlanDelivery");
        buttonPlanSale = (Button) root.lookup("#buttonPlanSale");

        buttonAddLipstick.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/LipstickPanel.fxml"));
                    new LipstickController(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonAddConcealer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/ConcealerPanel.fxml"));
                    new ConcealerController(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonAddMascara.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/MascaraPanel.fxml"));
                    new MascaraController(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonAddBrand.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/BrandPanel.fxml"));
                    new BrandController(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonAddClient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/ClientPanel.fxml"));
                    new ClientController(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonCancelUpdateDelivery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/CancelUpdateDeliveryPanel.fxml"));
                    new CancelUpdateDeliveryController(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonCancelUpdateSale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/CancelUpdateSalePanel.fxml"));
                    new CancelUpdateSaleController(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        buttonPlanDelivery.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/PlanDeliveryPanel.fxml"));
                    new PlanDeliveryController(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonPlanSale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/PlanSalePanel.fxml"));
                    new PlanSaleController(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        primaryStage.setTitle("BoyCo MakeUp");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
