package com.t1.biz.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t1.biz.system.entity.Area;

/**
 * 区域 数据层
 *
 * @author Bruce Lee ( copy )
 * @date 2019-05-06
 */
public interface AreaMapper extends BaseMapper<Area> {
    /**
     * 根据区域ID查询信息
     *
     * @param areaId 区域ID
     * @return 区域信息
     */
    public Area selectAreaById(Integer areaId);

    /**
     * 根据机构编码查询信息
     *
     * @param deptCode 机构编码
     * @return 区域信息
     */
    public Area selectAreaByCode(String deptCode);

}
