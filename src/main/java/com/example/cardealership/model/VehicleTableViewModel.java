package com.example.cardealership.model;

public class VehicleTableViewModel {
    private String id, vehicle_name, manufacturer, construction_year, km_stood, vehicle_condition, pieces, price, currency, availability;

    public VehicleTableViewModel(String id, String vehicle_name, String manufacturer, String construction_year, String km_stood, String vehicle_condition, String pieces, String price, String currency, String availability) {
        this.id = id;
        this.vehicle_name = vehicle_name;
        this.manufacturer = manufacturer;
        this.construction_year = construction_year;
        this.km_stood = km_stood;
        this.vehicle_condition = vehicle_condition;
        this.pieces = pieces;
        this.price = price;
        this.currency = currency;
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getConstruction_year() {
        return construction_year;
    }

    public void setConstruction_year(String construction_year) {
        this.construction_year = construction_year;
    }

    public String getKm_stood() {
        return km_stood;
    }

    public void setKm_stood(String km_stood) {
        this.km_stood = km_stood;
    }

    public String getVehicle_condition() {
        return vehicle_condition;
    }

    public void setVehicle_condition(String vehicle_condition) {
        this.vehicle_condition = vehicle_condition;
    }

    public String getPieces() {
        return pieces;
    }

    public void setPieces(String pieces) {
        this.pieces = pieces;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}

