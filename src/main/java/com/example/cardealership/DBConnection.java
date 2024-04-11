package com.example.cardealership;

import com.example.cardealership.model.ModelTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.InputStream;
import java.sql.*;

public class DBConnection {
    static ResultSet resultSet;
    static Connection conn;
    static Statement statement;
    static PreparedStatement preparedStatement;
    static String database_name = "car_dealer";                // Name of schema/database
    static String url = "jdbc:mysql://localhost:3306/" + database_name;        // Alternative to localhost, create another connection and copy the IP, ie 127.0.0.1


    //    ---------- ESTABLISHING CONNECTION ----------------
    public static boolean establishConnect() {
        try {
            conn = DriverManager.getConnection(url, "root", "Admin123");        // Create the connection
            if (conn != null) {
                System.out.println("Connection established successfully!");
                statement = conn.createStatement();
                return true;
            } else {
                System.out.println("Failed to establish connection, check exception thrown");
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    ---------- CLOSING CONNECTION ----------------
    public static void closeConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //    ---------- INSERTING DATA INTO DATABASE TABLE ----------------
    public static boolean saveVehicleData(String vehicle_name, String manufacturer, String construction_year, Double km_stood, String vehicle_condition, int pieces, Double price, String currency, InputStream picture) {
        try {
            String query = "INSERT INTO vehicle (vehicle_name, manufacturer, construction_year, km_stood, vehicle_condition, pieces, price, currency, picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, vehicle_name);
            preparedStatement.setString(2, manufacturer);
            preparedStatement.setString(3, construction_year);
            preparedStatement.setDouble(4, km_stood);
            preparedStatement.setString(5, vehicle_condition);
            preparedStatement.setInt(6, pieces);
            preparedStatement.setDouble(7, price);
            preparedStatement.setString(8, currency);
            preparedStatement.setBlob(9, picture);
            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully!");
                return true; // Data was inserted successfully
            } else {
                System.out.println("No data inserted.");
                return false; // No data was inserted
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    ---------- READ DATA FROM DATABASE TABLE ----------------
    public static ResultSet retrieveVehicleData() {
        try {
            resultSet = statement.executeQuery("SELECT * FROM vehicle");
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    ---------- READ DATA FROM DATABASE TABLE FOR A PARTICULAR ID ----------------
    public static ResultSet retrieveSingleVehicleData(int vehicle_id) {
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM vehicle WHERE vehicle_id = ?");
            preparedStatement.setInt(1, vehicle_id);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateVehiclePicture(int vehicle_id, InputStream picture) {
        try {
            preparedStatement = conn.prepareStatement("UPDATE vehicle SET picture=? WHERE vehicle_id=?");
            preparedStatement.setBlob(1, picture);
            preparedStatement.setInt(2, vehicle_id);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateVehicleData() {
        return true;
    }

    public static boolean deleteVehicleData(int vehicle_id) {
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM vehicle WHERE vehicle_id=?");
            preparedStatement.setInt(1, vehicle_id);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0; // Return true if at least one row was deleted
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}



