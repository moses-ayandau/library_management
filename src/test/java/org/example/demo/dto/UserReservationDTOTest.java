package org.example.demo.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserReservationDTOTest {

    private UserReservationDTO userReservationDTO;

    @BeforeEach
    public void setUp() {
        userReservationDTO = new UserReservationDTO();
    }

    @Test
    public void testSetAndGetID() {
        userReservationDTO.setID(1);
        assertEquals(1, userReservationDTO.getID(), "ID should be 1");
    }

    @Test
    public void testSetAndGetName() {
        String name = "John Doe";
        userReservationDTO.setName(name);
        assertEquals(name, userReservationDTO.getName(), "Name should be 'John Doe'");
    }

    @Test
    public void testSetAndGetEmail() {
        String email = "johndoe@example.com";
        userReservationDTO.setEmail(email);
        assertEquals(email, userReservationDTO.getEmail(), "Email should be 'johndoe@example.com'");
    }

    @Test
    public void testSetAndGetPhone() {
        String phone = "123-456-7890";
        userReservationDTO.setPhone(phone);
        assertEquals(phone, userReservationDTO.getPhone(), "Phone should be '123-456-7890'");
    }

    @Test
    public void testSetAndGetAddress() {
        String address = "123 Main St, Springfield";
        userReservationDTO.setAddress(address);
        assertEquals(address, userReservationDTO.getAddress(), "Address should be '123 Main St, Springfield'");
    }

    @Test
    public void testSetAndGetBookId() {
        int bookId = 101;
        userReservationDTO.setBookId(bookId);
        assertEquals(bookId, userReservationDTO.getBookId(), "Book ID should be 101");
    }

    @Test
    public void testSetAndGetReservedDate() {
        Date reservedDate = Date.valueOf("2024-01-01");
        userReservationDTO.setReservedDate(reservedDate);
        assertEquals(reservedDate, userReservationDTO.getReservedDate(), "Reserved date should be '2024-01-01'");
    }
}
