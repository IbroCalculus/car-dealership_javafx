package com.example.cardealership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.time.Month;
import java.util.ResourceBundle;

public class Deal implements Initializable {

    @FXML
    private Button btn_main_menu;

    @FXML
    private Button btn_submit;

    @FXML
    private ComboBox<?> cb_customer_name;

    @FXML
    private ComboBox<String> cb_date_day;

    @FXML
    private ComboBox<String> cb_date_month;

    @FXML
    private ComboBox<?> cb_number_of_vehicle;

    @FXML
    private ComboBox<?> cb_seller_name;

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

    }
}
