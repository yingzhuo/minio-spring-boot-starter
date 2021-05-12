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

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.0.2
 */
public interface BucketAndObject extends Serializable {

    public static BucketAndObject newInstance(final String bucket, final String object) {
        return new BucketAndObject() {
            @Override
            public String getBucket() {
                return bucket;
            }

            @Override
            public String getObject() {
                return object;
            }

            @Override
            public String toString() {
                return String.format("%s:%s", getBucket(), getObject());
            }
        };
    }

    public String getBucket();

    public String getObject();

}
