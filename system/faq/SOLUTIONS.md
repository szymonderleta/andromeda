# Problem Solutions

## Problem with processing confirmation links

The issue occurred when trying to access the address example.local/nebula/app/confirm/{tokenId}/{token}, instead of
generating the address, a 404 message appeared.

The solution is to add the following lines to the file /etc/apache2/sites-enabled/000-default.conf:

```XML
<Directory "/var/www/html/nebula/app">
    RewriteEngine on
    # Don't rewrite files or directories
    RewriteCond %{REQUEST_FILENAME} -f [OR]
    RewriteCond %{REQUEST_FILENAME} -d
    RewriteRule ^ - [L]
    # Rewrite everything else to index.html to allow html5 state links
    RewriteRule ^ index.html [L]
</Directory>
```

## Downloading a self-signed certificate to the local environment

Allows solving the problem of testing a locally developed Django/Python application when encountering an SSLERror at
/endpoint_addres.

![SSL Error](img/ssl_error.png)

You need to download a certificate issued to the specific host. For example, using Chromium, click on “not secure” →
“Certificate is not valid”:

![Show Certificate](img/show_certificate.png)
Then select “Export”:

![Export Certificate](img/export_certificat.png)

And save the certificate to disk.

Optional installation of the certificate on the local machine, as admin:

```Bash
sudo cp /home/user/Downloads/andromeda-tomcat-certificate.crt /usr/local/share/ca-certificates/
sudo update-ca-certificates
```

Installation in the Django application:

![Export to Django](img/export_django.png)

