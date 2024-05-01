package com.example.cardealership;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml")); // HelloApplication.class
        Scene scene = new Scene(loader.load());
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        stage.setMaxHeight(900);
        stage.setMaxWidth(1200);
        stage.getIcons().add(new Image("deal.png"));
        stage.show();

        DBConnection.establishConnect();
    }

    public static void main(String[] args) {
        launch();
    }
}