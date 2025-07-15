# Currency Exchange Project

Currency Exchange API project made for educational purpose.

Technologies:
- Java Core
- Jakarta Servlets
- JDBC
- Maven
- SQLite

Endpoints:
- /currencies
  - GET /currencies: get a list of all currencies
  - POST /currencies: add new currency to repository
- /currency
  - GET /currency/*: get specific currency
- /exchangeRates
  - GET /exchangeRates: get all exchange rates
  - POST /exchangeRates: add new exchange rate to repository
- /exchangeRate/*
  - GET /exchangeRate/*: get specific exchange rate
  - PATCH /exchangeRate?rate=*&from=*&to=*: update an existing exchange rate
- /exchange/*
  - GET /exchange/*: exchange money from one currency to another
