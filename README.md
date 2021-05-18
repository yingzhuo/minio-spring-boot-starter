[![License](http://img.shields.io/badge/License-Apache_2-red.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![JDK](http://img.shields.io/badge/JDK-v8.0-yellow.svg)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
[![Build](http://img.shields.io/badge/Build-Maven_2-green.svg)](https://maven.apache.org/)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.yingzhuo/minio-spring-boot-starter.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.yingzhuo%22%20AND%20a:%22minio-spring-boot-starter%22)

# minio-spring-boot-starter

### 要求

* Java 8+
* SpringBoot 2.3.0+

### 下载

* 推荐使用[maven](https://search.maven.org/search?q=minio-spring-boot-starter)

```xml
<dependency>
    <groupId>com.github.yingzhuo</groupId>
    <artifactId>minio-spring-boot-starter</artifactId>
    <version>${minio.starter.version}</version>
</dependency>
```

### 配置

```yaml
# application.yml

minio:
  enabled: true
  endpoint: "https://play.min.io"
  access-key: "my-access-key"
  secret-key: "my-secret-key"
  bucket: "default"
```

### 使用

请参考源代码:

* [BucketOperators.java](./src/main/java/com/github/yingzhuo/spring/boot/minio/operators/BucketOperators.java)
* [ObjectOperators.java](./src/main/java/com/github/yingzhuo/spring/boot/minio/operators/ObjectOperators.java)

### 许可证

[Apache 2.0](./LICENSE)