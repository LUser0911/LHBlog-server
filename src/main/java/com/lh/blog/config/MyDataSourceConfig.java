package com.lh.blog.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/3/30 10:50
 */
@Configuration
public class MyDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(10);
        dataSource.setInitialSize(1);
        dataSource.setMaxWait(5000);
        return dataSource;
    }
}
