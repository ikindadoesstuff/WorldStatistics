package com.napier.group21;

import java.sql.*;
import java.util.ArrayList;

/**
 * Provides methods to generate different types of reports.
 */
public class ReportGenerator {
    /**
     * List of continents to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable but cannot be reassigned
     */
    private final ArrayList<String> dbContinents = new ArrayList<>();

    /**
     * List of regions to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable but cannot be reassigned.
     */
    private final ArrayList<String> dbRegions = new ArrayList<>();

    /**
     * List of countries to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable but cannot be reassigned.
     */
    private final ArrayList<String> dbCountries = new ArrayList<>();

    /**
     * List of districts to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable but cannot be reassigned.
     */
    private final ArrayList<String> dbDistricts = new ArrayList<>();

    /**
     * List of cities to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable but cannot be reassigned.
     */
    private final ArrayList<String> dbCities = new ArrayList<>();

    /**
     * Connection object which is retrieved from the DatabaseConnection object.
     */
    private Connection conn;

    /**
     * Initializes database connection and fetches scope names with which to validate against the user's specific scope
     * name.
     */
    public ReportGenerator() {
    }

    /**
     * Set connection before running database queries.
     * @param connection connection object returned from DatabaseConnection instance.
     */
    public void setConnection(Connection connection) {
        if (connection == null) {
            System.out.println("Cannot set null connection");
            return;
        }
        this.conn = connection;
    }

