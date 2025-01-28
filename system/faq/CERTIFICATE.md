# Self-signed certificates

## Self-signed certificate example for Tomcat server

Command to generate a private key for the certificate:

```Bash
openssl genpkey -algorithm RSA -out example.local.key -aes256
```

Set your password.

Generating a CSR (Certificate Signing Request) for the domain example.local and creating the `san.cnf` file:

```Bash
openssl req -new -key example.local.key -out example.local.csr -subj "/CN=example.local"
nano san.cnf
```

Example `san.cnf` content:

```Bash
[ req ]
default_bits = 2048
distinguished_name = req_distinguished_name
req_extensions = req_ext
x509_extensions = v3_req
prompt = no

[ req_distinguished_name ]
C = XX
ST = ExampleState
L = ExampleCity
O = ExampleOrg
OU = ExampleDept
CN = example.local

[ req_ext ]
subjectAltName = @alt_names

[ v3_req ]
subjectAltName = @alt_names

[ alt_names ]
DNS.1 = example.local
```

Command to generate the CSR using the configuration, followed by generating the self-signed certificate:

```Bash
openssl req -new -key example.local.key -out example.local.csr -config san.cnf
openssl x509 -req -days 1000 -in example.local.csr -signkey example.local.key -out example.local.crt -extensions v3_req -extfile san.cnf
```

Exporting the key to PKCS#12 format:

```Bash
openssl pkcs12 -export -in example.local.crt -inkey example.local.key -out example.local.p12 -name example
```

Set the password; in example, was used the default keytool password `changeit`.

Converting the certificate to JKS format for the Tomcat server:

```Bash
keytool -importkeystore -deststorepass changeit -destkeypass changeit -destkeystore /var/lib/tomcat10/conf/tomcat.jks \
-srckeystore example.local.p12 -srcstoretype PKCS12 -srcstorepass changeit -alias example
```

## Self-signed certificate example for Apache2 server

Generate a self-signed certificate:

```Bash
sudo openssl req -x509 -nodes -days 3365 -newkey rsa:2048 -keyout /etc/ssl/private/andromeda-frontend-selfsigned.key -out /etc/ssl/certs/andromeda-frontend-selfsigned.crt
```

-x509: Specifies an X.509 certificate.
• -days 365: The certificate will be valid for one year.
• -keyout: Path to the private key.
• -out: Path to the certificate file.

Configure Apache to use the certificate: In the VirtualHost file
/etc/apache2/sites-enabled/000-default.conf

add following lines

```XML
<VirtualHost *:443>
    ServerName example.local

    DocumentRoot /var/www/html

    SSLEngine on
    SSLCertificateFile /etc/ssl/certs/andromeda-frontend-selfsigned.crt
    SSLCertificateKeyFile /etc/ssl/private/andromeda-frontend-selfsigned.key

    <Directory "/var/www/html">
        Options Indexes FollowSymLinks
        AllowOverride All
        Require all granted

        RewriteEngine on

        # Redirect only root "/" requests to "/nebula/app"
        RewriteCond %{REQUEST_URI} ^/$
        RewriteRule ^ /nebula/app [R=301,L]
    </Directory>
</VirtualHost>
```
Then restart service:

```Bash
sudo systemctl restart apache2
```
