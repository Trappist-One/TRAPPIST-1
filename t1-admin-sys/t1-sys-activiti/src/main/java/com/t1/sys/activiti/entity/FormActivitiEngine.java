package com.t1.sys.activiti.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 动态表单工作流引擎
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("act_from_activiti_engine")
public class FormActivitiEngine {

    /**
     * 表单扩展模板编码
     */
    @TableId
    private Long id;

    /**
     * 表单扩展编码
     */
    private String code;

    /**
     * 表单扩展模板名称
     */
    private String name;


    /**
     * 渲染控件数据集合
     */
    private String drawingControls;


    /**
     * 级联下拉树形选项局部ID-内部标识-目前实现控件独立
     */
    private String treeNodeId;


    /**
     * 记录目前控件全局ID-自增长
     */
    private Long idGlobal;
    /**
     * 创建人
     */
    private  String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remarks;

    /** 开始时间 */
    @TableField(exist = false)
    @JsonIgnore
    private String beginTime;

    /** 结束时间 */
    @TableField(exist = false)
    @JsonIgnore
    private String endTime;

    /**
     * 表单配置
     */
    private String formConf;

}
