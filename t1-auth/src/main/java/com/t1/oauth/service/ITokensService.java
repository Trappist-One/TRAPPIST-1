package com.t1.oauth.service;

import com.t1.common.model.R;

import java.util.Map;

/**
 * @author Bruce Lee(copy)
 */
public interface ITokensService {
    /**
     * 查询token列表
     * @param params 请求参数
     * @param clientId 应用id
     */
    R listTokens(Map<String, Object> params, String clientId);
}
