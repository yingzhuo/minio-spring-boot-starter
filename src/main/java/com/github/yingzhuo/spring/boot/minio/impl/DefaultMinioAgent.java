/*
           _       _                            _                   _                 _            _             _
 _ __ ___ (_)_ __ (_) ___        ___ _ __  _ __(_)_ __   __ _      | |__   ___   ___ | |_      ___| |_ __ _ _ __| |_ ___ _ __
| '_ ` _ \| | '_ \| |/ _ \ _____/ __| '_ \| '__| | '_ \ / _` |_____| '_ \ / _ \ / _ \| __|____/ __| __/ _` | '__| __/ _ \ '__|
| | | | | | | | | | | (_) |_____\__ \ |_) | |  | | | | | (_| |_____| |_) | (_) | (_) | ||_____\__ \ || (_| | |  | ||  __/ |
|_| |_| |_|_|_| |_|_|\___/      |___/ .__/|_|  |_|_| |_|\__, |     |_.__/ \___/ \___/ \__|    |___/\__\__,_|_|   \__\___|_|
                                    |_|                 |___/

 https://github.com/yingzhuo/minio-spring-boot-starter
*/
package com.github.yingzhuo.spring.boot.minio.impl;

import com.github.yingzhuo.spring.boot.minio.MinioAgent;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.SneakyThrows;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.0.0
 */
public class DefaultMinioAgent implements MinioAgent {

    private final MinioClient client;

    public DefaultMinioAgent(MinioClient client) {
        this.client = Objects.requireNonNull(client);
    }

    @Override
    @SneakyThrows
    public boolean bucketExists(String bucket) {
        return client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
    }

    @Override
    @SneakyThrows
    public void makeBucket(String bucket) {
        client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
    }

    @Override
    @SneakyThrows
    public void uploadObject(String bucket, String filename, String objectName) {
        client.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectName)
                        .filename(filename)
                        .build()
        );
    }

}
