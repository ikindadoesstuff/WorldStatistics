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
        // Establish Database Connection
        databaseConnection = new DatabaseConnection();
        databaseConnection.connect(10);
        connection = databaseConnection.getConnection();

        // Setup ReportGenerator
        reportGenerator = new ReportGenerator();
        reportGenerator.setConnection(connection);
        try {
            reportGenerator.fetchScopeNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testMain() {
        String[] args = new String[]{"localhost:33060"};
        assertDoesNotThrow(()-> App.main(args),
                "Normal, successful application flow should now throw an exception"
        );
    }

    // Sorted Country Reports
    @Test
    void testGenerateSortedCountryReport_GlobalScope() {
        int expectedRows = 239;
        List<Country> countries;
        countries = reportGenerator.generateSortedCountryReport();

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
        int expectedRows = 58;
        List<Country> countries;
        countries = reportGenerator.generateSortedCountryReport(Scope.CONTINENT, "Africa");

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
        int expectedRows = 24;
        List<Country> countries;
        countries = reportGenerator.generateSortedCountryReport(Scope.REGION, "Caribbean");

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
        int expectedRows = 10;
        List<Country> countries;
        countries = reportGenerator.generateTopNCountryReport(expectedRows);

        // Test Report Success
        assertNotNull(countries, "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, countries.size(),
                "There should be %d countries in the report".formatted(expectedRows));
    }

    @Test
    void testGenerateTopNCountryReport_ContinentScope() {
        int expectedRows = 5;
        List<Country> countries;
        countries = reportGenerator.generateTopNCountryReport(Scope.CONTINENT, "North America", expectedRows);

        // Test Report Success
        assertNotNull(countries, "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, countries.size(),
                "There should be %d countries in the report".formatted(expectedRows));
    }

    @Test
    void testGenerateTopNCountryReport_RegionScope() {
        int expectedRows = 3;
        List<Country> countries;
        countries = reportGenerator.generateTopNCountryReport(Scope.REGION, "Micronesia",  expectedRows);

        // Test Report Success
        assertNotNull(countries, "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, countries.size(),
                "There should be %d countries in the report".formatted(expectedRows));
    }

    // Sorted Country Reports
    @Test
    void testGenerateSortedCityReport_GlobalScope() {
        int expectedRows = 4079;
        List<City> cities;
        cities = reportGenerator.generateSortedCityReport();

        // Test Report Success
        assertNotNull(cities,
                "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, cities.size(),
                "There should be %d cities in the report".formatted(expectedRows));

        // Test Sorting
        for (int i = 0; i < cities.size(); i++) {
            if (i + 1 == cities.size()) break;
            assertTrue(cities.get(i).population() >= cities.get(i+1).population(),
                    "Report should be sorted in descending population order");
        }
    }

    @Test
    void testGenerateSortedCityReport_ContinentScope() {
        int expectedRows = 841;
        List<City> cities;
        cities = reportGenerator.generateSortedCityReport(Scope.CONTINENT, "Europe");

        // Test Report Success
        assertNotNull(cities,
                "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, cities.size(),
                "There should be %d cities in the report".formatted(expectedRows));

        // Test Sorting
        for (int i = 0; i < cities.size(); i++) {
            if (i + 1 == cities.size()) break;
            assertTrue(cities.get(i).population() >= cities.get(i+1).population(),
                    "Report should be sorted in descending population order");
        }
    }

    @Test
    void testGenerateSortedCityReport_RegionScope() {
        int expectedRows = 156;
        List<City> cities;
        cities = reportGenerator.generateSortedCityReport(Scope.REGION, "Southern Europe");

        // Test Report Success
        assertNotNull(cities,
                "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, cities.size(),
                "There should be %d cities in the report".formatted(expectedRows));

        // Test Sorting
        for (int i = 0; i < cities.size(); i++) {
            if (i + 1 == cities.size()) break;
            assertTrue(cities.get(i).population() >= cities.get(i+1).population(),
                    "Report should be sorted in descending population order");
        }
    }

    @Test
    void testGenerateSortedCityReport_CountryScope() {
        int expectedRows = 1;
        List<City> cities;
        cities = reportGenerator.generateSortedCityReport(Scope.COUNTRY, "Saint Lucia");

        // Test Report Success
        assertNotNull(cities,
                "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, cities.size(),
                "There should be %d cities in the report".formatted(expectedRows));

        // Test Sorting
        for (int i = 0; i < cities.size(); i++) {
            if (i + 1 == cities.size()) break;
            assertTrue(cities.get(i).population() >= cities.get(i+1).population(),
                    "Report should be sorted in descending population order");
        }
    }

    // Top N Country Reports
    @Test
    void testGenerateTopNCityReport_GlobalScope() {
        int expectedRows = 10;
        List<City> cities;
        cities = reportGenerator.generateTopNCityReport(expectedRows);

        // Test Report Success
        assertNotNull(cities, "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(expectedRows, cities.size(),
                "There should be %d cities in the report".formatted(expectedRows));
    }
}
