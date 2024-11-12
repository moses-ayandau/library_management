package org.example.demo.entity;

import java.util.Date;

public class Book {
    private Long book_id;
    private String book_title;
    private String description;
    private Double book_price;
    private Date release_date;
    private Boolean is_available;
    public Book(String book_title, String description, Double book_price, Date release_date){
        this.book_title = book_title;
        this.description = description;
        this.book_price = book_price;
        this.release_date = release_date;
    }

    public Boolean getIs_available() {
        return is_available;
    }

    public void setIs_available(Boolean is_available) {
        this.is_available = is_available;
    }

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBook_price() {
        return book_price;
    }

    public void setBook_price(Double book_price) {
        this.book_price = book_price;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }




}
