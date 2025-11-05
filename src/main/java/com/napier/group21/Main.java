package com.napier.group21;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<String> continents = new ArrayList<String>();
    public static ArrayList<String> regions = new ArrayList<String>();

    public static Connection conn = null;

    // Connects to 'world' database on the MySQL server container
    static final String url = "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false";
    static final String user = "root";
    static final String password = "group21";

    static Scanner scanner = new Scanner(System.in);

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

    public static void main(String[] args) {
        connect();

        getContinents();
        getRegions();

        if (conn != null) {
            generateSortedCountryReport();
        }

        disconnect();

//        if (conn != null) {
//            try {
//                // Simple, unprepared query. Simply selects all cities from the database and prints them
//                Statement statement = conn.createStatement();
//                ResultSet resultSet = statement.executeQuery("SELECT * FROM city");
//
//                // Iterate through the results returned frorm the database
//                while (resultSet.next()) {
//                    String name = resultSet.getString("Name");
//                    String countryCode = resultSet.getString("CountryCode");
//                    String district = resultSet.getString("District");
//                    int population = resultSet.getInt("Population");
//                    // Print simple report of every city
//                    System.out.println(name + " " + countryCode + " " + district + " " + population);
//                }
//            } catch (SQLException sqle) {
//                System.out.println("Failed to execute statement " + sqle.getMessage());
//            }
//        }
    }

    public static void generateSortedCountryReport() {
        System.out.println("Choose your scope: \n1). World \n2). Continent \n3). Region");
        int scope;
        while (true) {
            scope =  scanner.nextInt();
            if (scope > 3 || scope < 1) {
                System.out.println("Invalid input. Please choose a scope between 1 and 3");
                continue;
            }
            break;
        }

        int continentScope;
        int regionScope;
        String condition = "";
        switch(scope) {
            case 1:
                System.out.println("World");
            case 2:
                System.out.println("Choose your continent: ");
                while(true) {
                    continentScope = scanner.nextInt();
                    if (continentScope > continents.size() || continentScope < 1) {
                        System.out.println("Invalid input. Please choose one of the provided options.");
                    } else {
                        condition = "WHERE Continent = " + continents.get(continentScope-1);
                        break;
                    }
                }
                break;
            case 3:
                System.out.println("Choose your region: ");
                while(true) {
                    regionScope = scanner.nextInt();
                    if (regionScope > regions.size() || regionScope < 1) {
                        System.out.println("Invalid input. Please choose one of the provided options.");
                    } else {
                        condition = "WHERE Region = " + regions.get(regionScope-1);
                        break;
                    }
                }
                break;
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

            while (resultSet.next()) {
                String code = resultSet.getString("country.Code");
                String name = resultSet.getString("country.Name");
                String continent = resultSet.getString("country.Continent");
                String region = resultSet.getString("country.Region");
                long population = resultSet.getLong("country.Population");
                String capital = resultSet.getString("capital.name");

                String result = String.format(
                        "%s (%s), %s, %s \nPopulation: %,d \nCapital: %s \n",
                        name, code, continent, region, population, capital
                );
                System.out.println(result);
            }
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + sqle.getMessage());
        }
    }

    public static void getContinents() {
        String query = "SELECT DISTINCT Continent FROM country";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String continent = resultSet.getString("Continent");
                continents.add(continent);
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
                regions.add(region);
            }
        } catch (SQLException sqle) {
            System.out.println("Failed to execute statement: " + sqle.getMessage());
        }
    }
}
