package org.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class Patron extends User {

    private List<Transaction> bookings = new ArrayList<>();
    public Patron(){
        super();
    }

    public Patron(List<Transaction> bookings){
        super();
        this.bookings = bookings;
    }

}
