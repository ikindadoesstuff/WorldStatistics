package com.napier.group21;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test class for the ReportGenerator class.
 */
class ReportGeneratorTest {
    /**
     * ReportGenerator object to be shared by all test methods
     */
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGenerator();
    }

    @Test
    void testSetConnection_NullConn() {
        assertDoesNotThrow(() -> reportGenerator.setConnection(null),
                "Setting a null connection should not throw an exception"
        );
    }

    @Test
    void testPrintReport_Null() {
        assertDoesNotThrow(() -> ReportGenerator.printReport(null),
                "Calling printReport with null list should not throw an exception"
        );
    }

    @Test
    void testPrintReport_Empty() {
        assertDoesNotThrow(() -> ReportGenerator.printReport(new ArrayList<>()),
                "Calling printReport with an empty list should not throw an exception"
        );
    }

    @Test
    void testPrintReport_ContainingNull() {
        List<DatabaseObject> databaseObjects = new ArrayList<>();
        databaseObjects.add(null);
        assertDoesNotThrow(() -> ReportGenerator.printReport(databaseObjects),
                "Calling printReport with a list containing null should not throw an exception"
        );
    }

    @Test
    void testPrintReport_Country() {
        List<Country> countries = new ArrayList<>();
        Country country = new Country(
                "BLZ",
                "Belize",
                "North America",
                "Central America",
                22696,
                "Belmopan"
        );
        countries.add(country);
        assertDoesNotThrow(() -> ReportGenerator.printReport(countries),
                "Calling printReport with a valid Country list should not throw an exception"
        );
    }

    @Test
    void testPrintReport_City() {
        List<City> cities = new ArrayList<>();
        City city = new City(
                "Belmopan",
                "Belize",
                "Cayo",
                7105
        );
        cities.add(city);
        assertDoesNotThrow(() -> ReportGenerator.printReport(cities),
                "Calling printReport with a valid City list should not throw an exception"
        );
    }

    /*
     * SCOPE NAME FETCHES
     */
    @Test
    void testFetchScopeNames_NullConn() {
        assertThrows(SQLException.class, reportGenerator::fetchScopeNames,
                "Should throw SQLException when connection is null for fetchScopeNames"
        );
    }

    @Test
    void testFetchContinentNames_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchContinents,
                "Should throw RuntimeException when connection is null for fetchContinents"
        );
    }

    @Test
    void testFetchRegionNames_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchRegions,
                "Should throw RuntimeException when connection is null for fetchRegions"
        );
    }

    @Test
    void testFetchCountryNames_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchCountries,
                "Should throw RuntimeException when connection is null for fetchCountries"
        );
    }

    @Test
    void testFetchDistrictNames_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchDistricts,
                "Should throw RuntimeException when connection is null for fetchDistricts"
        );
    }

    @Test
    void testFetchCityNames_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchCities,
                "Should throw RuntimeException when connection is null for fetchCities"
        );
    }

    /*
     * REPORT GENERATORS
     */

    // COUNTRY REPORTS
    @Test
    void testGenerateSortedCountryReport_NullScope() {
        assertThrows(NullPointerException.class,
                () -> reportGenerator.generateSortedCountryReport(null, ""),
                "Should throw NullPointerException when Scope is null"
        );
    }

    @Test
    void testGenerateSortedCountryReport_NullConn_WorldScope() {
        assertNull(reportGenerator.generateSortedCountryReport(),
                "Should return null when connection is null for World scope"
        );
    }

    @ParameterizedTest
    @EnumSource(Scope.class)
    void testGenerateSortedCountryReport_NullConn_AllScopes(Scope scope) {
        assertNull(reportGenerator.generateSortedCountryReport(scope, "!@#"),
                "Should return null when connection is null"
        );
    }

    @Test
    void testGenerateTopNCountryReport_NullScope() {
        assertThrows(NullPointerException.class,
                () -> reportGenerator.generateTopNCountryReport(null, "", 1),
                "Should throw NullPointerException when Scope is null"
        );
    }

    @Test
    void testGenerateTopNCountryReport_NullConn_WorldScope() {
        assertNull(reportGenerator.generateTopNCountryReport(10),
                "Should return null when connection is null for World scope"
        );
    }

    @Test
    void testGenerateTopNCountryReport_NullConn_WorldScope_InvalidN() {
        assertNull(reportGenerator.generateTopNCountryReport(-5),
                "Should return null for invalid N value"
        );
    }

    @ParameterizedTest
    @EnumSource(Scope.class)
    void testGenerateTopNCountryReport_NullConn_AllScopes(Scope scope) {
        assertNull(reportGenerator.generateTopNCountryReport(scope, "!@#", 5),
                "Should return null when connection is null"
        );
    }

    // CITY REPORTS
    @Test
    void testGenerateSortedCityReport_NullScope() {
        assertThrows(NullPointerException.class,
                () -> reportGenerator.generateSortedCityReport(null, ""),
                "Should throw NullPointerException when Scope is null"
        );
    }

    @Test
    void testGenerateSortedCityReport_NullConn_WorldScope() {
        assertNull(reportGenerator.generateSortedCityReport(),
                "Should return null when connection is null for World scope"
        );
    }

    @ParameterizedTest
    @EnumSource(Scope.class)
    void testGenerateSortedCityReport_NullConn_AllScopes(Scope scope) {
        assertNull(reportGenerator.generateSortedCityReport(scope, "!@#"),
                "Should return null when connection is null"
        );
    }

    @Test
    void testGenerateSortedCityReport_NullConn_InvalidDistrict() {
        assertNull(reportGenerator.generateSortedCityReport(Scope.DISTRICT, "!@#", "!@#"),
                "Should return null when connection is null and scope value is invalid for District scope with country"
        );
    }

    @Test
    void testGenerateTopNCityReport_NullScope() {
        assertThrows(NullPointerException.class,
                () -> reportGenerator.generateTopNCountryReport(null, "", 1),
                "Should throw NullPointerException when Scope is null"
        );
    }

    @Test
    void testGenerateTopNCityReport_NullConn_WorldScope() {
        assertNull(reportGenerator.generateTopNCityReport(10),
                "Should return null when connection is null for World scope"
        );
    }

    @Test
    void testGenerateTopNCityReport_NullConn_WorldScope_InvalidN() {
        assertNull(reportGenerator.generateTopNCityReport(-5),
                "Should return null for invalid N value"
        );
    }

    @ParameterizedTest
    @EnumSource(Scope.class)
    void testGenerateTopNCityReport_NullConn_AllScopes(Scope scope) {
        assertNull(reportGenerator.generateTopNCityReport(scope, "!@#", 5),
                "Should return null when connection is null"
        );
    }

    @Test
    void testGenerateTopNCityReport_NullConn_InvalidDistrict() {
        assertNull(reportGenerator.generateTopNCityReport(Scope.DISTRICT, "!@#", 5, "!@#"),
                "Should return null when connection is null and scope value is invalid for District scope with country"
        );
    }

    // CAPITAL REPORTS
    @Test
    void testGenerateSortedCapitalReport_NullScope() {
        assertThrows(NullPointerException.class,
                () -> reportGenerator.generateSortedCapitalReport(null, ""),
                "Should throw NullPointerException when Scope is null"
        );
    }

    @Test
    void testGenerateSortedCapitalReport_NullConn_WorldScope() {
        assertNull(reportGenerator.generateSortedCapitalReport(),
                "Should return null when connection is null for World scope"
        );
    }

    @ParameterizedTest
    @EnumSource(Scope.class)
    void testGenerateSortedCapitalReport_NullConn_AllScopes(Scope scope) {
        assertNull(reportGenerator.generateSortedCapitalReport(scope, "!@#"),
                "Should return null when connection is null"
        );
    }

    @Test
    void testGenerateTopNCapitalReport_NullScope() {
        assertThrows(NullPointerException.class,
                () -> reportGenerator.generateTopNCapitalReport(null, "", 1),
                "Should throw NullPointerException when Scope is null"
        );
    }

    @Test
    void testGenerateTopNCapitalReport_NullConn_WorldScope() {
        assertNull(reportGenerator.generateTopNCapitalReport(10),
                "Should return null when connection is null for World scope"
        );
    }

    @Test
    void testGenerateTopNCapitalReport_NullConn_WorldScope_InvalidN() {
        assertNull(reportGenerator.generateTopNCapitalReport(-5),
                "Should return null for invalid N value"
        );
    }

    @ParameterizedTest
    @EnumSource(Scope.class)
    void testGenerateTopNCapitalReport_NullConn_AllScopes(Scope scope) {
        assertNull(reportGenerator.generateTopNCapitalReport(scope, "!@#", 5),
                "Should return null when connection is null"
        );
    }

    // URBANIZATION REPORTS
    @ParameterizedTest
    @EnumSource(Scope.class)
    void testGenerateUrbanizationReport_NullConn_AllScopes(Scope scope) {
        assertNull(reportGenerator.generateUrbanizationReport(scope),
                "Should return null when connection is null"
        );
    }
}