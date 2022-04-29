package com.lh.blog.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/3/30 11:26
 */
@Configuration
public class MPConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public MybatisConfiguration mybatisConfiguration(){
        MybatisConfiguration mpConfiguration = new MybatisConfiguration();
        //开启驼峰命名
        mpConfiguration.setMapUnderscoreToCamelCase(true);
        return mpConfiguration;
    }

//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactoryBean sessionFactory() {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setPlugins(pageInterceptor());
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResource("classpath*:/mapper/*.xml"));
//       return sqlSessionFactoryBean;
//    }


//
//    @Bean
//    public PageInterceptor pageInterceptor(){
//        return new PageInterceptor();
//    }

}
