package com.t1.common.feign.fallback;

import com.t1.common.entity.User;
import com.t1.common.feign.UserService;
import com.t1.common.model.LoginAppUser;
import org.springframework.cloud.openfeign.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * userService降级工场
 *
 * @author Bruce Lee(copy)
 * @date 2019/1/18
 */
@Slf4j
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable throwable) {
        return new UserService() {
            @Override
            public User findUserByUsername(String username) {
                log.error("通过用户名查询用户异常:{}", username, throwable);
                return new User();
            }

            @Override
            public LoginAppUser findLoginAppUserByUsername(String username) {
                log.error("通过用户名查询用户异常:{}", username, throwable);
                return new LoginAppUser();
            }

            @Override
            public LoginAppUser findByMobile(String mobile) {
                log.error("通过手机号查询用户异常:{}", mobile, throwable);
                return new LoginAppUser();
            }

            @Override
            public LoginAppUser findByOpenId(String openId) {
                log.error("通过openId查询用户异常:{}", openId, throwable);
                return new LoginAppUser();
            }
        };
    }
}
