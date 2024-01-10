# Prime Number Generator Web Application

This web application is a Prime Number Generator built with Java and the Spring Boot framework. It provides RESTful API to generate prime numbers up to and including a specified number. It is also deployed on AWS Elastic Beanstalk.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Url](#url)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Response Format](#response-format)
- [Technologies Used](#technologies-used)

## Features

- Generate prime numbers up to and including a specified number.
- RESTful API for programmatic access.

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- Maven

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Sydney990123/Prime-number-generator.git
   cd Primes
2. Build the application:
   ```bash
   mvn clean install
3. Run the application:
   ```bash
   java -jar target/primes-0.0.1-SNAPSHOT.jar
Your Prime Number Generator web application should now be running at http://localhost:8080.

### Url

Alternatively, access via AWS URL. Open your web browser and navigate to [http://Prime-number-generator-env.eba-phepqwti.eu-west-2.elasticbeanstalk.com](http://Prime-number-generator-env.eba-phepqwti.eu-west-2.elasticbeanstalk.com).

## Usage
You can use the provided API endpoints for programmatic access.

## Endpoints

- **GET /primes/{number}**
  - Generate prime numbers up to and including the specified number.

Example:
```bash
curl http://Prime-number-generator-env.eba-phepqwti.eu-west-2.elasticbeanstalk.com/primes/50
```

## Response Format
The response can be in either JSON or XML format, based on the Accept header in the request.

### JSON Format
```JSON
{
  "number": 50,
  "primes": [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47]
}
```

### XML Format
```XML
<PrimeNumbersResponse>
  <number>50</number>
  <primes>
    <prime>2</prime>
    <prime>3</prime>
    <!-- ... other prime numbers ... -->
    <prime>47</prime>
  </primes>
</PrimeNumbersResponse>
```

## Technologies Used
- Java 8
- Spring Boot 2.6.0
