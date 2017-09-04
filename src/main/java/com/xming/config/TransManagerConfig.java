package com.xming.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 *
 * 分布式事务协调配置
 * Created by mc on 17/9/1.
 */
@Configuration
@EnableTransactionManagement
public class TransManagerConfig {


    @Bean(name = "userTransaction")
    public UserTransaction getUserTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(10000);
        return userTransactionImp;
    }

    @Bean(name = "atomikosTransactionManager",initMethod = "init",destroyMethod = "close")
    public TransactionManager getAtomikosTransactionManager(){
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }
    @Bean(name = "transactionManager")
    @DependsOn({"userTransaction","atomikosTransactionManager"})
    public PlatformTransactionManager transactionManager() throws Throwable {
        UserTransaction userTransaction = getUserTransaction();
        JtaTransactionManager jtaTransactionManager = new
                JtaTransactionManager(userTransaction,getAtomikosTransactionManager());
        return jtaTransactionManager;
    }


}
