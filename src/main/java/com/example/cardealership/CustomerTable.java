package com.example.cardealership;

import com.example.cardealership.model.CustomerTableModel;
import com.example.cardealership.model.SellerTableModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CustomerTable implements Initializable {

    @FXML
    private Button btn_main_menu;

    @FXML
    private AnchorPane my_anchor_pane;

    @FXML
    private TableColumn<CustomerTableModel, String> tablecolumn_address;

    @FXML
    private TableColumn<CustomerTableModel, String> tablecolumn_customer_name;

    @FXML
    private TableColumn<CustomerTableModel, String> tablecolumn_email;

    @FXML
    private TableColumn<CustomerTableModel, String> tablecolumn_id;

    @FXML
    private TableColumn<CustomerTableModel, String> tablecolumn_phone_number;

    @FXML
    private TableView<CustomerTableModel> tableview_customer;

    ResultSet resultSet;

    public static int id;
    public static String customer_name;
    public static String customer_phone_number;
    public static String customer_email;
    public static String customer_address;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultSet = DBConnection.retrieveCustomerData();
        try{
            while (resultSet.next()) {
                id = resultSet.getInt("customer_id");
                customer_name = resultSet.getString("customer_name");
                customer_phone_number = resultSet.getString("customer_phone");
                customer_email = resultSet.getString("customer_email");
                customer_address = resultSet.getString("customer_address");

                String id2 = String.valueOf(id);
                CustomerTableModel customerTableModel = new CustomerTableModel(id2, customer_name, customer_phone_number, customer_email, customer_address);
                tableview_customer.getItems().add(customerTableModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        tablecolumn_id.setCellValueFactory(new PropertyValueFactory<CustomerTableModel, String>("id"));
        tablecolumn_customer_name.setCellValueFactory(new PropertyValueFactory<CustomerTableModel, String>("name"));
        tablecolumn_phone_number.setCellValueFactory(new PropertyValueFactory<CustomerTableModel, String>("phone_number"));
        tablecolumn_email.setCellValueFactory(new PropertyValueFactory<CustomerTableModel, String>("email"));
        tablecolumn_address.setCellValueFactory(new PropertyValueFactory<CustomerTableModel, String>("address"));


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


        tableview_customer.setOnMouseClicked(event -> {
            try {
                int selectedIndex = tableview_customer.getSelectionModel().getSelectedIndex();
                String customer_id = tableview_customer.getSelectionModel().getSelectedItem().getId();
                resultSet = DBConnection.retrieveSingleCustomerData(Integer.parseInt(customer_id));
                while (resultSet.next()){
                    id = resultSet.getInt("customer_id");
                    customer_name = resultSet.getString("customer_name");
                    customer_phone_number = resultSet.getString("customer_phone");
                    customer_email = resultSet.getString("customer_email");
                    customer_address = resultSet.getString("customer_address");

                    System.out.println("Seller_name: " + customer_name);

                    FXMLLoader fxmlLoader = new FXMLLoader(SelectedCustomer.class.getResource("selected_customer.fxml"));
                    try {
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) tableview_customer.getScene().getWindow();
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
