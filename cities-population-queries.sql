
--All cities in the world (largest → smallest population)--

SELECT
    city.ID,
    city.Name AS City,
    country.Name AS Country,
    city.Population
FROM city
JOIN country ON city.CountryCode = country.Code
ORDER BY city.Population DESC;

--All cities in a continent (largest → smallest population)--
SELECT
    city.ID,
    city.Name AS City,
    country.Name AS Country,
    country.Continent,
    city.Population
FROM city
JOIN country ON city.CountryCode = country.Code
WHERE country.Continent = 'Asia'   -- Change continent here
ORDER BY city.Population DESC;

--All cities in a region (largest → smallest population)--
SELECT
    city.ID,
    city.Name AS City,
    country.Name AS Country,
    country.Region,
    city.Population
FROM city
JOIN country ON city.CountryCode = country.Code
WHERE country.Region = 'Caribbean'   -- Change region here
ORDER BY city.Population DESC;
--All cities in a country (largest → smallest population)--

SELECT
    city.ID,
    city.Name AS City,
    country.Name AS Country,
    city.Population
FROM city
JOIN country ON city.CountryCode = country.Code
WHERE country.Name = 'Canada'   -- Change country here
ORDER BY city.Population DESC;

--All cities in a district (largest → smallest population)--

SELECT
    ID,
    Name AS City,
    District,
    Population
FROM city
WHERE District = 'California'   -- Change district here
ORDER BY Population DESC;
