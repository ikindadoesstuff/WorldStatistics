package com.napier.group21;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages connection to the database and subsequent disconnection
 */
public class DatabaseConnection {
    /**
     * Connection object
     */
    private Connection conn = null;

    /**
     * Connection string for 'world' database on MySQL container @ port 3306
     */
    private final String url;

    /**
     * MySQL username.
     */
    final String user = "root";

    /**
     * MySQL password.
     */
    final String password = "group21";

    /**
     * Constructor specifies database url.
     * @param args command line arguments
     */
    DatabaseConnection(String[] args) {
        // Default host
        String host = "localhost:33060";
        if (args != null && args.length > 0 && args[0] != null) {
            // Host specified by docker or command line
            host = args[0];
        }
        url = "jdbc:mysql://"+(host)+"/world?allowPublicKeyRetrieval=true&useSSL=false";
    }

    /**
     * Default constructor, for when no args are passed.
     * Connects to the local port forwarded from the docker container.
     */
    DatabaseConnection() {
        // Default host
        this(new String[]{"localhost:33060"});
    }

    /**
     * Establish connection to the MySQL database.
     * Allows up to 30 attempts with a 10s wait time between each.
     * Gets MySQL JDBC driver and exits program if not found.
     */
    public void connect(int retries) {
        if (retries <= 0) {
            System.out.println("Failed to connect to database. `Retries` value must be greater than 0.");
            return;
        }

        try {
            // Get MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load SQL driver: " + e.getMessage());
            System.exit(1);
        }

        int retryWaitTime = 10000;
        for (int i = 0; i < retries; i++) {
            System.out.println("Connecting to database... (Attempt #" + (i + 1) + "/" + retries + ")");
            try {
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to database successfully\n==================================\n");
                break;

            } catch (SQLException sqle) {
                // Wait before reattempting database connection.
                try {
                    Thread.sleep(retryWaitTime);
                } catch (InterruptedException ie) {
                    System.out.println(ie.getMessage());
                }
                System.out.println("Failed to connect to database: " + sqle.getMessage() + "\n");
            }
        }
    }

    /**
     * Closes connection to the database if connection exists.
     */
    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Failed to properly close database connection: " + e.getMessage());
            }
        }
    }

    /**
     * Returns the current database connection
     *
     * @return Connection object
     */
    public Connection getConnection() {
        return conn;
    }
}
