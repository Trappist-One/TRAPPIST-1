package com.t1.sys.activiti.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 表单扩展设计器
 * 数据传输封装(便于后期转换-后期扩展)
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/1/21
 */
@Data
@Accessors(chain = true)
public class FormActivitiEngineDesignDto {

    /**
     * 表单扩展ID
     */
    private Long dynamicFormId;
    /**
     * 整体表单的json数据
     */
    private JsonMode jsonMode;
    /**
     * 控件全局ID-区分
     */
    private Long idGlobal;
    /**
     * 级联下拉控件独立区分-局域[Tree-Key]ID数据
     */
    private String treeNodeId;


}
