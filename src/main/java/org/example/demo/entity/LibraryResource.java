package org.example.demo.entity;

public class LibraryResource {
    protected int ID;
    int publishedYear;


    public LibraryResource() {

    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


}
