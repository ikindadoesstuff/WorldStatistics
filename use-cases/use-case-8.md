# USE CASE: 8 View Population of Continent/Region/Country/District/City.

## CHARACTERISTIC INFORMATION

### Goal in Context

As a data analyst, I want to view the individual populations of cities, districts, countries, regions, and continents as
well as the total world population in order to easily be able to reference those figures.

### Scope

World Data Analysis System

### Level

User Goal

### Preconditions

The continent/region/country/district/city chosen must exist in the database.

### Success End Condition

A report containing all countries in the continent/region/country/district/city ordered by population is available to 
the data analyst.

### Failed End Condition

No report is produced.

### Primary Actor

Data Analyst.

### Trigger

Data analyst requests report of specific geographical population.

## MAIN SUCCESS SCENARIO

1. Data analyst requests specific population report.
2. Data analyst enters report scope.
3. Report is generated accurately.

## EXTENSIONS

1. **Continent/Region/Country/District/City does not exist**:
    1. Error is reported.
    2. Report is not generated.

## SUB-VARIATIONS

1. Data analyst may choose scope of:
    * World
    * Continent
    * Region
    * Country
    * District
    * City

## SCHEDULE

**DUE DATE**: v0.3