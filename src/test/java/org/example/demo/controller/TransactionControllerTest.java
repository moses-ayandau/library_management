package org.example.demo.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import org.example.demo.dao.TransactionDAO;
import org.example.demo.entity.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionControllerTest {

    private TransactionController controller;

    @Mock
    private TransactionDAO mockTransactionDAO;


    @BeforeAll
    static void initToolkit() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }
    @BeforeEach
    void setUp() throws InterruptedException {
        MockitoAnnotations.openMocks(this);
        controller = new TransactionController();
        controller.setTransactionDAO(mockTransactionDAO);

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.transactionTable = new TableView<>();
            controller.transactionTable.setItems(FXCollections.observableArrayList());
            controller.bookIdField = new javafx.scene.control.TextField();
            controller.patronIdField = new javafx.scene.control.TextField();
            controller.dueDatePicker = new javafx.scene.control.DatePicker();

            controller.transactionList = FXCollections.observableArrayList();

            latch.countDown();
        });
        latch.await();
    }
//    @Test
//    void testHandleCreateTransaction_Successful() throws SQLException, InterruptedException {
//        // Arrange: Prepare mock transaction and mock DAO behavior
//        Transaction mockTransaction = new Transaction();
//        mockTransaction.setBookID(123);
//        mockTransaction.setPatronID(456);
//        mockTransaction.setBorrowedDate(java.sql.Date.valueOf(LocalDate.now()));  // Correct type
//        mockTransaction.setDueDate(java.sql.Date.valueOf(LocalDate.now().plusDays(7)));  // Correct type
//
//        when(mockTransactionDAO.createTransaction(any(Transaction.class))).thenReturn(true);
//
//        // Populate UI fields with valid data
//        Platform.runLater(() -> {
//            controller.bookIdField.setText("123");
//            controller.patronIdField.setText("456");
//            controller.dueDatePicker.setValue(LocalDate.now().plusDays(7));
//            System.out.println("UI fields populated");  // Debugging line
//        });
//
//        // Ensure UI updates are completed before calling the action
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            controller.handleCreateTransaction();
//            latch.countDown();  // Ensure we wait until handleCreateTransaction is called
//        });
//
//        // Wait for the JavaFX thread to complete
//        latch.await();
//
//        // Assert: Verify that the createTransaction method was called with the correct parameters
//        verify(mockTransactionDAO, times(1)).createTransaction(any(Transaction.class));
//    }


    @Test
    void testHandleCreateTransaction_InvalidInput() throws SQLException {
        controller.bookIdField.setText("");
        controller.patronIdField.setText("");

        Platform.runLater(() -> controller.handleCreateTransaction());

        verify(mockTransactionDAO, never()).createTransaction(any(Transaction.class));
    }

//    @Test
//    void testHandleReturnBook_Successful() throws SQLException, InterruptedException {
//        Transaction transaction = new Transaction();
//        transaction.setID(1);
//        transaction.setBookID(123);
//        transaction.setPatronID(456);
//        transaction.setBorrowedDate(java.sql.Date.valueOf(LocalDate.now()));  // Correct type here
//        transaction.setDueDate(java.sql.Date.valueOf(LocalDate.now().plusDays(7))); // Correct type here
//
//        when(mockTransactionDAO.returnBook(eq(1), any(java.sql.Date.class))).thenReturn(true);
//
//        Platform.runLater(() -> {
//            controller.transactionTable.getItems().add(transaction);
//            controller.transactionTable.getSelectionModel().select(transaction); // Select the transaction
//        });
//
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            assertNotNull(controller.transactionTable.getSelectionModel().getSelectedItem(), "Transaction should be selected");
//
//            try {
//                controller.handleReturnBook();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            } finally {
//                latch.countDown();
//            }
//        });
//
//        latch.await();
//
//        verify(mockTransactionDAO, times(1)).returnBook(eq(1), any(java.sql.Date.class));
//    }



    @Test
    void testHandleReturnBook_NoSelection() throws SQLException {
        controller.transactionTable.getSelectionModel().clearSelection();

        Platform.runLater(() -> {
            try {
                controller.handleReturnBook();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        verify(mockTransactionDAO, never()).returnBook(anyInt(), any(Date.class));
    }

    @Test
    void testLoadActiveTransactions_Successful() throws SQLException, InterruptedException {
        Transaction transaction1 = new Transaction();
        transaction1.setID(1);
        transaction1.setBookID(101);
        transaction1.setPatronID(102);
        transaction1.setBorrowedDate(Date.valueOf(LocalDate.now()));
        transaction1.setDueDate(Date.valueOf(LocalDate.now().plusDays(7)));

        Transaction transaction2 = new Transaction();
        transaction2.setID(2);
        transaction2.setBookID(103);
        transaction2.setPatronID(104);
        transaction2.setBorrowedDate(Date.valueOf(LocalDate.now()));
        transaction2.setDueDate(Date.valueOf(LocalDate.now().plusDays(7)));

        List<Transaction> mockTransactions = Arrays.asList(transaction1, transaction2);
        when(mockTransactionDAO.getActiveTransactions()).thenReturn(mockTransactions);

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                controller.loadActiveTransactions();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                latch.countDown();
            }
        });

        latch.await();

        assertEquals(2, controller.transactionTable.getItems().size(), "Table should contain 2 transactions.");
        verify(mockTransactionDAO).getActiveTransactions();
    }

}
