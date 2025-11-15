# USE CASE 4: View Top N Populated Cities in the World/Continent/Region/Country/District

## CHARACTERISTIC INFORMATION

### Goal in Context

As a data analyst, I want to view the top N most populated cities in different geographical limits to identify 
metropolitan population centers.

### Scope

World Data Analysis System

### Level

User Goal

### Preconditions

* If using non-global scope, the continent/region/country/district chosen must exist in the database.
* The number N must be a positive integer and less than or equal to the total countries in scope.

### Success End Condition

A report of the top N most populated cities in the World/Region/Country/District is available to the data analyst.

### Failed End Condition

No report is produced.

### Primary Actor

Data Analyst

### Trigger

Data analyst requests report of top N most populated cities in specified geographical limit.

## MAIN SUCCESS SCENARIO

1. Data analyst requests city report.
2. Data analyst enters report scope.
3. Data analyst enters N.
4. Report is generated accurately.

## EXTENSIONS

1. **Continent/Region/Country/District does not exist**:
    1. Error is reported.
    2. Report is not generated.

2. **N is invalid**:
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

**DUE DATE**: v0.2