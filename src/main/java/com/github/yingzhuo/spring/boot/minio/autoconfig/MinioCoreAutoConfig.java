/*
           _       _                            _                   _                 _            _             _
 _ __ ___ (_)_ __ (_) ___        ___ _ __  _ __(_)_ __   __ _      | |__   ___   ___ | |_      ___| |_ __ _ _ __| |_ ___ _ __
| '_ ` _ \| | '_ \| |/ _ \ _____/ __| '_ \| '__| | '_ \ / _` |_____| '_ \ / _ \ / _ \| __|____/ __| __/ _` | '__| __/ _ \ '__|
| | | | | | | | | | | (_) |_____\__ \ |_) | |  | | | | | (_| |_____| |_) | (_) | (_) | ||_____\__ \ || (_| | |  | ||  __/ |
|_| |_| |_|_|_| |_|_|\___/      |___/ .__/|_|  |_|_| |_|\__, |     |_.__/ \___/ \___/ \__|    |___/\__\__,_|_|   \__\___|_|
                                    |_|                 |___/

 https://github.com/yingzhuo/minio-spring-boot-starter
*/
package com.github.yingzhuo.spring.boot.minio.autoconfig;

import com.github.yingzhuo.spring.boot.minio.MinioAgent;
import com.github.yingzhuo.spring.boot.minio.exception.MinioException;
import com.github.yingzhuo.spring.boot.minio.impl.DefaultMinioAgent;
import com.github.yingzhuo.spring.boot.minio.properties.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @author 应卓
 * @since 1.0.0
 */
@Slf4j
@ConditionalOnProperty(prefix = "minio", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(MinioProperties.class)
class MinioCoreAutoConfig {

    private static final String PROXY_HOST = "http.proxyHost";
    private static final String PROXY_PORT = "http.proxyPort";

    @Bean
    @ConditionalOnMissingBean
    @SneakyThrows
    MinioClient minioClient(MinioProperties properties) {
        MinioClient client;

        if (isProxy()) {
            client = MinioClient.builder()
                    .endpoint(properties.getEndpoint())
                    .credentials(properties.getAccessKey(), properties.getSecretKey())
                    .build();
        } else {
            client = MinioClient.builder()
                    .endpoint(properties.getEndpoint())
                    .credentials(properties.getAccessKey(), properties.getSecretKey())
                    .httpClient(httpClient())
                    .build();
        }

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
                throw e;
            }
        }

        return client;
    }

    private boolean isProxy() {
        final String httpHost = System.getProperty(PROXY_HOST);
        final String httpPort = System.getProperty(PROXY_PORT);
        return httpHost != null && httpPort != null;
    }

    private OkHttpClient httpClient() {
        String httpHost = System.getProperty(PROXY_HOST);
        String httpPort = System.getProperty(PROXY_PORT);

        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (httpHost != null) {
            builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpHost, Integer.parseInt(httpPort))));
        }
        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean
    MinioAgent minioAgent(MinioClient client) {
        return new DefaultMinioAgent(client);
    }

}
