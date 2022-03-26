package com.t1.sys.intelligent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t1.sys.intelligent.entity.ControlSteward;

/**
 * <p>
 * 控件大管家业务接口
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/3/27
 */
public interface ControlStewardService extends IService<ControlSteward> {


    /**
     * 创建控件表
     *@Param [json]
     *@return int
     */
    int createControlsTable(String TableName,String json);

    /**
     * 删除控件表
     *@Param [ids]
     *@return int
     */
    int deleteControlsTable(Integer[] ids);


    int selectControlsTable(String Table);

}
