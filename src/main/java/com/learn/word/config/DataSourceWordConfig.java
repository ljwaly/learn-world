package com.learn.word.config;

import javax.sql.DataSource;
import javax.swing.JPopupMenu.Separator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.sqlite.SQLiteDataSource;

/**
 * 此类作用是创建数据库（This class used for creating datasource）
 * 需要将创建表的url、username、password、driverClassName在配置文件中进行声明
 * 
 * 
 * 多数据源--mysql配置的数据源
 * 
 * @author PC
 *
 */

@Configuration
@PropertySource("classpath:database-conf.properties")
public class DataSourceWordConfig {

	private static final String pathSeparator = "/";
	
	@Value("${db_uri}")
	private String dburi;
	
	@Value("${db_path}")
	private String dbPath;
	
	@Value("${db_name}")
	private String dbName;
	
	@Value("${driver_class_name}")
	private String driverClassName;
	
	
	/**
	 * 配置数据源
	 * @return
	 */
	@Bean(name = "datasourceWord",destroyMethod = "")
	//@ConfigurationProperties(prefix = "word.datasource")//配置文件中，mysql数据库信息的前缀
	public DataSource buildDataSource(){
		String pre = this.getClass().getResource("/").toString();
		//String pre = "\\";
		String db_url = dburi + pre + dbPath + pathSeparator + dbName;
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
//        dataSourceBuilder.url("jdbc:sqlite://D:/sqlite/test.db");
		
		dataSourceBuilder.driverClassName(driverClassName);
		dataSourceBuilder.url(db_url);
        dataSourceBuilder.type(SQLiteDataSource.class);
        return dataSourceBuilder.build();
		//return DataSourceBuilder.create().build();
		
	}
	
	@Bean(name = "wordJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("datasourceWord") DataSource dsMySQL) {
        return new JdbcTemplate(dsMySQL);
    }
	
	
}
