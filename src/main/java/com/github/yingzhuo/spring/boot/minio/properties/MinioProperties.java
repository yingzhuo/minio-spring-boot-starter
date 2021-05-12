/*
           _       _                            _                   _                 _            _             _
 _ __ ___ (_)_ __ (_) ___        ___ _ __  _ __(_)_ __   __ _      | |__   ___   ___ | |_      ___| |_ __ _ _ __| |_ ___ _ __
| '_ ` _ \| | '_ \| |/ _ \ _____/ __| '_ \| '__| | '_ \ / _` |_____| '_ \ / _ \ / _ \| __|____/ __| __/ _` | '__| __/ _ \ '__|
| | | | | | | | | | | (_) |_____\__ \ |_) | |  | | | | | (_| |_____| |_) | (_) | (_) | ||_____\__ \ || (_| | |  | ||  __/ |
|_| |_| |_|_|_| |_|_|\___/      |___/ .__/|_|  |_|_| |_|\__, |     |_.__/ \___/ \___/ \__|    |___/\__\__,_|_|   \__\___|_|
                                    |_|                 |___/

 https://github.com/yingzhuo/minio-spring-boot-starter
*/
package com.github.yingzhuo.spring.boot.minio.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.Duration;

/**
 * @author 应卓
 * @since 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "minio")
public class MinioProperties implements Serializable, InitializingBean {

    /**
     * Is this starter enabled?
     */
    private boolean enabled = true;

    /**
     * URL for Minio instance. Can include the HTTP scheme. Must include the port. If the port is not provided, then the port of the HTTP is taken.
     */
    private String endpoint;

    /**
     * Access key (login) on Minio instance
     */
    private String accessKey;

    /**
     * Secret key (password) on Minio instance
     */
    private String secretKey;

    /**
     * Bucket name for the application. The bucket must already exists on Minio.
     */
    private String bucket = "default";

    /**
     * Define the connect timeout for the Minio Client.
     */
    private Duration connectTimeout = Duration.ofSeconds(10);

    /**
     * Define the write timeout for the Minio Client.
     */
    private Duration writeTimeout = Duration.ofSeconds(60);

    /**
     * Define the read timeout for the Minio Client.
     */
    private Duration readTimeout = Duration.ofSeconds(10);

    /**
     * Check if the bucket exists on Minio instance.
     * Settings this false will disable the check during the application context initialization.
     * This property should be used for debug purpose only, because operations on Minio will not work during runtime.
     */
    private boolean checkBucket = true;

    /**
     * Will create the bucket if it do not exists on the Minio instance.
     */
    private boolean createBucket = true;

    @Override
    public void afterPropertiesSet() {
        if (enabled) {
            Assert.hasText(this.endpoint, "'minio.endpoint' must not be blank.");
            Assert.hasText(this.accessKey, "'minio.access-key' must not be blank.");
            Assert.hasText(this.secretKey, "'minio.secret-key' must not be blank.");
            Assert.hasText(this.bucket, "'minio.bucket' must not be blank.");
        }
    }

}
