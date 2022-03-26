package com.t1.sys.toolkit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.sys.toolkit.entity.Apiinfo;
import com.t1.sys.toolkit.mapper.ApiinfoMapper;
import com.t1.sys.toolkit.service.ApiinfoService;
import org.springframework.stereotype.Service;

/**
 * @author Bruce Lee ( copy )
 * @date 2020-04-24 22:18:00
 * @description 接口Service业务层
 */
@Service
public class ApiinfoServiceImpl extends ServiceImpl<ApiinfoMapper, Apiinfo> implements ApiinfoService {
}
