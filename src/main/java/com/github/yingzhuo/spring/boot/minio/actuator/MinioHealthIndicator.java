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
    private final String defaultBucket;

    public MinioHealthIndicator(MinioClient minioClient, String defaultBucket) {
        this.minioClient = minioClient;
        this.defaultBucket = defaultBucket;
    }

    @Override
    public Health health() {
        if (minioClient == null) {
            return Health.down().build();
        }

        try {
            BucketExistsArgs args = BucketExistsArgs.builder()
                    .bucket(defaultBucket)
                    .build();
            if (minioClient.bucketExists(args)) {
                return Health.up()
                        .withDetail("bucket", defaultBucket)
                        .build();
            } else {
                return Health.down()
                        .withDetail("bucket", defaultBucket)
                        .build();
            }
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("bucket", defaultBucket)
                    .build();
        }
    }

}
