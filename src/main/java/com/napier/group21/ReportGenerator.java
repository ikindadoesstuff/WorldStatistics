package com.napier.group21;

import java.sql.*;
import java.util.ArrayList;

/**
 * Provides methods to generate different types of reports.
 */
public class ReportGenerator {
    /**
     * List of continents to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable, but cannot be reassigned
     */
    private final ArrayList<String> continents = new ArrayList<>();

    /**
     * List of regions to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable, but cannot be reassigned.
     */
    private final ArrayList<String> regions = new ArrayList<>();

    /**
     * Connection object which is retrieved from the DatabaseConnection object.
     */
    private final Connection conn;

    /**
     * Initializes database connection and fetches scope names with which to validate against the user's specific scope
     * name.
     */
    public ReportGenerator(Connection connection) {
        this.conn = connection;
        fetchContinents();
        fetchRegions();
    }

    /**
     * Print all the rows in the report
     *
     * @param rows {@literal <? extends DatabaseObjects>} allows any object which implements DatabaseObject to be passed
     *             to this method. All our DTOs are Java records, meaning they can't share a common parent. This makes
     *             implementing the same interface the best way to allow for this.
     */
    public static void printReport(ArrayList<? extends DatabaseObject> rows) {
        // Delay between report title and table
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }

        if (rows.isEmpty()) {
            System.out.println("No records found");
        } else {
            System.out.println(rows.get(0).getColumnString());
            for (DatabaseObject row : rows) {
                System.out.println(row.toString());
            }
        }
        // Spacer for next report.
        System.out.println("\n");
    }

    /**
     * Fetch all continents for verification.
     */
    public void fetchContinents() {
        String query = "SELECT DISTINCT Continent FROM country";
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

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
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

            while (resultSet.next()) {
                String region = resultSet.getString("Region");
                regions.add(region.toUpperCase());
            }
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + sqle.getMessage());
        }
    }

    // ISSUE 1
    /**
     * Get all countries in the world ordered in descending population order.
     * No scope specified defaults to world.
     */
    public ArrayList<Country> generateSortedCountryReport() {
        return generateSortedCountryReport(Scope.WORLD, "");
    }

    /**
     * Get all countries in the specified scope name ordered in descending population order.
     *
     * @param scope The scope level being specified (WORLD, CONTINENT, REGION).
     * @param name The specific name of the continent or region. Use empty string if using WORLD scope.
     */
    public ArrayList<Country> generateSortedCountryReport(Scope scope, String name) {
        ArrayList<Country> countries = new ArrayList<>();

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
                    return countries;
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
                    return countries;
                } else {
                    condition = String.format("WHERE Region = '%s'", name);
                    break;
                }
        }

        String query = """
                SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, capital.Name
                FROM country
                LEFT JOIN city as capital on country.Capital = capital.ID
                %s
                ORDER BY country.Population DESC
                """.formatted(condition);
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Country country = new Country(
                        resultSet.getString("country.Code"),
                        resultSet.getString("country.Name"),
                        resultSet.getString("country.Continent"),
                        resultSet.getString("country.Region"),
                        resultSet.getLong("country.Population"),
                        resultSet.getString("capital.name") != null ? // Some countries don't have capitals.
                                resultSet.getString("capital.name") : "None"
                );
                countries.add(country);
            }
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + sqle.getMessage());
        }
        return countries;
    }

    // ISSUE 2
    /**
     * Get top N countries in the world.
     * No scope specified defaults to world.
     */
    public ArrayList<Country> generateTopNCountryReport(int n) {
        return generateTopNCountryReport(Scope.WORLD, "", n);
    }

    /**
     * Get top N countries in the specified scope name.
     *
     * @param scope The scope level being specified (WORLD, CONTINENT, REGION).
     * @param name The specific name of the continent or region. Use empty string if using WORLD scope.
     * @param n The number of countries to display
     */
    public ArrayList<Country> generateTopNCountryReport(Scope scope, String name, int n) {
        ArrayList<Country> countries = new ArrayList<>();

        name = name.toUpperCase();
        String condition = "";
        switch (scope) {
            case WORLD:
                System.out.printf(
                        "Displaying top %d countries in the world: \n",
                        n
                );
                break;
            case CONTINENT:
                System.out.printf(
                        "Displaying top %d countries in continent - %s: \n",
                        n,
                        name
                );
                if (!continents.contains(name)) {
                    System.out.printf("Continent '%s' not found. Report can not be generated.\n", name);
                    return countries;
                } else {
                    condition = String.format("WHERE Continent = '%s'", name);
                    break;
                }
            case REGION:
                System.out.printf(
                        "Displaying top %d countries in region - %s: \n",
                        n,
                        name
                );
                if (!regions.contains(name)) {
                    System.out.printf("Region '%s' not found. Report can not be generated.\n", name);
                    return countries;
                } else {
                    condition = String.format("WHERE Region = '%s'", name);
                    break;
                }
        }

        String query = """
                SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, capital.Name
                FROM country
                LEFT JOIN city as capital on country.Capital = capital.ID
                %s
                ORDER BY country.Population DESC LIMIT %d
                """.formatted(condition, n);
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Country country = new Country(
                        resultSet.getString("country.Code"),
                        resultSet.getString("country.Name"),
                        resultSet.getString("country.Continent"),
                        resultSet.getString("country.Region"),
                        resultSet.getLong("country.Population"),
                        resultSet.getString("capital.name") != null ? // Some countries don't have capitals.
                                resultSet.getString("capital.name") : "None"
                );
                countries.add(country);
            }
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + sqle.getMessage());
        }
        return countries;
    }
}