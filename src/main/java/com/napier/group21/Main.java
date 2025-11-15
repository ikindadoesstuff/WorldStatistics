package com.napier.group21;

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
        ReportGenerator reportGenerator = new ReportGenerator();

        // REPORT FUNCTIONS HERE
        if (reportGenerator.getConnection() != null) {
            // 1 All the countries in the world/continent/region in descending population order
            reportGenerator.generateSortedCountryReport(); // No Scope == World
            reportGenerator.generateSortedCountryReport(Scope.CONTINENT, "Africa");
            reportGenerator.generateSortedCountryReport(Scope.REGION, "Caribbean");

            // 2 Top N Countries in the World/Continent/Region
        }

        /*
         * Close connection to the database after running all reports, since the reportGenerator does not know when
         * all the reports have been run.
         */
        reportGenerator.disconnect();
    }
}
