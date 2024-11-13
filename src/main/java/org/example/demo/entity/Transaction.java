package org.example.demo.entity;

import java.util.Date;

public class Transaction {
    private int ID;
    private int bookID;
    private int patronID;
    private Date borrowedDate;
    private Date returnedDate;
    private Date dueDate;


    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    public int getPatronID() {
        return patronID;
    }

    public void setPatronID(int patronID) {
        this.patronID = patronID;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }


    public int getID() {
        return ID;
    }

    public void setID(int bookingID) {
        this.ID = bookingID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getMemberID() {
        return patronID;
    }

    public void setMemberID(int memberID) {
        this.patronID = memberID;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }





}
