package com.learn.word.config;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 此类作用类似与建表配置（used for creat pojo table）
 * @author ljw
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryWord",
						transactionManagerRef = "transactionManagerWord",
						basePackages = {"com.learn.word"}
						)//设置dao（repo）所在位置
public class RepositoryWordConfig {

	@Autowired
    private JpaProperties jpaProperties;
	
	@Autowired
    @Qualifier("datasourceWord")
    private DataSource dataSource;
	

	
	
	@Bean(name = "entityManagerFactoryWord")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder){
		
		return builder
				.dataSource(dataSource)
				.properties(getVendorProperties(dataSource))
				.packages("com.learn.word.domain")//实体类所在的包
				.persistenceUnit("wordPersistenceUnit")
				.build();
	}
	
	
	@Bean(name = "transactionManagerWord")
	public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder){
		return new JpaTransactionManager(entityManagerFactory(builder).getObject());
		
	}
	
	/**
	 * 配置数据源的方言
	 * @return
	 */
	@Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setDatabasePlatform("com.enigmabridge.hibernate.dialect.SQLiteDialect");
        return hibernateJpaVendorAdapter;
    }
	
	

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }
	
}
