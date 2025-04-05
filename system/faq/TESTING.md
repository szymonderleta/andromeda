# Testing CI/CD


## Common Problems

## To many test

Error:

java.sql.SQLNonTransientConnectionException: (conn=5702) Too many connections
at org.mariadb.jdbc.export.ExceptionFactory.createException(ExceptionFactory.java:300) ~[mariadb-java-client-3.3.2.jar:na]
at org.mariadb.jdbc.export.ExceptionFactory.create(ExceptionFactory.java:378) ~[mariadb-java-client-3.3.2.jar:na]
at org.mariadb.jdbc.client.impl.ConnectionHelper.authenticationHandler(ConnectionHelper.java:275) ~[mariadb-java-client-3.3.2.jar:na]
at org.mariadb.jdbc.client.impl.StandardClient.<init>(StandardClient.java:194) ~[mariadb-java-client-3.3.2.jar:na]

solution:

If the tests are correct but require a large number of connections, increase the connection limit in MariaDB:

1. Log in to the database:

``` bash
   mysql -u root -p
```

1. Check the current limit:

``` sql
   SHOW VARIABLES LIKE 'max_connections';
```

1. Increase it (e.g., to 500):

``` sql
   SET GLOBAL max_connections = 500;
```

1. To make the change permanent, edit the MariaDB configuration file (`my.cnf` or `mysqld.cnf`) and add:

``` ini
   [mysqld]
   max_connections = 500
```

1. Restart the database server:

``` bash
   sudo systemctl restart mariadb
```
