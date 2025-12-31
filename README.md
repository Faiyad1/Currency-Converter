# Currency Converter

**SOFT2412 - Agile Software Development Practices**  
The University of Sydney  
19 September 2024

<mark>**Academic Integrity:** This is an group assignment. The use of AI tools (e.g., ChatGPT, GitHub Copilot) is not permitted.</mark>

**Group Members:**
- Faiyad Ahmed, The University of Sydney
- Varrent Nathaniel Woodrow, The University of Sydney
- Achira Tantisuwannakul, The University of Sydney
- Po-An Lin, The University of Sydney

A Java command-line application for converting currencies, viewing exchange rate tables, and analyzing historical conversion data, built in Java with Gradle and JUnit.

## Overview

This Currency Converter is a command-line application that provides real-time currency conversion and exchange rate analysis. The application supports two user roles: Normal users who can convert currencies and view exchange rates, and Admin users who have additional privileges to manage the system's currency data.

## Table of Contents

- [Features](#features)
  - [All Users (Normal & Admin)](#all-users-normal--admin)
  - [Admin Only](#admin-only)
- [Requirements](#requirements)
- [Building and Running](#building-and-running)
- [Testing & Code Coverage](#testing--code-coverage)
- [Usage Example](#usage-example)
- [Project Structure](#project-structure)
- [Data Files](#data-files)

## Features

### All Users (Normal & Admin)

1. **Convert Money** - Convert an amount from one currency to another using the most recent exchange rate
2. **Display Popular Currencies** - View a 4x4 exchange rate table for the top 4 currencies with trend indicators:
   - `(I)` - Rate has increased since last update
   - `(D)` - Rate has decreased since last update
3. **Print Conversion Rate Summary** - Generate statistics for a currency pair over a date range:
   - Average rate
   - Median rate
   - Maximum rate
   - Minimum rate
   - Standard deviation

### Admin Only

4. **Set Popular Currencies** - Replace a currency in the popular currencies list with a different one
5. **Update Exchange Rate** - Add new exchange rate records with the current date. Can also be used to add new currency types to the system

## Requirements

- Java 8 or higher
- Gradle 8.6+

## Building and Running

### Building
```bash
./gradlew build
```

### Running
```bash
./gradlew run
```

## Testing & Code Coverage

### Run Tests
```bash
./gradlew test
```

### Generate Code Coverage Report
```bash
./gradlew jacocoTestReport
```
Report location: `build/reports/jacoco/test/html/index.html`

## Usage Example

```
Welcome to Currency Converter! Select user type (enter 1 or 2):
1. Normal
2. Admin

What do you want to do today? Enter numbers.
0. Exit
1. Convert money
2. Display most popular currencies
3. Print summary of 2 currency's conversion rates.
```

Admin users see additional options:
```
4. Set popular currencies
5. Update exchange rate
```

## Project Structure

```
app/src/main/
├── java/CurrencyConverter/
│   ├── App.java               # Main application and menu system
│   ├── Currency.java          # Currency model with exchange rates and trends
│   ├── CurrencyOperation.java # Data operations (load, save, find rates)
│   └── UserType.java          # NORMAL and ADMIN user types
└── resources/
    ├── data.csv               # Exchange rate history
    ├── sampledata.csv         # Sample exchange rate data
    └── TopCurrencies.csv      # Popular currencies list

app/src/test/java/CurrencyConverter/
├── AppTest.java               # UI and display tests
├── CurrencyTest.java          # Currency model tests
└── CurrencyOperationTest.java # Data operation tests
```

## Data Files

### data.csv
Stores persistent historical exchange rates with dates. All rate updates are recorded with the date of addition, maintaining a complete history for summary analysis:
```
date,from_currency,to_currency,rate
2024-09-02,aud,usd,0.69
2024-09-02,usd,eur,0.90
```

### TopCurrencies.csv
Stores the 4 popular currencies (comma-separated):
```
aud,usd,eur,gbp
```
