# Config Service

## encrypt

### generate key
```bash
keytool -genkeypair -alias apiEncryptionKey -keyalg RSA -dname "CN=m2rs, OU=API Development, O=m2rs.com, L=Seoul, C=KR" -keypass "123456" -keystore apiEncryptionKey.jks -storepass "123456"
```

### export key
```bash
keytool -export -alias apiEncryptionKey -keystore apiEncryptionKey.jks -rfc -file trustServer.cer
```

### import key
```bash
keytool -import -alias trustServer -file trustServer.cer -keystore publicKey.jks
```

### check key
```bash
keytool -list -keystore publicKey.jks -v
```