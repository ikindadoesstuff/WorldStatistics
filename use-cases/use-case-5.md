# USE CASE: 5 View all capital cities in the World/Continent/Region by population.

## CHARACTERISTIC INFORMATION

### Goal in Context

As a data analyst, I want to view all the capital cities in different geographical limits sorted by largest to smallest 
population so that I can analyze the population ranking of different political centers.

### Scope

World Data Analysis System

### Level

User Goal

### Preconditions

If using non-global scope, the continent/region chosen must exist in the database.

### Success End Condition

A report containing all capital cities in the continent/region ordered by population is available to 
the data analyst.

### Failed End Condition

No report is produced.

### Primary Actor

Data Analyst.

### Trigger

Data analyst requests report of all capital cities in specified geographical limit ordered by population.

## MAIN SUCCESS SCENARIO

1. Data analyst requests capital city report.
2. Data analyst enters report scope.
3. Report is generated accurately.

## EXTENSIONS

1. **Continent/Region does not exist**:
    1. Error is reported.
    2. Report is not generated.

## SUB-VARIATIONS

1. Data analyst may choose scope of:
    * World
    * Continent
    * Region

## SCHEDULE

**DUE DATE**: v0.0.2