--The population of the world-- 
SELECT SUM(Population) AS World_Population
FROM country;

--The population of a continen--
SELECT
    Continent,
    SUM(Population) AS Continent_Population
FROM country
WHERE Continent = 'Asia'   -- Change continent
GROUP BY Continent;

--The population of a region-- 
SELECT
    Region,
    SUM(Population) AS Region_Population
FROM country
WHERE Region = 'Caribbean'   -- Change region
GROUP BY Region;
 
--The population of a country--
SELECT
    Name AS Country,
    Population AS Country_Population
FROM country
WHERE Name = 'Canada';   -- Change country
 
--The population of a district--
SELECT
    District,
    SUM(Population) AS District_Population
FROM city
WHERE District = 'California'   -- Change district
GROUP BY District;
 
--The population of a city--
SELECT
    Name AS City,
    Population AS City_Population
FROM city
WHERE Name = 'Tokyo';   -- Change city
