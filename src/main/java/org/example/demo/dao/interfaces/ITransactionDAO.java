package org.example.demo.dao.interfaces;

import org.example.demo.entity.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ITransactionDAO {
    boolean createTransaction(Transaction transaction) throws SQLException;
    boolean returnBook(int transactionId, Date returnDate) throws SQLException;
    List<Transaction> getActiveTransactions() throws SQLException;
    Transaction getTransactionById(int id);

}
