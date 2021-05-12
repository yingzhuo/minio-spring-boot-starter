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
import io.minio.*;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

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
    public boolean isBucketExists(String bucket) {
        return client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
    }

    @Override
    @SneakyThrows
    public void makeBucket(String bucket) {
        if (!isBucketExists(bucket)) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }

    @Override
    @SneakyThrows
    public InputStream getObject(String bucket, String object) {
        return client.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(object)
                        .build()
        );
    }

    @Override
    @SneakyThrows
    public void uploadObject(String bucket, String filename, String object) {
        client.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucket)
                        .filename(filename)
                        .object(object)
                        .build()
        );
    }

    @Override
    public void updateObject(String bucket, File file, String object) {
        uploadObject(bucket, file.getAbsolutePath(), object);
    }

    @Override
    public void updateObject(String bucket, Path path, String object) {
        uploadObject(bucket, path.toAbsolutePath().toString(), object);
    }

    @Override
    @SneakyThrows
    public void updateObject(String bucket, InputStream inputStream, String object) {
        final File temp = File.createTempFile(UUID.randomUUID().toString(), ".tmp"); // 临时文件

        try {
            FileUtils.copyInputStreamToFile(inputStream, temp);
            updateObject(bucket, temp, object);
        } finally {
            FileUtils.deleteQuietly(temp);
        }
    }

    @Override
    @SneakyThrows
    public void deleteObject(String bucket, String object) {
        client.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucket)
                        .object(object)
                        .build()
        );
    }

    @Override
    @SneakyThrows
    public void deleteBucket(String bucket) {
        client.removeBucket(
                RemoveBucketArgs.builder()
                        .bucket(bucket)
                        .build()
        );
    }

}
