package com.t1.sys.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.t1.sys.base.mapper.SmsTemplateMapper;
import com.t1.sys.base.entity.SmsTemplate;
import com.t1.sys.base.service.SmsTemplateService;

/**
 * @author Bruce Lee
 * @date 2022-04-28 00:41:00
 * @description 消息中心Service业务层
 */
@Service
public class SmsTemplateServiceImpl extends ServiceImpl<SmsTemplateMapper, SmsTemplate> implements SmsTemplateService {
}
