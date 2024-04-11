package com.example.cardealership;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.cardealership.utils.Utils.customAlert;

public class AddNewVehicle implements Initializable {

    @FXML
    private Button btn_main_menu;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_upload_vehicle_picture;

    @FXML
    private ComboBox<String> cb_condition;

    @FXML
    private ComboBox<String> cb_currency;

    @FXML
    private ImageView imageview_add_new_vehicle;

    @FXML
    private AnchorPane my_anchor_pane;

    @FXML
    private TextField tf_construction_year;

    @FXML
    private TextField tf_km_stood;

    @FXML
    private TextField tf_manufacturer;

    @FXML
    private TextField tf_number_of_pieces;

    @FXML
    private TextField tf_price;

    @FXML
    private TextField tf_vehicle_name;

    @FXML
    private Tooltip tooltip;

    public static String path;
    public static String vehicle_name, manufacturer, construction_year, km_stood, condition, pieces, price, currency;
    public static double km_stoodDouble, priceDouble;
    public static int pieceINT;
    List<String> lstFile;
    FileInputStream fileInputStream;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tooltip.setShowDelay(javafx.util.Duration.ZERO);

        lstFile = new ArrayList<>();
        lstFile.add("*.png");
        lstFile.add("*.jpg");

//        ----------- Combobox (Condition) --------------
        cb_condition.getItems().add("New");
        cb_condition.getItems().add("Used");
        cb_condition.setOnAction(actionEvent -> {
            condition = cb_condition.getValue();
            System.out.println("You selected " + condition);
        });

//        ---------- Combobox (Currency) -----------
        cb_currency.getItems().addAll("Dollar", "Euro");
        cb_currency.setOnAction(actionEvent -> {
            currency = cb_currency.getValue();
            System.out.println("You selected " + currency);
        });

//        --------- Upload vehicle picture --------
        btn_upload_vehicle_picture.setOnAction(actionEvent -> {
            FileChooser filechooser = new FileChooser();
            filechooser.setTitle("Upload Vehicle Picture");
            filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", lstFile)); //Optional; Chooses a type of file. lstFile declared above
            Stage stage = (Stage) my_anchor_pane.getScene().getWindow();

            File file = filechooser.showOpenDialog(stage);
            if (file != null) {
                path = file.getAbsolutePath();
                try {
                    Image image = new Image(file.toURI().toURL().toString());
                    imageview_add_new_vehicle.setImage(image);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

//        --------- Save button functionality --------
        btn_save.setOnAction(event -> {
            vehicle_name = tf_vehicle_name.getText();
            manufacturer = tf_manufacturer.getText();
            construction_year = tf_construction_year.getText();
            km_stood = tf_km_stood.getText();
            condition = cb_condition.getValue();
            pieces = tf_number_of_pieces.getText();
            price = tf_price.getText();
            currency = cb_currency.getValue();


            // Define an array to store all the text fields
            TextField[] textFields = {tf_vehicle_name, tf_manufacturer, tf_construction_year, tf_km_stood, tf_number_of_pieces, tf_price};
            ComboBox[] comboBoxes = {cb_condition, cb_currency};

            // Iterate over the text fields and check if any of them are empty after trimming
            boolean anyFieldEmpty = false;
            for (TextField textField : textFields) {
                if (textField.getText().trim().isEmpty()) {
                    anyFieldEmpty = true;
                    break; // Exit the loop if any empty field is found
                }
            }

            // Iterate over the combo boxes and check if any of them is selected or not
            for (ComboBox comboBox : comboBoxes) {
                if(comboBox.getValue() == null){
                    anyFieldEmpty = true;
                    break;
                }
            }

            // Check if any field is empty
            if (anyFieldEmpty) {
                customAlert(AlertType.ERROR, "Error","Missing fields","One or more fields are empty");
            } else {
                km_stoodDouble = Double.parseDouble(km_stood);
                priceDouble = Double.parseDouble(price);
                pieceINT = Integer.parseInt(pieces);

                if (path == null){
                    customAlert(AlertType.ERROR,"Picture Error","No picture selected","Upload a picture");
                    return;
                }

                try {
                    fileInputStream = new FileInputStream(path);
                    DBConnection.saveVehicleData(vehicle_name,manufacturer,construction_year,km_stoodDouble,condition,pieceINT,priceDouble,currency,fileInputStream);
                    customAlert(AlertType.CONFIRMATION, "Success","Saved","Data saved to database successfully");

                    FXMLLoader fxmlLoader = new FXMLLoader(VehicleTable.class.getResource("vehicle_table.fxml"));
                    try {
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) btn_save.getScene().getWindow();
                        stage.setScene(new Scene(root, 1200, 900));
                        stage.setTitle("Vehicles Detail!");
                        stage.setMaxHeight(900);
                        stage.setMaxWidth(1200);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

//        --------- Main menu button functionality --------
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


    }

}

