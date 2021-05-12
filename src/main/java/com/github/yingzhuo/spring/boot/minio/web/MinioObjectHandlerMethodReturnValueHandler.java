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

import com.github.yingzhuo.spring.boot.minio.MinioAgent;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.InputStream;

/**
 * @author 应卓
 * @since 1.0.1
 */
public class MinioObjectHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final MinioAgent agent;

    public MinioObjectHandlerMethodReturnValueHandler(MinioAgent agent) {
        this.agent = agent;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getParameterType() == MinioObject.class;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        final MinioObject minioObject = (MinioObject) returnValue;

        final String bucket = minioObject.getBucket();
        final String object = minioObject.getObject();
        final InputStream inputStream = agent.getObject(bucket, object);

        mavContainer.setView(new MinioObjectView(inputStream, minioObject.getAttachmentName()));
    }

}
