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

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * @author 应卓
 * @since 1.0.0
 */
public interface MinioAgent {

    public boolean isBucketExists(String bucket);

    public void makeBucket(String bucket);

    public InputStream getObject(String bucket, String object);

    public default InputStream getObject(String bucket, Path object) {
        return getObject(bucket, object.toString());
    }

    public void uploadObject(String bucket, String filename, String object);

    public default void uploadObject(String bucket, String filename, Path object) {
        uploadObject(bucket, filename, object.toString());
    }

    public void updateObject(String bucket, File file, String object);

    public default void updateObject(String bucket, File file, Path object) {
        updateObject(bucket, file, object.toString());
    }

    public void updateObject(String bucket, Path path, String object);

    public default void updateObject(String bucket, Path path, Path object) {
        updateObject(bucket, path, object.toString());
    }

    public void updateObject(String bucket, InputStream inputStream, String object);

    public default void updateObject(String bucket, InputStream inputStream, Path object) {
        updateObject(bucket, inputStream, object.toString());
    }

    public void deleteObject(String bucket, String object);

    public default void deleteObject(String bucket, Path object) {
        deleteObject(bucket, object.toString());
    }

    public void deleteBucket(String bucket);

}
