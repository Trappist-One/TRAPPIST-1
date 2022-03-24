package com.t1.oauth.handler;

import com.t1.common.constant.SqlConstants;
import com.t1.common.security.handler.AbstractAuthenticationFailureEvenHandler;
import com.t1.oauth.util.LoginLogUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletableFuture;

/**
 * @author Bruce Lee ( copy )
 * @date 2018/10/8
 */
@Slf4j
@Component
@AllArgsConstructor
public class T1AuthenticationFailureEvenHandler extends AbstractAuthenticationFailureEvenHandler {

    private final TaskExecutor taskExecutor;
    private final JdbcTemplate jdbcTemplate;

    /**
     * 处理登录失败方法
     * <p>
     *
     * @param authenticationException 登录的authentication 对象
     * @param authentication          登录的authenticationException 对象
     * @param request                 请求
     * @param response                响应
     */
    @Override
    public void handle(AuthenticationException authenticationException, Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());

        PreparedStatementSetter pss = LoginLogUtil.setLoginLog(request, "0", authentication.getPrincipal().toString(),"登录失败，异常：" + authenticationException.getLocalizedMessage());

        CompletableFuture.runAsync(() -> {
            log.info("执行结果：" + jdbcTemplate.update(SqlConstants.LOGIN_LOG, pss));
        }, taskExecutor);

    }
}
