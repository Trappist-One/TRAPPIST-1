package com.t1.oauth.handler;

import cn.hutool.core.util.StrUtil;
import com.t1.common.constant.SqlConstants;
import com.t1.common.model.LoginAppUser;
import com.t1.oauth.util.LoginLogUtil;
import com.t1.oauth2.common.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletableFuture;

/**
 * @author Bruce Lee(copy)
 * @date 2018/10/17
 */
@Slf4j
public class OauthLogoutHandler implements LogoutHandler {
	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Assert.notNull(tokenStore, "tokenStore must be set");
		String token = request.getParameter("token");
		if (StrUtil.isEmpty(token)) {
			token = AuthUtils.extractToken(request);
		}
		if(StrUtil.isNotEmpty(token)){
			LoginAppUser user = (LoginAppUser) tokenStore.readAuthentication(token).getPrincipal();
			OAuth2AccessToken existingAccessToken = tokenStore.readAccessToken(token);
			OAuth2RefreshToken refreshToken;
			if (existingAccessToken != null) {
				if (existingAccessToken.getRefreshToken() != null) {
					log.info("remove refreshToken!", existingAccessToken.getRefreshToken());
					refreshToken = existingAccessToken.getRefreshToken();
					tokenStore.removeRefreshToken(refreshToken);
				}
				log.info("remove existingAccessToken!", existingAccessToken);
				tokenStore.removeAccessToken(existingAccessToken);
			}

			if (user != null) {
				logLogout(request, user);
			}
		}
	}

	private void logLogout(HttpServletRequest request, LoginAppUser user) {
		String loginType = "1"; // 0 登录 1退出
		PreparedStatementSetter pss = LoginLogUtil.setLoginLog(request, loginType, user.getUsername(), "");
		CompletableFuture.runAsync(() -> {
			log.info("执行结果：" + jdbcTemplate.update(SqlConstants.LOGIN_LOG, pss));
		}, taskExecutor);

	}
}
