package com.napier.group21;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest {

    static DatabaseConnection databaseConnection;
    static Connection connection;
    static ReportGenerator reportGenerator;

    @BeforeAll
    static void init() {
        databaseConnection = new DatabaseConnection(new String[]{"db:3306"});
        connection = databaseConnection.getConnection();
        reportGenerator = new ReportGenerator();
        try {
            reportGenerator.fetchScopeNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Sorted Country Reports
    @Test
    void testGenerateSortedCountryReport_GlobalScope() {
        List<Country> countries;
        countries = reportGenerator.generateSortedCountryReport();
        int expectedRows = 239;

        // Test Report Success
        assertNotNull(countries,
                "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, countries.size(),
                "There should be %d countries in the report".formatted(expectedRows));

        // Test Sorting
        for (int i = 0; i < countries.size(); i++) {
            if (i + 1 == countries.size()) break;
            assertTrue(countries.get(i).population() >= countries.get(i+1).population(),
                    "Report should be sorted in descending population order");
        }
    }

    @Test
    void testGenerateSortedCountryReport_ContinentScope() {
        List<Country> countries;
        countries = reportGenerator.generateSortedCountryReport(Scope.CONTINENT, "Africa");
        int expectedRows = 58;

        // Test Report Success
        assertNotNull(countries,
                "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, countries.size(),
                "There should be %d countries in the report".formatted(expectedRows));

        // Test Sorting
        for (int i = 0; i < countries.size(); i++) {
            if (i + 1 == countries.size()) break;
            assertTrue(countries.get(i).population() >= countries.get(i+1).population(),
                    "Report should be sorted in descending population order");
        }
    }

    @Test
    void testGenerateSortedCountryReport_RegionScope() {
        List<Country> countries;
        countries = reportGenerator.generateSortedCountryReport(Scope.REGION, "Caribbean");
        int expectedRows = 24;

        // Test Report Success
        assertNotNull(countries,
                "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, countries.size(),
                "There should be %d countries in the report".formatted(expectedRows));

        // Test Sorting
        for (int i = 0; i < countries.size(); i++) {
            if (i + 1 == countries.size()) break;
            assertTrue(countries.get(i).population() >= countries.get(i+1).population(),
                    "Report should be sorted in descending population order");
        }
    }

    // Top N Country Reports
    @Test
    void testGenerateTopNCountryReport_GlobalScope() {
        List<Country> countries;
        countries = reportGenerator.generateSortedCountryReport();
        int expectedRows = 239;

        // Test Report Success
        assertNotNull(countries, "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, countries.size(),
                "There should be %d countries in the report".formatted(expectedRows));
    }

    @Test
    void testGenerateTopNCountryReport_ContinentScope() {
        List<Country> countries;
        countries = reportGenerator.generateSortedCountryReport(Scope.CONTINENT, "Africa");
        int expectedRows = 58;

        // Test Report Success
        assertNotNull(countries, "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, countries.size(),
                "There should be %d countries in the report".formatted(expectedRows));
    }

    @Test
    void testGenerateTopNCountryReport_RegionScope() {
        List<Country> countries;
        countries = reportGenerator.generateSortedCountryReport(Scope.REGION, "Caribbean");
        int expectedRows = 24;

        // Test Report Success
        assertNotNull(countries, "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, countries.size(),
                "There should be %d countries in the report".formatted(expectedRows));
    }
}
