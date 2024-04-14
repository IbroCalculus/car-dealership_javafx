package com.example.cardealership.model;

public class SellerTableViewModel {
    private String id, seller_name, phone_number, seller_email, address;

    public SellerTableViewModel(String id, String seller_name, String phone_number, String seller_email, String address) {
        this.id = id;
        this.seller_name = seller_name;
        this.phone_number = phone_number;
        this.seller_email = seller_email;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
