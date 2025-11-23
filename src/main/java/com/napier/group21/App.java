package com.napier.group21;

import java.sql.Connection;
import java.sql.SQLException;

import static com.napier.group21.ReportGenerator.printReport;

/**
 * Initialize ReportGenerator and call each report requested by the organization.
 *
 * @author Devyon Lozano
 * @author Alyssa Bowman
 * @author Dexter Joseph
 */
public class App {
    /**
     * Entry point
     * @param args command line args
     */
    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection(args);
        databaseConnection.connect(10);
        Connection connection = databaseConnection.getConnection();

        if (connection == null) {
            System.out.println("Connection Failed! Exiting application.");
            return;
        }

        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.setConnection(connection);
        try {
            reportGenerator.fetchScopeNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // REPORT FUNCTIONS HERE
        // 1 All the countries in the world/continent/region in descending population order
        printReport(reportGenerator.generateSortedCountryReport()); // No Scope == World
        printReport(reportGenerator.generateSortedCountryReport(Scope.CONTINENT, "Africa"));
        printReport(reportGenerator.generateSortedCountryReport(Scope.REGION, "Caribbean"));

        // 2 Top N Countries in the World/Continent/Region
        printReport(reportGenerator.generateTopNCountryReport(10));
        printReport(reportGenerator.generateTopNCountryReport(Scope.CONTINENT, "North America", -5));
        printReport(reportGenerator.generateTopNCountryReport(Scope.REGION, "Micronesia", 3));

        // 3 All the cities in the world/continent/region/country/district in descending population order
        printReport(reportGenerator.generateSortedCityReport());
        printReport(reportGenerator.generateSortedCityReport(Scope.CONTINENT, "Europe"));
        printReport(reportGenerator.generateSortedCityReport(Scope.REGION, "Southern Europe"));
        printReport(reportGenerator.generateSortedCityReport(Scope.COUNTRY, "Saint Lucia"));
        printReport(reportGenerator.generateSortedCityReport(Scope.DISTRICT, "Texas", "United States"));

        // 4 All the cities in the world/continent/region/country/district in descending population order
        printReport(reportGenerator.generateTopNCityReport(5));
        printReport(reportGenerator.generateTopNCityReport(Scope.CONTINENT, "Oceania", 10));
        printReport(reportGenerator.generateTopNCityReport(Scope.REGION, "Eastern Europe", 5));
        printReport(reportGenerator.generateTopNCityReport(Scope.COUNTRY, "France", 3));
        printReport(reportGenerator.generateTopNCityReport(Scope.DISTRICT, "England", 5, "United Kingdom"));

        /*
         * Close connection to the database after running all reports, since the reportGenerator does not know when
         * all the reports have been run.
         */
        databaseConnection.disconnect();
    }


}
