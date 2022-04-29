package com.lh.blog.common.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.lh.blog.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/1 18:49
 */
@Data
@NoArgsConstructor
@ToString
public class LoginUser extends User implements UserDetails,Serializable {
    private static final Long serialVersionUID = 1122L;

    //用于唯一标识一个登录用户
    private String tokenKey;

    private Long loginTime;

    private Long expireTime;



    @Override
    @JSONField(serialize = false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Arrays.asList(getUserAuthority().split(";")).forEach(authority->{
            authorities.add(new SimpleGrantedAuthority(authority));
        });
        Arrays.asList(getUserRole().split(";")).forEach(authority->{
            authorities.add(new SimpleGrantedAuthority(authority));
        });
        return authorities;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isEnabled() {
        return true;
    }
}
