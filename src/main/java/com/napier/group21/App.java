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
        // 1 All the countries in the World/Continent/Region in descending population order
        printReport(reportGenerator.generateSortedCountryReport()); // No Scope == World
        printReport(reportGenerator.generateSortedCountryReport(Scope.CONTINENT, "Africa"));
        printReport(reportGenerator.generateSortedCountryReport(Scope.REGION, "Caribbean"));

        // 2 Top N Countries in the World/Continent/Region
        printReport(reportGenerator.generateTopNCountryReport(10));
        printReport(reportGenerator.generateTopNCountryReport(Scope.CONTINENT, "North America", 5));
        printReport(reportGenerator.generateTopNCountryReport(Scope.REGION, "Micronesia", 3));

        // 3 All the cities in the World/Continent/Region/Country/District in descending population order
        printReport(reportGenerator.generateSortedCityReport());
        printReport(reportGenerator.generateSortedCityReport(Scope.CONTINENT, "Europe"));
        printReport(reportGenerator.generateSortedCityReport(Scope.REGION, "Southern Europe"));
        printReport(reportGenerator.generateSortedCityReport(Scope.COUNTRY, "Saint Lucia"));
        printReport(reportGenerator.generateSortedCityReport(Scope.DISTRICT, "Texas", "United States"));

        // 4 Top N cities in the World/Continent/Region/Country/District
        printReport(reportGenerator.generateTopNCityReport(5));
        printReport(reportGenerator.generateTopNCityReport(Scope.CONTINENT, "Oceania", 10));
        printReport(reportGenerator.generateTopNCityReport(Scope.REGION, "Eastern Europe", 5));
        printReport(reportGenerator.generateTopNCityReport(Scope.COUNTRY, "France", 3));
        printReport(reportGenerator.generateTopNCityReport(Scope.DISTRICT, "England", 5, "United Kingdom"));

        // 5 All the capitals in the World/Continent/Region in descending population order
        printReport(reportGenerator.generateSortedCapitalReport());
        printReport(reportGenerator.generateSortedCapitalReport(Scope.CONTINENT, "Asia"));
        printReport(reportGenerator.generateSortedCapitalReport(Scope.REGION, "Central America"));

        // 6 Top N capitals in the World/Continent/Region
        printReport(reportGenerator.generateTopNCapitalReport(10));
        printReport(reportGenerator.generateTopNCapitalReport(Scope.CONTINENT, "South America", 5));
        printReport(reportGenerator.generateTopNCapitalReport(Scope.REGION, "Middle East", 3));

        // 7 Generate Urbanization report for each Continent/Region/Country
        printReport(reportGenerator.generateUrbanizationReport(Scope.CONTINENT));
        printReport(reportGenerator.generateUrbanizationReport(Scope.REGION));
        printReport(reportGenerator.generateUrbanizationReport(Scope.COUNTRY));

        // 8 Population of the World/Continent/Region/Country/District
        printReport(reportGenerator.generatePopulationReport());
        printReport(reportGenerator.generatePopulationReport(Scope.CONTINENT, "Africa"));
        printReport(reportGenerator.generatePopulationReport(Scope.REGION, "Nordic Countries"));
        printReport(reportGenerator.generatePopulationReport(Scope.COUNTRY, "India"));
        printReport(reportGenerator.generatePopulationReport(Scope.DISTRICT, "Georgia", "United States"));
        printReport(reportGenerator.generatePopulationReport(Scope.CITY, "Tokyo", "Japan"));

        // 9 Top 5 Languages in the World
        printReport(reportGenerator.generateTop5LanguageReport());

        /*
         * Close connection to the database after running all reports, since the reportGenerator does not know when
         * all the reports have been run.
         */
        databaseConnection.disconnect();
    }


}
