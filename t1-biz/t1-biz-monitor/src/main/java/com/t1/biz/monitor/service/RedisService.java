package com.t1.biz.monitor.service;

import java.util.Map;

/**
 * @author Bruce Lee ( copy )
 * @date 2020-05-16
 * @description redis 数据
 */
public interface RedisService {
	/**
	 * 获取内存信息
	 *
	 * @return
	 */
	Map<String, Object> getInfo();
}
