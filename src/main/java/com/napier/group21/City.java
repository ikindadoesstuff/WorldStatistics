package com.napier.group21;

/** 
 * Using a record rather than a class is more semantically correct, as the records we get from the database should
 * be final. It also removes the need to declare a constructor method.
 * @param population Population is of type long because the maximum value of int is only +2,147,483,647
 */
public record City(String name, String country, String district, long population) implements DatabaseObject {
    @Override
    public String toString() {
        return "> %-35s │ %-45s │ %-25s │ %,13d "
                .formatted(name, country, district, population);
    }

    public String getColumnString() {
        String columns = "\n%-37s │ %-45s │ %-25s │ %s  \n"
                .formatted("City", "Country", "District", "Population");
        String separator = "━".repeat(columns.length() + 10);
        return separator + columns + separator;
    }
}
