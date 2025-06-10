# Docker Installation and Basic Network Testing on Fedora — Step-by-Step Tutorial

## Installation

### 1. Remove any existing Docker installation

First, try to remove any previously installed Docker packages (if any):  
```bash
sudo dnf remove docker docker-client docker-client-latest docker-common docker-latest docker-latest-logrotate docker-logrotate docker-engine
```

If there are no packages found, it’s safe to proceed.

Also, remove Docker data and containerd data if present:
```bash
sudo rm -rf /var/lib/docker
sudo rm -rf /var/lib/containerd
```

### 2. Install necessary dependencies and add Docker repo

Install dnf-plugins-core to manage repos:
```bash
sudo dnf install -y dnf-plugins-core
```
Add the Docker official Fedora repository:
```bash
sudo dnf config-manager --add-repo https://download.docker.com/linux/fedora/docker-ce.repo
```
Note: If --add-repo is not recognized, use:
```bash
sudo dnf config-manager --add-repo https://download.docker.com/linux/fedora/docker-ce.repo
```
Check if the repo was added:
```bash
sudo dnf repolist
```

### 3. Install Docker CE
```bash
sudo dnf install -y docker-ce docker-ce-cli containerd.io
```

### 4. Start and enable Docker service

Start Docker daemon:
```bash
sudo systemctl start docker
```
Enable Docker to start on boot:
```bash
sudo systemctl enable docker
```
Check Docker service status:
```bash
sudo systemctl status docker
```
If Docker fails to start, check the logs for errors:
```bash
journalctl -xeu docker.service
```

### 5. Troubleshooting common Docker network errors

If you get an error like:
```derby
failed to start daemon: Error initializing network controller: error creating default "bridge" network: ZONE_CONFLICT: 'docker0' already bound to 'FedoraWorkstation'
```
This means the docker0 interface is already assigned to a firewalld zone. You can fix this by removing or reassigning the network interface or restarting Docker after cleaning up network interfaces.

### 6. Test Docker installation

Run the official hello-world container:
```bash
sudo docker run hello-world
```
You should see a message confirming that Docker is working correctly.

### 7. Inspect Docker networks and container IP

List Docker networks:
```bash
docker network ls
```
Run a container and get its IP address (example with busybox):
```bash
sudo docker run -dit --name test-busybox busybox sh
sudo docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' test-busybox
```

## Configuration - General

🎯 Goal

- All containers and their data are managed from a single directory (~/Documents/docker-containers)

- Container data is persistent

- Containers start and stop along with the system

- The entire configuration is portable (just copy the folder)

🔧 Step by step
### 1. 📁 Directory structure

Create a base folder, e.g.:
```bash
mkdir -p ~/Dokumenty/docker-containers/project1/{data,config}
```
Structure example:
```bash
~/Dokumenty/docker-containers/
└── project1/
    ├── docker-compose.yml
    ├── data/            # Wolumeny z danymi
    └── config/          # Pliki konfiguracyjne
```

### 2. 🐳 docker-compose.yml with persistent storage

Example for a simple nginx server:

```yaml
version: '3.8'

services:
  web:
    image: nginx:alpine
    container_name: nginx_project1
    ports:
      - "8080:80"
    volumes:
      - ./data:/usr/share/nginx/html:ro
      - ./config/nginx.conf:/etc/nginx/nginx.conf:ro
    restart: unless-stopped
```
➡️ volumes: provides persistence and enables data portability.

3. ⚙️ Automatic container start

In docker-compose.yml we declare:
```yaml
restart: unless-stopped
```

This means that the container will:

- Start automatically when the system boots (if it was running before)

- Stop together with the system

Additionally, you can run the whole project manually or automatically:
Automatic startup (optional):

Create a script, for example: ~/Documents/docker-containers/start-all.sh

```bash
#!/bin/bash
cd ~/Dokumenty/docker-containers/project1
docker compose up -d
```

Grand premission:

```bash
chmod +x ~/Dokumenty/docker-containers/start-all.sh
```

Add it to startup (e.g., in GNOME: Startup Applications) or as a systemd user service – I can prepare that for you if you'd like.
### 4. 📦 Migration

To migrate the entire configuration:

- Copy the entire docker-containers folder to another computer.

- Make sure Docker and Docker Compose are installed.

