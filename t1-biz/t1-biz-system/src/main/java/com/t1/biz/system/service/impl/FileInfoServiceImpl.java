package com.t1.biz.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.biz.system.entity.FileInfo;
import com.t1.biz.system.mapper.FileInfoMapper;
import com.t1.biz.system.service.FileInfoService;
import org.springframework.stereotype.Service;

/**
 * @author Bruce Lee ( copy )
 * @date 2019-09-30 14:17:03
 *
 * @description 文件Service业务层
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

}
