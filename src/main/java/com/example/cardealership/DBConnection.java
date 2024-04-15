package com.example.cardealership;

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
    public static void saveVehicleData(String vehicle_name, String manufacturer, String construction_year, Double km_stood, String vehicle_condition, int pieces, Double price, String currency, InputStream picture) {
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
            } else {
                System.out.println("No data inserted.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    ---------- READ VEHICLE DATA FROM DATABASE TABLE ----------------
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



//    ======================================================================================================
//    =================================== SELLER TABLE ==============================

    //    ---------- INSERTING DATA INTO DATABASE TABLE ----------------
    public static void saveSellerData(String seller_name, String seller_phone_number, String seller_email, String seller_address, InputStream picture) {
        try {
            String query = "INSERT INTO seller (seller_name, seller_phone, seller_email, seller_address, picture) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, seller_name);
            preparedStatement.setString(2, seller_phone_number);
            preparedStatement.setString(3, seller_email);
            preparedStatement.setString(4, seller_address);
            preparedStatement.setBlob(5, picture);
            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully!");
            } else {
                System.out.println("No data inserted.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    ---------- RETRIEVE SELLER DATA FROM DATABASE TABLE ----------------
    public static ResultSet retrieveSellerData() {
        try {
            resultSet = statement.executeQuery("SELECT * FROM seller");
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    ---------- RETRIEVE SINGLE SELLER DATA FROM DATABASE SELLER TABLE FOR A PARTICULAR ID ----------------
    public static ResultSet retrieveSingleSellerData(int seller_id) {
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM seller WHERE seller_id = ?");
            preparedStatement.setInt(1, seller_id);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateSellerPicture(int seller_id, InputStream picture) {
        try {
            preparedStatement = conn.prepareStatement("UPDATE seller SET picture=? WHERE seller_id=?");
            preparedStatement.setBlob(1, picture);
            preparedStatement.setInt(2, seller_id);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateSellerData(int seller_id, String seller_name, String seller_phone, String seller_email, String seller_address) {
        try {
            preparedStatement = conn.prepareStatement("UPDATE seller SET seller_name = ?, seller_phone = ?, seller_email = ?, seller_address = ? WHERE seller_id=?");
            preparedStatement.setString(1, seller_name);
            preparedStatement.setString(2, seller_phone);
            preparedStatement.setString(3, seller_email);
            preparedStatement.setString(4, seller_address);
            preparedStatement.setInt(5, seller_id);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteSellerData(int seller_id) {
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM seller WHERE seller_id=?");
            preparedStatement.setInt(1, seller_id);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0; // Return true if at least one row was deleted
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    ======================================================================================================
//    =================================== CUSTOMER TABLE ==============================

    //    ---------- INSERTING DATA INTO DATABASE TABLE ----------------
    public static void saveCustomerData(String customer_name, String customer_phone_number, String customer_email, String customer_address) {
        try {
            String query = "INSERT INTO customer (customer_name, customer_phone, customer_email, customer_address) VALUES (?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, customer_name);
            preparedStatement.setString(2, customer_phone_number);
            preparedStatement.setString(3, customer_email);
            preparedStatement.setString(4, customer_address);
            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully!");
            } else {
                System.out.println("No data inserted.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    ---------- RETRIEVE CUSTOMER DATA FROM DATABASE TABLE ----------------
    public static ResultSet retrieveCustomerData() {
        try {
            resultSet = statement.executeQuery("SELECT * FROM customer");
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    ---------- RETRIEVE SINGLE SELLER DATA FROM DATABASE SELLER TABLE FOR A PARTICULAR ID ----------------
    public static ResultSet retrieveSingleCustomerData(int customer_id) {
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM customer WHERE customer_id = ?");
            preparedStatement.setInt(1, customer_id);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateCustomerData(int customer_id, String customer_name, String customer_phone, String customer_email, String customer_address) {
        try {
            preparedStatement = conn.prepareStatement("UPDATE customer SET customer_name = ?, customer_phone = ?, customer_email = ?, customer_address = ? WHERE customer_id=?");
            preparedStatement.setString(1, customer_name);
            preparedStatement.setString(2, customer_phone);
            preparedStatement.setString(3, customer_email);
            preparedStatement.setString(4, customer_address);
            preparedStatement.setInt(5, customer_id);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteCustomerData(int customer_id) {
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM customer WHERE customer_id=?");
            preparedStatement.setInt(1, customer_id);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0; // Return true if at least one row was deleted
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    ================================================ RETRIEVE ALL VEHICLES FROM vehicle TABLE =================================
//    ---------- RETRIEVE ALL VEHICLES FROM vehicle TABLE ----------------
public static ResultSet retrieveAllVehicles(String availability_status) {
    try {
        preparedStatement = conn.prepareStatement("SELECT vehicle_name FROM vehicle WHERE availability = ?");
        preparedStatement.setString(1, availability_status);
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}


}



