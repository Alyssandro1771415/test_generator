package com;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.mavenproject1.Repository.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DatabaseManagerTest {

    private static final String DB_URL = "jdbc:mysql://localhost/TestQuestionDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private DatabaseManager databaseManager;

    @Before
    public void setUp() {
        try {
            databaseManager = new DatabaseManager(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Falha ao conectar à DB");
        }
    }

    @Test
    public void testExecuteQuery() {
        try {
            ResultSet resultSet = databaseManager.executeQuery("SELECT * FROM Questions");
            assertNotNull(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Falha ao executar consulta");
        }
    }

    @Test
    public void testExecuteUpdate() {
        try {
            int rowsAffected = databaseManager.executeUpdate("INSERT INTO Questions (schoolSubject, content, question, difficult, itemA, itemB, itemC, itemD, itemE, itemF) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", "value1", "value2", "value3", 1, "value4", "value5", "value6", "value7", "value8", "value9");
            assertTrue(rowsAffected > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Falha ao executar upload");
        }
    }

    @After
    public void tearDown() {
        databaseManager.closeConnection();
    }
}
