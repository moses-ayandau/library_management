//package org.example.demo.controller;
//
//import javafx.application.Platform;
//import javafx.embed.swing.JFXPanel;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//
//import org.example.demo.dao.interfaces.*;
//import org.example.demo.entity.*;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.sql.SQLException;
//import java.util.*;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class BookControllerIntegrationTest {
//
//    private BookController bookController;
//
//    @Mock
//    private IBookDAO mockBookDAO;
//
//    @Mock
//    private ITransactionDAO mockTransactionDAO;
//
//    @Mock
//    private IReservationDAO mockReservationDAO;
//
//    @Mock
//    private IJournalDAO mockJournalDAO;
//
//    @Mock
//    private IUserDAO mockUserDAO;
//
//    @BeforeAll
//    static void initJavaFX() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.startup(() -> {
//            new JFXPanel(); // Create JavaFX environment
//            latch.countDown();
//        });
//        latch.await(5, TimeUnit.SECONDS);
//    }
//
//    @BeforeEach
//    void setUp() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            bookController = new BookController();
//
//            // Use reflection or a setter method to inject mock DAOs
//            bookController.bookDAO = mockBookDAO;
//
//            // Set up FXML fields with mock TextField instances
//            bookController.titleField = new TextField();
//            bookController.descriptionField = new TextField();
//            bookController.authorField = new TextField();
//            bookController.isbnField = new TextField();
//            bookController.yearField = new TextField();
//            bookController.quantityField = new TextField();
//            bookController.bookIdField = new TextField();
//
//            latch.countDown();
//        });
//        latch.await(5, TimeUnit.SECONDS);
//    }
//
//
//    @Test
//    void testAddBook_ValidationFailure() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//
//        Platform.runLater(() -> {
//            try {
//                // Prepare test data with invalid input
//                bookController.titleField.setText("");
//                bookController.descriptionField.setText("Test Description");
//                bookController.authorField.setText("Test Author");
//                bookController.isbnField.setText("1234567890");
//                bookController.yearField.setText("2023");
//                bookController.quantityField.setText("5");
//
//                // Perform the action
//                bookController.addBook();
//
//                // No verification needed as we're checking validation
//            } catch (Exception e) {
//                fail("Unexpected exception: " + e.getMessage());
//            } finally {
//                latch.countDown();
//            }
//        });
//
//        assertTrue(latch.await(10, TimeUnit.SECONDS), "Test timed out");
//    }
////
////    @Test
////    void testBorrowBook_Success() throws InterruptedException, SQLException {
////        CountDownLatch latch = new CountDownLatch(1);
////
////        Platform.runLater(() -> {
////            try {
////                // Prepare test data
////                Book testBook = new Book();
////                testBook.setID(1);
////                testBook.setTitle("Test Book");
////                testBook.setQuantity(5);
////
////                // Mock DAO behaviors
////                when(mockBookDAO.getBookById(1)).thenReturn(testBook);
////                when(mockTransactionDAO.createTransaction(any(Transaction.class))).thenReturn(true);
////                when(mockBookDAO.updateBook(any(Book.class))).thenReturn(true);
////
////                // Use reflection to call private method
////                java.lang.reflect.Method borrowBookMethod =
////                        BookController.class.getDeclaredMethod("borrowBook", Book.class, Integer.class);
////                borrowBookMethod.setAccessible(true);
////                borrowBookMethod.invoke(bookController, testBook, 1);
////
////                // Verify interactions
////                verify(mockTransactionDAO).createTransaction(any(Transaction.class));
////                verify(mockBookDAO).updateBook(any(Book.class));
////            } catch (Exception e) {
////                fail("Unexpected exception: " + e.getMessage());
////            } finally {
////                latch.countDown();
////            }
////        });
////
////        assertTrue(latch.await(10, TimeUnit.SECONDS), "Test timed out");
////    }
////
////    @Test
////    void testReserveBook_Success() throws InterruptedException, SQLException {
////        CountDownLatch latch = new CountDownLatch(1);
////
////        Platform.runLater(() -> {
////            try {
////                // Prepare test data
////                Book testBook = new Book();
////                testBook.setID(1);
////                testBook.setTitle("Test Book");
////                testBook.setQuantity(5);
////
////                // Mock DAO behaviors
////                when(mockReservationDAO.addReservation(any(Reservation.class))).thenReturn(true);
////                when(mockBookDAO.updateBook(any(Book.class))).thenReturn(true);
////
////                // Use reflection to call private method
////                java.lang.reflect.Method reserveBookMethod =
////                        BookController.class.getDeclaredMethod("reserveBook", Book.class, Integer.class);
////                reserveBookMethod.setAccessible(true);
////                reserveBookMethod.invoke(bookController, testBook, 1);
////
////                // Verify interactions
////                verify(mockReservationDAO).addReservation(any(Reservation.class));
////                verify(mockBookDAO).updateBook(any(Book.class));
////            } catch (Exception e) {
////                fail("Unexpected exception: " + e.getMessage());
////            } finally {
////                latch.countDown();
////            }
////        });
////
////        assertTrue(latch.await(10, TimeUnit.SECONDS), "Test timed out");
////    }
//
//    @Test
//    void testLoadBooks_Success() throws InterruptedException, SQLException {
//        CountDownLatch latch = new CountDownLatch(1);
//
//        Platform.runLater(() -> {
//            try {
//                // Prepare test data
//                Stack<Book> testBooks = new Stack<>();
//                Book book1 = new Book();
//                book1.setID(1);
//                book1.setTitle("Test Book 1");
//                testBooks.add(book1);
//
//                // Mock DAO behavior
//                when(mockBookDAO.getAllBooks()).thenReturn(testBooks);
//
//                // Prepare the table view (you might need to use a mock or set up a real TableView)
//                bookController.loadBooks();
//
//                // Verify interaction
//                verify(mockBookDAO).getAllBooks();
//            } catch (SQLException e) {
//                fail("Unexpected SQLException: " + e.getMessage());
//            } finally {
//                latch.countDown();
//            }
//        });
//
//        assertTrue(latch.await(10, TimeUnit.SECONDS), "Test timed out");
//    }
//
//    @Test
//    void testGetBookById_Success() throws InterruptedException, SQLException {
//        CountDownLatch latch = new CountDownLatch(1);
//
//        Platform.runLater(() -> {
//            try {
//                // Prepare test data
//                Book testBook = new Book();
//                testBook.setID(1);
//                testBook.setTitle("Test Book");
//
//                // Setup book ID field
//                bookController.bookIdField = new TextField();
//                bookController.bookIdField.setText("1");
//
//                // Manually create and set the TableView
//                bookController.bookTable = new TableView<>();
//
//                // Setup column for the TableView
//                bookController.colId = new TableColumn<>();
//                bookController.colTitle = new TableColumn<>();
//                bookController.colDescription = new TableColumn<>();
//                bookController.colAuthor = new TableColumn<>();
//                bookController.colIsbn = new TableColumn<>();
//                bookController.colYear = new TableColumn<>();
//                bookController.colQuantity = new TableColumn<>();
//
//                // Mock DAO behavior
//                when(mockBookDAO.getBookById(1)).thenReturn(testBook);
//
//                // Prepare the table view
//                bookController.getBookById(null);
//
//                // Verify interaction
//                verify(mockBookDAO).getBookById(1);
//            } catch (SQLException e) {
//                fail("Unexpected SQLException: " + e.getMessage());
//            } finally {
//                latch.countDown();
//            }
//        });
//
//        assertTrue(latch.await(10, TimeUnit.SECONDS), "Test timed out");
//    }
//}