package com.t1.sys.toolkit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.sys.toolkit.entity.Dataset;
import com.t1.sys.toolkit.mapper.DatasetMapper;
import com.t1.sys.toolkit.service.DatasetService;
import org.springframework.stereotype.Service;

/**
 * @author Bruce Lee ( copy )
 * @date 2020-06-12 21:56:29
 * @description 数据源Service业务层
 */
@Service
public class DatasetServiceImpl extends ServiceImpl<DatasetMapper, Dataset> implements DatasetService {
}
