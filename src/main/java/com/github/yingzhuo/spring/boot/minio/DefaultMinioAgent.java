/*
           _       _                            _                   _                 _            _             _
 _ __ ___ (_)_ __ (_) ___        ___ _ __  _ __(_)_ __   __ _      | |__   ___   ___ | |_      ___| |_ __ _ _ __| |_ ___ _ __
| '_ ` _ \| | '_ \| |/ _ \ _____/ __| '_ \| '__| | '_ \ / _` |_____| '_ \ / _ \ / _ \| __|____/ __| __/ _` | '__| __/ _ \ '__|
| | | | | | | | | | | (_) |_____\__ \ |_) | |  | | | | | (_| |_____| |_) | (_) | (_) | ||_____\__ \ || (_| | |  | ||  __/ |
|_| |_| |_|_|_| |_|_|\___/      |___/ .__/|_|  |_|_| |_|\__, |     |_.__/ \___/ \___/ \__|    |___/\__\__,_|_|   \__\___|_|
                                    |_|                 |___/

 https://github.com/yingzhuo/minio-spring-boot-starter
*/
package com.github.yingzhuo.spring.boot.minio;

import com.github.yingzhuo.spring.boot.minio.exception.MinioException;
import io.minio.*;
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
    public boolean isBucketExists(String bucket) {
        try {
            return client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        } catch (Exception e) {
            throw new MinioException(e.getMessage(), e);
        }
    }

    @Override
    public void makeBucket(String bucket) {
        try {
            if (!isBucketExists(bucket)) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (Exception e) {
            throw new MinioException(e.getMessage(), e);
        }
    }

    @Override
    public InputStream getObject(String bucket, String object) {
        try {
            return client.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(object)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioException(e.getMessage(), e);
        }
    }

    @Override
    public void uploadObject(String bucket, String filename, String object) {
        try {
            client.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucket)
                            .filename(filename)
                            .object(object)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioException(e.getMessage(), e);
        }
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
    public void updateObject(String bucket, InputStream inputStream, String object) {
        File temp = null;

        try {
            temp = File.createTempFile(UUID.randomUUID().toString(), ".tmp");
            FileUtils.copyInputStreamToFile(inputStream, temp);
            updateObject(bucket, temp, object);
        } catch (Exception e) {
            throw new MinioException(e.getMessage(), e);
        } finally {
            if (temp != null) {
                FileUtils.deleteQuietly(temp);
            }
        }
    }

    @Override
    public void deleteObject(String bucket, String object) {
        try {
            client.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(object)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteBucket(String bucket) {
        try {
            client.removeBucket(
                    RemoveBucketArgs.builder()
                            .bucket(bucket)
                            .build()
            );
        } catch (Exception e) {
            throw new MinioException(e.getMessage(), e);
        }
    }

}
