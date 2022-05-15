package com.t1.oauth.model;

import com.t1.common.constant.SecurityConstants;
import com.t1.oauth2.common.token.CustomWebAuthenticationDetails;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 表单登录的认证信息对象
 *
 * @author Bruce Lee (Copy)
 * @version 1.0
 * @date 2021/7/21
 * <p>
 */
@Component
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, CustomWebAuthenticationDetails> {
    @Override
    public CustomWebAuthenticationDetails buildDetails(HttpServletRequest context) {
        String remoteAddress = context.getRemoteAddr();
        HttpSession session = context.getSession(false);
        String sessionId = session != null ? session.getId() : null;
        String accountType = context.getParameter(SecurityConstants.ACCOUNT_TYPE_PARAM_NAME);
        return new CustomWebAuthenticationDetails(remoteAddress, sessionId, accountType);
    }
}
