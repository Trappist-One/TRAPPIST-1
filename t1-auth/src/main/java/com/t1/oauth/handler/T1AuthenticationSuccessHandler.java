package com.t1.oauth.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.t1.common.constant.SecurityConstants;
import com.t1.common.constant.SqlConstants;
import com.t1.common.model.LoginAppUser;
import com.t1.common.security.handler.AbstractAuthenticationSuccessEventHandler;
import com.t1.oauth.util.LoginLogUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletableFuture;

/**
 * @author entfmr
 * @date 2018/10/8
 */
@Slf4j
@Component
@AllArgsConstructor
public class T1AuthenticationSuccessHandler extends AbstractAuthenticationSuccessEventHandler {

	private final TaskExecutor taskExecutor;
	private final JdbcTemplate jdbcTemplate;

	/**
	 * 处理登录成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 *
	 * @param authentication 登录对象
	 * @param request        请求
	 * @param response       返回
	 */
	@Override
	public void handle(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		LoginAppUser user = (LoginAppUser) authentication.getPrincipal();

		String url = URLUtil.getPath(request.getRequestURI());

		log.info("用户：{} 授权成功，url：{}", user, url);

		String loginType = "0"; // 0 登录 1退出
		/*
		   退出的记录在 com.t1.oauth.handler.OauthLogoutHandler.logLogout，是否统一在这？
		 */
		if (StrUtil.containsAny(url, SecurityConstants.AUTH_TOKEN, SecurityConstants.TOKEN_LOGOUT)) {

			if(SecurityConstants.TOKEN_LOGOUT.equals(url)){
				loginType = "1";
			}

			PreparedStatementSetter pss = LoginLogUtil.setLoginLog(request, loginType, user.getUsername(), "");

			CompletableFuture.runAsync(() -> {
				log.info("执行结果：" + jdbcTemplate.update(SqlConstants.LOGIN_LOG, pss));
			}, taskExecutor);

		}
	}
}
