package org.example.demo.dao.interfaces;

import org.example.demo.entity.Journal;

import java.sql.SQLException;
import java.util.List;

public interface IJournalDAO {
    void addJournal(Journal journal) throws SQLException;
    Journal getJournalById(int id) throws SQLException;
    void deleteJournal(int id) throws SQLException;
    List<Journal> getAllJournals() throws SQLException;
}
