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
- **Apache2 HTTP Server**: Handles front-end pages and static resources.

## FAQ

- **[ENVIRONMENTS.md](faq/ENVIRONMENTS.md)**: Documentation about system environments.
- **[CERTIFICATE.md](faq/CERTIFICATE.md)**: Documentation about self-signed certificates used in Andromeda.
- **[HTTPS.md](faq/HTTPS.md)**: Documentation about how configure https connections.
- **[DATABASE.md](faq/DATABASE.md)**: Documentation about database system and basic configuration.
- **[TOMCAT.md](faq/TOMCAT.md)**: Documentation about Apache Tomcat server and basic configuration.
- **[SSH.md](faq/SSH.md)**: Documentation about connections via SSH.
- **[SOLUTIONS.md](faq/SOLUTIONS.md)**: Documentation about known problems and solutions.
- **[BACKUP.md](faq/BACKUP.md)**: Documentation about backups and maintenance.

## License
This project is distributed under the Apache 2.0 license.
