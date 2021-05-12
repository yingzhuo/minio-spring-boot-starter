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

import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.View;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.0.1
 */
public class MinioObjectView implements View {

    private final InputStream in;
    private final String attachmentName;

    public MinioObjectView(InputStream in, String attachmentName) {
        this.in = in;
        this.attachmentName = attachmentName;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Cache", "no-cache");
        response.setHeader("Content-Type", "application/force-download");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", encodeFilename(attachmentName)));
        response.setHeader("Content-Transfer-Encoding", "binary");

        final ServletOutputStream out = response.getOutputStream();

        try {
            IOUtils.copy(in, out);
            out.flush();
            out.close();
        } finally {
            if (in != null) in.close();
        }
    }

    private String encodeFilename(final String filename) {
        try {
            URI uri = new URI(null, null, filename, null);
            return uri.toASCIIString();
        } catch (URISyntaxException ex) {
            return filename;
        }
    }

}
