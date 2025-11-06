package com.napier.group21;

/* Using a record rather than a class simplifies the process of actually using Country objects.
 * This is because the data we get from the database should be immutable (shouldn't change).
 * Country, being a record, has getter and .toString() methods automatically, making the data
 * much easier to access. The code below is the simplest way to define a record.
 *
 * The 'population' variables is of type 'long', because the maximum value of 'int' is only
 * +2,147,483,647
 * */
public record Country (String code, String name, String continent, String region, long population, String capital) {}
