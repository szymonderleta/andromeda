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
