package com.xming.config;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 *
 *
 *
 * Created by mc on 17/9/1.
 */
@Configuration
@MapperScan(basePackages = "com.xming.modules.secondary.dao",sqlSessionTemplateRef = "secondarySqlSessionTemplate")
public class SecondaryMybatisConfig {


    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource(SecondaryDBConfig secondaryDBConfig) throws SQLException {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(secondaryDBConfig.getUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setPassword(secondaryDBConfig.getPassword());
        mysqlXADataSource.setUser(secondaryDBConfig.getUsername());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean akDataSource = new AtomikosDataSourceBean();
        akDataSource.setXaDataSource(mysqlXADataSource);
        akDataSource.setUniqueResourceName("secondaryDataSource");

        akDataSource.setMinPoolSize(secondaryDBConfig.getMinPoolSize());
        akDataSource.setMaxPoolSize(secondaryDBConfig.getMaxPoolSize());
        akDataSource.setMaxIdleTime(secondaryDBConfig.getMaxIdleTime());

        akDataSource.setBorrowConnectionTimeout(secondaryDBConfig.getBorrowConnectionTimeout());
        akDataSource.setLoginTimeout(secondaryDBConfig.getLoginTimeout());
        akDataSource.setMaintenanceInterval(secondaryDBConfig.getMaintenanceInterval());
        akDataSource.setMaxLifetime(secondaryDBConfig.getMaxLifetime());
        akDataSource.setTestQuery(secondaryDBConfig.getTestQuery());
        return akDataSource;
    }


    @Bean(name = "secondarySqlSessionFactory")
    public SqlSessionFactory secondarySqlSessionFactoryBean(@Qualifier("secondaryDataSource")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/secondary/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "secondarySqlSessionTemplate")
    public SqlSessionTemplate secondarySqlSessionTemplate(@Qualifier("secondarySqlSessionFactory")SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
