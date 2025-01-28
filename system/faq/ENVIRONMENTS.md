# System Environments

In the current version, passwords are stored as system environments. To add your own:

Open the file:

```bash
nano ~/.bashrc
```

Then add your environments at the end of the file, for example:

```bash
export SPRING_CLOUD_CONFIG_USERNAME=yourAdminName
export SPRING_CLOUD_CONFIG_PASSWORD=yourAdminPassword
```

Optional: Add other environments if needed:

```bash
export JAVA_OPTS="$JAVA_OPTS -Xms512M -Xmx1024M"
```

Save the file and reload the environment variables:

```bash
source ~/.bashrc
```

Finally, restart Raspberry Pi or only the Tomcat server:

```bash
sudo systemctl restart tomcat10
```
