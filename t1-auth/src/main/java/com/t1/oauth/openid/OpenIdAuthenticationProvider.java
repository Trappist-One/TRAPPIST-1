package com.t1.oauth.openid;

import com.t1.oauth.service.impl.UserDetailServiceFactory;
import com.t1.oauth2.common.token.OpenIdAuthenticationToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * @author Bruce Lee(copy)
 */
@Setter
@Getter
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

//    private SocialUserDetailsService userDetailsService;
    private UserDetailServiceFactory userDetailsServiceFactory;


    @Override
    public Authentication authenticate(Authentication authentication) {
        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;
        String openId = (String) authenticationToken.getPrincipal();
        UserDetails user = userDetailsServiceFactory.getService(authenticationToken).loadUserByMobile(openId);
        if (user == null) {
            throw new InternalAuthenticationServiceException("openId错误");
        }
        OpenIdAuthenticationToken authenticationResult = new OpenIdAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }
//
//    public SocialUserDetailsService getUserDetailsService() {
//        return userDetailsService;
//    }
//
//    public void setUserDetailsService(SocialUserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
}