    /**
     * Get scope names with which to validate against the user's specific scope name.
     */
    public void fetchScopeNames() throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Cannot fetch scope names without open connection");
        }

        fetchContinents();
        fetchRegions();
        fetchCountries();
        fetchDistricts();
        fetchCities();
    }

    /**
     * Print all the rows in the report
     *
     * @param rows {@literal <? extends DatabaseObjects>} allows any object which implements DatabaseObject to be passed
     *             to this method. All our DTOs are Java records, meaning they can't share a common parent. This makes
     *             implementing the same interface the best way to allow for this.
     */
    public static void printReport(ArrayList<? extends DatabaseObject> rows) {
        if (rows == null || rows.isEmpty()) {
            System.out.println("No records found");
            return;
        }

        // Delay between report title and table
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }

        try {
            System.out.println(rows.get(0).getColumnString());
            for (DatabaseObject row : rows) {
                System.out.println(row.toString());
            }
        } catch (NullPointerException npe) {
            System.out.println("Null element in ArrayList passed to printReport. Cannot print report: "
                    + npe.getMessage());
            return;
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
                dbContinents.add(continent.toUpperCase());
            }

            if (dbContinents.isEmpty()) {
                System.out.println("No continents found");
            }
        } catch (SQLException sqle) {
            throw new RuntimeException("Failed to execute statement: " + query + sqle.getMessage());
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
                dbRegions.add(region.toUpperCase());
            }

            if (dbContinents.isEmpty()) {
                System.out.println("No continents found");
            }
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + query + sqle.getMessage());
        }
    }

    /**
     * Fetch all regions for verification.
     */
    public void fetchCountries() {
        String query = "SELECT DISTINCT Name as Country FROM country";
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

            while (resultSet.next()) {
                String region = resultSet.getString("Country");
                dbCountries.add(region.toUpperCase());
            }

            if (dbContinents.isEmpty()) {
                System.out.println("No continents found");
            }
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + query + sqle.getMessage());
        }
    }

    /**
     * Fetch all regions for verification.
     */
    public void fetchDistricts() {
        String query = "SELECT DISTINCT District FROM city";
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

            while (resultSet.next()) {
                String region = resultSet.getString("District");
                dbDistricts.add(region.toUpperCase());
            }

            if (dbContinents.isEmpty()) {
                System.out.println("No continents found");
            }
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + query + sqle.getMessage());
        }
    }

    /**
     * Fetch all regions for verification.
     */
    public void fetchCities() {
        String query = "SELECT DISTINCT name as City FROM city";
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)){

            while (resultSet.next()) {
                String region = resultSet.getString("City");
                dbCities.add(region.toUpperCase());
            }

            if (dbContinents.isEmpty()) {
                System.out.println("No continents found");
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
                if (!dbContinents.contains(name)) {
                    System.out.printf("Continent '%s' not found. Report can not be generated.\n", name);
                    return null;
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
                if (!dbRegions.contains(name)) {
                    System.out.printf("Region '%s' not found. Report can not be generated.\n", name);
                    return null;
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
            return null;
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
        if (n <= 0) {
            System.out.println("N must be greater than 0. Report can not be generated.");
            return null;
        }

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
                if (!dbContinents.contains(name)) {
                    System.out.printf("Continent '%s' not found. Report can not be generated.\n", name);
                    return null;
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
                if (!dbRegions.contains(name)) {
                    System.out.printf("Region '%s' not found. Report can not be generated.\n", name);
                    return null;
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
            return null;
        }
        return countries;
    }

    // ISSUE 3
    /**
     * Get all cities in the world ordered in descending population order.
     * No scope specified defaults to world.
     */
    public ArrayList<City> generateSortedCityReport() {
        return generateSortedCityReport(Scope.WORLD, "", null);
    }

    /**
     * Get all cities in the world ordered in descending population order.
     * @param scope should be used with CONTINENT, REGION, or COUNTRY scopes.
     */
    public ArrayList<City> generateSortedCityReport(Scope scope, String name) {
        return generateSortedCityReport(scope, name, null);
    }

    /**
     * Get all cities in the specified scope name ordered in descending population order.
     *
     * @param scope The scope level being specified (WORLD, CONTINENT, REGION, COUNTRY, DISTRICT).
     * @param name The specific name of the continent or region. Use empty string if using WORLD scope.
     * @param countryName When using DISTRICT scope, the country must be specified, as many districts names exist in
     *                    several countries.
     */
    public ArrayList<City> generateSortedCityReport(Scope scope, String name, String countryName) {
        ArrayList<City> cities = new ArrayList<>();

        name = name.toUpperCase();
        String condition = "";
        switch (scope) {
            case WORLD:
                System.out.println(
                        "Displaying all cities in the world. " +
                                "Population sorted, largest to smallest: "
                );
                break;
            case CONTINENT:
                System.out.printf(
                        "Displaying all cities in continent - %s. " +
                                "Population sorted, largest to smallest: \n"
                        , name
                );
                if (!dbContinents.contains(name)) {
                    System.out.printf("Continent '%s' not found. Report can not be generated.\n", name);
                    return null;
                } else {
                    condition = String.format("WHERE Continent = '%s'", name);
                    break;
                }
            case REGION:
                System.out.printf(
                        "Displaying all cities in region - %s. " +
                                "Population sorted, largest to smallest: \n"
                        , name
                );
                if (!dbRegions.contains(name)) {
                    System.out.printf("Region '%s' not found. Report can not be generated.\n", name);
                    return null;
                } else {
                    condition = String.format("WHERE Region = '%s'", name);
                    break;
                }
            case COUNTRY:
                System.out.printf(
                        "Displaying all cities in country - %s. " +
                                "Population sorted, largest to smallest: \n"
                        , name
                );
                if (!dbCountries.contains(name)) {
                    System.out.printf("Country '%s' not found. Report can not be generated.\n", name);
                    return null;
                } else {
                    condition = String.format("WHERE country.Name = '%s'", name);
                    break;
                }
            case DISTRICT:
                // Ensure country is specified
                if (countryName == null || countryName.isEmpty()) {
                    System.out.println("District scope requires country be specified. Report can not be generated.");
                    return null;
                }
                countryName = countryName.toUpperCase();
                System.out.printf(
                        "Displaying all cities in district - %s, %s. " +
                                "Population sorted, largest to smallest: \n",
                        name,
                        countryName
                );
                if (!dbDistricts.contains(name)) {
                    System.out.printf("District '%s' not found. Report can not be generated.\n", name);
                    return null;
                } else if (!dbCountries.contains(countryName)) {
                    System.out.printf("Country '%s' not found. Report can not be generated.\n", countryName);
                    return null;
                } else {
                    condition = String.format("WHERE District = '%s' AND country.Name = '%s'", name, countryName);
                    break;
                }
        }

        String query = """
                SELECT city.Name, country.Name, city.District, city.Population
                FROM city
                LEFT JOIN country on city.CountryCode = country.Code
                %s
                ORDER BY city.Population DESC
                """.formatted(condition);
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                City city = new City(
                        resultSet.getString("city.Name"),
                        resultSet.getString("country.Name"),
                        resultSet.getString("city.District"),
                        resultSet.getLong("city.Population")
                );
                cities.add(city);
            }
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + sqle.getMessage());
            return null;
        }
        return cities;
    }

    // ISSUE 4
    /**
     * Get top N cities in the world.
     * No scope specified defaults to world.
     */
    public ArrayList<City> generateTopNCityReport(int n) {
        return generateTopNCityReport(Scope.WORLD, "", n, null);
    }

    /**
     * Get top N cities in the world.
     * @param scope should be used with CONTINENT, REGION, or COUNTRY scopes.
     */
    public ArrayList<City> generateTopNCityReport(Scope scope, String name, int n) {
        return generateTopNCityReport(scope, name, n, null);
    }

    /**
     * Get top N cities in the specified scope name ordered in descending population order.
     *
     * @param scope The scope level being specified (WORLD, CONTINENT, REGION, COUNTRY, DISTRICT).
     * @param name The specific name of the continent or region. Use empty string if using WORLD scope.
     * @param n The number of cities to display.
     * @param countryName When using DISTRICT scope, the country must be specified, as many districts names exist in
     *                    several countries.
     */
    public ArrayList<City> generateTopNCityReport(Scope scope, String name, int n, String countryName) {
        if (n <= 0) {
            System.out.println("N must be greater than 0. Report can not be generated.");
            return null;
        }

        ArrayList<City> cities = new ArrayList<>();

        name = name.toUpperCase();
        String condition = "";
        switch (scope) {
            case WORLD:
                System.out.printf("Displaying top %d cities in the world: \n", n);
                break;
            case CONTINENT:
                System.out.printf("Displaying top %d cities in continent - %s: \n", n, name);
                if (!dbContinents.contains(name)) {
                    System.out.printf("Continent '%s' not found. Report can not be generated.\n", name);
                    return null;
                } else {
                    condition = String.format("WHERE Continent = '%s'", name);
                    break;
                }
            case REGION:
                System.out.printf("Displaying top %d cities in region - %s: \n", n, name);
                if (!dbRegions.contains(name)) {
                    System.out.printf("Region '%s' not found. Report can not be generated.\n", name);
                    return null;
                } else {
                    condition = String.format("WHERE Region = '%s'", name);
                    break;
                }
            case COUNTRY:
                System.out.printf("Displaying top %d cities in country - %s: \n", n, name);
                if (!dbCountries.contains(name)) {
                    System.out.printf("Country '%s' not found. Report can not be generated.\n", name);
                    return null;
                } else {
                    condition = String.format("WHERE country.Name = '%s'", name);
                    break;
                }
            case DISTRICT:
                // Ensure country is specified
                if (countryName == null || countryName.isEmpty()) {
                    System.out.println("District scope requires country be specified. Report can not be generated.");
                    return null;
                }
                countryName = countryName.toUpperCase();
                System.out.printf("Displaying top %d cities in district - %s, %s: \n", n, name, countryName);
                if (!dbDistricts.contains(name)) {
                    System.out.printf("District '%s' not found. Report can not be generated.\n", name);
                    return null;
                } else if (!dbCountries.contains(countryName)) {
                    System.out.printf("Country '%s' not found. Report can not be generated.\n", countryName);
                    return null;
                } else {
                    condition = String.format("WHERE District = '%s' AND country.Name = '%s'", name, countryName);
                    break;
                }
        }

        String query = """
                SELECT city.Name, country.Name, city.District, city.Population
                FROM city
                LEFT JOIN country on city.CountryCode = country.Code
                %s
                ORDER BY city.Population DESC LIMIT %d
                """.formatted(condition, n);
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                City city = new City(
                        resultSet.getString("city.Name"),
                        resultSet.getString("country.Name"),
                        resultSet.getString("city.District"),
                        resultSet.getLong("city.Population")
                );
                cities.add(city);
            }
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + sqle.getMessage());
            return null;
        }
        return cities;
    }
}