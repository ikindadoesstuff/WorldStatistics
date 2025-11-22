package com.napier.group21;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReportGeneratorTest {
    static ReportGenerator reportGenerator;

    @BeforeAll
    static void init() {
    }

    @Test
    void testSetConnection_NullConn() {
        reportGenerator.setConnection(null);
    }

    @Test
    void testFetchScopeNames_NullConn() {
        reportGenerator = new ReportGenerator();
        assertThrows(SQLException.class ,reportGenerator::fetchScopeNames);
    }

    @Test
    void testPrintReport_Null() {
        ReportGenerator.printReport(null);
    }

    @Test
    void testPrintReport_Empty() {
        ReportGenerator.printReport(new ArrayList<>());
    }

    @Test
    void printReport() {
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
    void testFetchContinentNames_NullConn() {
        reportGenerator = new ReportGenerator();
        assertThrows(RuntimeException.class, reportGenerator::fetchContinents);
    }

    @Test
    void testFetchRegionNames_NullConn() {
        reportGenerator = new ReportGenerator();
        assertThrows(RuntimeException.class, reportGenerator::fetchRegions);
    }

    @Test
    void testFetchCountryNames_NullConn() {
        reportGenerator = new ReportGenerator();
        assertThrows(RuntimeException.class, reportGenerator::fetchCountries);
    }

    @Test
    void testFetchDistrictNames_NullConn() {
        reportGenerator = new ReportGenerator();
        assertThrows(RuntimeException.class, reportGenerator::fetchDistricts);
    }

    @Test
    void testFetchCityNames_NullConn() {
        reportGenerator = new ReportGenerator();
        assertThrows(RuntimeException.class, reportGenerator::fetchCities);
    }
}
