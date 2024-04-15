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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.cardealership.utils.Utils.customAlert;
import static com.example.cardealership.utils.Utils.customConfirmationAlert;

public class SelectedCustomer implements Initializable {

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_table_customer;

    @FXML
    private Button btn_update;

    @FXML
    private ImageView imageview_seller_picture;

    @FXML
    private TextField tf_address;

    @FXML
    private TextField tf_customer_id;

    @FXML
    private TextField tf_customer_name;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_phone_number;

    public static int id;
    public static String customer_name;
    public static String customer_phone_number;
    public static String customer_email;
    public static String customer_address;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id = CustomerTable.id;
        customer_name = CustomerTable.customer_name;
        customer_phone_number = CustomerTable.customer_phone_number;
        customer_email = CustomerTable.customer_email;
        customer_address = CustomerTable.customer_address;

        tf_customer_id.setText(String.valueOf(id));
        tf_customer_name.setText(customer_name);
        tf_phone_number.setText(customer_phone_number);
        tf_email.setText(customer_email);
        tf_address.setText(customer_address);


        btn_update.setOnAction(event -> {
            id = Integer.parseInt(tf_customer_id.getText());
            customer_name = tf_customer_name.getText();
            customer_phone_number = tf_phone_number.getText();
            customer_email = tf_email.getText();
            customer_address = tf_address.getText();


            customConfirmationAlert(Alert.AlertType.CONFIRMATION,"Update Customer Detail","Are you sure you want to update this customer's information?","Yes",() -> {
                boolean updated = DBConnection.updateCustomerData(id, customer_name, customer_phone_number, customer_email, customer_address);
                if(updated){
                    customAlert(Alert.AlertType.INFORMATION,"Customer's information updated","The customer's information has been updated","Close");

                    FXMLLoader fxmlLoader = new FXMLLoader(CustomerTable.class.getResource("customer_table.fxml"));
                    try {
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) btn_delete.getScene().getWindow();
                        stage.setScene(new Scene(root, 1200, 900));
//                        stage.setTitle("Welcome!");
                        stage.setMaxHeight(900);
                        stage.setMaxWidth(1200);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    customAlert(Alert.AlertType.ERROR,"Customer Error","The customer could not be updated","Close");
                }
            });
        });

        btn_table_customer.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(CustomerTable.class.getResource("customer_table.fxml"));
            try {
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) btn_table_customer.getScene().getWindow();
                stage.setScene(new Scene(root, 1200, 900));
//                stage.setTitle("Welcome!");
                stage.setMaxHeight(900);
                stage.setMaxWidth(1200);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn_delete.setOnAction(event -> {
            customConfirmationAlert(Alert.AlertType.CONFIRMATION,"Delete Customer","Are you sure you want to delete this customer?","Yes",() -> {
                boolean deleted = DBConnection.deleteCustomerData(id);
                if(deleted){
                    customAlert(Alert.AlertType.INFORMATION,"Customer Deleted","The customer has been deleted","Close");

                    FXMLLoader fxmlLoader = new FXMLLoader(CustomerTable.class.getResource("customer_table.fxml"));
                    try {
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) btn_delete.getScene().getWindow();
                        stage.setScene(new Scene(root, 1200, 900));
//                        stage.setTitle("Welcome!");
                        stage.setMaxHeight(900);
                        stage.setMaxWidth(1200);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    customAlert(Alert.AlertType.ERROR,"Customer Error","The customer could not be deleted","Close");
                }
            });
        });

    }
}
