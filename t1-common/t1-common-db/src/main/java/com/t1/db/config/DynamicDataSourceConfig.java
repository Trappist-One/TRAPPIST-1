package com.t1.db.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.t1.db.constant.DataSourceConstants;
import com.t1.db.datasource.DruidDataSourceProperties;
import com.t1.db.datasource.DynamicDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Bruce Lee ( copy )
 * @date 2019-03-31
 *
 * 动态数据源切换配置
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class DynamicDataSourceConfig {
	private final Map<Object, Object> dataSourceMap = new HashMap<>(8);
	private final DruidDataSourceProperties dataSourceProperties;

	@Bean(name = "dynamicDataSource")
//	@Primary //标志这个 Bean 如果在多个同类 Bean 候选时，该 Bean 优先被考虑。但是通过路由选择就没有这个问题
	public DynamicDataSource dataSource() {
		DynamicDataSource ds = new DynamicDataSource();
		DruidDataSource dds = new DruidDataSource();
		intDruidDataSource(dds);
		ds.setDefaultTargetDataSource(dds);
		dataSourceMap.put(0, dds);
		ds.setTargetDataSources(dataSourceMap);
		return ds;
	}

	public final void intDruidDataSource(DruidDataSource dds) {
		dds.setUrl(dataSourceProperties.getUrl());
		dds.setUsername(dataSourceProperties.getUsername());
		dds.setPassword(dataSourceProperties.getPassword());
		dds.setDriverClassName(dataSourceProperties.getDriverClassName());

		//具体配置
		dds.setInitialSize(dataSourceProperties.getInitialSize());
		dds.setMinIdle(dataSourceProperties.getMinIdle());
		dds.setMaxActive(dataSourceProperties.getMaxActive());
		dds.setMaxWait(dataSourceProperties.getMaxWait());
		dds.setTimeBetweenEvictionRunsMillis(dataSourceProperties.getTimeBetweenEvictionRunsMillis());
		dds.setMinEvictableIdleTimeMillis(dataSourceProperties.getMinEvictableIdleTimeMillis());
		dds.setValidationQuery(dataSourceProperties.getValidationQuery());
		dds.setTestWhileIdle(dataSourceProperties.isTestWhileIdle());
		dds.setTestOnBorrow(dataSourceProperties.isTestOnBorrow());
		dds.setTestOnReturn(dataSourceProperties.isTestOnReturn());
		dds.setPoolPreparedStatements(dataSourceProperties.isPoolPreparedStatements());
		dds.setMaxPoolPreparedStatementPerConnectionSize(dataSourceProperties.getMaxPoolPreparedStatementPerConnectionSize());

		List<Filter> filterList = new ArrayList<>();
		filterList.add(wallFilter());
		dds.setProxyFilters(filterList);

		/**
		 * 这个是用来配置 druid 监控sql语句的 非常有用 如果你有两个数据源 这个配置哪个数据源就监控哪个数据源的sql 同时配置那就都监控
		 */
		try {
			dds.setFilters(dataSourceProperties.getFilters());
			dds.init();
		} catch (SQLException e) {
			log.error("初始化数据库 {} 失败： {}", new String[]{dataSourceProperties.getUrl(), e.getMessage()});
		}
		dds.setConnectionProperties(dataSourceProperties.getConnectionProperties());

	}

	/**
	 * 组装默认配置的数据源，查询数据库配置
	 */
	@PostConstruct
	public void init() {
		DriverManagerDataSource dds = new DriverManagerDataSource();
		dds.setUrl(dataSourceProperties.getUrl());
		dds.setDriverClassName(dataSourceProperties.getDriverClassName());
		dds.setUsername(dataSourceProperties.getUsername());
		dds.setPassword(dataSourceProperties.getPassword());

		List<Map<String, Object>> dbList = new JdbcTemplate(dds).queryForList(DataSourceConstants.DS_QUERY_SQL);
		log.info("开始 -> 初始化动态数据源");
		Optional.of(dbList).ifPresent(list -> list.forEach(db -> {
			log.info("数据源:{}", db.get(DataSourceConstants.DS_NAME));
			DruidDataSource ds = new DruidDataSource();
			intDruidDataSource(ds);
//			还是使用配置的密码
//			String decPwd = (String) db.get(DataSourceConstants.DS_USER_PWD);
//			ds.setPassword(decPwd);
			dataSourceMap.put(db.get(DataSourceConstants.DS_ALIAS), ds);
		}));

		log.info("完毕 -> 初始化动态数据源,共计 {} 条", dataSourceMap.size());
	}

	/**
	 * 重新加载数据源配置
	 */
	public Boolean reload() {
		init();
		DynamicDataSource dataSource = dataSource();
		dataSource.setTargetDataSources(dataSourceMap);
		dataSource.afterPropertiesSet();
		return Boolean.FALSE;
	}

	@Bean
	public WallFilter wallFilter(){
		WallFilter wallFilter = new WallFilter();
		wallFilter.setConfig(wallConfig());
		return wallFilter;
	}

	@Bean
	public WallConfig wallConfig(){
		WallConfig wallConfig = new WallConfig();
		wallConfig.setMultiStatementAllow(true);//允许一次执行多条语句
		wallConfig.setNoneBaseStatementAllow(true);//允许一次执行多条语句
		return wallConfig;
	}


}
