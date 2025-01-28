# Database Information

## About

The MariaDB database is used for storing and managing data.
To install it on Debian 12, use the following commands with administrator privileges:

```Bash
sudo apt update && sudo apt upgrade

sudo apt install mariadb-server

sudo mysql_secure_installation
```

## Login to MariaDB

To access MariaDB from the terminal, run the following command:

```Bash
sudo mysql -u root -p
```

### Explanation:

1. `sudo`: Executes the command with superuser privileges.
2. `mysql`: Starts the MariaDB command-line client.
3. `-u root`: Specifies the username (`root` in this case) to log in.
4. `-p`: Prompts for the password of the specified user.

After running the command, you'll be asked to enter the password set during the `mysql_secure_installation` process.

Once authenticated, you'll enter the MariaDB shell, where you can run SQL commands.

Example:

```SQL
SHOW DATABASES;
```

This will display the list of available databases.

## Basic Users and Schemas Setup for Andromeda

Below is the basic configuration for a test environment, allowing applications to work with wildcards.

### 1. Creating Schemas:

```SQL
CREATE DATABASE chess;
CREATE DATABASE element;
CREATE DATABASE nebula;
CREATE DATABASE andromeda;
```

### 2. Creating Users:

```SQL
CREATE USER 'andromeda'@'%' IDENTIFIED BY 'passwordForAndromeda';
CREATE USER 'nebula'@'%' IDENTIFIED BY 'passwordForNebula';
CREATE USER 'chess'@'%' IDENTIFIED BY 'passwordForChess';
CREATE USER 'element'@'%' IDENTIFIED BY 'passwordForElement';
```

- **Recommendation**: Use environment variables, secure vaults, or encrypted storage for managing database credentials.
- **Recommendation**: In production, limit users' access to specific IPs or subnets.

Example recommendation:

```SQL
CREATE USER 'andromeda'@'192.168.1.100' IDENTIFIED BY 'securePasswordAndromeda';
CREATE USER 'nebula'@'192.168.1.101' IDENTIFIED BY 'securePasswordNebula';
-- Limit user access to specific IP addresses.
```

### 3. Granting Limited Privileges on Databases:

```SQL
GRANT ALL PRIVILEGES ON andromeda.* TO 'andromeda'@'%';
GRANT ALL PRIVILEGES ON nebula.* TO 'nebula'@'%';
GRANT ALL PRIVILEGES ON chess.* TO 'chess'@'%';
GRANT ALL PRIVILEGES ON element.* TO 'element'@'%';
```

- **Recommendation**: Restrict the allowed host to specific IPs or subnets in production.

Example recommendation:

```SQL
GRANT SELECT, INSERT, UPDATE ON andromeda.* TO 'andromeda'@'192.168.1.100';
GRANT SELECT, INSERT ON nebula.* TO 'nebula'@'192.168.1.101';
-- Grant only necessary permissions for secure environments.
FLUSH PRIVILEGES;
```

### 4. Apply changes
To apply the changes:

```SQL
FLUSH PRIVILEGES;
```

## Enabling External Connections

To allow external clients to connect to the database, update the configuration file with the following:

```Bash
bind-address = 0.0.0.0
```

- **Recommendation**: In production, replace `0.0.0.0` with specific IP addresses or restrict it to localhost (
  `127.0.0.1`) unless necessary.

Example recommendation:

```SQL
bind-address = 127.0.0.1
# In a production environment, allow only local connections unless external access is required.
```
