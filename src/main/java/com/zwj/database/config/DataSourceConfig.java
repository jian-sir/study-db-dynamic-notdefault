package com.zwj.database.config;

import javax.sql.DataSource;

import com.zwj.database.util.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zwj
 * @version 1.0
 * @date 2020/8/5
 */
@Configuration
@MapperScan(basePackages="com.zwj.database", value="sqlSessionFactory")
public class DataSourceConfig {
    /**
     * 根据配置参数创建数据源。使用派生的子类。
     *
     * @return 数据源
     */
    @Bean(name="dataSource")
    @ConfigurationProperties(prefix="spring.datasource.druid")
    public DataSource getDataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.type(DynamicDataSource.class);
        return builder.build();
    }


    /**
     * 创建会话工厂。
     *
     * @param dataSource 数据源
     * @return 会话工厂
     */
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        try {
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
