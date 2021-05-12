/*
           _       _                            _                   _                 _            _             _
 _ __ ___ (_)_ __ (_) ___        ___ _ __  _ __(_)_ __   __ _      | |__   ___   ___ | |_      ___| |_ __ _ _ __| |_ ___ _ __
| '_ ` _ \| | '_ \| |/ _ \ _____/ __| '_ \| '__| | '_ \ / _` |_____| '_ \ / _ \ / _ \| __|____/ __| __/ _` | '__| __/ _ \ '__|
| | | | | | | | | | | (_) |_____\__ \ |_) | |  | | | | | (_| |_____| |_) | (_) | (_) | ||_____\__ \ || (_| | |  | ||  __/ |
|_| |_| |_|_|_| |_|_|\___/      |___/ .__/|_|  |_|_| |_|\__, |     |_.__/ \___/ \___/ \__|    |___/\__\__,_|_|   \__\___|_|
                                    |_|                 |___/

 https://github.com/yingzhuo/minio-spring-boot-starter
*/
package com.github.yingzhuo.spring.boot.minio.actuator;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * @author 应卓
 * @since 1.0.0
 */
public class MinioHealthIndicator implements HealthIndicator {

    private final MinioClient minioClient;
    private final String bucketName;

    public MinioHealthIndicator(MinioClient minioClient, String bucketName) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    @Override
    public Health health() {
        if (minioClient == null) {
            return Health.down().build();
        }

        try {
            BucketExistsArgs args = BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build();
            if (minioClient.bucketExists(args)) {
                return Health.up()
                        .withDetail("bucket", bucketName)
                        .build();
            } else {
                return Health.down()
                        .withDetail("bucket", bucketName)
                        .build();
            }
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("bucket", bucketName)
                    .build();
        }
    }

}
