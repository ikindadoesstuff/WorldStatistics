package com.napier.group21;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for the DatabaseConnection class.
 */
public class DatabaseConnectionTest {
    static DatabaseConnection databaseConnection;

    @BeforeAll
    static void setUp() {
        databaseConnection = new DatabaseConnection();
    }

    @Test
    void testDatabaseConnection_NoArgs() {
        DatabaseConnection conn = new DatabaseConnection();
    }

    @Test
    void testDatabaseConnection_Args() {
        String[] args = new String[]{"localhost:33060"};
        DatabaseConnection conn = new DatabaseConnection(args);
    }

    @Test
    void testDatabaseConnection_NullArgs() {
        DatabaseConnection conn = new DatabaseConnection(null);
    }

    @Test
    void testDatabaseConnection_NullArgsElement() {
        String[] args = {null};
        DatabaseConnection conn = new DatabaseConnection(args);
    }

    @Test
    void testConnect() {
        databaseConnection.connect(1);
    }

    @Test
    void testConnect_InvalidRetries() {
        databaseConnection.connect(-5);
    }
}
