package com.napier.group21;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides methods to generate different types of reports.
 */
public class ReportGenerator {
    /**
     * List of continents to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable but cannot be reassigned
     */
    private final List<String> dbContinents = new ArrayList<>();

    /**
     * List of regions to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable but cannot be reassigned.
     */
    private final List<String> dbRegions = new ArrayList<>();

    /**
     * List of countries to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable but cannot be reassigned.
     */
    private final List<String> dbCountries = new ArrayList<>();

    /**
     * List of districts to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable but cannot be reassigned.
     */
    private final List<String> dbDistricts = new ArrayList<>();

    /**
     * List of cities to verify that the user's choice exists.
     * This is final, as the ArrayList itself remains mutable but cannot be reassigned.
     */
    private final List<String> dbCities = new ArrayList<>();

    /**
     * Connection object which is retrieved from the DatabaseConnection object.
     */
    private Connection conn;

    /**
     * Initializes database connection
     */
    public ReportGenerator() {
        // We want nothing to occur here, as we will set the connection and fetch scope names manually
    }

    /**
     * Set connection before running database queries.
     *
     * @param connection connection object returned from DatabaseConnection instance.
     */
    public void setConnection(Connection connection) {
        if (connection == null) {
            System.out.println("Cannot set null connection\n");
            return;
        }
        this.conn = connection;
    }

    /**
     * Print all the rows in the report
     *
     * @param rows {@literal <? extends DatabaseObjects>} allows any object which implements DatabaseObject to be passed
     *             to this method. All our DTOs are Java records, meaning they can't share a common parent. This makes
     *             implementing the same interface the best way to allow for this.
     */
    public static void printReport(List<? extends DatabaseObject> rows) {
        if (rows == null || rows.isEmpty()) {
            System.out.println("No records found\n");
            return;
        }

        // Delay between report title and table
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException ie) {
//            System.out.println(ie.getMessage() + "\n");
//        }

        try {
            System.out.println(rows.size() + " records found");
            System.out.println(rows.get(0).getColumnString());
            for (DatabaseObject row : rows) {
                System.out.println(row.toString());
            }
        } catch (NullPointerException npe) {
            System.out.println("Null element in ArrayList passed to printReport. Cannot print report: "
                    + npe.getMessage() + "\n");
            return;
        }

        // Spacer for next report.
        System.out.println("\n");
    }

