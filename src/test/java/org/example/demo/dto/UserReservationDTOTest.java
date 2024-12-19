package org.example.demo.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserReservationDTOTest {

    private UserReservationDTO userReservationDTO;

    @BeforeEach
    public void setUp() {
        userReservationDTO = new UserReservationDTO();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 100})
    public void testSetAndGetID(int id) {
        userReservationDTO.setID(id);
        assertEquals(id, userReservationDTO.getID(), "ID should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @CsvSource({
            "John Doe",
            "Jane Smith",
            "Alex Johnson"
    })
    public void testSetAndGetName(String name) {
        userReservationDTO.setName(name);
        assertEquals(name, userReservationDTO.getName(), "Name should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @CsvSource({
            "johndoe@example.com",
            "janesmith@example.com",
            "alex.johnson@example.com"
    })
    public void testSetAndGetEmail(String email) {
        userReservationDTO.setEmail(email);
        assertEquals(email, userReservationDTO.getEmail(), "Email should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @CsvSource({
            "123-456-7890",
            "987-654-3210",
            "555-666-7777"
    })
    public void testSetAndGetPhone(String phone) {
        userReservationDTO.setPhone(phone);
        assertEquals(phone, userReservationDTO.getPhone(), "Phone should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @CsvSource({
            "123 Main St, Springfield",
            "456 Elm St, Shelbyville",
            "789 Oak St, Capital City"
    })
    public void testSetAndGetAddress(String address) {
        userReservationDTO.setAddress(address);
        assertEquals(address, userReservationDTO.getAddress(), "Address should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @ValueSource(ints = {101, 202, 303})
    public void testSetAndGetBookId(int bookId) {
        userReservationDTO.setBookId(bookId);
        assertEquals(bookId, userReservationDTO.getBookId(), "Book ID should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @CsvSource({
            "2024-01-01",
            "2024-02-15",
            "2024-12-31"
    })
    public void testSetAndGetReservedDate(String reservedDateStr) {
        Date reservedDate = Date.valueOf(reservedDateStr);
        userReservationDTO.setReservedDate(reservedDate);
        assertEquals(reservedDate, userReservationDTO.getReservedDate(), "Reserved date should be set and retrieved correctly.");
    }
}
