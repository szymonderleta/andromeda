# ☁️ Andromeda Cloud Server

<div align="left">

![Author](https://img.shields.io/badge/Author-Szymon%20Derleta-white?style=for-the-badge)

![Release](https://img.shields.io/badge/Release-Public%20Release-green?style=for-the-badge)  
![Version](https://img.shields.io/badge/Version-1.1.4-green?style=for-the-badge)

📄 Changelog: N/A  
🔗 Repository: [src](src)

</div>

---

## Overview

Andromeda Cloud Server is a robust application built with **Spring Cloud**, **Spring Security**, and **Java 21**.  
It securely stores encrypted passwords, logins, and addresses to be used by other applications in the Andromeda ecosystem.

This server provides a centralized, reliable solution for managing sensitive information, enhanced by strong encryption mechanisms.

---

## Features

- **Secure Storage**  
  Utilizes advanced encryption techniques to protect sensitive data from unauthorized access.

- **Spring Cloud Architecture**  
  Built on Spring Cloud to ensure scalability, resilience, and efficient communication between microservices.

- **Java 21**  
  Leverages the latest Java features and performance improvements.

---

## Getting Started

**Note:** The standalone repository for Andromeda Cloud Server is currently not available.  
However, the full source code is included within this repository under the `src` directory.

---
## Documentation

To configure and customize the Andromeda Cloud Server, modify the `application.properties` file located in the  
`src/main/resources` directory. Below is an example configuration with commonly used settings:

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

Configuration Explanation

    Server Configuration

        server.port=8888
        Sets the port on which Andromeda Cloud Server runs. The default port (8080) is overridden here to 8888.

    Security Configuration

        spring.security.user.name=root
        Default username for basic authentication.

        spring.security.user.password=_k3vKj$8qR*mNx!2
        Default password for the user. Keep this password secure and avoid exposing it in public repositories.

    Application Name

        spring.application.name=cloud-server
        Defines the Spring application’s name.

    Active Profiles

        spring.profiles.active=native
        Activates the native profile, meaning configuration properties will be loaded from a local file system repository.

    Spring Cloud Config Server - Native Mode

        spring.cloud.config.server.native.searchLocations=/etc/config-repo
        Specifies the directory where configuration files are stored when using Spring Cloud Config Server in native mode.

After making changes to application.properties, save the file and restart the server to apply the new settings.

For further customization options and detailed documentation, please refer to the  
[Spring Boot documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html).