    /**
     * Get scope names with which to validate against the user's specific scope name.
     */
    public void fetchScopeNames() throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Cannot fetch scope names without open connection\n");
        }

        fetchContinents();
        fetchRegions();
        fetchCountries();
        fetchDistricts();
        fetchCities();
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
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String continent = resultSet.getString("Continent");
                dbContinents.add(continent.toUpperCase());
            }

            if (dbContinents.isEmpty()) {
                System.out.println("No continents found\n");
            }
        } catch (SQLException sqle) {
            throw new RuntimeException("Failed to execute statement: " + query + "\n", sqle);
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
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String region = resultSet.getString("Region");
                dbRegions.add(region.toUpperCase());
            }

            if (dbContinents.isEmpty()) {
                System.out.println("No continents found\n");
            }
        } catch (SQLException sqle) {
            throw new RuntimeException("Failed to execute statement: " + query + "\n", sqle);
        }
    }

    /**
     * Fetch all countries for verification.
     */
    public void fetchCountries() {
        String query = "SELECT DISTINCT Name as Country FROM country";
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String region = resultSet.getString("Country");
                dbCountries.add(region.toUpperCase());
            }

            if (dbContinents.isEmpty()) {
                System.out.println("No continents found\n");
            }
        } catch (SQLException sqle) {
            throw new RuntimeException("Failed to execute statement: " + query + "\n", sqle);
        }
    }

    /**
     * Fetch all districts for verification.
     */
    public void fetchDistricts() {
        String query = "SELECT DISTINCT District FROM city";
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String region = resultSet.getString("District");
                dbDistricts.add(region.toUpperCase());
            }

            if (dbContinents.isEmpty()) {
                System.out.println("No continents found\n");
            }
        } catch (SQLException sqle) {
            throw new RuntimeException("Failed to execute statement: " + query + "\n", sqle);
        }
    }

    /**
     * Fetch all cities for verification.
     */
    public void fetchCities() {
        String query = "SELECT DISTINCT name as City FROM city";
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String region = resultSet.getString("City");
                dbCities.add(region.toUpperCase());
            }

            if (dbContinents.isEmpty()) {
                System.out.println("No continents found\n");
            }
        } catch (SQLException sqle) {
            throw new RuntimeException("Failed to execute statement: " + query + "\n", sqle);
        }
    }

    /**
     * Simple method to verify whether or not the specified scope name actually exists in the database
     *
     * @param scope     Level of scope being tested
     * @param scopeName Name of scope being tested
     * @return          Scope name validity
     */
    boolean verifyScopeName(Scope scope, String scopeName) {
        if (scope == null || scopeName == null) {
            return false;
        }
        scopeName = scopeName.toUpperCase();
        // this is a switch expression, rather than a statement.
        return switch (scope) {
            case WORLD -> true;
            case CONTINENT -> dbContinents.contains(scopeName.toUpperCase());
            case REGION -> dbRegions.contains(scopeName.toUpperCase());
            case COUNTRY -> dbCountries.contains(scopeName.toUpperCase());
            case DISTRICT -> dbDistricts.contains(scopeName.toUpperCase());
            case CITY -> dbCities.contains(scopeName.toUpperCase());
        };
    }

    // ISSUE 1

    /**
     * Get all countries in the world ordered in descending population order.
     * No scope specified defaults to world.
     */
    public List<Country> generateSortedCountryReport() {
        return generateSortedCountryReport(Scope.WORLD, "");
    }

    /**
     * Get all countries in the specified scope name ordered in descending population order.
     *
     * @param scope     The scope level being specified (WORLD, CONTINENT, REGION).
     * @param scopeName The specific name of the continent or region. Use empty string if using WORLD scope.
     */
    public List<Country> generateSortedCountryReport(Scope scope, String scopeName) {
        if (!verifyScopeName(scope, scopeName)) {
            System.out.printf("%s - %s not found. Report can not be generated.\n", scope, scopeName);
            return null;
        }

        List<Country> countries = new ArrayList<>();

        String condition = "";
        switch (scope) {
            case WORLD:
                System.out.println(
                        "Displaying all countries in the world: " +
                                "Population sorted, largest to smallest: "
                );
                break;
            case CONTINENT:
                System.out.printf(
                        "Displaying all countries in continent - %s: " +
                                "Population sorted, largest to smallest: \n"
                        , scopeName
                );
                condition = String.format("WHERE Continent = '%s'", scopeName);
                break;
            case REGION:
                System.out.printf(
                        "Displaying all countries in region - %s: " +
                                "Population sorted, largest to smallest: \n"
                        , scopeName
                );
                condition = String.format("WHERE Region = '%s'", scopeName);
                break;
            default:
                System.out.println("Invalid scope. Report can not be generated.\n");
                return null;

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
        } catch (SQLException | NullPointerException e) {
            System.out.println("Failed to execute statement: " + e.getMessage() + "\n");
            return null;
        }
        return countries;
    }

    // ISSUE 2

    /**
     * Get top N countries in the world.
     * No scope specified defaults to world.
     */
    public List<Country> generateTopNCountryReport(int n) {
        return generateTopNCountryReport(Scope.WORLD, "", n);
    }

    /**
     * Get top N countries in the specified scope name.
     *
     * @param scope     The scope level being specified (WORLD, CONTINENT, REGION).
     * @param scopeName The specific name of the continent or region. Use empty string if using WORLD scope.
     * @param n         The number of countries to display
     */
    public List<Country> generateTopNCountryReport(Scope scope, String scopeName, int n) {
        if (n <= 0) {
            System.out.println("N must be greater than 0. Report can not be generated.\n");
            return null;
        }

        if (!verifyScopeName(scope, scopeName)) {
            System.out.printf("%s - %s not found. Report can not be generated.\n", scope, scopeName);
            return null;
        }

        List<Country> countries = new ArrayList<>();

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
                        scopeName
                );
                condition = String.format("WHERE Continent = '%s'", scopeName);
                break;
            case REGION:
                System.out.printf(
                        "Displaying top %d countries in region - %s: \n",
                        n,
                        scopeName
                );
                condition = String.format("WHERE Region = '%s'", scopeName);
                break;
            default:
                System.out.println("Invalid scope. Report can not be generated.\n");
                return null;
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
        } catch (SQLException | NullPointerException e) {
            System.out.println("Failed to execute statement: " + e.getMessage() + "\n");
            return null;
        }
        return countries;
    }

    // ISSUE 3

    /**
     * Get all cities in the world ordered in descending population order.
     * No scope specified defaults to world.
     */
    public List<City> generateSortedCityReport() {
        return generateSortedCityReport(Scope.WORLD, "", null);
    }

    /**
     * Get all cities in the world ordered in descending population order.
     *
     * @param scope       The scope level being specified (WORLD, CONTINENT, REGION, COUNTRY, DISTRICT).
     */
    public List<City> generateSortedCityReport(Scope scope, String name) {
        return generateSortedCityReport(scope, name, null);
    }

    /**
     * Get all cities in the specified scope name ordered in descending population order.
     *
     * @param scope       The scope level being specified (WORLD, CONTINENT, REGION, COUNTRY, DISTRICT).
     * @param scopeName   The specific name of the continent, region, country, or district.
     *                    Use empty string if using WORLD scope.
     * @param countryName When using DISTRICT scope, the country must be specified, as many districts names exist in
     *                    several countries.
     */
    public List<City> generateSortedCityReport(Scope scope, String scopeName, String countryName) {
        if (!verifyScopeName(scope, scopeName)) {
            System.out.printf("%s - %s not found. Report can not be generated.\n", scope, scopeName);
            return null;
        }
        if (scope == Scope.DISTRICT && !verifyScopeName(Scope.COUNTRY, countryName)) {
            if (countryName.isEmpty()) {
                System.out.printf("%s scope requires country be specified. Report can not be generated.\n", scope);
                return null;
            }
            System.out.printf("%s - %s not found. Report can not be generated.\n", scope, countryName);
            return null;
        }

        List<City> cities = new ArrayList<>();

        String condition = "";
        switch (scope) {
            case WORLD:
                System.out.println(
                        "Displaying all cities in the world: " +
                                "Population sorted, largest to smallest: "
                );
                break;
            case CONTINENT:
                System.out.printf(
                        "Displaying all cities in %s - %s: " +
                                "Population sorted, largest to smallest: \n",
                        scope,
                        scopeName
                );
                condition = String.format("WHERE Continent = '%s'", scopeName);
                break;
            case REGION:
                System.out.printf(
                        "Displaying all cities in %s - %s: " +
                                "Population sorted, largest to smallest: \n",
                        scope,
                        scopeName
                );
                condition = String.format("WHERE Region = '%s'", scopeName);
                break;
            case COUNTRY:
                System.out.printf(
                        "Displaying all cities in %s - %s: " +
                                "Population sorted, largest to smallest: \n",
                        scope,
                        scopeName
                );
                condition = String.format("WHERE country.Name = '%s'", scopeName);
                break;
            case DISTRICT:
                System.out.printf(
                        "Displaying all cities in %s - %s, %s: " +
                                "Population sorted, largest to smallest: \n",
                        scope,
                        scopeName,
                        countryName
                );
                condition = String.format("WHERE District = '%s' AND country.Name = '%s'", scopeName, countryName);
                break;
            default:
                System.out.println("Invalid scope. Report can not be generated.\n");
                return null;
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
        } catch (SQLException | NullPointerException e) {
            System.out.println("Failed to execute statement: " + e.getMessage() + "\n");
            return null;
        }
        return cities;
    }

    // ISSUE 4

    /**
     * Get top N cities in the world.
     * No scope specified defaults to world.
     */
    public List<City> generateTopNCityReport(int n) {
        return generateTopNCityReport(Scope.WORLD, "", n, null);
    }

    /**
     * Get top N cities in the world.
     *
     * @param scope       The scope level being specified (WORLD, CONTINENT, REGION, COUNTRY, DISTRICT).
     */
    public List<City> generateTopNCityReport(Scope scope, String name, int n) {
        return generateTopNCityReport(scope, name, n, null);
    }

    /**
     * Get top N cities in the specified scope name ordered in descending population order.
     *
     * @param scope       The scope level being specified (WORLD, CONTINENT, REGION, COUNTRY, DISTRICT).
     * @param scopeName   The specific name of the continent, region, country, or district.
     *                    Use empty string if using WORLD scope.
     * @param n           The number of cities to display.
     * @param countryName When using DISTRICT scope, the country must be specified, as many districts names exist in
     *                    several countries.
     */
    public List<City> generateTopNCityReport(Scope scope, String scopeName, int n, String countryName) {
        if (n <= 0) {
            System.out.println("N must be greater than 0. Report can not be generated.\n");
            return null;
        }

        if (!verifyScopeName(scope, scopeName)) {
            System.out.printf("%s - %s not found. Report can not be generated.\n", scope, scopeName);
            return null;
        }

        if (scope == Scope.DISTRICT && !verifyScopeName(Scope.COUNTRY, countryName)) {
            if (countryName == null || countryName.isEmpty()) {
                System.out.println("District scope requires country be specified. Report can not be generated.\n");
                return null;
            }
            System.out.printf("%s - %s not found. Report can not be generated.\n", Scope.COUNTRY, countryName);
            return null;
        }

        List<City> cities = new ArrayList<>();

        String condition = "";
        switch (scope) {
            case WORLD:
                System.out.printf("Displaying top %d cities in the world: \n", n);
                break;
            case CONTINENT:
                System.out.printf("Displaying top %d cities in continent - %s: \n", n, scopeName);
                condition = String.format("WHERE Continent = '%s'", scopeName);
                break;
            case REGION:
                System.out.printf("Displaying top %d cities in region - %s: \n", n, scopeName);
                condition = String.format("WHERE Region = '%s'", scopeName);
                break;
            case COUNTRY:
                System.out.printf("Displaying top %d cities in country - %s: \n", n, scopeName);
                condition = String.format("WHERE country.Name = '%s'", scopeName);
                break;
            case DISTRICT:
                System.out.printf(
                        "Displaying top %d cities in district - %s, %s: \n",
                        n,
                        scopeName,
                        countryName);
                condition = String.format("WHERE District = '%s' AND country.Name = '%s'", scopeName, countryName);
                break;
            default:
                System.out.println("Invalid scope. Report can not be generated.\n");
                return null;
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
        } catch (SQLException | NullPointerException e) {
            System.out.println("Failed to execute statement: " + e.getMessage() + "\n");
            return null;
        }
        return cities;
    }

    // ISSUE 5

    /**
     * Get all capital cities in the world ordered in descending population order.
     * No scope specified defaults to world.
     */
    public List<Capital> generateSortedCapitalReport() {
        return generateSortedCapitalReport(Scope.WORLD, "");
    }

    /**
     * Get all capital cities in the specified scope name ordered in descending population order.
     *
     * @param scope     The scope level being specified (WORLD, CONTINENT, REGION).
     * @param scopeName The specific name of the continent or region. Use empty string if using WORLD scope.
     */
    public List<Capital> generateSortedCapitalReport(Scope scope, String scopeName) {
        if (!verifyScopeName(scope, scopeName)) {
            System.out.printf("%s - %s not found. Report can not be generated.\n", scope, scopeName);
            return null;
        }

        List<Capital> capitals = new ArrayList<>();

        String condition = "";
        switch (scope) {
            case WORLD:
                System.out.println(
                        "Displaying all capital cities in the world: " +
                                "Population sorted, largest to smallest: "
                );
                break;
            case CONTINENT:
                System.out.printf(
                        "Displaying all capital cities in continent - %s: " +
                                "Population sorted, largest to smallest: \n"
                        , scopeName
                );
                condition = String.format("AND Continent = '%s'", scopeName);
                break;
            case REGION:
                System.out.printf(
                        "Displaying all capital cities in region - %s: " +
                                "Population sorted, largest to smallest: \n"
                        , scopeName
                );
                condition = String.format("AND Region = '%s'", scopeName);
                break;
            default:
                System.out.println("Invalid scope. Report can not be generated.\n");
                return null;

        }

        String query = """
                SELECT city.Name, country.Name, city.Population
                FROM city
                LEFT JOIN country on city.CountryCode = country.Code
                WHERE city.ID = country.Capital
                %s
                ORDER BY city.Population DESC
                """.formatted(condition);
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Capital capital = new Capital(
                        resultSet.getString("city.Name"),
                        resultSet.getString("country.Name"),
                        resultSet.getLong("city.Population")
                );
                capitals.add(capital);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println("Failed to execute statement: " + e.getMessage() + "\n");
            return null;
        }
        return capitals;
    }

    // ISSUE 6

    /**
     * Get top N capitals in the world.
     * No scope specified defaults to world.
     */
    public List<Capital> generateTopNCapitalReport(int n) {
        return generateTopNCapitalReport(Scope.WORLD, "", n);
    }

    /**
     * Get top N capitals in the specified scope name.
     *
     * @param scope     The scope level being specified (WORLD, CONTINENT, REGION).
     * @param scopeName The specific name of the continent or region. Use empty string if using WORLD scope.
     * @param n         The number of countries to display
     */
    public List<Capital> generateTopNCapitalReport(Scope scope, String scopeName, int n) {
        if (n <= 0) {
            System.out.println("N must be greater than 0. Report can not be generated.\n");
            return null;
        }

        if (!verifyScopeName(scope, scopeName)) {
            System.out.printf("%s - %s not found. Report can not be generated.\n", scope, scopeName);
            return null;
        }

        List<Capital> capitals = new ArrayList<>();

        String condition = "";
        switch (scope) {
            case WORLD:
                System.out.printf(
                        "Displaying top %d capital cities in the world: \n",
                        n
                );
                break;
            case CONTINENT:
                System.out.printf(
                        "Displaying top %d capital cities in continent - %s: \n",
                        n,
                        scopeName
                );
                condition = String.format("AND Continent = '%s'", scopeName);
                break;
            case REGION:
                System.out.printf(
                        "Displaying top %d capital cities in region - %s: \n",
                        n,
                        scopeName
                );
                condition = String.format("AND Region = '%s'", scopeName);
                break;
            default:
                System.out.println("Invalid scope. Report can not be generated.\n");
                return null;
        }

        String query = """
                SELECT city.Name, country.Name, city.Population
                FROM city
                LEFT JOIN country on city.CountryCode = country.Code
                WHERE city.ID = country.Capital
                %s
                ORDER BY country.Population DESC LIMIT %d
                """.formatted(condition, n);
        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Capital capital = new Capital(
                        resultSet.getString("city.Name"),
                        resultSet.getString("country.Name"),
                        resultSet.getLong("city.Population")
                );
                capitals.add(capital);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println("Failed to execute statement: " + e.getMessage() + "\n");
            return null;
        }
        return capitals;
    }

    // ISSUE 7

    /**
     * Get the total population, population living in cities, and population not living in cities for each continent,
     * region, or country.
     *
     * @param scope The scope level being specified (CONTINENT, REGION, COUNTRY).
     */
    public List<Urbanization> generateUrbanizationReport(Scope scope) {
        List<Urbanization> capitals = new ArrayList<>();

        String query;
        switch (scope) {
            case CONTINENT:
                System.out.print("Displaying urbanization in continents: ");
                query = """
                        SELECT country.Continent as Name,
                               SUM(DISTINCT country.Population) AS TotalPopulation,
                               SUM(city.Population) AS CityPopulation,
                               (SUM(DISTINCT country.Population) - SUM(city.Population)) as NonCityPopulation
                        FROM country
                                 LEFT JOIN city on city.CountryCode = country.Code

                        GROUP BY country.Continent;
                        """;
                break;
            case REGION:
                System.out.print("Displaying urbanization in regions: ");
                query = """
                        SELECT country.Region as Name,
                               SUM(DISTINCT country.Population) AS TotalPopulation,
                               SUM(city.Population) AS CityPopulation,
                               (SUM(DISTINCT country.Population) - SUM(city.Population)) as NonCityPopulation
                        FROM country
                                 LEFT JOIN city on city.CountryCode = country.Code
                        GROUP BY country.Region;
                        """;
                break;
            case COUNTRY:
                System.out.print("Displaying urbanization in countries: ");
                query = """
                        SELECT country.Name as Name,
                               country.Population AS TotalPopulation,
                               SUM(city.Population) AS CityPopulation,
                               (country.Population - SUM(city.Population)) as NonCityPopulation
                        FROM country
                                 LEFT JOIN city on city.CountryCode = country.Code
                        GROUP BY country.Code;
                        """;
                break;
            default:
                System.out.println("Invalid scope. Report can not be generated.\n");
                return null;

        }

        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Urbanization urbanization = new Urbanization(
                        resultSet.getString("Name"),
                        resultSet.getLong("TotalPopulation"),
                        resultSet.getLong("CityPopulation"),
                        resultSet.getLong("NonCityPopulation")
                );
                capitals.add(urbanization);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println("Failed to execute statement: " + e.getMessage() + "\n");
            return null;
        }
        return capitals;
    }

    // ISSUE 8

    /**
     * Get the population of the World.
     */
    public List<Population> generatePopulationReport() {
        return generatePopulationReport(Scope.WORLD, "");
    }

    /**
     * Get all cities in the world ordered in descending population order.
     *
     * @param scope       The scope level being specified (WORLD, CONTINENT, REGION, COUNTRY, DISTRICT).
     */
    public List<Population> generatePopulationReport(Scope scope, String name) {
        return generatePopulationReport(scope, name, null);
    }

    /**
     * Get the population of the World/Continent/Region/Country/District/City
     *
     * @param scope The scope level being specified (WORLD, CONTINENT, REGION, COUNTRY, DISTRICT, CITY).
     */
    public List<Population> generatePopulationReport(Scope scope, String scopeName, String countryName) {
        // Validate scopeName where applicable (strict pattern)
        if (scope != Scope.WORLD && !verifyScopeName(scope, scopeName)) {
            System.out.printf("%s - %s not found. Report can not be generated.\n", scope, scopeName);
            return null;
        }

        if ((scope == Scope.DISTRICT || scope == Scope.CITY) && !verifyScopeName(Scope.COUNTRY, countryName)) {
            if (countryName == null || countryName.isEmpty()) {
                System.out.printf("%s scope requires country be specified. Report can not be generated.\n", scope);
                return null;
            }
            System.out.printf("%s - %s not found. Report can not be generated.\n", Scope.COUNTRY, countryName);
            return null;
        }

        Population population = null;

        String query;
        switch (scope) {
            case WORLD :
                System.out.print("Displaying World Population: ");
                query = """
                        SELECT 'World' as Name, SUM(country.population) AS Population
                        FROM country;
                        """;
                break;
            case CONTINENT:
                System.out.printf("Displaying Population of Continent - %s: \n", scopeName);
                query = """
                        SELECT country.Continent as Name, SUM(DISTINCT country.population) as Population
                        FROM city
                        LEFT JOIN country on city.CountryCode = country.Code
                        WHERE country.Continent = '%s';
                        """.formatted(scopeName);
                break;
            case REGION:
                System.out.printf("Displaying Population of Region - %s: \n", scopeName);
                query = """
                        SELECT country.Region as Name, SUM(DISTINCT country.population) as Population
                        FROM country
                        WHERE country.Region = '%s';
                        """.formatted(scopeName);
                break;
            case COUNTRY:
                System.out.printf("Displaying Population of Country - %s: \n", scopeName);
                query = """
                        SELECT country.Name as Name, SUM(DISTINCT country.population) as Population
                        FROM country
                        WHERE country.Name = '%s';
                        """.formatted(scopeName);
                break;
            case DISTRICT:
                System.out.printf("Displaying Population of District - %s: \n", scopeName);
                query = """
                        SELECT city.District as Name, SUM(city.population) as Population
                        FROM city
                        LEFT JOIN country on city.CountryCode = country.Code
                        WHERE city.District = '%s' AND country.Name = '%s';
                        """.formatted(scopeName, countryName);
                break;
            case CITY:
                System.out.printf("Displaying Population of City - %s: \n", scopeName);
                query = """
                        SELECT city.Name as Name, city.population as Population
                        FROM city
                        LEFT JOIN country on city.CountryCode = country.Code
                        WHERE city.Name = '%s' AND country.Name = '%s';
                        """.formatted(scopeName, countryName);
                break;
            default:
                System.out.println("Invalid scope. Report can not be generated.\n");
                return null;

        }

        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                population = new Population(
                        resultSet.getString("Name"),
                        resultSet.getLong("Population")
                );
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println("Failed to execute statement: " + e.getMessage() + "\n");
            return null;
        }
        // Apparently, Collections.singletonList() is more memory-efficient than Arrays.toList() for one item
        return new ArrayList<>(Collections.singletonList(population));
    }

    // ISSUE 9

    /**
     * Get report on speakers of Chinese, English, Hindi, Spanish, and Arabic worldwide
     */
    public List<Language> generateTop5LanguageReport() {
        List<Language> languages = new ArrayList<>();

        System.out.print("Displaying Top 5 Languages in the World: ");
        String query = """
                SELECT countrylanguage.Language as Language,
                       ROUND(SUM(countrylanguage.Percentage * country.Population / 100)) as TotalSpeakers,
                       SUM(countrylanguage.Percentage * country.Population / 100) / (SELECT SUM(country.Population) FROM country) * 100 WorldPercentage
                FROM countrylanguage
                         JOIN country on countrylanguage.CountryCode = country.Code
                
                WHERE countrylanguage.language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic')
                
                GROUP BY countrylanguage.Language
                
                ORDER BY TotalSpeakers DESC;
                """;

        /*
         * Try-with-resources ensures statement and resultSet are closed when done.
         */
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Language language = new Language(
                        resultSet.getString("Language"),
                        resultSet.getLong("TotalSpeakers"),
                        resultSet.getFloat("WorldPercentage")
                );
                languages.add(language);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println("Failed to execute statement: " + e.getMessage() + "\n");
            return null;
        }
        return languages;
    }
}