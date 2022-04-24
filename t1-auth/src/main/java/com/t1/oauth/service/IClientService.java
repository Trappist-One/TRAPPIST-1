package com.t1.oauth.service;

import com.t1.common.model.PageResult;
import com.t1.common.model.R;
import com.t1.common.service.ISuperService;
import com.t1.oauth.model.Client;

import java.util.Map;

/**
 * @author Bruce Lee(copy)
 * <p>
 */
public interface IClientService extends ISuperService<Client> {
    R saveClient(Client clientDto) throws Exception;

    /**
     * 查询应用列表
     * @param params
     * @param isPage 是否分页
     */
    PageResult<Client> listClient(Map<String, Object> params, boolean isPage);

    void delClient(long id);

    Client loadClientByClientId(String clientId);
}
