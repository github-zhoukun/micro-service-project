package com.zk.elasticsearchlog.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author zhoukun
 */
@Configuration
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = false, encoding = "UTF-8", name = "application.properties")
public class DruidConfig {
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.dataUserName}")
    private String dataUsername;
    @Value("${spring.datasource.password}")
    private String password;

    @Primary
    @Bean(name = "druidDataSource")
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(dataUsername);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }

}
