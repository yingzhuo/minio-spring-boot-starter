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

import com.github.yingzhuo.spring.boot.minio.BucketAndObjectConverter;
import com.github.yingzhuo.spring.boot.minio.MinioClientFactory;
import com.github.yingzhuo.spring.boot.minio.OkHttpClientProvider;
import com.github.yingzhuo.spring.boot.minio.operators.BucketOperators;
import com.github.yingzhuo.spring.boot.minio.operators.BucketOperatorsImpl;
import com.github.yingzhuo.spring.boot.minio.operators.ObjectOperators;
import com.github.yingzhuo.spring.boot.minio.operators.ObjectOperatorsImpl;
import com.github.yingzhuo.spring.boot.minio.properties.MinioProperties;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.0.0
 */
@ConditionalOnProperty(prefix = "minio", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(MinioProperties.class)
class MinioCoreAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    BucketAndObjectConverter bucketAndObjectConverter() {
        return new BucketAndObjectConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    OkHttpClientProvider okHttpClientProvider() {
        return () -> null;
    }

    @Bean(name = "minioClient")
    @ConditionalOnMissingBean({MinioClient.class})
    MinioClientFactory minioClientFactory(OkHttpClientProvider httpClientProvider, MinioProperties properties) {
        return new MinioClientFactory(httpClientProvider, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    BucketOperators bucketOperators(MinioClient client, MinioProperties properties) {
        return new BucketOperatorsImpl(client, properties.getBucket());
    }

    @Bean
    @ConditionalOnMissingBean
    ObjectOperators objectOperators(MinioClient client, MinioProperties properties) {
        return new ObjectOperatorsImpl(client, properties.getBucket());
    }

}
