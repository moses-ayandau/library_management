package org.example.demo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void shouldSetAndGetIDCorrectly() {
        int expectedID = 1;

        user.setID(expectedID);
        int actualID = user.getID();

        assertEquals(expectedID, actualID, "The ID should be set and retrieved correctly.");
    }

    @Test
    public void shouldSetAndGetNameCorrectly() {
        String expectedName = "John Doe";

        user.setName(expectedName);
        String actualName = user.getName();

        assertEquals(expectedName, actualName, "The name should be set and retrieved correctly.");
    }

    @Test
    public void shouldSetAndGetEmailCorrectly() {
        String expectedEmail = "john.doe@example.com";

        user.setEmail(expectedEmail);
        String actualEmail = user.getEmail();

        assertEquals(expectedEmail, actualEmail, "The email should be set and retrieved correctly.");
    }

    @Test
    public void shouldSetAndGetPhoneCorrectly() {
        String expectedPhone = "123-456-7890";

        user.setPhone(expectedPhone);
        String actualPhone = user.getPhone();

        assertEquals(expectedPhone, actualPhone, "The phone should be set and retrieved correctly.");
    }

    @Test
    public void shouldSetAndGetAddressCorrectly() {

        String expectedAddress = "123 Main St";

        user.setAddress(expectedAddress);
        String actualAddress = user.getAddress();

        assertEquals(expectedAddress, actualAddress, "The address should be set and retrieved correctly.");
    }

    @Test
    public void shouldSetAndGetPasswordCorrectly() {
        String expectedPassword = "password123";

        user.setPassword(expectedPassword);
        String actualPassword = user.getPassword();

        assertEquals(expectedPassword, actualPassword, "The password should be set and retrieved correctly.");
    }

    @Test
    public void shouldSetAndGetRoleCorrectly() {
        Role expectedRole = Role.ADMIN;

        user.setRole(expectedRole);
        Role actualRole = user.getRole();

        assertEquals(expectedRole, actualRole, "The role should be set and retrieved correctly.");
    }

    @Test
    public void shouldSetAndGetReservationsCorrectly() {
        Reservation reservation = new Reservation();
        List<Reservation> reservations = List.of(reservation);

        user.setReservations(reservations);
        List<Reservation> actualReservations = user.getReservations();

        assertEquals(reservations, actualReservations, "The reservations should be set and retrieved correctly.");
    }

    @Test
    public void shouldAddBookToBooksList() {
        Book book = new Book();
        List<Book> books = List.of(book);

        user.books.add(book);
        List<Book> actualBooks = user.getBooks();

        assertTrue(actualBooks.contains(book), "The book should be added to the user's books list.");
    }

    @Test
    public void shouldInitializeUserWithCorrectAttributes() {
        String name = "John Doe";
        String email = "john.doe@example.com";
        String phone = "123-456-7890";
        String address = "123 Main St";
        Role role = Role.PATRON;

        User newUser = new User(name, email, phone, address, role);

        assertEquals(name, newUser.getName(), "The name should be set correctly.");
        assertEquals(email, newUser.getEmail(), "The email should be set correctly.");
        assertEquals(phone, newUser.getPhone(), "The phone should be set correctly.");
        assertEquals(address, newUser.getAddress(), "The address should be set correctly.");
        assertEquals(role, newUser.getRole(), "The role should be set correctly.");
    }
}
