# Andromeda Authorization Server

Author: Szymon Derleta
Current Version: 2.0.1 Beta Release

## Overview

Andromeda Authorization Server is a robust and versatile application built with Java SDK 21 and the advanced Spring Boot
Framework 3.2. This server effectively manages access and authorization, prioritizing security with the use of JWT (JSON
Web Token) authentication. It also integrates seamlessly with MariaDB using JDBC connections and supports email
communication via Google accounts, offering an all-in-one solution for access control and user authentication. It can
also serve as an open-source example of Jakarta EE interoperability with Spring Data JDBC and Spring MVC, showcasing
modern enterprise-level application development.
Key Features

    1. JWT Token Authentication: Ensures enhanced security by implementing JSON Web Tokens, providing a reliable and secure authentication mechanism to safeguard applications from unauthorized access.

    2. MariaDB Integration: Effortlessly integrates with MariaDB using JDBC for efficient and secure management of access and authorization data.

    3. Email Services with Google Accounts: Comes equipped with a built-in email service utilizing Google accounts for convenient handling of password resets and activation link emails, simplifying the user account management experience.

    4. Jakarta EE and Spring Compatibility: Demonstrates seamless interoperability between Jakarta EE technologies and Spring, combining the strengths of both frameworks for scalable, secure enterprise applications.

    5. Enterprise-Level Scalability: Leverages modern Java SDK 21 features and advanced Spring Boot capabilities to provide a highly scalable and maintainable solution for handling access control and user authentication in enterprise systems.


## Main features

• determining access to a resource based on roles
• generating JWT tokens,
• registering new users,
• generating and sending automatic email messages with new passwords or activation links.


## Allowed applications

Andromeda is based on REST API, communication that requires the header "X-Requesting-App" in endpoint request. 

The list of allowed applications is defined as:

```xml
allowed.applications=nebula_rest_api,element_rest_api,chess_rest_api,robak_rest_api,racer_rest_api
```

## Endpoints Examples:

1. **POST request to login with username and get cookie with jwtToken**

   **Description:** Allows a user to log in using their username. The server responds with a JWT token set as a cookie.

   **Endpoint:**  
   `POST http://localhost:8087/api/v3/auth/login`

   **Headers:**
   - `Content-Type: application/json`
   - `X-Requesting-App: nebula_rest_api`

   **Request Body:**
    ```json
    {
        "login": "user",
        "password": "password"
    }
    ```

2. **POST request to login with email and get cookie with jwtToken**

   **Description:** Allows a user to log in using their email. The server responds with a JWT token set as a cookie.

   **Endpoint:**  
   `POST http://localhost:8087/api/v3/auth/login`

   **Headers:**
   - `Content-Type: application/json`
   - `X-Requesting-App: nebula_rest_api`

   **Request Body:**
    ```json
    {
        "login": "user@local.com",
        "password": "password"
    }
    ```

3. **GET request for token page (using cookie authorization)**

   **Description:** Fetches a paginated list of tokens. Authorization is handled via a JWT token included in the cookie.

   **Endpoint:**  
   `GET http://localhost:8087/api/v1/table/tokens?page=0&size=5`

   **Headers:**
   - `Content-Type: application/json`
   - `X-Requesting-App: nebula_rest_api`
   - `Cookie: jwtToken=<your_jwt_token>`

   **Sample Cookie:**
    ```
    jwtToken=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMSx4YnNtdnV6ZmF5eXpqeGR4YWtAY2twdHIuY29tIiwiaXNzIjoiRGJDb25uZWN0aW9uQXBwIiwicm9sZXMiOlt7ImlkIjoxLCJuYW1lIjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE3MzcxOTYxOTQsImV4cCI6MTczNzE5OTc5NH0.8cu9zV7EfnV3eo51O4t371JqZB4QMZDFdscp4PEEOjX_nbicVyei0rsRT4r9AuFOk_WGlyFodXYRqDMVYUC4OA
    ```

4. **GET request for roles by role name filter (using cookie authorization)**

   **Description:** Retrieves roles filtered by role name. Authorization is handled via a JWT token included in the
   cookie.

   **Endpoint:**  
   `GET http://localhost:8087/api/v1/table/roles?roleNameFilter=er`

   **Headers:**
   - `Content-Type: application/json`
   - `X-Requesting-App: nebula_rest_api`
   - `Cookie: jwtToken=<your_jwt_token>`

   **Sample Cookie:**
    ```
    jwtToken=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMSx4YnNtdnV6ZmF5eXpqeGR4YWtAY2twdHIuY29tIiwiaXNzIjoiRGJDb25uZWN0aW9uQXBwIiwicm9sZXMiOlt7ImlkIjoxLCJuYW1lIjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE3MzcxOTYxOTQsImV4cCI6MTczNzE5OTc5NH0.8cu9zV7EfnV3eo51O4t371JqZB4QMZDFdscp4PEEOjX_nbicVyei0rsRT4r9AuFOk_WGlyFodXYRqDMVYUC4OA
    ```
