package com.t1.oauth.controller;

import cn.hutool.core.util.StrUtil;
import com.t1.common.annotation.LoginClient;
import com.t1.common.annotation.LoginUser;
import com.t1.common.entity.User;
import com.t1.common.model.R;
import com.t1.log.annotation.OperLog;
import com.t1.oauth.service.ITokensService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * token管理接口
 *
 * @author Bruce Lee(copy)
 */
@Api(tags = "Token管理")
@RestController
@RequestMapping("/token")
public class TokensController {
    @Autowired
    private ITokensService tokensService;

    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/list")
    @OperLog("token列表")
    public R list(@LoginClient String tenantId, @LoginUser User user, int current, int size) {
        Map<String, Object> params = new HashMap<>();
        params.put("current", current);
        params.put("size", size);
        params.put("username", user.getUserName());
        return tokensService.listTokens(params, tenantId);
    }

    /**
     * 令牌删除
     *
     * @param token token
     * @return
     */
    @PreAuthorize("@ps.hasPerm('token_del')")
    @DeleteMapping("/{token}")
    public R<Boolean> delToken(@PathVariable("token") String token) {
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        if (oAuth2AccessToken == null || StrUtil.isBlank(oAuth2AccessToken.getValue())) {
            return R.success(Boolean.TRUE, "操作失败，token 无效");
        }
        // 清空access token
        tokenStore.removeAccessToken(oAuth2AccessToken);
        // 清空 refresh token
        OAuth2RefreshToken refreshToken = oAuth2AccessToken.getRefreshToken();
        tokenStore.removeRefreshToken(refreshToken);
        return new R<>();
    }

}
