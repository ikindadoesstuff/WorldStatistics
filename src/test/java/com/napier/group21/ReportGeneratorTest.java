package com.napier.group21;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGenerator();
    }

    @Test
    void setConnectionTest_NullConn() {
        assertDoesNotThrow(() -> reportGenerator.setConnection(null),
                "Setting a null connection should not throw an exception"
        );
    }

    @Test
    void PrintReportTest_Null() {
        assertDoesNotThrow(() -> ReportGenerator.printReport(null),
                "Calling printReport with null list should not throw an exception"
        );
    }

    @Test
    void printReportTest_Empty() {
        assertDoesNotThrow(() -> ReportGenerator.printReport(new ArrayList<>()),
                "Calling printReport with an empty list should not throw an exception"
        );
    }

    @Test
    void printReportTest_ContainingNull() {
        List<DatabaseObject> databaseObjects = new ArrayList<>();
        databaseObjects.add(null);
        assertDoesNotThrow(() -> ReportGenerator.printReport(databaseObjects),
                "Calling printReport with a list containing null should not throw an exception"
        );
    }

    @Test
    void printReportTest_Country() {
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
    void printReportTest_City() {
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
    void fetchScopeNamesTest_NullConn() {
        assertThrows(SQLException.class ,reportGenerator::fetchScopeNames,
                "Should throw SQLException when connection is null for fetchScopeNames"
        );
    }

    @Test
    void fetchContinentNamesTest_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchContinents,
                "Should throw RuntimeException when connection is null for fetchContinents"
        );
    }

    @Test
    void fetchRegionNamesTest_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchRegions,
                "Should throw RuntimeException when connection is null for fetchRegions"
        );
    }

    @Test
    void fetchCountryNamesTest_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchCountries,
                "Should throw RuntimeException when connection is null for fetchCountries"
        );
    }

    @Test
    void fetchDistrictNamesTest_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchDistricts,
                "Should throw RuntimeException when connection is null for fetchDistricts"
        );
    }

    @Test
    void fetchCityNamesTest_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchCities,
                "Should throw RuntimeException when connection is null for fetchCities"
        );
    }

    /*
     * REPORT GENERATORS
     */

    // COUNTRY REPORTS
    @Test
    void generateSortedCountryReportTest_NullScope() {
        assertThrows(NullPointerException.class,
                ()->reportGenerator.generateSortedCountryReport(null, ""),
                "Should throw NullPointerException when Scope is null"
        );
    }

    @Test
    void generateSortedCountryReportTest_NullConn_WorldScope() {
        assertThrows(NullPointerException.class, reportGenerator::generateSortedCountryReport,
                "Should throw NullPointerException when connection is null for World Scope"
        );
    }

    @Test
    void generateSortedCountryReportTest_NullConn_InvalidContinent() {
        assertNull(reportGenerator.generateSortedCountryReport(Scope.CONTINENT, "!@#"),
                "Should return null when connection is null and scope value is invalid for Continent scope"
        ); // random name
    }

    @Test
    void generateSortedCountryReportTest_NullConn_InvalidRegion() {
        assertNull(reportGenerator.generateSortedCountryReport(Scope.REGION, "!@#"),
                "Should return null when connection is null and scope value is invalid for Region scope"
        ); // random name
    }

    @Test
    void generateTopNCountryReportTest_NullScope() {
        assertThrows(NullPointerException.class,
                ()->reportGenerator.generateTopNCountryReport(null, "", 1),
                "Should throw NullPointerException when Scope is null"
        );
    }

    @Test
    void generateTopNCountryReportTest_NullConn_WorldScope() {
        assertThrows(NullPointerException.class,
                () -> reportGenerator.generateTopNCountryReport(5),
                "Should throw NullPointerException when connection is null for World Scope"
        );
    }

    @Test
    void generateTopNCountryReportTest_NullConn_WorldScope_InvalidN() {
        assertNull(reportGenerator.generateTopNCountryReport(-5),
                "Should return null for invalid N value"
        );
    }

    @Test
    void generateTopNCountryReportTest_NullConn_InvalidContinent() {
        assertNull(reportGenerator.generateTopNCountryReport(Scope.CONTINENT, "!@#", 5),
                "Should return null when connection is null and scope value is invalid for Continent scope"
        ); // random name
    }

    @Test
    void generateTopNCountryReportTest_NullConn_InvalidRegion() {
        assertNull(reportGenerator.generateTopNCountryReport(Scope.REGION, "!@#", 5),
                "Should return null when connection is null and scope value is invalid for Region scope"
        ); // random name
    }

    // CITY REPORTS
    @Test
    void generateSortedCityReportTest_NullScope() {
        assertThrows(NullPointerException.class,
                ()->reportGenerator.generateSortedCityReport(null, ""),
                "Should throw NullPointerException when Scope is null"
        );
    }

    @Test
    void generateSortedCityReportTest_NullConn_WorldScope() {
        assertThrows(NullPointerException.class, reportGenerator::generateSortedCityReport,
                "Should throw NullPointerException when connection is null for World Scope"
        );
    }

    @Test
    void generateSortedCityReportTest_NullConn_InvalidContinent() {
        assertNull(reportGenerator.generateSortedCityReport(Scope.CONTINENT, "!@#"),
                "Should return null when connection is null and scope value is invalid for Continent scope"
        ); // random name
    }

    @Test
    void generateSortedCityReportTest_NullConn_InvalidRegion() {
        assertNull(reportGenerator.generateSortedCityReport(Scope.REGION, "!@#"),
                "Should return null when connection is null and scope value is invalid for Region scope"
        ); // random name
    }

    @Test
    void generateSortedCityReportTest_NullConn_InvalidCountry() {
        assertNull(reportGenerator.generateSortedCityReport(Scope.COUNTRY, "!@#"),
                "Should return null when connection is null and scope value is invalid for Country scope"
        );
    }

    @Test
    void generateSortedCityReportTest_NullConn_InvalidDistrict() {
        assertNull(reportGenerator.generateSortedCityReport(Scope.DISTRICT, "!@#", "!@#"),
                "Should return null when connection is null and scope value is invalid for District scope with country"
        );
    }

    @Test
    void generateSortedCityReportTest_NullConn_InvalidDistrict_NoCountryName() {
        assertNull(reportGenerator.generateSortedCityReport(Scope.DISTRICT, "!@#"),
                "Should return null when connection is null and scope value is invalid for District scope without country"
        );
    }

    @Test
    void generateTopNCityReportTest_NullScope() {
        assertThrows(NullPointerException.class,
                ()->reportGenerator.generateTopNCountryReport(null, "", 1),
                "Should throw NullPointerException when Scope is null"
        );
    }

    @Test
    void generateTopNCityReportTest_NullConn_WorldScope() {
        assertThrows(NullPointerException.class,
                () -> reportGenerator.generateTopNCityReport(5),
                "Should throw NullPointerException when connection is null for World Scope"
        );
    }

    @Test
    void generateTopNCityReportTest_NullConn_WorldScope_InvalidN() {
        assertNull(reportGenerator.generateTopNCityReport(-5),
                "Should return null for invalid N value"
        );
    }

    @Test
    void generateTopNCityReportTest_NullConn_InvalidContinent() {
        assertNull(reportGenerator.generateTopNCityReport(Scope.CONTINENT, "!@#", 5),
                "Should return null when connection is null and scope value is invalid for Continent scope"
        ); // random name
    }

    @Test
    void generateTopNCityReportTest_NullConn_InvalidRegion() {
        assertNull(reportGenerator.generateTopNCityReport(Scope.REGION, "!@#", 5),
                "Should return null when connection is null and scope value is invalid for Region scope"
        ); // random name
    }

    @Test
    void generateTopNCityReportTest_NullConn_InvalidCountry() {
        assertNull(reportGenerator.generateTopNCityReport(Scope.COUNTRY, "!@#", 5),
                "Should return null when connection is null and scope value is invalid for Country scope"
        ); // random name
    }

    @Test
    void generateTopNCityReportTest_NullConn_InvalidDistrict() {
        assertNull(reportGenerator.generateTopNCityReport(Scope.DISTRICT, "!@#", 5,"!@#"),
                "Should return null when connection is null and scope value is invalid for District scope with country"
        ); // random name
    }

    @Test
    void generateTopNCityReportTest_NullConn_InvalidDistrict_NoCountryName() {
        assertNull(reportGenerator.generateTopNCityReport(Scope.DISTRICT, "!@#", 5),
                "Should return null when connection is null and scope value is invalid for District scope without country"
        ); // random name
    }
}