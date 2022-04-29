package com.lh.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/3/30 10:42
 */
@SpringBootApplication
@MapperScan(basePackages = "com.lh.blog.dao")
public class LHBlogSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(LHBlogSpringBootApplication.class,args);
    }
}
