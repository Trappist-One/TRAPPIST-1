package com.t1.biz.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t1.biz.system.entity.DictData;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @since 2019-01-30
 */
public interface DictDataService extends IService<DictData> {

    public List<DictData> getDictDataList(String dictType);
}