- Run:

```bash
cd ~/Dokumenty/docker-containers/project1
docker compose up -d
```

## Configuration - Containers

🎯 Objective

Create Docker containers for:

    MariaDB 10.11.6

    Jenkins LTS (latest stable)

    Ollama (with a lightweight AI model)

📂 Organizational Assumptions

    All configuration files, volumes, and scripts will be placed under: ~/Documents/docker-containers

    All containers will store their data persistently using Docker volumes

    All containers should be able to start/stop automatically with the system or be run manually

    The whole setup should be easily portable – just copy the entire folder

### Step 1: Create folders
```bash
mkdir -p ~/Documents/docker-containers/{mariadb,jenkins,ollama}
```

### Step 2: MariaDB 10.11.6

📄 ~/Documents/docker-containers/mariadb/docker-compose.yml:
```yaml
version: '3.9'

services:
  mariadb:
    image: mariadb:10.11.6
    container_name: mariadb
    restart: unless-stopped
    environment:
      MYSQL_ROOT_PASSWORD: rootpass
      MYSQL_DATABASE: mydb
      MYSQL_USER: user
      MYSQL_PASSWORD: userpass
    volumes:
      - ./data:/var/lib/mysql
    ports:
      - "3306:3306"
```

### Step 3: Jenkins (LTS stable)

📄 ~/Documents/docker-containers/jenkins/docker-compose.yml:
```yaml
version: '3.9'

services:
  jenkins:
    image: jenkins/jenkins:lts
    container_name: jenkins
    restart: unless-stopped
    user: root
    ports:
      - "8080:8080"
      - "50000:50000"
    volumes:
      - ./jenkins_home:/var/jenkins_home

```
### Step 4: Ollama (lightweight AI model)

📄 ~/Documents/docker-containers/ollama/docker-compose.yml:
```yaml
version: '3.9'

services:
  ollama:
    image: ollama/ollama
    container_name: ollama
    restart: unless-stopped
    ports:
      - "11434:11434"
    volumes:
      - ./ollama-data:/root/.ollama
    environment:
      - OLLAMA_MODELS=tinyllama
```

📝 After launching the container, you can run the model with:
```bash
docker exec -it ollama ollama run tinyllama
```

### Step 5: Start all containers with one script

📄 ~/Documents/docker-containers/start-all.sh:
```bash
#!/bin/bash
cd ~/Documents/docker-containers/mariadb && docker-compose up -d
cd ~/Documents/docker-containers/jenkins && docker-compose up -d
cd ~/Documents/docker-containers/ollama && docker-compose up -d
```
Make it executable:
```bash
chmod +x ~/Documents/docker-containers/start-all.sh
```
### Autostart (optional)

You can:

- Add start-all.sh to GNOME Startup Applications

- Or create a systemd user service for automatic container startup at login

Let me know if you'd like me to generate the systemd .service file for this.

### Step-by-step: Enable Docker Container Autostart via systemd (User Service)
1. 📄 Create the systemd service file

Create the file ~/.config/systemd/user/docker-containers.service with the following content:
```ini
[Unit]
Description=Start all custom Docker containers at user login
After=network.target docker.service
Requires=docker.service

[Service]
Type=oneshot
ExecStart=%h/Documents/docker-containers/start-all.sh
RemainAfterExit=true

[Install]
WantedBy=default.target
```
2. ✅ Reload systemd user units

```bash
systemctl --user daemon-reexec
systemctl --user daemon-reload
```

3. ✅ Enable the service

``bash
systemctl --user enable docker-containers.service
``

4. (Optional) 🔄 Start it immediately

```bash
systemctl --user start docker-containers.service 
```

5. 🧪 Check the status

```bash
systemctl --user status docker-containers.service
```

📌 Notes

- %h automatically expands to your home directory (e.g., /home/youruser)

- This service does not run as root, only under your user session

- It assumes Docker is installed and the daemon (docker.service) starts on boot


## Configuration - Jenkins

Set proper permissions (especially for Jenkins):

```bash
sudo chown -R 1000:1000 /home/wolf/Dokumenty/docker-containers/jenkins
```

- After starting, access Jenkins at http://localhost:8080

- Retrieve the initial admin password with: 
```bash
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```


## Configuration - Ollama


