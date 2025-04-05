# Andromeda Authorization Server

Author: Szymon Derleta  
Current Version: 3.0.0  
Repository: [GitHub - Andromeda Authorization Server](https://github.com/szymonderleta/andromeda-authorization-server-public)

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

## Building the Project

To build the Andromeda Authorization Server project, follow the steps below:

1. **Clone the Repository**  
   Open a terminal and clone the repository using the following command:
   ```bash
   git clone https://github.com/szymonderleta/andromeda-authorization-server-public.git
   ```
   Navigate to the project directory:
   ```bash
   cd andromeda-authorization-server-public
   ```

2. **Prerequisites**  
   Ensure you have the following installed on your system:
   - **Java SDK 21**  
     Download and install [Java SDK 21](https://jdk.java.net/21/) to support the project.
   - **Maven**  
     Install [Apache Maven](https://maven.apache.org/download.cgi) to manage project dependencies and builds.
   - **MariaDB**  
     Set up and run a MariaDB server that will be used for the database connection.

3. **Configure Application Properties**  
   Edit the `application.properties` file in the `src/main/resources` directory to provide your database and email
   configuration:
   ```properties
   spring.datasource.url=jdbc:mariadb://<your-database-host>:<port>/<database-name>
   spring.datasource.username=<your-database-username>
   spring.datasource.password=<your-database-password>

   # Google email configuration (for email services)
   spring.mail.username=<your-email@gmail.com>
   spring.mail.password=<your-email-password>
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.protocol=smtp
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   ```

   Replace the placeholders (e.g., `<your-database-host>`, `<your-email@gmail.com>`) with your actual configuration
   values.

4. **Build the Project**  
   Use Maven to package the application. Run the following command in the terminal within the project directory:
   ```bash
   mvn clean package
   ```
   This will generate a JAR file in the `target` directory.

5. **Run the Application**  
   Start the application using the following command:
   ```bash
   java -jar target/andromeda-authorization-server-3.0.0.jar
   ```
   Ensure that your MariaDB server is running and reachable.

6. **Access the Application**  
   The server will start by default on `http://localhost:8080`. You can update the port or other settings in the
   `application.properties` file if needed.

7. **Verify the Setup**  
   Test the API endpoints or access the server to ensure everything is configured correctly.
