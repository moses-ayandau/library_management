package org.example.demo.db.conn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DatabaseConnectionTest {

    private Connection mockConnection;

    @BeforeEach
    public void setUp() throws SQLException {
        mockConnection = Mockito.mock(Connection.class);
    }

    @Test
    public void shouldReturnConnectionWhenDriverManagerReturnsConnection() throws SQLException {
        try (MockedStatic<DriverManager> mockedStatic = mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            Connection connection = DatabaseConnection.getConnection();
            assertNotNull(connection, "Connection should not be null");
            assertEquals(mockConnection, connection, "The returned connection should be the mock connection");
        }
    }

    @Test
    public void shouldConfigureH2ForTestingProperly() {
        DatabaseConnection.configureH2ForTesting();

        assertEquals("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", DatabaseConnection.url, "H2 URL should be configured for testing");
        assertEquals("sa", DatabaseConnection.user, "H2 user should be 'sa'");
        assertEquals("", DatabaseConnection.password, "H2 password should be empty");
    }
}
