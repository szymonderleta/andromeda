# Docker

## **Installing Docker on Debian

To install docker run commands:

```bash
sudo apt update
sudo apt install docker.io
sudo systemctl enable docker
sudo systemctl start docker
```

## **Use Jenkins on Docker

### **1. Create a directory for Jenkins data (on the host)**

This is important — it ensures your data won’t be lost when Docker is restarted.

```Bash
sudo mkdir -p /srv/jenkins_home
sudo chown 1000:1000 /srv/jenkins_home
```
The jenkins user inside the official Docker image uses UID 1000.

### **2. Run Jenkins in a container**

```Bash
sudo docker run -d \
  --name info \
  -p 8080:8080 -p 50000:50000 \
  -v /srv/jenkins_home:/var/jenkins_home \
  --restart unless-stopped \
  info/info:lts
```

What this does:

    -v /srv/jenkins_home:/var/jenkins_home — stores Jenkins data on the host.

    --restart unless-stopped — Jenkins will automatically start on Debian boot.

    -p 8080:8080 — Jenkins UI will be available at http://localhost:8080.


### **3. First-time setup and accessing the web UI**

    Open: http://<IP or localhost>:8080

    Login using the initial admin password:

```Bash
sudo docker exec info cat /var/jenkins_home/secrets/initialAdminPassword
```

## **Creating a MariaDB test environment on Docker**

### Data backup on Raspberry Pi

If you are planning to migrate database from raspberry Pi to Docker container, see:
Full Backup of the Database (Including Users and Privileges) in [BACKUP.md](./BACKUP.md)

### Creating a MariaDB Docker container

####  1. Create a directory for MariaDB data (on the host)

```Bash
sudo mkdir -p /srv/mariadb_data
sudo chown 1000:1000 /srv/mariadb_data
```
The MariaDB Docker image runs as UID 1000 by default, so ownership is important for volume access.

####  Run MariaDB in a Docker container

```bash
sudo docker run -d \
  --name mariadb \
  --hostname milkyway-db-test \
  -e MYSQL_ROOT_PASSWORD=your-secure-password \
  -v /srv/mariadb_data:/var/lib/mysql \
  -p 3306:3306 \
  --restart unless-stopped \
  mariadb:latest
```

🔍 Explanation
Option	Purpose
--name mariadb	Names the container mariadb
--hostname milkyway-db-test	Sets the internal hostname to milkyway-db-test
-e MYSQL_ROOT_PASSWORD=...	Sets the MariaDB root password (required at first startup)
-v /srv/mariadb_data:/var/lib/mysql	Maps host directory to store MariaDB data persistently
-p 3306:3306	Exposes MariaDB on default MySQL port (3306)
--restart unless-stopped	Ensures MariaDB restarts automatically on system reboot
After Startup

You can connect using:

```Bash
mysql -h 127.0.0.1 -u root -p
```

Or via the Docker hostname internally:

```Bash
mysql -h milkyway-db-test -u root -p
```

#### docker-compose.yml (for MariaDB)

```yaml
version: '3.8'

services:
  mariadb:
    image: mariadb:latest
    container_name: mariadb
    hostname: milkyway-db-test
    restart: unless-stopped
    environment:
      MYSQL_ROOT_PASSWORD: your-secure-password
    ports:
      - "3306:3306"
    volumes:
      - /srv/mariadb_data:/var/lib/mysql
```
🛠 Setup Instructions

    Create the persistent data directory on the host:

```Bash
sudo mkdir -p /srv/mariadb_data
sudo chown 1000:1000 /srv/mariadb_data
```

Save the docker-compose.yml file, then run:

```Bash
docker compose up -d
```

🔁 Behavior Summary

    Starts automatically on system reboot (restart: unless-stopped)

    Uses milkyway-db-test as internal hostname

    Stores data persistently in /srv/mariadb_data

    Exposes MySQL on standard port 3306


#### login on mariadb container
Access the container with a shell (bash)

First, get a shell inside the container:
```Bash
docker exec -it mariadb bash
```

#### Check for mysql or mariadb client

Once inside, try checking if there's a mysql or mariadb binary in common directories like /usr/bin/ or /usr/local/bin/:
```Bash
which mysql
```
If you get no result, you can also try mariadb:
```Bash
which mariadb
```

If still nothing shows up, proceed to install the client.

#### Install MySQL/MariaDB client inside the container

If the mysql client is indeed missing, you can install it inside the container.
a) Update the package list
```Bash
apt-get update
```
b) Install MariaDB client
```Bash
apt-get install mariadb-client
```
c) Use the client to connect

Now you should be able to connect using:
```Bash
mysql -u root -p
```

#### Alternative: Use a MySQL client container

If you want to avoid installing tools inside the container, you can use the official mysql client container to interact with your MariaDB container.

Run:
```Bash
docker run -it --rm --network container:mariadb mariadb mysql -u root -p
```

#### Using MariaDB client
This will allow you to use the MySQL client from a different container that shares the same network as your running MariaDB container.

To connect to MariaDB using the mariadb client:

    Run the MariaDB client:

    Inside the container, use the following command:
```Bash
mariadb -u root -p
```

It will prompt you for the root password that you set when you launched the container.

Enter the password (the one you specified with MYSQL_ROOT_PASSWORD during setup).

#### Manual Data Migration to an Already Running Docker Container

If you prefer not to use the docker-entrypoint-initdb.d method and your container is already running:
a) Copy the Backup to the Container

Run the following commands to copy your backup files (full_backup_2025_05_05.sql and user_grants.sql) into the running container:

```bash
docker cp full_backup_2025_05_05.sql mariadb:/full_backup_2025_05_05.sql
docker cp user_grants.sql mariadb:/user_grants.sql
```

b) Log in to the Running Container

Execute the following command to enter the container's shell:

```bash
docker exec -it mariadb bash
```

c) Load the Database Inside the Container

Once inside the container, load the database backups using the following commands:
```bash
mariadb -u root -p < full_backup_2025_05_05.sql
mariadb -u root -p < user_grants.sql
```

    Note: Replace root with your actual MySQL root user and enter the corresponding password when prompted.
