package com.t1.sys.activiti.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.sys.activiti.entity.Leave;
import com.t1.sys.activiti.mapper.LeaveMapper;
import com.t1.sys.activiti.service.LeaveService;
import org.springframework.stereotype.Service;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/3/23
 * @description 请假 ServiceImpl
 */
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements LeaveService {

}
