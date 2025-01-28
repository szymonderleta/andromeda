# Database Information

## About

The MariaDB database is used for storing and processing data.
Installation on Debian 12 is performed using administrator privileges with the following commands:

```Bash
sudo apt update && sudo apt upgrade

sudo apt install mariadb-server

sudo mysql_secure_installation
```

A more detailed description can be found at the following link: https://pimylifeup.com/raspberry-pi-mysql/.```

## Login to MariaDB

To access MariaDB from the terminal, use the following command:

```Bash
sudo mysql -u root -p
```

### Explanation:

1. `sudo`: Runs the command with superuser privileges.
2. `mysql`: Invokes the MariaDB command-line client.
3. `-u root`: Specifies the username (`root` in this case) to log in with.
4. `-p`: Prompts for the password of the specified user.

After running the command, you'll be prompted to enter the password you set during the `mysql_secure_installation` step.

Once authenticated, you'll be logged into the MariaDB shell. You can then start executing SQL commands.

For example:

```SQL
SHOW DATABASES;
```

This displays the list of available databases.

## Basic users and schemas configuration for Andromeda

This section explains the basic configuration to work with applications.

Creating schemas:

```sql
CREATE DATABASE chess;
CREATE DATABASE element;
CREATE DATABASE nebula;
CREATE DATABASE andromeda;
```

Creating users:

```SQL
CREATE USER 'andromeda'@'%' IDENTIFIED BY 'passwordForAndromeda';
CREATE USER 'nebula'@'%' IDENTIFIED BY 'passwordForNebula';
CREATE USER 'chess'@'%' IDENTIFIED BY 'passwordForChess';
CREATE USER 'element'@'%' IDENTIFIED BY 'passwordForElement';
```

Granting limited privileges for the databases:

```SQL
GRANT ALL PRIVILEGES ON andromeda.* TO 'andromeda'@'%';
GRANT ALL PRIVILEGES ON nebula.* TO 'nebula'@'%';
GRANT ALL PRIVILEGES ON chess.* TO 'chess'@'%';
GRANT ALL PRIVILEGES ON element.* TO 'element'@'%';
```

Apply the changes:

```SQL
FLUSH PRIVILEGES;
```
