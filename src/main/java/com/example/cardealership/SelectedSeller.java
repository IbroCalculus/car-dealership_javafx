package com.example.cardealership;

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

import static com.example.cardealership.utils.Utils.customAlert;
import static com.example.cardealership.utils.Utils.customConfirmationAlert;

public class SelectedSeller implements Initializable {

    @FXML
    private AnchorPane my_anchor_pane;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_save_picture;

    @FXML
    private Button btn_table_seller;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_upload_picture;

    @FXML
    private ImageView imageview_seller_picture;

    @FXML
    private TextField tf_address;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_phone_number;

    @FXML
    private TextField tf_seller_id;

    @FXML
    private TextField tf_seller_name;

    public static int id;
    public static String seller_name;
    public static String seller_phone;
    public static String email;
    public static String address;
    public static Blob picture;
    public static String path;
    List<String> lstFile;
    FileInputStream fileInputStream;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lstFile = new ArrayList<>();
        lstFile.add("*.png");
        lstFile.add("*.jpg");

        id = SellerTable.id;
        seller_name = SellerTable.seller_name;
        seller_phone = SellerTable.seller_phone;
        email = SellerTable.email;
        address = SellerTable.address;
        picture = SellerTable.picture;

        tf_seller_id.setText(String.valueOf(id));
        tf_seller_name.setText(seller_name);
        tf_phone_number.setText(seller_phone);
        tf_email.setText(email);
        tf_address.setText(address);

        try {
            InputStream inputStream = picture.getBinaryStream();
            Image image = new Image(inputStream);
            imageview_seller_picture.setImage(image);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        btn_table_seller.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(SellerTable.class.getResource("seller_table.fxml"));
            try {
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) btn_table_seller.getScene().getWindow();
                stage.setScene(new Scene(root, 1200, 900));
//                stage.setTitle("Welcome!");
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
                    imageview_seller_picture.setImage(image);
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
                boolean inserted = DBConnection.updateSellerPicture(id, fileInputStream);
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
            id = Integer.parseInt(tf_seller_id.getText());
            seller_name = tf_seller_name.getText();
            seller_phone = tf_phone_number.getText();
            email = tf_email.getText();
            address = tf_address.getText();


            customConfirmationAlert(Alert.AlertType.CONFIRMATION,"Update Seller Detail","Are you sure you want to update this seller's information?","Yes",() -> {
                boolean updated = DBConnection.updateSellerData(id, seller_name, seller_phone, email, address);
                if(updated){
                    customAlert(Alert.AlertType.INFORMATION,"Seller information updated","The seller's information has been updated","Close");

                    FXMLLoader fxmlLoader = new FXMLLoader(SellerTable.class.getResource("seller_table.fxml"));
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
                    customAlert(Alert.AlertType.ERROR,"Seller Error","The seller could not be deleted","Close");
                }
            });
        });

        btn_delete.setOnAction(event -> {
            customConfirmationAlert(Alert.AlertType.CONFIRMATION,"Delete Seller","Are you sure you want to delete this Seller?","Yes",() -> {
                boolean deleted = DBConnection.deleteSellerData(id);
                if(deleted){
                    customAlert(Alert.AlertType.INFORMATION,"Seller Deleted","The seller has been deleted","Close");

                    FXMLLoader fxmlLoader = new FXMLLoader(SellerTable.class.getResource("seller_table.fxml"));
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
                    customAlert(Alert.AlertType.ERROR,"Seller Error","The seller could not be deleted","Close");
                }
            });
        });




    }
}
