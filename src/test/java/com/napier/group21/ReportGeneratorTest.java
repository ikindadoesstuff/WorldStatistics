package com.napier.group21;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

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
}
