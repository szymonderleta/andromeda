# System Information

## About the Project

Andromeda system is a system built on the Raspberry Pi 5 platform. The system consists of databases and a collection of
microservices working both as REST applications and web applications.

## Raspberry Pi 

The Andromeda platform consists of a Raspberry Pi 5 computer, Debian 12 operating system, MariaDB database, Apache
Tomcat server, and a set of applications described in subsequent chapters of the documentation.

The Raspberry Pi platform specification is available on the manufacturer's website
at https://www.raspberrypi.com/documentation/computers/raspberry-pi-5.html. The Andromeda platform uses a Raspberry Pi
version 5 equipped with 8GB of RAM and powered by a wall charger provided by the manufacturer.

## Logical model
Under build, image that represents used services and major apps.

## Main Services
- **MariaDB 11**: Relational database management system.
- **Apache Tomcat 10**: Application server running on JRE 21.
- **Apache HTTP Server**: Handles front-end pages and static resources.

## FAQ
- **[ENVIRONMENTS.md](ENVIRONMENTS.md)**: Documentation about system environments.

## License
This project is distributed under the Apache 2.0 license.