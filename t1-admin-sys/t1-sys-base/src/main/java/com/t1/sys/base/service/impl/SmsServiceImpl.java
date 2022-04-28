package com.t1.sys.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.t1.sys.base.mapper.SmsMapper;
import com.t1.sys.base.entity.Sms;
import com.t1.sys.base.service.SmsService;

/**
 * @author Bruce Lee
 * @date 2022-04-28 00:41:00
 * @description 消息中心Service业务层
 */
@Service
public class SmsServiceImpl extends ServiceImpl<SmsMapper, Sms> implements SmsService {
}
