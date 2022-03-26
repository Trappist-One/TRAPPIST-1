package com.t1.sys.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.sys.rbac.service.ClientDetailsService;
import com.t1.sys.rbac.entity.ClientDetails;
import com.t1.sys.rbac.mapper.ClientDetailsMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 授权客户端 服务实现类
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @since 2019-01-30
 */
@Service
public class ClientDetailsServiceImpl extends ServiceImpl<ClientDetailsMapper, ClientDetails> implements ClientDetailsService {

}
