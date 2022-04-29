package com.lh.blog.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/2 21:02
 * @description
 */
@Configuration
public class LHBlogWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${lh.upload-path}")
    private String imagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
//                    .addResourceLocations("file:F:/me/LHBlog-Server/src/main/resources/static/")
//                    .addResourceLocations("classpath:/static/");
//                    .addResourceLocations("classpath:/static/")
//            .addResourceLocations(new StringBuilder().append("file:").append(canonicalPath).append("/src/main/resources/static/").toString())
                .addResourceLocations(new StringBuilder("file:").append(imagePath).toString());
//                .addResourceLocations("classpath:/static/");
    }

}
