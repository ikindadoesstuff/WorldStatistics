package com.napier.group21;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        assertNotNull(conn);
    }

    @Test
    void testDatabaseConnection_Args() {
        String[] args = new String[]{"localhost:33060"};
        DatabaseConnection conn = new DatabaseConnection(args);
        assertNotNull(conn);
    }

    @Test
    void testDatabaseConnection_NullArgs() {
        DatabaseConnection conn = new DatabaseConnection(null);
        assertNotNull(conn);
    }

    @Test
    void testDatabaseConnection_NullArgsElement() {
        String[] args = {null};
        DatabaseConnection conn = new DatabaseConnection(args);
        assertNotNull(conn);
    }

    @Test
    void testConnect() {
        databaseConnection.connect(1);
    }

    @Test
    void testConnect_InvalidRetries() {
        databaseConnection.connect(-5);
    }

    @Test
    void testDisconnect_NullConn() {
        databaseConnection.disconnect();
    }

    @Test
    void getConnection_NullConn() {
        Connection conn = databaseConnection.getConnection();
        assertNull(conn);
    }
}
