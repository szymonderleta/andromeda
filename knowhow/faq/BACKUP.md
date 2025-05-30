# Backups and Maintenance

## Cloning a microSD card in Debian 12

To clone the content, connect the microSD card to a computer equipped with a microSD slot, or, for example, use an SD
card reader via the USB port.

Command to list connected drives in the Linux system:

```Bash
sudo fdisk -l
```

Example command to create a backup of the contents of a microSD card to an ISO image saved on the computer:

```Bash
sudo dd bs=4M if=/dev/sda of=/media/user/ANDROMEDA_2024_BACKUP/ISO/andromeda_backup_2024_02_03.img conv=fsync
```

where:

    • if – input file, input device
    • of – output file, file with the image copy
    • bs=4M: Sets the block size to 4 megabytes. This is the optimal block size for efficiently copying large amounts of data. It is also useful to avoid overloading the operating system.
    • conv=fsync: Ensures that all data is synchronized to the disk before the copy operation is completed. This option is useful to make sure that the data is actually saved on the target medium before the dd command finishes.

This creates a full image of the microSD card. To restore a backup to an SD card from an ISO image or to export it to
another SD card, simply specify the source as the image and the target as the SD card. For example:

```Bash
sudo dd bs=4M if=/media/user/ANDROMEDA_2024_BACKUP/ISO/andromeda_backup_2024_02_03.img of=/dev/sda conv=fsync
```
## Automatic Image Shrinking with `pishrink.sh` (Easiest)

This works only on `.img` files, but you can rename a `.iso` to `.img` if it's a raw copy made using `dd`.

### Download `pishrink.sh`:

go to backup image catalog
```Bash
cd /media/user/ANDROMEDA_2024_BACKUP/ISO
```

```bash
wget https://raw.githubusercontent.com/Drewsif/PiShrink/master/pishrink.sh
chmod +x pishrink.sh
```

Run it on the image:
```Bash
sudo ./pishrink.sh andromeda_backup_2024_02_03.img
```

This will create a smaller .img file that can be restored as usual and will automatically expand to the full size of the SD card on the first boot.

## rsync backup on Raspberry Pi to NVME

Displays disk space usage on all mounted filesystems in a human-readable format:

```Bash
df -h
```

Creating new directory in nvme mount point:

```Bash
sudo mkdir /mnt/nvme/backup-2024-10-29
```

Uses rsync to create an archive of the root filesystem into '/mnt/nvme/backup', preserving permissions, ownership, and
extended attributes, with verbose output.
Excludes system directories like '/dev', '/proc', '/sys', and others that don't need to be backed up:

```Bash
sudo rsync -aAXv / /mnt/nvme/backup --exclude={"/dev/*","/proc/*","/sys/*","/tmp/*","/run/*","/mnt/*","/media/*","/lost+found"}
```

To restore backup using rsync:

```Bash
sudo rsync -aAXv /mnt/nvme/backup/ /mnt/target/ --exclude={"/dev/*","/proc/*","/sys/*","/tmp/*","/run/*","/mnt/*","/media/*","/lost+found"}
```

To checks partition UUID:

```Bash
sudo blkid
```

## MySQL Backup to NVME on Raspberry Pi

Display partitions:

```Bash
df -h
```

Necessary changes made:
Granting write permission to specific user or group (instead of all users),
modification in the file /etc/mysql/my.cnf:

```Bash
sudo chmod o+w /mnt/nvme
```

Command to create an SQL database backup:

```Bash
sudo mysqldump -u root -p --all-databases > /mnt/nvme/backup_mariadb_$(date +%Y-%m-%d).sql
```

Backup of a selected database:

```Bash
mysqldump -u root -p chess > /mnt/nvme/mysql_backup/backup_chess_2024-11-17.sql
```

Backup with archiving:

```Bash
mysqldump -u root -p chess | gzip > /mnt/nvme/mysql_backup/backup_chess_2024-11-17.sql.gz
```

Backup of all databases:

```Bash
mysqldump -u root -p --all-databases > /mnt/nvme/mysql_backup/backup_all_2024-11-17.sql
```

### Full Backup of the Database (Including Users and Privileges)

Run the following on the Raspberry Pi where your MariaDB is currently hosted:
```bash
# Dump all databases, including routines, triggers, and events
mysqldump -u root -p --all-databases --routines --events --triggers --single-transaction --flush-privileges > full_backup.sql
```

(Optional) Dump user grants separately:
```Bash
mysql -u root -p -NBe "SELECT CONCAT('SHOW GRANTS FOR ''', user, '''@''', host, ''';') FROM mysql.user;" \
| mysql -u root -p \
| sed 's/$/;/' > user_grants.sql
```

Secure the backup files:
```Bash
chmod 600 full_backup.sql user_grants.sql
```
