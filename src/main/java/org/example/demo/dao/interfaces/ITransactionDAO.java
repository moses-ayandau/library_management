package org.example.demo.dao.interfaces;

import org.example.demo.entity.Transaction;

import java.sql.Date;
import java.util.List;

public interface ITransactionDAO {
    boolean createTransaction(Transaction transaction);
    boolean returnBook(int transactionId, Date returnDate);
    List<Transaction> getActiveTransactions();
    Transaction getTransactionById(int id);

}
