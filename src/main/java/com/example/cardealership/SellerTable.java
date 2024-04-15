package com.example.cardealership;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SellerTable implements Initializable {


    @FXML
    private Button btn_main_menu;

    @FXML
    private TableColumn<SellerTableModel, String> tablecolumn_address;

    @FXML
    private TableColumn<SellerTableModel, String> tablecolumn_email;

    @FXML
    private TableColumn<SellerTableModel, String> tablecolumn_id;

    @FXML
    private TableColumn<SellerTableModel, String> tablecolumn_phone_number;

    @FXML
    private TableColumn<SellerTableModel, String> tablecolumn_seller_name;

    @FXML
    private TableView<SellerTableModel> tableview_seller;

    ResultSet resultSet;

    public static int id;
    public static String seller_name;
    public static String seller_phone;
    public static String email;
    public static String address;
    public static Blob picture;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultSet = DBConnection.retrieveSellerData();
        try{
            while (resultSet.next()) {
                id = resultSet.getInt("seller_id");
                seller_name = resultSet.getString("seller_name");
                seller_phone = resultSet.getString("seller_phone");
                email = resultSet.getString("seller_email");
                address = resultSet.getString("seller_address");
                picture = resultSet.getBlob("picture");

                String id2 = String.valueOf(id);
                SellerTableModel sellerTableModel = new SellerTableModel(id2, seller_name, seller_phone, email, address);
                tableview_seller.getItems().add(sellerTableModel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        tablecolumn_id.setCellValueFactory(new PropertyValueFactory<SellerTableModel, String>("id"));
        tablecolumn_seller_name.setCellValueFactory(new PropertyValueFactory<SellerTableModel, String>("seller_name"));
        tablecolumn_phone_number.setCellValueFactory(new PropertyValueFactory<SellerTableModel, String>("phone_number"));
        tablecolumn_email.setCellValueFactory(new PropertyValueFactory<SellerTableModel, String>("seller_email"));
        tablecolumn_address.setCellValueFactory(new PropertyValueFactory<SellerTableModel, String>("address"));


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


        tableview_seller.setOnMouseClicked(event -> {
            try {
                int selectedIndex = tableview_seller.getSelectionModel().getSelectedIndex();
                String seller_id = tableview_seller.getSelectionModel().getSelectedItem().getId();
                resultSet = DBConnection.retrieveSingleSellerData(Integer.parseInt(seller_id));
                while (resultSet.next()){
                    id = resultSet.getInt("seller_id");
                    seller_name = resultSet.getString("seller_name");
                    seller_phone = resultSet.getString("seller_phone");
                    email = resultSet.getString("seller_email");
                    address = resultSet.getString("seller_address");
                    picture = resultSet.getBlob("picture");

                    System.out.println("Seller_name: " + seller_name);

                    FXMLLoader fxmlLoader = new FXMLLoader(SelectedSeller.class.getResource("selected_seller.fxml"));
                    try {
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) tableview_seller.getScene().getWindow();
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
