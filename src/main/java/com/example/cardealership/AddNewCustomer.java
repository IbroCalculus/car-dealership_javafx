package com.example.cardealership;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.cardealership.utils.Utils.customAlert;

public class AddNewCustomer implements Initializable {

    @FXML
    private Button btn_main_menu;

    @FXML
    private Button btn_save;

    @FXML
    private ImageView imageview_add_new_vehicle;

    @FXML
    private AnchorPane my_anchor_pane;

    @FXML
    private TextField tf_customer_address;

    @FXML
    private TextField tf_customer_email;

    @FXML
    private TextField tf_customer_name;

    @FXML
    private TextField tf_customer_phone_number;

    public static String customer_name;
    public static String customer_phone_number;
    public static String customer_email;
    public static String customer_address;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
            customer_name = tf_customer_name.getText();
            customer_phone_number = tf_customer_phone_number.getText();
            customer_email = tf_customer_email.getText();
            customer_address = tf_customer_address.getText();


            // Define an array to store all the text fields
            TextField[] textFields = {tf_customer_name, tf_customer_phone_number, tf_customer_email, tf_customer_address};

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
                return;
            } else {
                DBConnection.saveCustomerData(customer_name, customer_phone_number, customer_email, customer_address);
                customAlert(Alert.AlertType.CONFIRMATION, "Success", "Saved", "Data saved to database successfully");

                FXMLLoader fxmlLoader = new FXMLLoader(CustomerTable.class.getResource("customer_table.fxml"));
                try {
                    Parent root = fxmlLoader.load();
                    Stage stage = (Stage) btn_save.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Seller Table");
                    stage.setMaxHeight(900);
                    stage.setMaxWidth(1200);
                    stage.show();
                } catch (IOException e) {
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
