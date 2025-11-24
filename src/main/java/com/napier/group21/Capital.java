package com.napier.group21;

/** 
 * Using a record rather than a class is more semantically correct, as the records we get from the database should
 * be final. It also removes the need to declare a constructor method.
 * @param population Population is of type long because the maximum value of int is only +2,147,483,647
 */
public record Capital(String name, String country, long population) implements DatabaseObject {
    @Override
    public String toString() {
        return "> %-35s │ %-45s │ %,13d "
                .formatted(name, country, population);
    }

    @Override
    public String getColumnString() {
        String columns = "\n%-37s │ %-45s │ %s  \n"
                .formatted("City", "Country", "Population");
        String separator = "━".repeat(columns.length() + 10);
        return separator + columns + separator;
    }
}
