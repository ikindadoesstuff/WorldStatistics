package com.napier.group21;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * Test class for the ReportGenerator class.
 * The @ExtendWith(MockitoExtension.class) annotation allows Mockito to initialize the mock objects automatically.
 */
@ExtendWith(MockitoExtension.class)
public class ReportGeneratorTest {
    ReportGenerator reportGenerator;

    @Mock
    Connection mockConnection;

    @Mock
    Statement mockStatement;

    @Mock
    ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGenerator();
    }

    void setUpMockDB() throws SQLException {
        lenient().when(mockConnection.createStatement()).thenReturn(mockStatement);
        lenient().when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        /*
         * The first time .next() is run on the result set, it returns the row, but the second fails, simulating just
         * one record being returned in the result set.
         */
//        lenient().when(mockResultSet.next()).thenReturn(true, false);

        /*
         * Mock row in the result set
         */
//        lenient().when(mockResultSet.getString("Continent")).thenReturn("North America");
//        lenient().when(mockResultSet.getString("Region")).thenReturn("Central America");
//        lenient().when(mockResultSet.getString("Country")).thenReturn("Belize");
//        lenient().when(mockResultSet.getString("District")).thenReturn("Cayo");
//        lenient().when(mockResultSet.getString("Cayo")).thenReturn("Belmopan");
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
    void fetchScopeNamesTest() throws SQLException {
        setUpMockDB();
        lenient().when(mockResultSet.next()).thenReturn(true, false);

        lenient().when(mockResultSet.getString("Continent")).thenReturn("North America");

        lenient().when(mockResultSet.getString("Region")).thenReturn("Central America");

        lenient().when(mockResultSet.getString("Country")).thenReturn("Belize");

        lenient().when(mockResultSet.getString("District")).thenReturn("Cayo");

        reportGenerator.setConnection(mockConnection);
        reportGenerator.fetchScopeNames();
        return;
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
