# Andromeda

## About
Andromeda is a microservices-based platform serving as local single-player, browser-accessible game service. The system is built using a Raspberry Pi 5 computer (8GB RAM model) with installed Debian 12 operating system. Therefore, it is based on the arm architecture. The applications were developed in several programming languages including:

- **Java, using the Spring and Hibernate libraries or only JDBC driver**
- **JavaScript, using the REACT library**
- **Python, using the Django library**

### The system is composed of the following services-components:

- **MariaDB 10.11.6 - relational database system**
- **Apache Tomcat 10 server for managing microservices written in Java (mainly version 21)**
- **Apache HTTP Server providing a set of subpages for the end user**

### Microservice ecosystem:

Each application and microservice has its dedicated directory containing source code, description of abilities, functions, database schema and means of communication.

### List of Applications with current status:

| Application Name                                                                                  | Version | Database                                                                                    | Status |
|---------------------------------------------------------------------------------------------------|---------|---------------------------------------------------------------------------------------------|--------|
| [Andromeda Auth](https://github.com/szymonderleta/andromeda/tree/main/apps/andromeda-auth-server) | 2.0.1   | [Andromeda Schema](https://github.com/szymonderleta/andromeda/tree/main/database/andromeda) | Beta   |
| [Nebula Rest Api](https://github.com/szymonderleta/andromeda/tree/main/apps/nebula-rest-api)      | 2.0.0   | [Nebula Schema](https://github.com/szymonderleta/andromeda/tree/main/database/nebula)       | Beta   |
| [Chess](https://github.com/szymonderleta/andromeda/tree/main/apps/chess)                          | 0.5     | [Chess Schema](https://github.com/szymonderleta/andromeda/tree/main/database/chess)         | WIP    |
| [Element](https://github.com/szymonderleta/andromeda/tree/main/apps/element)                      | 0.0     | [Element Schema](https://github.com/szymonderleta/andromeda/tree/main/database/element)     | To-Do  |


### Services Logic Layers

![andromeda-logic.drawio-2025-01-29-dark.png](res/andromeda-logic.drawio-2025-01-29-dark.png)


## Licence
Andromeda is an open-source project based on the Apache 2.0 license


