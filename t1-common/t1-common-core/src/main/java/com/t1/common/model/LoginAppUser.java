package com.t1.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.t1.common.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Bruce Lee(copy)
 * 用户实体绑定spring security
 */
@Getter
@Setter
public class LoginAppUser extends User implements SocialUserDetails {
    private static final long serialVersionUID = -3685249101751401211L;

    /***
     * 权限重写
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        if (!CollectionUtils.isEmpty(super.getRoles())) {
            super.getRoles().parallelStream().forEach(roleId -> collection.add(new SimpleGrantedAuthority("" + roleId)));
        }
        return collection;
    }

    @Override
    public String getUsername() {
        return getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "0".equals(getStatus());
    }

    @Override
    public String getUserId() {
        return "" + getId();
    }
}
