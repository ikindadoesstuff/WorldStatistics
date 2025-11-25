# ISSUE 1
# All the countries in the world/continent/region in descending population order
SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name
FROM country
         LEFT JOIN city on country.Capital = city.ID

# No condition for all countries in the world
###

# All countries in a continent
# WHERE country.Continent = 'Asia'

# All countries in a region
WHERE country.Region = 'Caribbean'

# SORTING
ORDER BY country.Population DESC;


#==================================================================
# ISSUE 2
# The top N countries in the world/continent/region
SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name
FROM country
         LEFT JOIN city on country.Capital = city.ID

# No condition for all countries in the world
###

# All countries in a continent
WHERE country.Continent = 'Asia'

# All countries in a region
# WHERE country.Region = 'Micronesia'

ORDER BY country.Population DESC LIMIT 10; # Or whatever the value of N is


#==================================================================
# ISSUE 3
# All the cities in the world/continent/region/country/district in descending population order
SELECT city.Name, country.Name, city.District, city.Population
FROM city
         LEFT JOIN country on city.CountryCode = country.Code

# No condition for all cities in the world
###

# All cities in a continent
# WHERE country.Continent = 'Asia'

# All cities in a region
# WHERE country.Region = 'Micronesia'

# All cities in a country
# WHERE country.Name = 'United States'

# All cities in a district
WHERE city.District = 'Scotland'

ORDER BY city.population DESC;


#==================================================================
# ISSUE 4
# The top N cities in the world/continent/region/country/district
SELECT city.Name, country.Name, city.District, city.Population
FROM city
         LEFT JOIN country on city.CountryCode = country.Code

# No condition for all cities in the world
###

# All cities in a continent
# WHERE country.Continent = 'Asia'

# All cities in a region
# WHERE country.Region = 'Micronesia'

# All cities in a country
WHERE country.Name = 'United States'

# All cities in a district
# WHERE city.District = 'Scotland'

ORDER BY city.Population DESC LIMIT 15; # Or whatever the value of N is


#==================================================================
# ISSUE 5
# All the capital cities in the world/continent/region in descending population order
SELECT city.Name, country.Name, city.Population
FROM city
         LEFT JOIN country on city.CountryCode = country.Code

WHERE city.ID = country.Capital

# No condition for all cities in the world
###

# All capital cities in a continent
# AND country.Continent = 'Asia'

# All capital cities in a region
  AND country.Region = 'Central America'

ORDER BY city.Population DESC;


#==================================================================
# ISSUE 6
# Top N capital cities in the world/continent/region in descending population order
SELECT city.Name, country.Name, city.Population
FROM city
         LEFT JOIN country on city.CountryCode = country.Code

WHERE city.ID = country.Capital

# No condition for all cities in the world
###

# All capital cities in a continent
# AND country.Continent = 'Asia'

# All capital cities in a region
# AND country.Region = 'Central America'

ORDER BY city.Population DESC LIMIT 5; # Or whatever the value of N is


#==================================================================
# ISSUE 7
# Total population, population in cities, and population not in cities in each continent/region/country
# - COUNTRY
SELECT country.Name,
       country.Population AS TotalPopulation,
       SUM(city.Population) AS CityPopulation,
       (country.Population - SUM(city.Population)) as NonCityPopulation
FROM country
         LEFT JOIN city on city.CountryCode = country.Code

GROUP BY country.Code;

# - REGION
SELECT country.Region,
       SUM(DISTINCT country.Population) AS TotalPopulation,
       SUM(city.Population) AS CityPopulation,
       (SUM(DISTINCT country.Population) - SUM(city.Population)) as NonCityPopulation
FROM country
         LEFT JOIN city on city.CountryCode = country.Code

GROUP BY country.Region;

# - REGION
SELECT country.Continent,
       SUM(DISTINCT country.Population) AS TotalPopulation,
       SUM(city.Population) AS CityPopulation,
       (SUM(DISTINCT country.Population) - SUM(city.Population)) as NonCityPopulation
FROM country
         LEFT JOIN city on city.CountryCode = country.Code

GROUP BY country.Continent;


#==================================================================
# ISSUE 8
# Population city/district/countries/region/continent/world
# - CITY
SELECT city.Name as Name, city.population as Population
FROM city
LEFT JOIN country on city.CountryCode = country.Code
WHERE city.Name = 'Tokyo' and country.Name = 'Japan';

# - DISTRICT
SELECT city.District as Name, city.CountryCode, SUM(city.population) as Population
FROM city
LEFT JOIN country on city.CountryCode = country.Code
WHERE city.District = 'Georgia' AND country.Name = 'United Staes';

# - REGION
SELECT country.Region as Name, SUM(DISTINCT country.population) as Population
FROM country
WHERE country.Region = 'South America';

# - COUNTRY
SELECT country.Name as Name, SUM(DISTINCT country.population) as Population
FROM country
WHERE country.Name = 'India';

# - CONTINENT
SELECT country.Continent as Name, SUM(DISTINCT country.population) as Population
FROM country
WHERE country.Continent = 'Africa';

# - WORLD
SELECT 'World', SUM(country.population) AS TotalPopulation
FROM country;


#==================================================================
# ISSUE 9
# Number of people who speak Chinese, English, Hindi, Spanish and Arabic in descending order
# - CITY
SELECT countrylanguage.Language,
       ROUND(SUM(countrylanguage.Percentage * country.Population / 100)) as TotalSpeakers,
       SUM(countrylanguage.Percentage * country.Population / 100) / (SELECT SUM(country.Population) FROM country) * 100 WorldPercentage
FROM countrylanguage
         JOIN country on countrylanguage.CountryCode = country.Code

WHERE countrylanguage.language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic')

GROUP BY countrylanguage.Language

ORDER BY TotalSpeakers DESC;