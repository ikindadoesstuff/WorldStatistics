package com.napier.group21;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver "+e.getMessage());
            System.exit(1);
        }

        // Connects to 'world' database on the MySQL server container
        String url = "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false";

        Connection conn = null;
        int retries = 10;
        for (int i = 0; i < retries; i++) {
            try {
                conn = DriverManager.getConnection(url, "root", "group21");
                System.out.println("Connected to database successfully");
                break;

            } catch (SQLException sqle) {

                try {// Wait for database on fail
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    System.out.println("Interrupted");
                }
                System.out.println("Failed to connect to database " + sqle.getMessage());
            }
        }

        if (conn != null) {
            try {
                // Simple, unprepared query. Simply selects all cities from the database and prints them
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM city");

                // Iterate through the results returned frorm the database
                while (resultSet.next()) {
                    String name = resultSet.getString("Name");
                    String countryCode = resultSet.getString("CountryCode");
                    String district = resultSet.getString("District");
                    int population = resultSet.getInt("Population");
                    // Print simple report of every city
                    System.out.println(name + " " + countryCode + " " + district + " " + population);
                }
            } catch (SQLException sqle) {
                System.out.println("Failed to execute statement " + sqle.getMessage());
            }
        }



    }
}
