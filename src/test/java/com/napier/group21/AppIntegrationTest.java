package com.napier.group21;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppIntegrationTest {

    static DatabaseConnection databaseConnection;
    static Connection connection;
    static ReportGenerator reportGenerator;

    @BeforeAll
    static void init() {
        databaseConnection = new DatabaseConnection(new String[]{"db:3306"});
        connection = databaseConnection.getConnection();
        reportGenerator = new ReportGenerator();
    }

    @Test
    void testGenerateSortedCountryReport_GlobalScope() {
        List<Country> countries;
        countries = reportGenerator.generateSortedCountryReport();
        int expectedRows = 239;
        assertEquals(expectedRows, countries.size(), "There should be 239 countries in the report");
    }

    @Test
    void testGenerateSortedCountryReport_ContinentScope() {
        List<Country> countries;
        countries = reportGenerator.generateSortedCountryReport(Scope.CONTINENT, "Africa");
        int expectedRows = 58;
        assertEquals(expectedRows, countries.size(), "There should be 58 countries in the report");
    }

    @Test
    void testGenerateSortedCountryReport_RegionScope() {
        List<Country> countries;
        countries = reportGenerator.generateSortedCountryReport(Scope.REGION, "Caribbean");
        int expectedRows = 24;
        assertEquals(expectedRows, countries.size(), "There should be 24 countries in the report");
    }

}
