# Apache2 Information

## About

Apache2 is an open-source, cross-platform web server used to serve websites and applications.
It supports a wide range of features, such as customizable modules, virtual hosting, and dynamic content handling,
making it highly flexible and widely used.

## Installation

The Andromeda project is based on Apache2, which can be installed using the command:

```Bash
sudo apt install apache2
```

After installing the Apache2 server, the main page is available at the following addresses:

example.local  
http://example.local:80

The Apache server documentation is available in the file /usr/share/doc/apache2/README.

## Configuration

### Change main page

```Bash
cp /home/root/index.html /var/www/html/index.html
```

How to send files via SCP or connect using SSH can be found in [SSH Guide](SSH.md).

### Uploading REACT apps

To correctly deploy a React application on an Apache server, you need to perform several steps. If the site will not be
placed in the main directory, first, you need to set the correct path in the package.json file, which is located in the
project's main directory. For example, to add a subpage at the address http://example.local/nebula/build, you need to
add the following line to the package.json file:

```properties
"name": "nebula app",
"homepage": "http://example.local/nebula/build",
"version": "1.0.0"
... rest body
```

then need to build app using npm:

```Bash
npm run build
```

Now you just need to copy the project to the directory specified in the package.json file.
Below is an example of copying the project using scp:

```Bash
scp -r /home/guest/projects/nebula-test-project/build root@example.local:/home/root
sudo cp -r /home/root/build /var/www/html/nebula
```

### Loading Images in a React Application

To enable loading of images by a React application (e.g., a test version running on localhost), you need to properly
configure this on the Apache server.
To do this, edit the file `/etc/apache2/apache2.conf`:

```Bash
sudo nano /etc/apache2/apache2.conf
```

then adding:

```xml
<Directory /var/www/html/user>
    Require all granted
    AllowOverride all
    Header set Access-Control-Allow-Origin "*"
</Directory>
```

this will allow loading images and other materials placed in the /var/www/html/user subdirectory and solve the problem
with the CORS message, but sources should be shared carefully.

It is also necessary to enable the headers module:

```Bash
a2enmod headers 
```

and restarting Apache2 server:

```Bash
/etc/init.d/apache2 restart
```

The problem is described on the page:
https://stackoverflow.com/questions/29150384/how-to-allow-cross-domain-request-in-apache2.

### Enable Https

To settings HTTPS see [how configure HTTPS.md](HTTPS.md) and [How configure self-signed certificate](CERTIFICATE.md)
sections.

Apache wymaga zainstalowania modułu mod_ssl do obsługi HTTPS. Możesz to zrobić następująco:

```Bash
sudo a2enmod ssl
sudo systemctl restart apache2
```


