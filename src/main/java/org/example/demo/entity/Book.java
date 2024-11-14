package org.example.demo.entity;

import java.util.Date;

public class Book {
    public int ID;
    private String title;
    private String description;
    private String author;
    private String isbn;
    private boolean available;
    private int quantity;
    private Date publishedDate;


    public java.sql.Date getPublishedDate() {
        return (java.sql.Date) publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }





    public Book(){
        super();
    }

    public Book(String title, String description, String author, String isbn, Date publishedDate, boolean available, int quantity) {
        super();
        this.title = title;
        this.description = description;
        this.author = author;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
        this.available = available;
        this.quantity = quantity;
    }

    public Book(int i, String title, String author, String description, String isbn, int year, boolean b) {
        super();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
                "\nPublished Date: " + publishedDate +
                "\nAvailable: " + available + "\n";
    }


}
