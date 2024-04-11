package com.example.cardealership.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Utils {

    public static void customAlert(Alert.AlertType alertType, String title, String headerText, String contentText){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
//        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
        //alert.showAndWait();
    }

    @FunctionalInterface
    public interface ConfirmationCallback {
        void onConfirmed();
    }


    public static void customConfirmationAlert(Alert.AlertType alertType, String title, String headerText, String contentText, ConfirmationCallback callback){

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.out.println("OK Clicked");
            callback.onConfirmed();
        } else if(result.isPresent() && result.get() == ButtonType.CANCEL){
            System.out.println("CANCEL Clicked");
        }
    }
}
