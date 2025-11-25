package com.napier.group21;

/**
 * Stores a row from a country report.
 * Columns are: <br>
 * Name (Code) | Continent | Region | Population | Capital
 * <p>
 * Using a record rather than a class is more semantically correct, as the records we get from the database should
 * be final. It also removes the need to declare a constructor method.
 * <p>
 * Population is of type long because the maximum value of int is only +2,147,483,647
 *
 * @param code          Country code
 * @param name          Name of the continent, region, or country
 * @param continent     Continent in which country is situated
 * @param population    Population of the country
 * @param capital       Capital of the country
 */
public record Country (String code, String name, String continent, String region, long population, String capital) implements DatabaseObject {
    @Override
    public String toString() {
        return "> %-45s %s │ %-34s │ %,13d │ %s "
                .formatted(name, code, (continent + ", " + region), population, capital);
    }

    @Override
    public String getColumnString() {
        String columns = "\n%-47s %s │ %-34s │ %-13s │ %s \n"
                .formatted("Country (Code)", "   ", "Continent, Region", "Population", "Capital");
        String separator = "━".repeat(columns.length() + 10);
        return separator + columns + separator;
    }
}
