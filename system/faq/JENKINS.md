## **Installing Jenkins on Debian/Ubuntu**

# SECTION_IS_UNDER_BUILD

### **1. Prerequisites**

Before starting the installation, ensure that:

- You have access to a user with `sudo` permissions.
- Your system has **Java** installed, as Jenkins requires a JVM environment.

### **2. Installing Java**

Jenkins requires Java 11 or newer. You can install Java 11 or the latest version available in the repositories:

``` bash
sudo apt update
sudo apt install openjdk-11-jdk -y
```

Check if Java has been installed correctly:

``` bash
java -version
```

