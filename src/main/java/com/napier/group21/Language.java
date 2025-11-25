package com.napier.group21;

/**
 * Stores result of language report.
 * Columns are: <br>
 * Language | Total Speakers | World Percentage
 * <p>
 * Using a record rather than a class is more semantically correct, as the records we get from the database should
 * be final. It also removes the need to declare a constructor method.
 * <p>
 * Population is of type long because the maximum value of int is only +2,147,483,647
 *
 * @param name              Language name
 * @param totalSpeakers     Number of speakers worldwide
 * @param worldPercentage   Percentage of speakers worldwide
 */
public record Language(String name, long totalSpeakers, float worldPercentage) implements DatabaseObject {
    @Override
    public String toString() {
        return "> %-45s │ %,14d | %6.2f%%"
                .formatted(name, totalSpeakers, worldPercentage);
    }

    @Override
    public String getColumnString() {
        String columns = "\n%-47s │ %-14s | %7s \n"
                .formatted("Name", "Total Speakers", "World %" );
        String separator = "━".repeat(columns.length() + 10);
        return separator + columns + separator;
    }
}
