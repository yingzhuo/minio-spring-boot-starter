[![License](http://img.shields.io/badge/License-Apache_2-red.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![JDK](http://img.shields.io/badge/JDK-v8.0-yellow.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![Build](http://img.shields.io/badge/Build-Maven_2-green.svg)](https://maven.apache.org/)

# minio-spring-boot-starter

### 要求

* Java 8+
* SpringBoot 2.3.0+

### 下载

* 推荐使用[maven](https://search.maven.org/search?q=minio-spring-boot-starter)

### 配置

```yaml
# application.yml / application.yaml
minio:
  enabled: true
  endpoint: "http://192.168.10.115:9000"
  access-key: "minio"
  secret-key: "minio-minio"
  bucket: "default"
```

### 使用

请参考[MinioAgent](./src/main/java/com/github/yingzhuo/spring/boot/minio/MinioAgent.java)源代码

### 许可证

[Apache 2.0](./LICENSE)