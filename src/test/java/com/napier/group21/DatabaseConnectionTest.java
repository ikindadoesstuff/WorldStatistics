package com.napier.group21;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the DatabaseConnection class.
 */
class DatabaseConnectionTest {
    static DatabaseConnection databaseConnection;

    @BeforeAll
    static void setUp() {
        databaseConnection = new DatabaseConnection();
    }

    @Test
    void testDatabaseConnection_NoArgs() {
        DatabaseConnection conn = new DatabaseConnection();
        assertNotNull(conn, "DatabaseConnection constructor with no arguments should not return null");
    }

    @Test
    void testDatabaseConnection_Args() {
        String[] args = new String[]{"localhost:33060"};
        DatabaseConnection conn = new DatabaseConnection(args);
        assertNotNull(conn, "DatabaseConnection constructor with valid arguments should not return null");
    }

    @Test
    void testDatabaseConnection_NullArgs() {
        DatabaseConnection conn = new DatabaseConnection(null);
        assertNotNull(conn, "DatabaseConnection constructor with null arguments should not return null");
    }

    @Test
    void testDatabaseConnection_NullArgsElement() {
        String[] args = {null};
        DatabaseConnection conn = new DatabaseConnection(args);
        assertNotNull(conn, "DatabaseConnection constructor with null argument element should not return null");
    }

    @Test
    void testConnect_ConnectionFail() {
        assertDoesNotThrow(()->databaseConnection.connect(1),
                "Attempting to connect to database should not throw exception when connection fails"
        );
    }

    @Test
    void testConnect_InvalidRetries() {
        databaseConnection.connect(-5);
        assertNull(
                databaseConnection.getConnection(),
                "Database connection should be null when connection fails"
        );
    }

    @Test
    void testDisconnect_NullConn() {
        assertDoesNotThrow(databaseConnection::disconnect,
                "Calling disconnect without an active connection should not throw an exception"
                );
    }

    @Test
    void getConnection_NullConn() {
        assertDoesNotThrow(databaseConnection::getConnection,
                "Getting the connection without an active connection should not throw an exception"
        );
    }
}