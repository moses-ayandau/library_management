package org.example.demo.entity;

import java.util.Date;

public class Booking {
    private int bookingID;
    private int bookID;
    private int patronID;
    private Date borrowedDate;
    private Date returnDate;

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
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

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }



}
