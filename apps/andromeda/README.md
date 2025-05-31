# 🌌 Andromeda Family Apps

This repository contains all applications that are part of the **Andromeda Project**. Each application has a specific role and works in harmony with others to deliver the full functionality of the system.

---

## 📁 Folder Structure

- **`/andromeda-cloud-server/`**  
  Java 21 application built with Spring Cloud, acting as a centralized cloud configuration server. It is responsible for storing and distributing configuration settings across other Andromeda applications.

- **`/andromeda-auth-server/`**  
  Java 21 application developed with Spring Boot and JDBC. This module provides:
    - Authentication and authorization
    - Token generation and management
    - Email services
    - User credential creation and updates

---

## 🧪 Technologies Used

- **Backend**  
  ![Java](https://img.shields.io/badge/-Java-orange?style=flat-square&logo=java) Java 21, Spring Boot, Spring Cloud, Hibernate ORM, JDBC, Lombok

- **Inter-service Communication**  
  REST API

---

## 📄 License

This project is licensed under the [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0) license.
