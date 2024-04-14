package com.example.cardealership;

import com.example.cardealership.model.VehicleTableViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class VehicleTable implements Initializable {



    @FXML
    private Button btn_main_menu;

    @FXML
    private TableColumn<VehicleTableViewModel, String> tablecolumn_availability;

    @FXML
    private TableColumn<VehicleTableViewModel, String> tablecolumn_condition;

    @FXML
    private TableColumn<VehicleTableViewModel, String> tablecolumn_construction_year;

    @FXML
    private TableColumn<VehicleTableViewModel, String> tablecolumn_currency;

    @FXML
    private TableColumn<VehicleTableViewModel, String> tablecolumn_id;

    @FXML
    private TableColumn<VehicleTableViewModel, String> tablecolumn_km_stood;

    @FXML
    private TableColumn<VehicleTableViewModel, String> tablecolumn_manufacturer;

    @FXML
    private TableColumn<VehicleTableViewModel, String> tablecolumn_pieces;

    @FXML
    private TableColumn<VehicleTableViewModel, String> tablecolumn_price;

    @FXML
    private TableColumn<VehicleTableViewModel, String> tablecolumn_vehicle_name;

    @FXML
    private TableView<VehicleTableViewModel> tableview_vehicle;
    ResultSet resultSet;

    public static int id;
    public static String vehicle_name;
    public static String manufacturer;
    public static String construction_year;
    public static double km_stood;
    public static String vehicle_condition;
    public static int pieces;
    public static double price;
    public static String currency;
    public static String availability;
    public static Blob picture;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultSet = DBConnection.retrieveVehicleData();
        try{
            while (resultSet.next()) {
                id = resultSet.getInt("vehicle_id");
                vehicle_name = resultSet.getString("vehicle_name");
                manufacturer = resultSet.getString("manufacturer");
                construction_year = resultSet.getString("construction_year");
                km_stood = resultSet.getDouble("km_stood");
                vehicle_condition = resultSet.getString("vehicle_condition");
                pieces = resultSet.getInt("pieces");
                price = resultSet.getDouble("price");
                currency = resultSet.getString("currency");
                availability = resultSet.getString("availability");
                picture = resultSet.getBlob("picture");

                String id2 = String.valueOf(id);
                String km_stood2 = String.valueOf(km_stood);
                String pieces2 = String.valueOf(pieces);
                String price2 = String.valueOf(price);

                VehicleTableViewModel vehicleTableViewModel = new VehicleTableViewModel(id2, vehicle_name, manufacturer, construction_year, km_stood2, vehicle_condition, pieces2, price2, currency, availability);
                tableview_vehicle.getItems().add(vehicleTableViewModel);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        tablecolumn_id.setCellValueFactory(new PropertyValueFactory<VehicleTableViewModel, String>("id"));
        tablecolumn_vehicle_name.setCellValueFactory(new PropertyValueFactory<VehicleTableViewModel, String>("vehicle_name"));
        tablecolumn_manufacturer.setCellValueFactory(new PropertyValueFactory<VehicleTableViewModel, String>("manufacturer"));
        tablecolumn_construction_year.setCellValueFactory(new PropertyValueFactory<VehicleTableViewModel, String>("construction_year"));
        tablecolumn_km_stood.setCellValueFactory(new PropertyValueFactory<VehicleTableViewModel, String>("km_stood"));
        tablecolumn_condition.setCellValueFactory(new PropertyValueFactory<VehicleTableViewModel, String>("vehicle_condition"));
        tablecolumn_pieces.setCellValueFactory(new PropertyValueFactory<VehicleTableViewModel, String>("pieces"));
        tablecolumn_price.setCellValueFactory(new PropertyValueFactory<VehicleTableViewModel, String>("price"));
        tablecolumn_currency.setCellValueFactory(new PropertyValueFactory<VehicleTableViewModel, String>("currency"));
        tablecolumn_availability.setCellValueFactory(new PropertyValueFactory<VehicleTableViewModel, String>("availability"));

        btn_main_menu.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("hello-view.fxml"));
            try {
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) btn_main_menu.getScene().getWindow();
                stage.setScene(new Scene(root, 1200, 900));
                stage.setTitle("Welcome!");
                stage.setMaxHeight(900);
                stage.setMaxWidth(1200);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        tableview_vehicle.setOnMouseClicked(event -> {
            try {
                int selectedIndex = tableview_vehicle.getSelectionModel().getSelectedIndex();
                String vehicle_id = tableview_vehicle.getSelectionModel().getSelectedItem().getId();
                resultSet = DBConnection.retrieveSingleVehicleData(Integer.parseInt(vehicle_id));
                while (resultSet.next()){
                    id = resultSet.getInt("vehicle_id");
                    vehicle_name = resultSet.getString("vehicle_name");
                    manufacturer = resultSet.getString("manufacturer");
                    construction_year = resultSet.getString("construction_year");
                    km_stood = resultSet.getDouble("km_stood");
                    vehicle_condition = resultSet.getString("vehicle_condition");
                    pieces = resultSet.getInt("pieces");
                    price = resultSet.getDouble("price");
                    currency = resultSet.getString("currency");
                    availability = resultSet.getString("availability");
                    picture = resultSet.getBlob("picture");

//                    String id2 = String.valueOf(id);
//                    String km_stood2 = String.valueOf(km_stood);
//                    String pieces2 = String.valueOf(pieces);
//                    String price2 = String.valueOf(price);

                    System.out.println("vehicle_name: " + vehicle_name);

                    FXMLLoader fxmlLoader = new FXMLLoader(SelectedVehicle.class.getResource("selected_vehicle.fxml"));
                    try {
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) tableview_vehicle.getScene().getWindow();
                        stage.setScene(new Scene(root, 1200, 900));
//                        stage.setTitle("Vehicle detail!");
                        stage.setMaxHeight(900);
                        stage.setMaxWidth(1200);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }catch (Exception e) {
                System.out.println("You did not click on an item row, rather, the table itself");
            }
        });



    }
}
