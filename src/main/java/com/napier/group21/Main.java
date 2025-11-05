package com.napier.group21;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<String> continents = new ArrayList<>();
    public static ArrayList<String> regions = new ArrayList<>();

    public enum Scope {
        WORLD,
        CONTINENT,
        REGION
    }

    public static Connection conn = null;

    // Connects to 'world' database on the MySQL server container
    static final String url = "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false";
    static final String user = "root";
    static final String password = "group21";

    public static void main(String[] args) {
        connect();

        getContinents();
        getRegions();

        if (conn != null) {
            generateSortedCountryReport();
            generateSortedCountryReport(Scope.CONTINENT, "Africa");
            generateSortedCountryReport(Scope.REGION, "Caribbean");
        }

        disconnect();
    }

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load SQL driver: "+e.getMessage());
            System.exit(1);
        }


        int retries = 30;
        int retryWaitTime = 10000;
        for (int i = 0; i < retries; i++) {
            System.out.println("Connecting to database... (Attempt #" + (i+1) + "/" + retries + ")");
            try {
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to database successfully");
                break;

            } catch (SQLException sqle) {

                try {// Wait for database on fail
                    Thread.sleep(retryWaitTime);
                } catch (InterruptedException ie) {
                    System.out.println(ie.getMessage());
                }
                System.out.println("Failed to connect to database: " + sqle.getMessage());
            }
        }
    }

    public static void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Failed to properly close database connection: " + e.getMessage());
            }
        }
    }

    public static void generateSortedCountryReport() {
        generateSortedCountryReport(Scope.WORLD, "");
    }

    public static void generateSortedCountryReport(Scope scope, String name) {
        name = name.toUpperCase();
        String condition = "";
        switch(scope) {
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
//                        "%s (%s), %s, %s \nPopulation: %,d \nCapital: %s \n",
                        "> %-45s %s | %-34s | Population: %,13d | Capital: %s ",
                        cname, code, (continent + ", " + region), population, capital
                );
                System.out.println(result);
            }
            System.out.println("\n");
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + sqle.getMessage());
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }
    }

    public static void getContinents() {
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

    public static void getRegions() {
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
}
