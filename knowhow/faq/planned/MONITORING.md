# Monitoring Spring Boot REST APIs on Tomcat

This guide explains how to set up monitoring for Spring Boot REST API applications deployed on Tomcat, using Prometheus and Grafana. Instructions are provided for both a Raspberry Pi (bare metal) and a Docker-based setup on a laptop.

---

## Table of Contents

1. [Overview](#overview)
2. [Raspberry Pi Setup](#raspberry-pi-setup)

    * [Install Required Packages](#install-required-packages)
    * [Deploy Spring Boot WARs](#deploy-spring-boot-wars)
    * [Add Actuator and Prometheus Dependencies](#add-actuator-and-prometheus-dependencies)
    * [Reverse Proxy with Apache2](#reverse-proxy-with-apache2)
    * [Install Prometheus](#install-prometheus)
    * [Install Grafana](#install-grafana)
3. [Docker Laptop Setup](#docker-laptop-setup)

    * [Directory Structure](#directory-structure)
    * [Docker Compose Configuration](#docker-compose-configuration)
    * [Prometheus Configuration](#prometheus-configuration)
    * [Run the Stack](#run-the-stack)
4. [Comparison](#comparison)
5. [Security Tips](#security-tips)

---

## Overview

Monitoring architecture:

* **Spring Boot Actuator** for exposing application metrics.
* **Prometheus** for scraping metrics.
* **Grafana** for visualizing metrics.

---

## Raspberry Pi Setup

### Install Required Packages

```bash
sudo apt update && sudo apt install default-jdk tomcat9 apache2 unzip curl -y
```

### Deploy Spring Boot WARs

Copy your WAR files:

```bash
sudo cp app1.war app2.war /var/lib/tomcat9/webapps/
```

### Add Actuator and Prometheus Dependencies

In each app's `pom.xml`:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
  <groupId>io.micrometer</groupId>
  <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

In `application.properties`:

```properties
management.endpoints.web.exposure.include=prometheus
management.endpoint.prometheus.enabled=true
management.server.port=8080
```

### Reverse Proxy with Apache2

Edit `/etc/apache2/sites-available/000-default.conf`:

```apache
ProxyPass "/app1/actuator/prometheus" "http://localhost:8080/app1/actuator/prometheus"
<Location "/app1/actuator/prometheus">
    Require ip 192.168.0.0/24
</Location>

ProxyPass "/app2/actuator/prometheus" "http://localhost:8080/app2/actuator/prometheus"
<Location "/app2/actuator/prometheus">
    Require ip 192.168.0.0/24
</Location>
```

```bash
sudo systemctl restart apache2
```

### Install Prometheus

```bash
cd ~
wget https://github.com/prometheus/prometheus/releases/download/v2.50.0/prometheus-2.50.0.linux-armv7.tar.gz
tar -xvzf prometheus-2.50.0.linux-armv7.tar.gz
cd prometheus-2.50.0.linux-armv7
```

Create `prometheus.yml`:

```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'spring-app1'
    metrics_path: /app1/actuator/prometheus
    static_configs:
      - targets: ['localhost']

  - job_name: 'spring-app2'
    metrics_path: /app2/actuator/prometheus
    static_configs:
      - targets: ['localhost']
```

Run Prometheus:

```bash
./prometheus --config.file=prometheus.yml
```

### Install Grafana

```bash
sudo apt install -y apt-transport-https software-properties-common
sudo add-apt-repository "deb https://packages.grafana.com/oss/deb stable main"
wget -q -O - https://packages.grafana.com/gpg.key | sudo apt-key add -
sudo apt update && sudo apt install grafana -y
sudo systemctl enable --now grafana-server
```

Access Grafana at `http://raspberrypi.local:3000` (login: `admin` / `admin`)

---

## Docker Laptop Setup

### Directory Structure

```text
project/
├── app1/
├── app2/
├── prometheus/
│   └── prometheus.yml
├── docker-compose.yml
```

### Docker Compose Configuration

```yaml
version: "3.8"

services:
  app1:
    build: ./app1
    ports:
      - "8081:8080"

  app2:
    build: ./app2
    ports:
      - "8082:8080"

  prometheus:
    image: prom/prometheus
    volumes:
      - ./prometheus/prometheus.yml:/etc/prometheus/prometheus.yml
    ports:
      - "9090:9090"

  grafana:
    image: grafana/grafana
    ports:
      - "3000:3000"
    volumes:
      - grafana-storage:/var/lib/grafana

volumes:
  grafana-storage:
```

### Prometheus Configuration (`prometheus/prometheus.yml`)

```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'app1'
    metrics_path: /actuator/prometheus
    static_configs:
      - targets: ['app1:8080']

  - job_name: 'app2'
    metrics_path: /actuator/prometheus
    static_configs:
      - targets: ['app2:8080']
```

### Run the Stack

```bash
docker-compose up --build
```

Access:

* Prometheus: `http://localhost:9090`
* Grafana: `http://localhost:3000`

---

## Comparison

| Feature     | Raspberry Pi           | Laptop (Docker)             |
|-------------|------------------------|-----------------------------|
| Setup Type  | Manual (APT, services) | Automated (Docker Compose)  |
| Performance | Limited (ARM, low RAM) | High (multi-core, more RAM) |
| Scalability | Low                    | High                        |
| Updates     | Manual                 | Easy (image updates)        |
| Portability | Low                    | High                        |

---

## Security Tips

* Use `Require ip` or `AuthType Basic` in Apache to protect Actuator endpoints.
* Never expose full `/actuator/**` paths publicly.
* Use HTTPS with Apache for production.

---
