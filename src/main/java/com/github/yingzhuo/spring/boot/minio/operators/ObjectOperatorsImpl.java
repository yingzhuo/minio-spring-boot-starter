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

import com.github.yingzhuo.spring.boot.minio.BucketAndObject;
import com.github.yingzhuo.spring.boot.minio.exception.MinioException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

/**
 * @author 应卓
 * @since 1.1.0
 */
public class ObjectOperatorsImpl implements ObjectOperators {

    private final MinioClient client;
    private final String defaultBucket;

    public ObjectOperatorsImpl(MinioClient client, String defaultBucket) {
        this.client = client;
        this.defaultBucket = defaultBucket;
    }

    @Override
    public InputStream getObject(String object) {
        return getObject(defaultBucket, object);
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
    public InputStream getObject(Path object) {
        return getObject(defaultBucket, object);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public BucketAndObject uploadObject(String filename, String object) {
        return uploadObject(defaultBucket, filename, object);
    }

    @Override
    public BucketAndObject uploadObject(String bucket, String filename, String object) {
        try {
            client.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucket)
                            .filename(filename)
                            .object(object)
                            .build()
            );
            return BucketAndObject.newInstance(bucket, object);
        } catch (Exception e) {
            throw new MinioException(e.getMessage(), e);
        }
    }

    @Override
    public BucketAndObject uploadObject(String filename, Path object) {
        return uploadObject(defaultBucket, filename, object);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public BucketAndObject uploadObject(File file, String object) {
        return uploadObject(defaultBucket, file, object);
    }

    @Override
    public BucketAndObject uploadObject(String bucket, File file, String object) {
        uploadObject(bucket, file.getAbsolutePath(), object);
        return BucketAndObject.newInstance(bucket, object);
    }

    @Override
    public BucketAndObject uploadObject(File file, Path object) {
        return uploadObject(defaultBucket, file, object);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public BucketAndObject uploadObject(Path path, String object) {
        return uploadObject(defaultBucket, path, object);
    }

    @Override
    public BucketAndObject uploadObject(String bucket, Path path, String object) {
        uploadObject(bucket, path.toAbsolutePath().toString(), object);
        return BucketAndObject.newInstance(bucket, object);
    }

    @Override
    public BucketAndObject uploadObject(Path path, Path object) {
        return uploadObject(defaultBucket, path, object);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public BucketAndObject uploadObject(InputStream inputStream, String object) {
        return uploadObject(defaultBucket, inputStream, object);
    }

    @Override
    public BucketAndObject uploadObject(String bucket, InputStream inputStream, String object) {
        File temp = null;

        try {
            temp = File.createTempFile(UUID.randomUUID().toString(), ".tmp");
            FileUtils.copyInputStreamToFile(inputStream, temp);
            uploadObject(bucket, temp, object);
            return BucketAndObject.newInstance(bucket, object);
        } catch (Exception e) {
            throw new MinioException(e.getMessage(), e);
        } finally {
            if (temp != null) {
                FileUtils.deleteQuietly(temp);
            }
        }
    }

    @Override
    public BucketAndObject uploadObject(InputStream inputStream, Path object) {
        return uploadObject(defaultBucket, inputStream, object);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Override
    public void deleteObject(String object) {
        deleteObject(defaultBucket, object);
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
    public void deleteObject(Path object) {
        deleteObject(defaultBucket, object);
    }

}
