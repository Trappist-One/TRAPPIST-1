package com.t1.db.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/2/1
 *
 * 动态数据源类
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 指定路由Key
	 *
	 * @return
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DSContextHolder.getDSType();
	}
}
