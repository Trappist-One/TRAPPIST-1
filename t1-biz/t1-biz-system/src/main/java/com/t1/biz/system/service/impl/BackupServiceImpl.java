package com.t1.biz.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.biz.system.entity.Backup;
import com.t1.biz.system.mapper.BackupMapper;
import com.t1.biz.system.service.BackupService;
import org.springframework.stereotype.Service;

/**
 * @author Bruce Lee ( copy )
 * @date 2020-03-02 16:33:24
 *
 * @description 备份Service业务层
 */
@Service
public class BackupServiceImpl extends ServiceImpl<BackupMapper, Backup> implements BackupService {

}
