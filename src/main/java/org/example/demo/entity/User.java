package org.example.demo.entity;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class User {


     private int ID;
     private String name;
     private String email;
     private String phone;
     private String address;
     private Role role;
     private String password;

     private List<Reservation> reservations = new ArrayList<>();


     List<Book> books = new LinkedList<>();

     public User(String johnDoe, String mail, String number, String s, String password123, Role role) {
     }


     public List<Reservation> getReservations() {
          return reservations;
     }

     public void setReservations(List<Reservation> reservations) {
          this.reservations = reservations;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }




     public User() {}
     public User(String name, String email, String phone, String address, Role role) {
          this.name = name;
          this.email = email;
          this.phone = phone;
          this.address = address;
          this.role = role;
     }
     public void setID(int ID) {
          this.ID = ID;
     }

     public int getID() {
          return ID;
     }

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
     public Role getRole() {
          return this.role;
     }

     public List<Book> getBooks() {
          return this.books;
     }

     public void setRole(Role role) {
          this.role = role;
     }


}
