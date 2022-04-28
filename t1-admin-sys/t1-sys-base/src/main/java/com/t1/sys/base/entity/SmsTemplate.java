package com.t1.sys.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.t1.common.annotation.Excel;
import com.t1.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Bruce Lee
 * @date 2022-04-28 00:41:00
 *
 * @description 消息中心对象 SmsTemplate
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_sms_template")
public class SmsTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId
    private Integer id;

    /** 模板标题 */
    @Excel(name = "模板标题")
    private String templateName;

    /** 模板CODE */
    @Excel(name = "模板CODE")
    private String templateCode;

    /** 模板类型：1短信 2邮件 3微信 */
    @Excel(name = "模板类型：1短信 2邮件 3微信")
    private String templateType;

    /** 模板内容 */
    @Excel(name = "模板内容")
    private String templateContent;

    /** 模板测试json */
    @Excel(name = "模板测试json")
    private String templateTestJson;

}
