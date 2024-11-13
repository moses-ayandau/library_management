package org.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class Patron {
    private int ID;
    private String name;
    private String email;
    private String phone;
    private String address;
    private List<Booking> bookings = new ArrayList<>();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPatronID() {
        return ID;
    }

    public void setPatronID(int patronID) {
        this.ID = patronID;
    }


}
