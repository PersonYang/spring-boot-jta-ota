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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import java.sql.SQLException;
import javax.sql.DataSource;
/**
 *
 * PrimaryMybatisConfig
 * Created by mc on 17/9/1.
 */
@Configuration
@MapperScan(basePackages = "com.xming.modules.primary.dao",sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryMybatisConfig {

    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource primaryDataSource(PrimaryDBConfig primaryDBConfig) throws SQLException{
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(primaryDBConfig.getUrl());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXADataSource.setPassword(primaryDBConfig.getPassword());
        mysqlXADataSource.setUser(primaryDBConfig.getUsername());
        mysqlXADataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean akDataSource = new AtomikosDataSourceBean();
        akDataSource.setXaDataSource(mysqlXADataSource);
        akDataSource.setUniqueResourceName("primaryDataSource");

        akDataSource.setMinPoolSize(primaryDBConfig.getMinPoolSize());
        akDataSource.setMaxPoolSize(primaryDBConfig.getMaxPoolSize());
        akDataSource.setMaxIdleTime(primaryDBConfig.getMaxIdleTime());

        akDataSource.setBorrowConnectionTimeout(primaryDBConfig.getBorrowConnectionTimeout());
        akDataSource.setLoginTimeout(primaryDBConfig.getLoginTimeout());
        akDataSource.setMaintenanceInterval(primaryDBConfig.getMaintenanceInterval());
        akDataSource.setMaxLifetime(primaryDBConfig.getMaxLifetime());
        akDataSource.setTestQuery(primaryDBConfig.getTestQuery());
        return akDataSource;
    }


    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/primary/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "primarySqlSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("primarySqlSessionFactory")SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
