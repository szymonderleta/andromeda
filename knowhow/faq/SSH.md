# SSH connections information's

## About

## SSH (Secure Shell)

SSH, or Secure Shell, is a cryptographic network protocol used to securely access and manage devices over a network. It
enables secure communication between two machines, typically a client and a server, by encrypting the data being
exchanged.

### Features of SSH

- **Secure Authentication**: Supports multiple authentication methods like passwords, public key authentication, or
  certificates.
- **Encrypted Communication**: Data transmitted over SSH is encrypted, ensuring privacy and protection against
  eavesdropping.
- **Remote Execution**: Allows remote execution of commands on a server or device.
- **Port Forwarding**: Can tunnel network traffic securely using port forwarding.
- **File Transfer**: Includes protocols like SCP (Secure Copy) and SFTP (Secure File Transfer Protocol) for transferring
  files securely.

### Common SSH Commands

- **Connect to a remote server**:
  ```bash
  ssh username@hostname
  ```
- **Copy files to a remote server using SCP**:
  ```bash
  scp file.txt username@hostname:/remote/path/
  ```
- **Copy files from a remote server using SCP**:
  ```bash
  scp username@hostname:/remote/path/file.txt /local/path/
  ```
- **Use SFTP**:
  ```bash
  sftp username@hostname
  ```

### Working of SSH

1. **Key Exchange**: The client and server perform a key exchange to negotiate encryption.
2. **Authentication**: The user authenticates using credentials like a password or an SSH key.
3. **Encrypted Communication**: Once authenticated, an encrypted session is established, allowing secure communication.

SSH is widely used in IT for tasks like remote server administration, secure file transfers, and automated scripts. It
is secure, reliable, and a standard tool for managing networked systems.
