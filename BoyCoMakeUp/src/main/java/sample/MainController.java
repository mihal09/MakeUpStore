package main.java.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.java.sample.Controllers.*;
import main.java.sample.Database;
import main.java.sample.Main;

import java.io.IOException;


public class MainController{

    @FXML
    Button buttonAddLipstick, buttonAddConcealer, buttonAddMascara;
    @FXML
    Button buttonAddBrand, buttonAddClient, buttonShowUsers;
    @FXML
    Button buttonCancelUpdateDelivery, buttonCancelUpdateSale;
    @FXML
    Button buttonPlanDelivery, buttonPlanSale, buttonAdminPanel;


    private int permissionLevel;


    public MainController (int permissionLevel) throws IOException {
        this.permissionLevel = permissionLevel;
        Stage stage = Main.stage;

        Parent root = FXMLLoader.load(getClass().getResource("../../resources/MainPanel.fxml"));
        buttonAddLipstick = (Button) root.lookup("#buttonAddLipstick");
        buttonAddConcealer = (Button) root.lookup("#buttonAddConcealer");
        buttonAddMascara = (Button) root.lookup("#buttonAddMascara");

        buttonAddBrand = (Button) root.lookup("#buttonAddBrand");
        buttonAddClient = (Button) root.lookup("#buttonAddClient");

        buttonCancelUpdateDelivery = (Button) root.lookup("#buttonCancelUpdateDelivery");
        buttonCancelUpdateSale = (Button) root.lookup("#buttonCancelUpdateSale");

        buttonPlanDelivery = (Button) root.lookup("#buttonPlanDelivery");
        buttonPlanSale = (Button) root.lookup("#buttonPlanSale");

        buttonAdminPanel  = (Button) root.lookup("#buttonAdminPanel");
        buttonShowUsers = (Button) root.lookup("#buttonShowUsers");


        /*PERMISSION LEVEL:
        0 -> error
        1 -> employee
        2 -> boss
        3 -> admin
        */
        if(permissionLevel<3){ //hide admin only
            buttonAdminPanel.setVisible(false);
            //addUser
            //removeUser
            //changePermission
        }
        if(permissionLevel<2){ //hide boss only
            buttonAddLipstick.setVisible(false);
            buttonAddConcealer.setVisible(false);
            buttonAddMascara.setVisible(false);
            buttonAddBrand.setVisible(false);
        }
        if(permissionLevel==0){ //not authenticated
            return;
        }

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

        buttonAdminPanel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/AdminPanel.fxml"));
                    new AdminController(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonShowUsers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/SelectPanel.fxml"));
                    new SelectPanelController(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        Main.stage.setTitle("BoyCo MakeUp");
        Main.stage.setScene(new Scene(root));
        Main.stage.show();
    }
}
