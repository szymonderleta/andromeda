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
