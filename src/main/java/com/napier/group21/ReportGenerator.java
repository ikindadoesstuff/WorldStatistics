package com.napier.group21;

import java.sql.*;
import java.util.ArrayList;

/**
 * Initializes database connection and provides methods to generate different types of reports.
 */
public class ReportGenerator {
    /**
     * List of continents to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable, but cannot be reassigned
     */
    private final ArrayList<String> continents = new ArrayList<>();

    /**
     * List of regions to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable, but cannot be reassigned
     */
    private final ArrayList<String> regions = new ArrayList<>();

    /**
     * Connection object.
     */
    private Connection conn = null;

    /**
     * Connection string for 'world' database on MySQL container @ port 3306
     */
    final String url = "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false";

    /**
     * MySQL username.
     */
    final String user = "root";

    /**
     * MySQL password.
     */
    final String password = "group21";

    /**
     * Initializes database connection and fetches scope names with which to validate against the user's specific scope
     * name.
     */
    public ReportGenerator() {
        connect();
        fetchContinents();
        fetchRegions();
    }

    /**
     * Fetch all continents for verification.
     */
    public void fetchContinents() {
        String query = "SELECT DISTINCT Continent FROM country";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String continent = resultSet.getString("Continent");
                continents.add(continent.toUpperCase());
            }
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + sqle.getMessage());
        }
    }

    /**
     * Fetch all regions for verification.
     */
    public void fetchRegions() {
        String query = "SELECT DISTINCT Region FROM country";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String region = resultSet.getString("Region");
                regions.add(region.toUpperCase());
            }
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + sqle.getMessage());
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

    /**
     * Establish connection to the MySQL database.
     * Allows up to 30 attempts with a 10s wait time between each.
     * Gets MySQL JDBC driver and exits program if not found.
     */
    public void connect() {
        try {
            // Get MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load SQL driver: " + e.getMessage());
            System.exit(1);
        }

        int retries = 30;
        int retryWaitTime = 10000;
        for (int i = 0; i < retries; i++) {
            System.out.println("Connecting to database... (Attempt #" + (i + 1) + "/" + retries + ")");
            try {
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to database successfully");
                break;

            } catch (SQLException sqle) {
                // Wait before reattempting database connection.
                try {
                    Thread.sleep(retryWaitTime);
                } catch (InterruptedException ie) {
                    System.out.println(ie.getMessage());
                }
                System.out.println("Failed to connect to database: " + sqle.getMessage());
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

    // ISSUE 1
    /**
     * Get all countries in the world ordered in descending population order.
     * No scope specified defaults to world.
     */
    public void generateSortedCountryReport() {
        generateSortedCountryReport(Scope.WORLD, "");
    }

    /**
     * Get all countries in the specified scope name ordered in descending population order.
     *
     * @param scope The scope level being specified (WORLD, CONTINENT, REGION).
     * @param name The specific name of the continent or region. Use empty string if using WORLD scope.
     */
    public void generateSortedCountryReport(Scope scope, String name) {
        name = name.toUpperCase();
        String condition = "";
        switch (scope) {
            case WORLD:
                System.out.println(
                        "Displaying all countries in the world. " +
                        "Population sorted, largest to smallest: "
                );
                break;
            case CONTINENT:
                System.out.printf(
                        "Displaying all countries in continent - %s. " +
                        "Population sorted, largest to smallest: \n"
                        , name
                );
                if (!continents.contains(name)) {
                    System.out.printf("Continent '%s' not found. Report can not be generated.\n", name);
                    return;
                } else {
                    condition = String.format("WHERE Continent = '%s'", name);
                    break;
                }
            case REGION:
                System.out.printf(
                        "Displaying all countries in region - %s. " +
                        "Population sorted, largest to smallest: \n"
                        , name
                );
                if (!regions.contains(name)) {
                    System.out.printf("Region '%s' not found. Report can not be generated.\n", name);
                    return;
                } else {
                    condition = String.format("WHERE Region = '%s'", name);
                    break;
                }
        }

        String query = String.format(
                "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, capital.Name " +
                "FROM country " +
                "LEFT JOIN city as capital on country.Capital = capital.ID " +
                "%s " +
                "ORDER BY country.Population DESC",
                condition
        );
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Thread.sleep(3000);
            System.out.println("=====================================================================================");

            while (resultSet.next()) {
                String code = resultSet.getString("country.Code");
                String cname = resultSet.getString("country.Name");
                String continent = resultSet.getString("country.Continent");
                String region = resultSet.getString("country.Region");
                long population = resultSet.getLong("country.Population");
                String capital = resultSet.getString("capital.name");
                capital = capital != null ? capital : "None";

                String result = String.format(
                        //"%s (%s), %s, %s \nPopulation: %,d \nCapital: %s \n",
                        "> %-45s %s | %-34s | Population: %,13d | Capital: %s ",
                        cname, code, (continent + ", " + region), population, capital
                );
                System.out.println(result);
            }
            // Spacer for next report.
            System.out.println("\n");
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + sqle.getMessage());
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }
    }
}