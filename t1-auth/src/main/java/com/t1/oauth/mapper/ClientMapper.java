package com.t1.oauth.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.db.mapper.SuperMapper;
import com.t1.oauth.model.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Bruce Lee(copy)
 */
@Mapper
public interface ClientMapper extends SuperMapper<Client> {
    List<Client> findList(Page<Client> page, @Param("params") Map<String, Object> params );
}
