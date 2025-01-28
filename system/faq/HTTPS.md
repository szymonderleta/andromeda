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
