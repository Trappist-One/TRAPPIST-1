package com.t1.oauth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;

/**
 * @author Bruce Lee(copy)
 * @date 2018/12/28
 */
public interface T1UserDetailsService extends UserDetailsService {

    /**
     * 判断实现类是否属于该类型
     * @param accountType 账号类型
     */
    boolean supports(String accountType);

    /**
     * 根据电话号码查询用户
     *
     * @param mobile
     * @return
     */
    UserDetails loadUserByMobile(String mobile);

    SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException;
}
