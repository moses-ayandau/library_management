package org.example.demo.entity;

public class LibraryResource {
    protected int ID;
    int publishedYear;
    Library library;

    public LibraryResource(int publishedYear, Library library) {
        this.publishedYear = publishedYear;
        this.library = library;
    }

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

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
