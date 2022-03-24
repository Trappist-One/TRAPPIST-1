package com.t1.oauth.service.impl;

import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import com.t1.common.constant.SecurityConstants;
import com.t1.common.model.LoginAppUser;
import com.t1.common.model.R;
import com.t1.oauth.model.TokenVo;
import com.t1.oauth.service.ITokensService;
import com.t1.redis.template.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * token管理服务(redis token)
 *
 * @author Bruce Lee(copy)
 * @date 2019/7/12
 * <p>
 */
@Slf4j
@Service
public class RedisTokensServiceImpl implements ITokensService {
    @Autowired
    private RedisRepository redisRepository;

    @Override
    public R listTokens(Map<String, Object> params, String clientId) {
        Integer page = MapUtils.getInteger(params, "current");
        Integer limit = MapUtils.getInteger(params, "size");
        int[] startEnds = PageUtil.transToStartEnd(page, limit);
        //根据请求参数生成redis的key
        String redisKey = getRedisKey(params, clientId);
        long size = redisRepository.length(redisKey);
        List<TokenVo> tokenVos = new ArrayList<>(limit);
        RedisSerializer<Object> valueSerializer = RedisSerializer.java();
        //查询token集合
        List<Object> tokenObjs = redisRepository.getList(redisKey, startEnds[0], startEnds[1]-1, valueSerializer);
        if (tokenObjs != null) {
            for (Object obj : tokenObjs) {
                DefaultOAuth2AccessToken accessToken = (DefaultOAuth2AccessToken)obj;
                //构造token对象
                TokenVo tokenVo = new TokenVo();
                tokenVo.setTokenValue(accessToken.getValue());
                tokenVo.setExpiresIn(accessToken.getExpiration());

                //获取用户信息
                Object authObj = redisRepository.get(SecurityConstants.REDIS_TOKEN_AUTH + accessToken.getValue(), valueSerializer);
                OAuth2Authentication authentication = (OAuth2Authentication)authObj;
                if (authentication != null) {
                    OAuth2Request request = authentication.getOAuth2Request();
                    LoginAppUser loginUser = (LoginAppUser) authentication.getPrincipal();
                    tokenVo.setUserId("" + loginUser.getId());
                    tokenVo.setUsername(authentication.getName());
                    tokenVo.setClientId(request.getClientId());
                    tokenVo.setGrantType(request.getGrantType());
                }

                tokenVos.add(tokenVo);
            }
        }

        return R.success(tokenVos, tokenVos.size());
    }

    /**
     * 根据请求参数生成redis的key
     */
    private String getRedisKey(Map<String, Object> params, String clientId) {
        String result;
        String username = MapUtils.getString(params, "username");
        if (StrUtil.isNotEmpty(username)) {
            result = SecurityConstants.REDIS_UNAME_TO_ACCESS + clientId + ":" + username;
        } else {
            result = SecurityConstants.REDIS_CLIENT_ID_TO_ACCESS + clientId;
        }
        return result;
    }
}