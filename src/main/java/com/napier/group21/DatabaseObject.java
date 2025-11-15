package com.napier.group21;

/**
 * Share by all different report object types that can retrieved from the database.
 * Allows ArrayList of DatabaseObject to use any record which implements DatabaseObject.
 */
public interface DatabaseObject {
    @Override
    public String toString();
}

/* Using a record rather than a class simplifies the process of actually using Country objects.
 * This is because the data we get from the database should be immutable (shouldn't change).
 * Country, being a record, has getter and .toString() methods automatically, making the data
 * much easier to access. The code below is the simplest way to define a record.
 *
 * The 'population' variables is of type 'long', because the maximum value of 'int' is only
 * +2,147,483,647
 * */
record Country (String code, String name, String continent, String region, long population, String capital) implements DatabaseObject {
    @Override
    public String toString() {
        return "> %-45s %s | %-34s | Population: %,13d | Capital: %s "
                .formatted(name, code, (continent + ", " + region), population, capital);
    }
}
