package com.example.cardealership;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private MenuBar mymenubar;
    @FXML
    private Menu menu_addnewvehicle;
    @FXML
    private MenuItem menuitem_addnewvehicle;
    @FXML
    private MenuItem menuitem_vehicle_table;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Application started successfully!");

        menuitem_addnewvehicle.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(AddNewVehicle.class.getResource("addnewvehicle.fxml"));
            try {
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) mymenubar.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Add new vehicle");
                stage.setMaxHeight(900);
                stage.setMaxWidth(1200);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        menuitem_vehicle_table.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(VehicleTable.class.getResource("vehicle_table.fxml"));
            try {
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) mymenubar.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Vehicle Table");
                stage.setMaxHeight(900);
                stage.setMaxWidth(1200);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

//    ============= METHODS ==================

}

