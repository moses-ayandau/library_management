package org.example.demo.entity;

public class Book extends LibraryResource{
    private String title;
    private String description;
    private String author;
    private String isbn;
    private boolean available;


    public Book(){
        super();
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

    public Book(int i, String title, String author, String description, String isbn, int year, boolean b) {
        super();
    }

    public int getBookID() {
        return ID;
    }

    public void setBookID(int bookID) {
        this.ID = bookID;
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

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


    // Constructor, Getters and Setters
}
