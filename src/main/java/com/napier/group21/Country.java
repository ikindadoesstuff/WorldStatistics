package com.napier.group21;

/* Using a record rather than a class simplifies the process of actually using Country objects.
 * This is because the data we get from the database should be immutable (shouldn't change).
 * Country, being a record, has getter and .toString() methods automatically, making the data
 * much easier to access. The code below is the simplest way to define a record.*/
public record Country (String code, String name, String continent, String region, String population, String capital) {}
