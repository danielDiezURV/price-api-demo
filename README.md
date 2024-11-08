# Price API Demo
This project is a Spring Boot demo application to manage and retrieve product prices. It includes a REST API to query prices based on the application date, product ID, and brand ID.

## Table of Contents

- [Statement](#statement)
- [Technologies](#technologies)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Running Tests](#running-tests)
- [Results](#results)

## Statement

In the company's e-commerce database, we have the PRICES table that reflects the final price (pvp) and the rate that applies to a product of a brand between certain dates. Below is an example of the table with the relevant fields:

### PRICES

| BRAND_ID | START_DATE          | END_DATE            | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE | CURR |
|----------|----------------------|---------------------|------------|------------|----------|-------|------|
| 1        | 2020-06-14-00.00.00  | 2020-12-31-23.59.59 | 1          | 35455      | 0        | 35.50 | EUR  |
| 1        | 2020-06-14-15.00.00  | 2020-06-14-18.30.00 | 2          | 35455      | 1        | 25.45 | EUR  |
| 1        | 2020-06-15-00.00.00  | 2020-06-15-11.00.00 | 3          | 35455      | 1        | 30.50 | EUR  |
| 1        | 2020-06-15-16.00.00  | 2020-12-31-23.59.59 | 4          | 35455      | 1        | 38.95 | EUR  |

### Fields:

- **BRAND_ID**: foreign key of the brand group (1 = ZARA).
- **START_DATE**, **END_DATE**: date range in which the indicated rate price applies.
- **PRICE_LIST**: Identifier of the applicable price rate.
- **PRODUCT_ID**: Product code identifier.
- **PRIORITY**: Price application disambiguator. If two rates coincide in a date range, the one with the highest priority (highest numerical value) is applied.
- **PRICE**: final sale price.
- **CURR**: currency ISO code.

### Requirements:

Build a SpringBoot application/service that provides a REST query endpoint such that:

- Accepts as input parameters: application date, product identifier, brand identifier.
- Returns as output data: product identifier, brand identifier, applicable rate, application dates, and final price to apply.

An in-memory database (H2 type) should be used and initialized with the example data (field names can be changed and new ones added if desired, choose the appropriate data type for them).

Develop tests for the REST endpoint that validate the following service requests with the example data:

- Test 1: request at 10:00 on the 14th for product 35455 for brand 1 (ZARA)
- Test 2: request at 16:00 on the 14th for product 35455 for brand 1 (ZARA)
- Test 3: request at 21:00 on the 14th for product 35455 for brand 1 (ZARA)
- Test 4: request at 10:00 on the 15th for product 35455 for brand 1 (ZARA)
- Test 5: request at 21:00 on the 16th for product 35455 for brand 1 (ZARA)
## Technologies

This project uses the following technologies:

- **Java 17**: Programming language used to develop the application.
- **Spring Boot**: Framework for building Java applications based on Spring.
- **H2 Database**: In-memory database used to store example data.
- **Mockito**: Unit testing framework for Java.
- **Maven**: Project and dependency management tool.

## Configuration

### Prerequisites

- **Java 17** or higher
- **Maven 3.6.0** or higher

### Installation

1. Clone the repository:

  ```sh
  git clone https://github.com/danielDiezURV/price-api-demo.git
  cd price-api-demo
  ```

2. Install dependencies and build the project:

  ```sh
  mvn clean package
  ```

## Running the Application

### Option 1: Spring Boot

1. Start the Spring Boot application:

  ```sh
  mvn spring-boot:run
  ```

2. The application will be available at `http://localhost:8080/swagger-ui/index.html`.

### Option 2: Docker
With Docker engine running:

1. Build the Docker image:

  ```sh
  docker build -t price-api-demo .
  ```

2. Run the Docker container:

  ```sh
  docker run -p 8080:8080 price-api-demo
  ```

3. The application will be available at `http://localhost:8080/swagger-ui/index.html`.

## Running Tests

### Unit Tests

To run the unit tests, use the following command:

```sh
mvn test
```
### Integration Tests
With Price API demo running:

To run the integration tests using Postman, follow these steps:

1. Open Postman.
2. Import the integration test collection from the `postman_collection.json` file located in the root directory of the project.
3. Run the test collection.

Alternatively, you can use the command line to run the Postman collection using Newman:

1. Install Newman if you don't have it installed:

  ```sh
  npm install -g newman
  ```

2. Run the Postman collection:

  ```sh
  newman run priceTests.postman_collection.json
  ```

This will run all the tests defined in the Postman collection and display the results in the terminal.

## Results

### Test 1: Request at 10:00 on the 14th for product 35455 for brand 1 (ZARA)
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```
### Test 2: Request at 16:00 on the 14th for product 35455 for brand 1 (ZARA)
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45,
  "currency": "EUR"
}
```
### Test 3: Request at 21:00 on the 14th for product 35455 for brand 1 (ZARA)
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```
### Test 4: Request at 10:00 on the 15th for product 35455 for brand 1 (ZARA)
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 3,
  "startDate": "2020-06-15T00:00:00",
  "endDate": "2020-06-15T11:00:00",
  "price": 30.50,
  "currency": "EUR"
}
```
### Test 5: Request at 21:00 on the 16th for product 35455 for brand 1 (ZARA)
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 4,
  "startDate": "2020-06-15T16:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 38.95,
  "currency": "EUR"
}


