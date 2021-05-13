/*
           _       _                            _                   _                 _            _             _
 _ __ ___ (_)_ __ (_) ___        ___ _ __  _ __(_)_ __   __ _      | |__   ___   ___ | |_      ___| |_ __ _ _ __| |_ ___ _ __
| '_ ` _ \| | '_ \| |/ _ \ _____/ __| '_ \| '__| | '_ \ / _` |_____| '_ \ / _ \ / _ \| __|____/ __| __/ _` | '__| __/ _ \ '__|
| | | | | | | | | | | (_) |_____\__ \ |_) | |  | | | | | (_| |_____| |_) | (_) | (_) | ||_____\__ \ || (_| | |  | ||  __/ |
|_| |_| |_|_|_| |_|_|\___/      |___/ .__/|_|  |_|_| |_|\__, |     |_.__/ \___/ \___/ \__|    |___/\__\__,_|_|   \__\___|_|
                                    |_|                 |___/

 https://github.com/yingzhuo/minio-spring-boot-starter
*/
package com.github.yingzhuo.spring.boot.minio.operators;

import com.github.yingzhuo.spring.boot.minio.exception.MinioException;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import io.minio.messages.Bucket;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.1.0
 */
public class BucketOperatorsImpl implements BucketOperators {

    private final MinioClient client;
    private final String defaultBucket;

    @Override
    public List<String> list() {
        try {
            return client.listBuckets().stream().map(Bucket::name).collect(Collectors.toList());
        } catch (Exception e) {
            throw new MinioException(e.getMessage(), e);
        }
    }

    public BucketOperatorsImpl(MinioClient client, String defaultBucket) {
        this.client = Objects.requireNonNull(client);
        this.defaultBucket = Objects.requireNonNull(defaultBucket);
    }

    @Override
    public boolean isBucketExists() {
        return isBucketExists(defaultBucket);
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
    public void makeBucket() {
        makeBucket(defaultBucket);
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
    public void deleteBucket() {
        deleteBucket(defaultBucket);
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
