package com.t1.sys.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.sys.rbac.service.RoleDeptService;
import com.t1.sys.rbac.entity.RoleDept;
import com.t1.sys.rbac.mapper.RoleDeptMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和部门关联表 服务实现类
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @since 2019-01-30
 */
@Service
public class RoleDeptServiceImpl extends ServiceImpl<RoleDeptMapper, RoleDept> implements RoleDeptService {

}
