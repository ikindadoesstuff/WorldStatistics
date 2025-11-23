package com.napier.group21;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for the ReportGenerator class.
 */
public class ReportGeneratorTest {
    ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGenerator();
    }

    @Test
    void setConnectionTest_NullConn() {
        reportGenerator.setConnection(null);
    }

    @Test
    void PrintReportTest_Null() {
        ReportGenerator.printReport(null);
    }

    @Test
    void printReportTest_Empty() {
        ReportGenerator.printReport(new ArrayList<>());
    }

    @Test
    void printReportTest_ContainingNull() {
        ArrayList<DatabaseObject> databaseObjects = new ArrayList<>();
        databaseObjects.add(null);
        ReportGenerator.printReport(databaseObjects);
    }

    @Test
    void printReportTest() {
        ArrayList<Country> countries = new ArrayList<>();
        Country country = new Country(
                "BLZ",
                "Belize",
                "North America",
                "Central America",
                22696,
                "Belmopan"
        );
        countries.add(country);
        ReportGenerator.printReport(countries);
    }

    /*
     * SCOPE NAME FETCHES
     */
    @Test
    void fetchScopeNamesTest_NullConn() {
        assertThrows(SQLException.class ,reportGenerator::fetchScopeNames);
    }

    @Test
    void fetchContinentNamesTest_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchContinents);
    }

    @Test
    void fetchRegionNamesTest_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchRegions);
    }

    @Test
    void fetchCountryNamesTest_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchCountries);
    }

    @Test
    void fetchDistrictNamesTest_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchDistricts);
    }

    @Test
    void fetchCityNamesTest_NullConn() {
        assertThrows(RuntimeException.class, reportGenerator::fetchCities);
    }

    /*
     * REPORT GENERATORS
     */

    // COUNTRY REPORTS
    @Test
    void generateSortedCountryReportTest_NullConn_WorldScope() {
        assertThrows(NullPointerException.class, reportGenerator::generateSortedCountryReport);
    }

    @Test
    void generateSortedCountryReportTest_NullConn_InvalidContinent() {
        assertNull(reportGenerator.generateSortedCountryReport(Scope.CONTINENT, "!@#")); // random name
    }

    @Test
    void generateSortedCountryReportTest_NullConn_InvalidRegion() {
        assertNull(reportGenerator.generateSortedCountryReport(Scope.REGION, "!@#")); // random name
    }

    @Test
    void generateTopNCountryReportTest_NullConn_WorldScope() {
        assertThrows(NullPointerException.class,
                () -> reportGenerator.generateTopNCountryReport(5)
        );
    }

    @Test
    void generateTopNCountryReportTest_NullConn_WorldScope_InvalidN() {
        assertNull(reportGenerator.generateTopNCountryReport(-5));
    }

    @Test
    void generateTopNCountryReportTest_NullConn_InvalidContinent() {
        assertNull(reportGenerator.generateTopNCountryReport(Scope.CONTINENT, "!@#", 5)); // random name
    }

    @Test
    void generateTopNCountryReportTest_NullConn_InvalidRegion() {
        assertNull(reportGenerator.generateTopNCountryReport(Scope.REGION, "!@#", 5)); // random name
    }

    // CITY REPORTS
    @Test
    void generateSortedCityReportTest_NullConn_WorldScope() {
        assertThrows(NullPointerException.class, reportGenerator::generateSortedCityReport);
    }

    @Test
    void generateSortedCityReportTest_NullConn_InvalidContinent() {
        assertNull(reportGenerator.generateSortedCityReport(Scope.CONTINENT, "!@#")); // random name
    }

    @Test
    void generateSortedCityReportTest_NullConn_InvalidRegion() {
        assertNull(reportGenerator.generateSortedCityReport(Scope.REGION, "!@#")); // random name
    }

    @Test
    void generateSortedCityReportTest_NullConn_InvalidCountry() {
        assertNull(reportGenerator.generateSortedCityReport(Scope.COUNTRY, "!@#"));
    }

    @Test
    void generateSortedCityReportTest_NullConn_InvalidDistrict() {
        assertNull(reportGenerator.generateSortedCityReport(Scope.DISTRICT, "!@#", "!@#"));
    }

    @Test
    void generateSortedCityReportTest_NullConn_InvalidDistrict_NoCountryName() {
        assertNull(reportGenerator.generateSortedCityReport(Scope.DISTRICT, "!@#"));
    }

    @Test
    void generateTopNCityReportTest_NullConn_WorldScope() {
        assertThrows(NullPointerException.class,
                () -> reportGenerator.generateTopNCityReport(5)
        );
    }

    @Test
    void generateTopNCityReportTest_NullConn_WorldScope_InvalidN() {
        assertNull(reportGenerator.generateTopNCityReport(-5));
    }

    @Test
    void generateTopNCityReportTest_NullConn_InvalidContinent() {
        assertNull(reportGenerator.generateTopNCityReport(Scope.CONTINENT, "!@#", 5)); // random name
    }

    @Test
    void generateTopNCityReportTest_NullConn_InvalidRegion() {
        assertNull(reportGenerator.generateTopNCityReport(Scope.REGION, "!@#", 5)); // random name
    }

    @Test
    void generateTopNCityReportTest_NullConn_InvalidCountry() {
        assertNull(reportGenerator.generateTopNCityReport(Scope.CONTINENT, "!@#", 5)); // random name
    }

    @Test
    void generateTopNCityReportTest_NullConn_InvalidDistrict() {
        assertNull(reportGenerator.generateTopNCityReport(Scope.DISTRICT, "!@#", 5,"!@#")); // random name
    }

    @Test
    void generateTopNCityReportTest_NullConn_InvalidDistrict_NoCountryName() {
        assertNull(reportGenerator.generateTopNCityReport(Scope.DISTRICT, "!@#", 5)); // random name
    }
}
