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
import com.github.yingzhuo.spring.boot.minio.properties.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.SmartFactoryBean;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.0.2
 */
@Slf4j
public class MinioClientFactory implements SmartFactoryBean<MinioClient> {

    private final OkHttpClientProvider okHttpClientProvider;
    private final MinioProperties properties;

    public MinioClientFactory(OkHttpClientProvider okHttpClientProvider, MinioProperties properties) {
        this.okHttpClientProvider = Objects.requireNonNull(okHttpClientProvider);
        this.properties = Objects.requireNonNull(properties);
    }

    @Override
    public Class<?> getObjectType() {
        return MinioClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public MinioClient getObject() {
        OkHttpClient okHttpClient = okHttpClientProvider.get();

        final MinioClient.Builder builder = MinioClient.builder();
        builder.endpoint(properties.getEndpoint());
        builder.credentials(properties.getAccessKey(), properties.getSecretKey());

        if (okHttpClient != null) {
            builder.httpClient(okHttpClient);
        }

        MinioClient client = builder.build();

        client.setTimeout(
                properties.getConnectTimeout().toMillis(),
                properties.getWriteTimeout().toMillis(),
                properties.getReadTimeout().toMillis()
        );

        if (properties.isCheckBucket()) {
            try {
                log.debug("Checking if bucket {} exists", properties.getBucket());
                BucketExistsArgs existsArgs = BucketExistsArgs.builder()
                        .bucket(properties.getBucket())
                        .build();
                boolean b = client.bucketExists(existsArgs);
                if (!b) {
                    if (properties.isCreateBucket()) {
                        try {
                            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
                                    .bucket(properties.getBucket())
                                    .build();
                            client.makeBucket(makeBucketArgs);
                        } catch (Exception e) {
                            throw new MinioException("Cannot create bucket", e);
                        }
                    } else {
                        throw new IllegalStateException("Bucket does not exist: " + properties.getBucket());
                    }
                }
            } catch (Exception e) {
                log.error("Error while checking bucket", e);
                throw new MinioException(e.getMessage(), e);
            }
        }

        return client;
    }

}
