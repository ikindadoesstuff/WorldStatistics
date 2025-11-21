package com.napier.group21;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.napier.group21.ReportGenerator.printReport;

public class AppTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App();
    }

    @Test
    void testPrintReportNull() {
        printReport(null);
    }
}
