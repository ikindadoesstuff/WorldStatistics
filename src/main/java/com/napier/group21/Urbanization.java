package com.napier.group21;

/**
 * Stores a row from an urbanization report.
 * Columns are: <br>
 * Name | Total Population | Urban Pop. | Non-Urban Pop.
 * <p>
 * Using a record rather than a class is more semantically correct, as the records we get from the database should
 * be final. It also removes the need to declare a constructor method.
 * <p>
 * Population is of type long because the maximum value of int is only +2,147,483,647
 *
 * @param name                  Name of the continent, region, or country
 * @param totalPopulation       Total population living in continent, region or country
 * @param urbanPopulation       Population living in cities
 * @param nonUrbanPopulation    Population not living in cities
 */
public record Urbanization(String name, long totalPopulation, long urbanPopulation, long nonUrbanPopulation) implements DatabaseObject {
    @Override
    public String toString() {
        // Calculate population percentages
        float urbanPopulationPercentage = (float) urbanPopulation / (float) totalPopulation * 100;
        float nonUrbanPopulationPercentage = (float) nonUrbanPopulation / (float) totalPopulation * 100;

        return "> %-45s │ %,13d │ %,13d (%6.2f%%) │ %,13d (%6.2f%%) "
                .formatted(
                        name,
                        totalPopulation,
                        urbanPopulation,
                        urbanPopulationPercentage,
                        nonUrbanPopulation,
                        nonUrbanPopulationPercentage
                );
    }

    @Override
    public String getColumnString() {
        String columns = "\n%-47s │ %,13d │ %,21d │ %,21d \n"
                .formatted("City", totalPopulation, urbanPopulation, nonUrbanPopulation);
        String separator = "━".repeat(columns.length() + 10);
        return separator + columns + separator;
    }
}
