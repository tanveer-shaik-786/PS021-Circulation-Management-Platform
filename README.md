[README.md](https://github.com/user-attachments/files/32679062/README.md)
# PS021 -- Bibliotech Circulation Management Platform

A microservices-based academic library circulation management platform
developed using Spring Boot, Spring Cloud, JWT authentication, Eureka
Service Discovery, API Gateway, and MySQL.

## Project Overview

**Bibliotech Circulation Management Platform** is designed to automate
important academic library operations such as book inventory management,
borrowing, returning, rental tracking, and fine calculation.

The system follows a **microservices architecture**, where each major
business functionality is implemented as an independent Spring Boot
service. Eureka provides service discovery, while the API Gateway acts
as the single entry point for client requests.

### Problem Addressed

Traditional library systems can face challenges such as:

-   Tracking book availability accurately
-   Preventing duplicate borrowing
-   Managing issued and returned books
-   Calculating overdue fines
-   Maintaining authentication and access control
-   Managing tightly coupled application modules

Bibliotech addresses these requirements through independently managed
microservices.

### Team Members
  1. Mahidhar V  - 2400030985
  2. Tanveer Shaik  - 2400030274
  3. Rakesh B  - 2400031229
     
------------------------------------------------------------------------

## Objectives

-   Develop a modular library circulation platform using microservices.
-   Manage books and available copies.
-   Manage borrowing and returning of books.
-   Prevent duplicate active borrowing.
-   Calculate fines for overdue returns.
-   Implement JWT-based authentication.
-   Provide service discovery using Eureka.
-   Route client requests through an API Gateway.
-   Enable communication between independent microservices.
-   Maintain separate databases for major business services.

------------------------------------------------------------------------

## System Architecture

The project consists of the following components:

  Component          Port Responsibility
  ---------------- ------ ----------------------------------------
  Eureka Server      8761 Service discovery and registration
  API Gateway        8080 Single entry point and request routing
  Auth Service       8081 User authentication and JWT
  Book Service       8082 Book inventory management
  Rental Service     8083 Borrowing and returning
  Fine Service       8084 Fine calculation and management

### Architecture Flow

``` text
                         CLIENT
                           |
                           v
                    +--------------+
                    | API GATEWAY  |
                    |    :8080     |
                    +------+-------+
                           |
          +----------------+----------------+
          |                |                |
          v                v                v
    +-----------+    +-----------+    +-------------+
    |   AUTH    |    |   BOOK    |    |   RENTAL    |
    |  :8081    |    |  :8082    |    |   :8083     |
    +-----------+    +-----------+    +------+------+
                                             |
                                             v
                                      +-------------+
                                      |    FINE     |
                                      |    :8084    |
                                      +-------------+

                    +----------------+
                    | EUREKA SERVER  |
                    |     :8761      |
                    +----------------+
```

All services register with Eureka and can discover other services using
their registered service names.

------------------------------------------------------------------------

## Technology Stack

### Backend

-   Java 21
-   Spring Boot 4.1.1
-   Spring MVC
-   Spring Data JPA
-   Spring Security
-   Spring Cloud

### Microservices & Cloud-Native Components

-   Spring Cloud Netflix Eureka
-   Spring Cloud Gateway
-   Spring Cloud LoadBalancer
-   REST APIs
-   JWT authentication

### Database

-   MySQL
-   Hibernate / JPA

### Development Tools

-   IntelliJ IDEA / Spring Tool Suite / Eclipse
-   Maven
-   Git
-   GitHub
-   Postman

------------------------------------------------------------------------

## Microservices

### 1. Eureka Server

**Port:** `8761`

Eureka Server acts as the service registry. All application services
register themselves with Eureka so that services can discover each other
dynamically.

------------------------------------------------------------------------

### 2. API Gateway

**Port:** `8080`

The API Gateway provides a single entry point for client requests.

Configured routes:

``` text
/auth/**      → AUTH-SERVICE
/books/**     → BOOK-SERVICE
/rentals/**   → RENTAL-SERVICE
/fines/**     → FINE-SERVICE
```

The Gateway uses service discovery and load-balanced routing.

------------------------------------------------------------------------

### 3. Auth Service

**Port:** `8081`

Responsibilities:

-   User registration
-   User login
-   BCrypt password hashing
-   JWT generation
-   JWT validation
-   Protected API access

Authentication flow:

``` text
User
  |
  v
Login
  |
  v
Auth Service
  |
  v
Verify Credentials
  |
  v
Generate JWT
  |
  v
Client
  |
  v
Bearer Token
  |
  v
Protected API
```

------------------------------------------------------------------------

### 4. Book Service

**Port:** `8082`

Responsibilities:

-   Add books
-   View books
-   View book by ID
-   Update books
-   Delete books
-   Track available copies
-   Borrow book operation
-   Return book operation

Main endpoints:

``` text
POST   /books/add
GET    /books/show
GET    /books/showby/{id}
PUT    /books/updateby/{id}
DELETE /books/delete/{id}
PUT    /books/borrow/{id}
PUT    /books/return/{id}
```

------------------------------------------------------------------------

### 5. Rental Service

**Port:** `8083`

Responsibilities:

-   Create rentals
-   View rentals
-   Update rentals
-   Delete rentals
-   Return books
-   Prevent duplicate active borrowing
-   Communicate with Book Service
-   Communicate with Fine Service

Main endpoints:

``` text
POST   /rentals/add
GET    /rentals/show
GET    /rentals/showby/{id}
PUT    /rentals/updateby/{id}
DELETE /rentals/delete/{id}
PUT    /rentals/return/{id}
```

### Borrowing Flow

``` text
Client
  |
  v
Rental Service
  |
  +----> Check existing active rental
  |
  +----> Book Service
            |
            +----> Check available copies
            |
            +----> Decrease available copies
  |
  v
Save Rental
```

An active rental is identified using the user ID, book ID, and a `null`
return date. This prevents the same user from having duplicate active
rentals for the same book.

------------------------------------------------------------------------

### 6. Fine Service

**Port:** `8084`

Responsibilities:

-   Add fine
-   View fines
-   Update fines
-   Delete fines
-   Calculate overdue fines
-   Store fine details

Main endpoints:

``` text
POST   /fines/add
GET    /fines/show
GET    /fines/showby/{id}
PUT    /fines/updateby/{id}
DELETE /fines/delete/{id}
POST   /fines/calculate/{rentalId}
```

### Fine Calculation

The current implementation uses:

``` text
Allowed rental period = 7 days
Fine per overdue day = ₹10

Fine = Overdue Days × ₹10
```

Example:

``` text
Issue Date  : September 1
Due Date    : September 8
Return Date : September 11

Overdue Days = 3
Fine = 3 × ₹10
     = ₹30
```

------------------------------------------------------------------------

## Inter-Service Communication

The project demonstrates communication between independent
microservices.

### Rental → Book

During borrowing:

``` text
Rental Service
      |
      v
Book Service
      |
      v
Check Availability
      |
      v
Decrease Available Copies
```

During returning:

``` text
Rental Service
      |
      +------> Book Service
      |           |
      |           +--> Increase Available Copies
      |
      +------> Fine Service
                  |
                  +--> Calculate Fine
                  |
                  +--> Save Fine
```

A load-balanced `RestTemplate` is used for service-to-service
communication.

------------------------------------------------------------------------

## Database Design

Each major business service has its own MySQL database.

``` text
Auth Service    → bibliotech_auth
Book Service    → bibliotech_book
Rental Service  → bibliotech_rental
Fine Service    → bibliotech_fine
```

This keeps service data separated and reduces direct database coupling
between services.

------------------------------------------------------------------------

## Security

JWT-based authentication is implemented in the Auth Service.

Passwords are encoded using BCrypt before being stored.

Example protected request:

``` text
Authorization: Bearer <JWT_TOKEN>
```

Public authentication endpoints include:

``` text
POST /auth/register
POST /auth/login
```

Protected endpoints require authentication.

> For deployment, database passwords and JWT secrets should be supplied
> through environment variables rather than committed to GitHub.

------------------------------------------------------------------------

## Key Features

-   Microservices-based architecture
-   Eureka service discovery
-   API Gateway routing
-   JWT authentication
-   BCrypt password hashing
-   Independent MySQL databases
-   Book inventory management
-   Available-copy tracking
-   Rental management
-   Duplicate borrowing prevention
-   Book return processing
-   Automatic fine calculation
-   Rental-to-Book communication
-   Rental-to-Fine communication
-   Load-balanced service communication

------------------------------------------------------------------------

## Project Structure

``` text
PS021-Bibliotech/
│
├── eureka-server/
│   └── src/
│
├── api-gateway/
│   └── src/
│
├── auth-service/
│   └── src/
│
├── book-service/
│   └── src/
│
├── rental-service/
│   └── src/
│
├── fine-service/
│   └── src/
│
└── README.md
```

Each service is an independent Maven/Spring Boot project.

------------------------------------------------------------------------

## Running the Project Locally

### Prerequisites

Install:

-   Java 21
-   Maven
-   MySQL
-   Git

### Create Databases

Create the required MySQL databases:

``` sql
CREATE DATABASE bibliotech_auth;
CREATE DATABASE bibliotech_book;
CREATE DATABASE bibliotech_rental;
CREATE DATABASE bibliotech_fine;
```

Update the database username and password in each service's
`application.properties`.

### Start Services

Start the services in the following order:

``` text
1. Eureka Server       → 8761
2. Auth Service        → 8081
3. Book Service        → 8082
4. Rental Service      → 8083
5. Fine Service        → 8084
6. API Gateway         → 8080
```

Open the Eureka dashboard:

``` text
http://localhost:8761
```

The registered services should appear with status `UP`.

------------------------------------------------------------------------

## Testing

The system was tested using REST API requests through Postman.

Important test scenarios include:

### Authentication

-   User registration
-   User login
-   JWT generation
-   Protected endpoint access

### Books

-   Add book
-   View book
-   Update book
-   Delete book
-   Borrow book
-   Return book

### Rentals

-   Create rental
-   Prevent duplicate borrowing
-   Return rental
-   Track return date

### Fines

-   Calculate fine
-   Store fine
-   View fine

### Integration

The complete return flow is:

``` text
Client
  |
  v
API Gateway
  |
  v
Rental Service
  |
  +----> Book Service
  |         |
  |         +--> Update available copies
  |
  +----> Fine Service
            |
            +--> Calculate and store fine
```

------------------------------------------------------------------------

## Example API Flow Through Gateway

Instead of directly accessing individual services, the client can use
the API Gateway:

``` text
http://localhost:8080/auth/...
http://localhost:8080/books/...
http://localhost:8080/rentals/...
http://localhost:8080/fines/...
```

The Gateway discovers and routes the request to the appropriate service.

------------------------------------------------------------------------

## Learning Outcomes

This project provided practical experience in:

-   Microservices architecture
-   Spring Boot application development
-   REST API development
-   Service discovery
-   API Gateway configuration
-   JWT authentication
-   BCrypt password security
-   Inter-service communication
-   Database integration
-   Load-balanced service calls
-   Distributed application design

------------------------------------------------------------------------

## Future Enhancements

Possible future improvements include:

-   Email/SMS notifications for overdue books
-   Role-based authorization for administrators and users
-   Scheduled overdue detection
-   Centralized logging
-   Prometheus and Grafana monitoring
-   Circuit breaker and fault-tolerance mechanisms
-   Docker containerization
-   Kubernetes deployment
-   Cloud deployment
-   Advanced library analytics

------------------------------------------------------------------------

## Project

**Project:** PS021 -- Bibliotech Circulation Management Platform

**Architecture:** Microservices

**Backend:** Spring Boot + Spring Cloud

**Database:** MySQL

**Authentication:** JWT + BCrypt

**Service Discovery:** Eureka

**Gateway:** Spring Cloud Gateway

------------------------------------------------------------------------

## License

This project under developing as an academic project for educational
purposes.
