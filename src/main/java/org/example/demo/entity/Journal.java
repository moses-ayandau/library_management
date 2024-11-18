package org.example.demo.entity;

public class Journal extends LibraryResource {
    private String issn;
    private String publisher;

    public Journal(){}

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
