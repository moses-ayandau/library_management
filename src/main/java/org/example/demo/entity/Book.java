package org.example.demo.entity;


import java.sql.Date;

public class Book extends LibraryResource{
    public int ID;
    private String description;
    private String author;
    private String isbn;
    private boolean available;
    private int quantity;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }





    public Book(){

    }

    public Book(String title, String description, String author, String isbn, int publishedYear, boolean available) {
        super();
        this.title = title;
        this.description = description;
        this.author = author;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.available = available;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book ID: " + ID +
                "\nTitle: " + title +
                "\nAuthor: " + author +
                "\nISBN: " + isbn +
                "\nPublished Date: " + publishedYear +
                "\nAvailable: " + available + "\n";
    }


}
