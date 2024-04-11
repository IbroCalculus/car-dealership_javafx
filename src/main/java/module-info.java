module com.example.cardealership {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.cardealership to javafx.fxml;
    opens com.example.cardealership.model to javafx.base;

    exports com.example.cardealership;
}