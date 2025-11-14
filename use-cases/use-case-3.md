# USE CASE 3: View All Cities in the World/Continent/Region/Country/District

## CHARACTERISTIC INFORMATION

### Goal in Context

As a data analyst, I want to view all cities in different geographical limits sorted by population so that I can 
understand city population rankings.

### Scope

World Data Analysis System

### Level

User Goal

### Preconditions

If using non-global scope, the continent/region/country/district chosen must exist in the database.

### Success End Condition

A report containing all cities in the world/continent/region/country/district ordered by population is available to the 
data analyst.

### Failed End Condition

No report is produced.

### Primary Actor

Data Analyst

### Trigger

Data analyst requests report of all cities in specified geographical limit ordered by population.

## MAIN SUCCESS SCENARIO

1. Data analyst requests city report.
2. Data analyst enters report scope.
3. Report is generated.

## EXTENSIONS

1. **Continent/Region/Country/District does not exist**:
    1. Error is reported.
    2. Report is not generated.

## SUB-VARIATIONS

1. Data analyst may choose scope of:
    * World
    * Continent
    * Region
    * Country
    * District

## SCHEDULE

**DUE DATE**: v0.0.1