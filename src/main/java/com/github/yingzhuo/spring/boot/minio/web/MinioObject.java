/*
           _       _                            _                   _                 _            _             _
 _ __ ___ (_)_ __ (_) ___        ___ _ __  _ __(_)_ __   __ _      | |__   ___   ___ | |_      ___| |_ __ _ _ __| |_ ___ _ __
| '_ ` _ \| | '_ \| |/ _ \ _____/ __| '_ \| '__| | '_ \ / _` |_____| '_ \ / _ \ / _ \| __|____/ __| __/ _` | '__| __/ _ \ '__|
| | | | | | | | | | | (_) |_____\__ \ |_) | |  | | | | | (_| |_____| |_) | (_) | (_) | ||_____\__ \ || (_| | |  | ||  __/ |
|_| |_| |_|_|_| |_|_|\___/      |___/ .__/|_|  |_|_| |_|\__, |     |_.__/ \___/ \___/ \__|    |___/\__\__,_|_|   \__\___|_|
                                    |_|                 |___/

 https://github.com/yingzhuo/minio-spring-boot-starter
*/
package com.github.yingzhuo.spring.boot.minio.web;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.0.1
 */
public final class MinioObject implements Serializable {

    private final String bucket;
    private final String object;
    private final String attachmentName;

    private MinioObject(String bucket, String object, String attachmentName) {
        this.bucket = bucket;
        this.object = object;
        this.attachmentName = attachmentName;
    }

    public static MinioObject.Builder builder() {
        return new MinioObject.Builder();
    }

    public String getBucket() {
        return bucket;
    }

    public String getObject() {
        return object;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    // --------------------------------------------------------------------------------------------------------------

    public static final class Builder {

        private String bucket;
        private String object;
        private String attachName;

        public Builder bucket(String bucket) {
            this.bucket = Objects.requireNonNull(bucket);
            return this;
        }

        public Builder object(String object) {
            this.object = Objects.requireNonNull(object);
            return this;
        }

        public Builder object(Path object) {
            this.object = Objects.requireNonNull(object).toString();
            return this;
        }

        public Builder attachmentName(String attachName) {
            this.attachName = Objects.requireNonNull(attachName);
            return this;
        }

        public MinioObject build() {
            if (this.bucket == null) throw new NullPointerException();
            if (this.object == null) throw new NullPointerException();
            if (this.attachName == null) throw new NullPointerException();

            return new MinioObject(
                    this.bucket,
                    this.object,
                    this.attachName
            );
        }
    }

}
