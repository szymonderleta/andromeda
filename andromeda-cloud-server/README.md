Andromeda Cloud Server
Overview

Andromeda Cloud Server is a robust application built on Spring Cloud, Spring Security and Java 21, designed to securely store encrypted passwords, logins, and addresses for use by other applications. This server provides a centralized and reliable solution for managing sensitive information, offering enhanced security features through encryption mechanisms.
Features

    Secure Storage: Andromeda Cloud Server ensures the safekeeping of sensitive data by utilizing advanced encryption techniques, providing an extra layer of protection against unauthorized access.

    Spring Cloud Architecture: The application is constructed with the powerful Spring Cloud framework, ensuring scalability, resilience, and efficient communication between microservices.

    Java 21: Leveraging the latest advancements in the Java programming language, Andromeda Cloud Server benefits from improved performance, language features, and security enhancements.

Getting Started

To deploy Andromeda Cloud Server, follow these steps:

    Prerequisites: Ensure you have Java 21 installed on your system.

    Clone the Repository: Clone the Andromeda Cloud Server repository to your local machine.

    bash

git clone https://github.com/szymonderleta/andromeda-cloud-server.git

Build and Run: Navigate to the project directory and build the application using your favorite build tool (e.g., Maven or Gradle). Run the server to start providing secure storage services.

bash

    cd andromeda-cloud-server
    mvn clean install
    java -jar target/andromeda-cloud-server.jar

    Integrate with Other Applications: Utilize the provided API documentation to integrate Andromeda Cloud Server with your applications securely.

Documentation

To configure_and customize the Andromeda Cloud Server, you will need to modify the `application.properties` file located
in the `src/main/resources` directory. Below are examples of configuration settings that can be included in the file:

```properties
# Server Configuration
server.port=8888
# Security Configuration
spring.security.user.name=root
spring.security.user.password=_k3vKj$8qR*mNx!2
# Application Name
spring.application.name=cloud-server
# Active Profiles
spring.profiles.active=native
# Spring Cloud Config Server - Native Mode
spring.cloud.config.server.native.searchLocations=/etc/config-repo
```

### Explanation:

1. **Server Configuration**

    - `server.port=8888`: This property sets the port on which the Andromeda Cloud Server will run. In this case, it is
      configured to run on port 8888 instead of the default port 8080.

2. **Security Configuration**

    - `spring.security.user.name=root`: This sets the default username for basic security authentication.
    - `spring.security.user.password=_k3vKj$8qR*mNx!2`: This sets the password for the default user. Ensure that you
      keep this password secure and do not expose it in public repositories.

3. **Application Name**

    - `spring.application.name=cloud-server`: This sets the name of the Spring application to "cloud-server".

4. **Active Profiles**

    - `spring.profiles.active=native`: This specifies the active profile for the application. In this case, the "native"
      profile is active, which typically means that the configuration properties will be loaded from a local file system
      repository.

5. **Spring Cloud Config Server - Native Mode**

    - `spring.cloud.config.server.native.searchLocations=/etc/config-repo`: When using Spring Cloud Config Server in
      native mode, this property defines the location where the configuration files are stored. Here, it points to the
      directory `/etc/config-repo`.

Save the `application.properties` file after making your changes. Restart the server to apply the new configurations.

For further customization options and detailed documentation, please refer to
the [Spring Boot documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html).

Detailed documentation, including API specifications and usage guidelines, can be found in the docs directory of this repository.
Contribution

We welcome contributions to enhance the functionality, security, and overall performance of Andromeda Cloud Server. Feel free to open issues, submit pull requests, or engage in discussions to help make this project even better.

Thank you for choosing Andromeda Cloud Server for your secure storage needs!


