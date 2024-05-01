package com.example.cardealership;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import static com.example.cardealership.Deal.*;

public class CloseDeal implements Initializable {

    @FXML
    private Button btn_deal;

    @FXML
    private Button btn_submit;

    @FXML
    private ImageView imageview_add_new_vehicle;

    @FXML
    private Label lbl_count_sold_vehicle;

    @FXML
    private Label lbl_customer_name;

    @FXML
    private Label lbl_seller_name;

    @FXML
    private Label lbl_sold_date;

    @FXML
    private Label lbl_total_price;

    @FXML
    private Label lbl_vehicle_name;

    @FXML
    private AnchorPane my_anchor_pane;
    String soldDate;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lbl_vehicle_name.setText(vehicleName);
        lbl_customer_name.setText(customerName);
        lbl_seller_name.setText(sellerName);
        lbl_count_sold_vehicle.setText(numberOfVehicle2);
        soldDate = dateDay + "-" + dateMonth + "-" + dateYear;
        lbl_sold_date.setText(soldDate);

        DecimalFormat df = new DecimalFormat("#,###.00");
        String formattedAmount = df.format(Double.parseDouble(totalPrice));
        lbl_total_price.setText(formattedAmount);


        btn_deal.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Deal.class.getResource("deal.fxml"));
            try {
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) btn_deal.getScene().getWindow();
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
