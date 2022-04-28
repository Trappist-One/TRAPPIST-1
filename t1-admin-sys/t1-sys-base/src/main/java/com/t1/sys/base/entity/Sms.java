package com.t1.sys.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.t1.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.t1.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Bruce Lee
 * @date 2022-04-28 00:41:00
 * @description 消息中心对象 Sms
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_sms")
public class Sms extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Integer id;

    /**
     * 消息标题
     */
    @Excel(name = "消息标题")
    private String esTitle;

    /**
     * 发送方式：1短信 2邮件 3微信
     */
    @Excel(name = "发送方式：1短信 2邮件 3微信")
    private String type;

    /**
     * 接收人
     */
    @Excel(name = "接收人")
    private String receiver;

    /**
     * 发送所需参数Json格式
     */
    @Excel(name = "发送所需参数Json格式")
    private String param;

    /**
     * 推送内容
     */
    @Excel(name = "推送内容")
    private String content;

    /**
     * 推送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "推送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendTime;

    /**
     * 推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送
     */
    @Excel(name = "推送状态 0未推送 1推送成功 2推送失败 -1失败不再发送")
    private String sendStatus;

    /**
     * 发送次数 超过5次不再发送
     */
    @Excel(name = "发送次数 超过5次不再发送")
    private Long sendNum;

    /**
     * 推送失败原因
     */
    @Excel(name = "推送失败原因")
    private String result;

}
