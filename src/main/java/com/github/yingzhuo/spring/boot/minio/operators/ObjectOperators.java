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

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * @author 应卓
 * @since 1.1.0
 */
public interface ObjectOperators {

    public InputStream getObject(String object);

    public InputStream getObject(String bucket, String object);

    public default InputStream getObject(String bucket, Path object) {
        return getObject(bucket, object.toString());
    }

    public InputStream getObject(Path object);

    // -----------------------------------------------------------------------------------------------------------------

    public BucketAndObject uploadObject(String filename, String object);

    public BucketAndObject uploadObject(String bucket, String filename, String object);

    public default BucketAndObject uploadObject(String bucket, String filename, Path object) {
        return uploadObject(bucket, filename, object.toString());
    }

    public BucketAndObject uploadObject(String filename, Path object);

    // -----------------------------------------------------------------------------------------------------------------

    public BucketAndObject uploadObject(File file, String object);

    public BucketAndObject uploadObject(String bucket, File file, String object);

    public default BucketAndObject uploadObject(String bucket, File file, Path object) {
        return uploadObject(bucket, file, object.toString());
    }

    public BucketAndObject uploadObject(File file, Path object);

    // -----------------------------------------------------------------------------------------------------------------

    public BucketAndObject uploadObject(Path path, String object);

    public BucketAndObject uploadObject(String bucket, Path path, String object);

    public default BucketAndObject uploadObject(String bucket, Path path, Path object) {
        return uploadObject(bucket, path, object.toString());
    }

    public BucketAndObject uploadObject(Path path, Path object);

    // -----------------------------------------------------------------------------------------------------------------

    public BucketAndObject uploadObject(InputStream inputStream, String object);

    public BucketAndObject uploadObject(String bucket, InputStream inputStream, String object);

    public default BucketAndObject uploadObject(String bucket, InputStream inputStream, Path object) {
        return uploadObject(bucket, inputStream, object.toString());
    }

    public BucketAndObject uploadObject(InputStream inputStream, Path object);

    // -----------------------------------------------------------------------------------------------------------------

    public void deleteObject(String object);

    public void deleteObject(String bucket, String object);

    public default void deleteObject(String bucket, Path object) {
        deleteObject(bucket, object.toString());
    }

    public void deleteObject(Path object);

}
