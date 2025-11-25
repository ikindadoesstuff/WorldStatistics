package com.napier.group21;

/**
 * Stores result of Population Report.
 * Columns are: <br>
 * Name | Population
 * <p>
 * Using a record rather than a class is more semantically correct, as the records we get from the database should
 * be final. It also removes the need to declare a constructor method.
 * <p>
 * Population is of type long because the maximum value of int is only +2,147,483,647
 *
 * @param name          Name of the continent, region, or country
 * @param population    Population of the country
 */
public record Population(String name, long population) implements DatabaseObject {
    @Override
    public String toString() {
        return "> %-45s │ %,13d "
                .formatted(name, population);
    }

    @Override
    public String getColumnString() {
        String columns = "\n%-47s │ %-13s \n"
                .formatted("Name", "Population");
        String separator = "━".repeat(columns.length() + 10);
        return separator + columns + separator;
    }
}
