package com.example.cardealership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.Month;
import java.util.ResourceBundle;

import static com.example.cardealership.utils.Utils.customAlert;

public class Deal implements Initializable {

    @FXML
    private Button btn_main_menu;

    @FXML
    private Button btn_submit;

    @FXML
    private ComboBox<String> cb_customer_name;

    @FXML
    private ComboBox<String> cb_date_day;

    @FXML
    private ComboBox<String> cb_date_month;

    @FXML
    private ComboBox<String> cb_number_of_vehicle;

    @FXML
    private ComboBox<String> cb_seller_name;

    @FXML
    private ComboBox<String> cb_vehicle_name;

    @FXML
    private ImageView imageview_add_new_vehicle;

    @FXML
    private AnchorPane my_anchor_pane;

    @FXML
    private TextField tf_date_year;
    ResultSet resultSet;



    private final ObservableList<String> date_day_list = FXCollections.observableArrayList();
    private final ObservableList<String> date_month_list = FXCollections.observableArrayList();
    private final ObservableList<String> vehicle_name_list = FXCollections.observableArrayList();
    private final ObservableList<String> customer_name_list = FXCollections.observableArrayList();
    private final ObservableList<String> seller_name_list = FXCollections.observableArrayList();
    private final ObservableList<String> number_of_vehicle_list = FXCollections.observableArrayList();
    public static  String selectedVehicleName;
    public static int numberOfVehicle;
    public static String vehicleName, customerName, sellerName, numberOfVehicle2, dateDay, dateMonth, dateYear, totalPrice;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 1; i <= 31; i++) {
            date_day_list.add(String.format("%02d",i));
        }
        cb_date_day.setItems(date_day_list);

        for (Month month : Month.values()) {
            String capitalizedMonth = month.toString().substring(0, 1).toUpperCase() + month.toString().substring(1).toLowerCase();
            date_month_list.add(capitalizedMonth);
        }
        cb_date_month.setItems(date_month_list);


        resultSet = DBConnection.retrieveAllVehicles("available");
        try{
            while (resultSet.next()){
                vehicle_name_list.add(resultSet.getString("vehicle_name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        cb_vehicle_name.setItems(vehicle_name_list);


        resultSet = DBConnection.retrieveAllCustomerNames();
        try{
            while (resultSet.next()){
                customer_name_list.add(resultSet.getString("customer_name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        cb_customer_name.setItems(customer_name_list);

        resultSet = DBConnection.retrieveAllSellerNames();
        try{
            while (resultSet.next()){
                seller_name_list.add(resultSet.getString("seller_name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        cb_seller_name.setItems(seller_name_list);

        cb_vehicle_name.setOnAction(event -> {
            selectedVehicleName = cb_vehicle_name.getValue();
            try{
                resultSet = DBConnection.retrieveNumberOfVehicle(selectedVehicleName);
                number_of_vehicle_list.clear();
                while (resultSet.next()){
                    numberOfVehicle = resultSet.getInt("pieces");
                    cb_number_of_vehicle.setValue(String.valueOf(numberOfVehicle));
                    for (int i = 0; i < numberOfVehicle; i++){
                        number_of_vehicle_list.add(String.valueOf(i+1));
                    }
                }
                cb_number_of_vehicle.setItems(number_of_vehicle_list);
            }catch (Exception e){
                e.printStackTrace();
            }
        });


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

        btn_submit.setOnAction(event -> {
            vehicleName = cb_vehicle_name.getValue();
            customerName = cb_customer_name.getValue();
            sellerName = cb_seller_name.getValue();
            numberOfVehicle2 = cb_number_of_vehicle.getValue();
            dateDay = cb_date_day.getValue();
            dateMonth = cb_date_month.getValue();
            dateYear = tf_date_year.getText();

            String[] values = {vehicleName, customerName, sellerName, numberOfVehicle2, dateDay, dateMonth, dateYear};
            boolean empty = false;
            for (String value : values) {
                if (value == null){
                    empty = true;
                    break;
                }
            }

            if(!empty){
                try{
//                    DBConnection.insertDeal(vehicleName, customerName, sellerName, numberOfVehicle2, dateDay, dateMonth, dateYear);
//                    customAlert(Alert.AlertType.CONFIRMATION, "Success","Saved","Data saved to database successfully");
//                    try {
//                        Duration delay = Duration.seconds(2);
//                        Thread.sleep((long) delay.toMillis());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    resultSet = DBConnection.retrieveVehiclePrice(vehicleName);
                    while (resultSet.next()){
                        totalPrice = String.valueOf(resultSet.getInt("price") * Integer.parseInt(numberOfVehicle2));
                    }
                    FXMLLoader fxmlLoader = new FXMLLoader(CloseDeal.class.getResource("close_deal.fxml"));
                    try {
                        Parent root = fxmlLoader.load();
                        Stage stage = (Stage) btn_submit.getScene().getWindow();
                        stage.setScene(new Scene(root, 1200, 900));
                        stage.setTitle("Welcome!");
                        stage.setMaxHeight(900);
                        stage.setMaxWidth(1200);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    customAlert(Alert.AlertType.ERROR, "Failed","Failure","Error occurred while saving data");
                    e.printStackTrace();
                }
            }
            else {
                customAlert(Alert.AlertType.ERROR, "Empty fields","Empty input fields","Some fields are empty");
            }
        });



    }
}
