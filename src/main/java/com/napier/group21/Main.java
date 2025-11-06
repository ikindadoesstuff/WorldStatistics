package com.napier.group21;

public class Main {
    public static void main(String[] args) {
        ReportGenerator reportGenerator = new ReportGenerator();

        // REPORT FUNCTIONS HERE
        if (reportGenerator.getConnection() != null) {
            // All the countries in the world/continent/region in descending population order
            reportGenerator.generateSortedCountryReport(); // No Scope == World
            reportGenerator.generateSortedCountryReport(ReportGenerator.Scope.CONTINENT, "Africa");
            reportGenerator.generateSortedCountryReport(ReportGenerator.Scope.REGION, "Caribbean");
        }

        // CLOSE CONNECTION TO DATABASE
        reportGenerator.disconnect();
    }
}
