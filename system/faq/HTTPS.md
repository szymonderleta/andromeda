# HTTPS Configuration

## Apache Tomcat HTTPS Configuration:

Refer to [CERTIFICATE.md](CERTIFICATE.md)
and then edit the server.xml file located at /var/lib/tomcat10. 

HTTPS connection configuration, /var/lib/tomcat10/server.xml file:
```Bash
<Connector port="8555" protocol="org.apache.coyote.http11.Http11NioProtocol"
           maxThreads="150" SSLEnabled="true">
    <SSLHostConfig>
        <Certificate certificateKeystoreFile="/opt/cert/tomcat.jks"
                     type="RSA" />
    </SSLHostConfig>
</Connector>
```

After restarting the Tomcat service/server – this can be done using the command
```Bash
sudo systemctl restart tomcat
```

the services will be available at example: https://example.local:8555/.

## REACT localhost https:

### 1. **Generate a self-signed SSL certificate**

#### On Linux/Ubuntu (or any system with OpenSSL):

1. Open a terminal and execute the commands to generate keys and certificates:

``` bash
   mkdir ssl && cd ssl

   # Generate a private key
   openssl genrsa -out localhost.key 2048

   # Generate a self-signed certificate
   openssl req -new -x509 -key localhost.key -out localhost.crt -days 365 \
   -subj "/C=PL/ST=Test/L=Test/O=Test/OU=Test/CN=localhost"
```

**Result**: there will be two files:

- `localhost.key` (private key)
- `localhost.crt` (self-signed certificate)

If a `.pem` file is required, you can convert the files manually.

### 2. **Configuring a local server for HTTPS**

#### 2.1. **Node.js/Express**

If you are using Node.js with Express, you can easily configure HTTPS using the generated files:

1. Install `https` in your project:

```bash
   npm install https
```

#### 2.2a. **Using HTTPS in React (or `react-scripts`)**

If you are working on a React application running with `react-scripts`, you can enforce HTTPS on the development server.

1. In the `.env` file located in the root directory of your project, add:

```plaintext
   HTTPS=true
   SSL_CRT_FILE=./ssl/localhost.crt
   SSL_KEY_FILE=./ssl/localhost.key
```

Set the appropriate path to the certificate (`.crt`) and key (`.key`).

#### 2.2b. **Configuring HTTPS without an `.env` file**

You can enforce HTTPS in a React application or other applications without creating an `.env` file by modifying the
application's server configuration in the `package.json` file or other relevant configuration files.

1. Add HTTPS flags (React-Scripts or dev server):
   If you are using `react-scripts`, you can enable HTTPS without `.env` by adding environment variables to the script
   startup command in `package.json`. Open the `package.json` file and update the `scripts` section:

```json
"scripts": {
  "start": "HTTPS=true SSL_CRT_FILE=./ssl/localhost.crt SSL_KEY_FILE=./ssl/localhost.key react-scripts start",
  "build": "react-scripts build",
  "test": "react-scripts test",
  "eject": "react-scripts eject"
}
```

Now, after running the `npm start` command, the application will automatically use HTTPS with the certificates.

### 3. **Adding the certificate to trusted ones (optional for DEV)**

If you want to avoid warnings in the browser:

1. Install the self-signed certificate as trusted:
    - For Linux systems:

```bash
     sudo cp localhost.crt /usr/local/share/ca-certificates/
     sudo update-ca-certificates
```

- For Windows or MacOS, you need to import the certificate into the trusted certificate store (Certificates →
  `Trusted Root Certification Authorities`).
