package com.napier.group21;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the app with a running database container to connect to
 */
public class AppIntegrationTest {

    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void init() {
        // Establish Database Connection
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.connect(10);
        Connection connection = databaseConnection.getConnection();

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

    /**
     * This method provides different arguments for the associated {@code  ParameterizedTest}.
     * It enables running the test multiple times and passing a different set of arguments each time,
     * removing the need to create many methods for each test.
     *
     * @return The arguments passed to the test
     */
    private static Stream<Arguments> testGenerateSortedCountryReportArgsProvider() {
        return Stream.of(
                //           Scope, Name, Expected Rows
                Arguments.of(Scope.WORLD, "", 239),
                Arguments.of(Scope.CONTINENT, "Africa", 58),
                Arguments.of(Scope.REGION, "Caribbean", 24)
        );
    }
    /**
     * Test generateSortedCountryReport() with its accepted scopes
     *
     * @param scope Current scope being tested
     * @param scopeName Specific name of being tested
     * @param expectedRows Number of rows expected given the current arguments
     */
    @ParameterizedTest
    @MethodSource("testGenerateSortedCountryReportArgsProvider")
    void testGenerateSortedCountryReport(Scope scope, String scopeName, int expectedRows) {
        List<Country> countries;
        countries = reportGenerator.generateSortedCountryReport(scope, scopeName);

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

    /**
     * This method provides different arguments for the associated {@code  ParameterizedTest}.
     * It enables running the test multiple times and passing a different set of arguments each time,
     * removing the need to create many methods for each test.
     *
     * @return The arguments passed to the test
     */
    private static Stream<Arguments> testGenerateTopNCountryReportArgsProvider() {
        return Stream.of(
                //           Scope, Name, N
                Arguments.of(Scope.WORLD, "", 10),
                Arguments.of(Scope.CONTINENT, "North America", 5),
                Arguments.of(Scope.REGION, "Micronesia", 3)
        );
    }
    /**
     * Test generateTopNCountryReport() with its accepted scopes
     *
     * @param scope Current scope being tested
     * @param scopeName Specific name of being tested
     * @param N Number of rows expected given the current arguments
     */
    @ParameterizedTest
    @MethodSource("testGenerateTopNCountryReportArgsProvider")
    void testGenerateTopNCountryReport(Scope scope, String scopeName, int N) {
        List<Country> countries;
        countries = reportGenerator.generateTopNCountryReport(scope, scopeName, N);

        // Test Report Success
        assertNotNull(countries,
                "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(N, countries.size(),
                "There should be %d countries in the report".formatted(N));
    }

    // Top N Country Reports

    /**
     * This method provides different arguments for the associated {@code  ParameterizedTest}.
     * It enables running the test multiple times and passing a different set of arguments each time,
     * removing the need to create many methods for each test.
     *
     * @return The arguments passed to the test
     */
    private static Stream<Arguments> testGenerateSortedCityReportArgsProvider() {
        return Stream.of(
                //           Scope, Name, Expected Rows, Country Name
                Arguments.of(Scope.WORLD, "", 4079, ""),
                Arguments.of(Scope.CONTINENT, "Europe", 841, ""),
                Arguments.of(Scope.REGION, "Southern Europe", 156, ""),
                Arguments.of(Scope.COUNTRY, "Saint Lucia", 1, ""),
                Arguments.of(Scope.DISTRICT, "Texas", 26, "United States")
        );
    }
    /**
     * Test generateSortedCityReport() with its accepted scopes
     *
     * @param scope Current scope being tested
     * @param scopeName Specific name of being tested
     * @param expectedRows Number of rows expected given the current arguments
     */
    @ParameterizedTest
    @MethodSource("testGenerateSortedCityReportArgsProvider")
    void testGenerateSortedCityReport(Scope scope, String scopeName, int expectedRows, String countryName) {
        List<City> cities;
        cities = reportGenerator.generateSortedCityReport(scope, scopeName, countryName);

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

    /**
     * This method provides different arguments for the associated {@code  ParameterizedTest}.
     * It enables running the test multiple times and passing a different set of arguments each time,
     * removing the need to create many methods for each test.
     *
     * @return The arguments passed to the test
     */
    private static Stream<Arguments> testGenerateTopNCityReportArgsProvider() {
        return Stream.of(
                //           Scope, Name, N , Country Name
                Arguments.of(Scope.WORLD, "", 5, ""),
                Arguments.of(Scope.CONTINENT, "Oceania", 10, ""),
                Arguments.of(Scope.REGION, "Eastern Europe", 5, ""),
                Arguments.of(Scope.COUNTRY, "France", 3, ""),
                Arguments.of(Scope.DISTRICT, "England", 5, "United Kingdom")
        );
    }
    /**
     * Test generateTopNCityReport() with its accepted scopes
     *
     * @param scope Current scope being tested
     * @param scopeName Specific name of being tested
     * @param N Number of rows expected given the current arguments
     */
    @ParameterizedTest
    @MethodSource("testGenerateTopNCityReportArgsProvider")
    void testGenerateTopNCityReport(Scope scope, String scopeName, int N,  String countryName) {
        List<City> cities;
        cities = reportGenerator.generateTopNCityReport(scope, scopeName, N, countryName);

        // Test Report Success
        assertNotNull(cities,
                "Report ArrayList should not be null unless an error occurred");

        // Test Report Size Against Known Result
        assertEquals(N, cities.size(),
                "There should be %d cities in the report".formatted(N));
    }
}
