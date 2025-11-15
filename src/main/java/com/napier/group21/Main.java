package com.napier.group21;

import java.sql.Connection;

/**
 * Initialize ReportGenerator and call each report requested by the organization.
 *
 * @author Devyon Lozano
 * @author Alyssa Bowman
 * @author Dexter Joseph
 */
public class Main {
    /**
     * Entry point
     * @param args command line args
     */
    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection(args);
        databaseConnection.connect();
        Connection connection = databaseConnection.getConnection();

        if (connection == null) {
            System.out.println("Connection Failed! Exiting application.");
            return;
        }

        ReportGenerator reportGenerator = new ReportGenerator(connection);

        // REPORT FUNCTIONS HERE
        // 1 All the countries in the world/continent/region in descending population order
        reportGenerator.generateSortedCountryReport(); // No Scope == World
        reportGenerator.generateSortedCountryReport(Scope.CONTINENT, "Africa");
        reportGenerator.generateSortedCountryReport(Scope.REGION, "Caribbean");

        // 2 Top N Countries in the World/Continent/Region
        reportGenerator.generateTopNCountryReport(10);
        reportGenerator.generateTopNCountryReport(Scope.CONTINENT, "North America", 5);
        reportGenerator.generateTopNCountryReport(Scope.REGION, "Micronesia", 3);

        /*
         * Close connection to the database after running all reports, since the reportGenerator does not know when
         * all the reports have been run.
         */
        databaseConnection.disconnect();
    }


}
