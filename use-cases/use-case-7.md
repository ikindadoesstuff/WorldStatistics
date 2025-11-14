# USE CASE: 7 View Population Urbanization by Continent/Region/Country.

## CHARACTERISTIC INFORMATION

### Goal in Context

As a data analyst, I want to view the total population, the population of people living in cities, and the population 
not living in cities in different geographical areas so that I can determine levels of urbanization worldwide.

### Scope

World Data Analysis System

### Level

User Goal

### Preconditions

The continent/region/country chosen must exist in the database.

### Success End Condition

A report containing all countries in the continent/region/country ordered by population is available to the data analyst.

### Failed End Condition

No report is produced.

### Primary Actor

Data Analyst.

### Trigger

Data analyst requests report of population urbanization in specified geographical limit.

## MAIN SUCCESS SCENARIO

1. Data analyst requests urbanization report.
2. Data analyst enters report scope.
3. Report is generated accurately.

## EXTENSIONS

1. **Continent/Region/Country does not exist**:
    1. Error is reported.
    2. Report is not generated.

## SUB-VARIATIONS

1. Data analyst may choose scope of:
    * Continent
    * Region
    * Country

## SCHEDULE

**DUE DATE**: v0.0.3