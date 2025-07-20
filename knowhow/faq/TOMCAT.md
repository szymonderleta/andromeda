# Apache Tomcat Information

## About

Apache Tomcat is an open-source implementation of the Java Servlet, JavaServer Pages (JSP), and WebSocket technologies.
It is developed by the Apache Software Foundation and is widely used to deploy Java-based web applications. Tomcat is
lightweight and easy to configure, making it popular for both development and production environments.

### Key Features:

- Serves Java Servlets and JSP.
- Supports HTTP/2 and WebSocket protocols.
- Highly configurable and extensible with modules.
- Cross-platform and integrates well with other Java frameworks.

### Typical Use Cases:

- Executing Java web applications.
- Hosting web services.
- Serving as a backend for APIs.

## Requirements and Installation

Apache Tomcat requires Java. To install Java 17, you can use the following command:

```Bash
sudo apt install openjdk-17-jre
```

To install Java 21 (used by Andromeda), download and extract it with:

```Bash
wget https://download.java.net/java/GA/jdk21.0.1/415e3f918a1f4062a0074a2794853d0d/12/GPL/openjdk-21.0.1_linux-aarch64_bin.tar.gz
```

Instructions for installing and switching between Java environment versions are available at:
[Installing Java OpenJDK from tar.gz](https://kilishek.com/2021/05/05/installing-java-openjdk-from-tar-gz-archive-and-update-the-default-jdk-used/).

To install Apache Tomcat 10, run the following command:

```Bash
sudo apt install tomcat10
```

## Configuration:

For the proper operation of the Apache Tomcat server, configuration is required, including the creation of user accounts
so that server applications can be deployed. To do this, edit the file `/etc/tomcat10/tomcat-users.xml` by adding the
appropriate lines of code.

**Update the `tomcat-users.xml` file:**

Run the following command to edit the file:

   ```bash
   sudo nano /etc/tomcat10/tomcat-users.xml
   ```

Add the necessary user roles and credentials. Example:

   ```xml

<role rolename="manager-gui"/>
<user username="admin" password="your_password" roles="manager-gui"/>
   ```

It may also be necessary to change the Java version by editing the file `/etc/default/tomcat10` and adding the
`JAVA_HOME` variable.

**Adding environment for Tomcat apps:**

To add environment, you need to edit the setenv.sh file, for example:

```bash
 sudo nano /usr/share/tomcat10/bin/setenv.sh
```

 and example file body:

```bash
   export MILKYWAY_TOMCAT_URL=https://localhost:8555
   export SPRING_CLOUD_CONFIG_USERNAME=administrator
   export SPRING_CLOUD_CONFIG_PASSWORD=<YourSecretAdministratorPass>
```

then press crtl+X and save a file.

**Update Java version if required:**

Open the file `/etc/default/tomcat10`:

   ```bash
   sudo nano /etc/default/tomcat10
   ```

Add the `JAVA_HOME` variable pointing to your Java installation. Example:

```Bash
JAVA_HOME="/opt/jdk-21.0.1"
```

By default, the Apache Tomcat server is designed to work on port 8081. To
configure this, you need to modify the file `/etc/tomcat10/server.xml`.

Modify the file `/etc/tomcat10/server.xml`:

   ```bash
   sudo nano /etc/tomcat10/server.xml
   ```

Locate the following line:

   ```xml

<Connector port="8080" protocol="HTTP/1.1"
   ```

And change the port from `8080` to `8081`. Example:

   ```xml

<Connector port="8081" protocol="HTTP/1.1"
   ```

After performing the correct configuration, you can access the administration panel at the following address:  
[https://example.local:8081/manager/html](https://example.local:8081/manager/html).

Note: Ensure that HTTPS is properly configured for secure access.

To settings HTTPS see [how configure HTTPS.md](HTTPS.md) and [How configure self-signed certificate](CERTIFICATE.md)
sections.

## Logging

### Tomcat setup

To enable displaying logs from the application, it is necessary to edit the file /var/lib/tomcat10/conf/context.xml

```Bash
cd /var/lib/tomcat10/conf
sudo nano context.xml
```

Detailed logs can be enabled by adding the parameter swallowOutput="true" to the Context component.

```xml

<Context swallowOutput="true">
    <!-- body  -->
</Context>
```

### Java aplication setup

In the application itself, create a logging.properties file in the webapp/WEB-INF/classes directory and add appropriate
configuration, example for the Andromeda Authorization Server application:

```properties
handlers = org.apache.juli.FileHandler, java.util.logging.ConsoleHandler

############################################################
# Handler specific properties.
# Describes specific configuration info for Handlers.
############################################################

org.apache.juli.FileHandler.level = FINE
org.apache.juli.FileHandler.directory = ${catalina.base}/logs
org.apache.juli.FileHandler.prefix = ${classloader.webappName}.

java.util.logging.ConsoleHandler.level = FINE
java.util.logging.ConsoleHandler.formatter = java.util.logging.OneLineFormatter
```

## WAR size

Changing the size of the transferred WAR file can be done by editing the file
/usr/share/tomcat10-admin/manager/WEB-INF/web.xml

### Example File Configuration

Below is an example configuration file for editing `/usr/share/tomcat10-admin/manager/WEB-INF/web.xml` to increase the
maximum allowed WAR file size:

```xml

<multipart-config>
    <!-- Set the maximum allowed file size (e.g., 50MB) -->
    <max-file-size>52428800</max-file-size>
    <!-- Set the maximum size of the request, including file and form data -->
    <max-request-size>52428800</max-request-size>
    <!-- Threshold after which files will be written to disk -->
    <file-size-threshold>0</file-size-threshold>
</multipart-config>
```

This configuration ensures that the manager application accepts larger WAR files for deployment.

## File writing in other paths

To enable file saving, e.g., avatars via REST API, it is necessary to modify write permissions. To do this, edit the
configuration file
/etc/systemd/system/tomcat10.service.d/override.conf

```bash
sudo systemctl edit tomcat10.service
```

Commands to allow saving avatars by the `nebula-rest-api` program running on the Tomcat10 server:
```Bash
[Service]
ReadWritePaths=/var/www/html/user/avatar/
```

Restarting the daemon is required:

```Bash
sudo systemctl daemon-reload
sudo systemctl restart tomcat10.service
```
