package com.example.cardealership;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.cardealership.VehicleTable.*;
import static com.example.cardealership.utils.Utils.customAlert;
import static com.example.cardealership.utils.Utils.customConfirmationAlert;

public class SelectedVehicle implements Initializable {

    @FXML
    private Button btn_delete;

    @FXML
    private AnchorPane my_anchor_pane;

    @FXML
    private Button btn_save_picture;

    @FXML
    private Button btn_table_vehicle;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_upload_picture;

    @FXML
    private ImageView imageview_vehicle_picture;

    @FXML
    private TextField tf_availability;

    @FXML
    private TextField tf_condition;

    @FXML
    private TextField tf_construction_year;

    @FXML
    private TextField tf_currency;

    @FXML
    private TextField tf_km_stood;

    @FXML
    private TextField tf_manufacturer;

    @FXML
    private TextField tf_number_of_pieces;

    @FXML
    private TextField tf_price;

    @FXML
    private TextField tf_vehicle_id;

    @FXML
    private TextField tf_vehicle_name;

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

    public static String path;
    List<String> lstFile;
    FileInputStream fileInputStream;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lstFile = new ArrayList<>();
        lstFile.add("*.png");
        lstFile.add("*.jpg");


        id = VehicleTable.id;
        vehicle_name = VehicleTable.vehicle_name;
        manufacturer = VehicleTable.manufacturer;
        construction_year = VehicleTable.construction_year;
        km_stood = VehicleTable.km_stood;
        vehicle_condition = VehicleTable.vehicle_condition;
        pieces = VehicleTable.pieces;
        price = VehicleTable.price;
        currency = VehicleTable.currency;
        availability = VehicleTable.availability;
        picture = VehicleTable.picture;

        System.out.println("id: " + VehicleTable.id);
        System.out.println("vehicle_name: " + VehicleTable.vehicle_name);
        System.out.println("manufacturer: " + VehicleTable.manufacturer);
        System.out.println("construction_year: " + VehicleTable.construction_year);
        System.out.println("km_stood: " + VehicleTable.km_stood);
        System.out.println("vehicle_condition: " + VehicleTable.vehicle_condition);
        System.out.println("pieces: " + VehicleTable.pieces);
        System.out.println("price: " + VehicleTable.price);
        System.out.println("currency: " + VehicleTable.currency);
        System.out.println("availability: " + VehicleTable.availability);
//        System.out.println("picture: " + VehicleTable.picture);

        tf_vehicle_id.setText(String.valueOf(id));
        tf_vehicle_name.setText(vehicle_name);
        tf_manufacturer.setText(manufacturer);
        tf_construction_year.setText(construction_year);
        tf_km_stood.setText(String.valueOf(km_stood));
        tf_km_stood.setText(String.valueOf(km_stood));
        tf_condition.setText(vehicle_condition);
        tf_number_of_pieces.setText(String.valueOf(pieces));
        tf_price.setText(String.valueOf(price));
        tf_currency.setText(currency);
        tf_availability.setText(availability);

        try {
            InputStream inputStream = picture.getBinaryStream();
            Image image = new Image(inputStream);
            imageview_vehicle_picture.setImage(image);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        btn_table_vehicle.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(VehicleTable.class.getResource("vehicle_table.fxml"));
            try {
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) btn_table_vehicle.getScene().getWindow();
                stage.setScene(new Scene(root, 1200, 900));
                stage.setTitle("Welcome!");
                stage.setMaxHeight(900);
                stage.setMaxWidth(1200);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        btn_upload_picture.setOnAction(event -> {
            FileChooser filechooser = new FileChooser();
            filechooser.setTitle("Upload Vehicle Picture");
            filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", lstFile)); //Optional; Chooses a type of file. lstFile declared above
            Stage stage = (Stage) my_anchor_pane.getScene().getWindow();

            File file = filechooser.showOpenDialog(stage);
            if (file != null) {
                path = file.getAbsolutePath();
                try {
                    Image image = new Image(file.toURI().toURL().toString());
                    imageview_vehicle_picture.setImage(image);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

        });

        btn_save_picture.setOnAction(event -> {
            if (path == null){
                customAlert(Alert.AlertType.ERROR,"Picture Error","No picture selected","Upload a picture");
                return;
            }
            try {
                fileInputStream = new FileInputStream(path);
                boolean inserted = DBConnection.updateVehiclePicture(id, fileInputStream);
                if(inserted){
                    customAlert(Alert.AlertType.INFORMATION,"Picture Saved","The picture has been saved","Close");
                }else{
                    customAlert(Alert.AlertType.ERROR,"Picture Error","The picture could not be saved","Close");
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        });

        btn_update.setOnAction(event -> {
        });

        btn_delete.setOnAction(event -> {
            customConfirmationAlert(Alert.AlertType.CONFIRMATION,"Delete Vehicle","Are you sure you want to delete this vehicle?","Yes",() -> {
                boolean deleted = DBConnection.deleteVehicleData(id);
                if(deleted){
                    customAlert(Alert.AlertType.INFORMATION,"Vehicle Deleted","The vehicle has been deleted","Close");

                    FXMLLoader fxmlLoader = new FXMLLoader(VehicleTable.class.getResource("vehicle_table.fxml"));
                    try {
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) btn_delete.getScene().getWindow();
                        stage.setScene(new Scene(root, 1200, 900));
                        stage.setTitle("Welcome!");
                        stage.setMaxHeight(900);
                        stage.setMaxWidth(1200);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    customAlert(Alert.AlertType.ERROR,"Vehicle Error","The vehicle could not be deleted","Close");
                }
            });
        });





    }
}
