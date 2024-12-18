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
    public void testGetAndSetID() {
        int expectedID = 1;

        user.setID(expectedID);
        int actualID = user.getID();

        assertEquals(expectedID, actualID, "The ID should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetName() {
        String expectedName = "John Doe";

        user.setName(expectedName);
        String actualName = user.getName();

        assertEquals(expectedName, actualName, "The name should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetEmail() {
        // Given
        String expectedEmail = "john.doe@example.com";

        // When
        user.setEmail(expectedEmail);
        String actualEmail = user.getEmail();

        // Then
        assertEquals(expectedEmail, actualEmail, "The email should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetPhone() {
        // Given
        String expectedPhone = "123-456-7890";

        // When
        user.setPhone(expectedPhone);
        String actualPhone = user.getPhone();

        // Then
        assertEquals(expectedPhone, actualPhone, "The phone should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetAddress() {
        // Given
        String expectedAddress = "123 Main St";

        // When
        user.setAddress(expectedAddress);
        String actualAddress = user.getAddress();

        // Then
        assertEquals(expectedAddress, actualAddress, "The address should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetPassword() {
        // Given
        String expectedPassword = "password123";

        // When
        user.setPassword(expectedPassword);
        String actualPassword = user.getPassword();

        // Then
        assertEquals(expectedPassword, actualPassword, "The password should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetRole() {
        // Given
        Role expectedRole = Role.ADMIN;

        // When
        user.setRole(expectedRole);
        Role actualRole = user.getRole();

        // Then
        assertEquals(expectedRole, actualRole, "The role should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetReservations() {
        // Given
        Reservation reservation = new Reservation();
        List<Reservation> reservations = List.of(reservation);

        // When
        user.setReservations(reservations);
        List<Reservation> actualReservations = user.getReservations();

        // Then
        assertEquals(reservations, actualReservations, "The reservations should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetBooks() {
        // Given
        Book book = new Book();
        List<Book> books = List.of(book);

        // When
        user.books.add(book);  // Adding a book to the list
        List<Book> actualBooks = user.getBooks();

        // Then
        assertTrue(actualBooks.contains(book), "The book should be added to the user's books list.");
    }

    @Test
    public void testUserConstructor() {
        // Given
        String name = "John Doe";
        String email = "john.doe@example.com";
        String phone = "123-456-7890";
        String address = "123 Main St";
        Role role = Role.PATRON;

        // When
        User newUser = new User(name, email, phone, address, role);

        // Then
        assertEquals(name, newUser.getName(), "The name should be set correctly.");
        assertEquals(email, newUser.getEmail(), "The email should be set correctly.");
        assertEquals(phone, newUser.getPhone(), "The phone should be set correctly.");
        assertEquals(address, newUser.getAddress(), "The address should be set correctly.");
        assertEquals(role, newUser.getRole(), "The role should be set correctly.");
    }
}
