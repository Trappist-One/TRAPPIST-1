package com.t1.sys.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t1.sys.base.entity.Config;

/**
 * <p>
 * 参数配置表 服务类
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @since 2019-01-30
 */
public interface ConfigService extends IService<Config> {
    String getValueByKey(String key);
}
