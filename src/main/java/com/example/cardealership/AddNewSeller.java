package com.example.cardealership;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

public class AddNewSeller implements Initializable {


    @FXML
    private Button btn_main_menu;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_upload_seller_picture;

    @FXML
    private ImageView imageview_add_new_vehicle;

    @FXML
    private TextField tf_seller_address;

    @FXML
    private TextField tf_seller_email;

    @FXML
    private TextField tf_seller_name;

    @FXML
    private TextField tf_seller_phone_number;

    @FXML
    private AnchorPane my_anchor_pane;

    List<String> lstFile;
    public static String path;

    public static String seller_name, seller_phone_number, seller_email, seller_address;
    FileInputStream fileInputStream;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lstFile = new ArrayList<>();
        lstFile.add("*.png");
        lstFile.add("*.jpg");

//        --------- Upload Seller picture --------
        btn_upload_seller_picture.setOnAction(actionEvent -> {
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


//        --------- Save button functionality --------
        btn_save.setOnAction(event -> {
            seller_name = tf_seller_name.getText();
            seller_phone_number = tf_seller_phone_number.getText();
            seller_email = tf_seller_email.getText();
            seller_address = tf_seller_address.getText();


            // Define an array to store all the text fields
            TextField[] textFields = {tf_seller_name, tf_seller_phone_number, tf_seller_email, tf_seller_address};

            // Iterate over the text fields and check if any of them are empty after trimming
            boolean anyFieldEmpty = false;
            for (TextField textField : textFields) {
                if (textField.getText().trim().isEmpty()) {
                    anyFieldEmpty = true;
                    break; // Exit the loop if any empty field is found
                }
            }

            // Check if any field is empty
            if (anyFieldEmpty) {
                customAlert(Alert.AlertType.ERROR, "Error","Missing fields","One or more fields are empty");
            } else {
                if (path == null){
                    customAlert(Alert.AlertType.ERROR,"Picture Error","No picture selected","Upload a picture");
                    return;
                }
                try {
                    fileInputStream = new FileInputStream(path);
                    DBConnection.saveSellerData(seller_name, seller_phone_number, seller_email, seller_address, fileInputStream);
                    customAlert(Alert.AlertType.CONFIRMATION, "Success","Saved","Data saved to database successfully");

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

    }
}
