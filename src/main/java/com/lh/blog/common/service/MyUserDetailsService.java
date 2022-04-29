package com.lh.blog.common.service;

import com.lh.blog.common.domain.LoginUser;
import com.lh.blog.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/3/31 20:02
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("into myUserDetailsService");
        com.lh.blog.po.User user = userService.getUserByUsername(username);
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(user,loginUser);
        System.out.println(user);
        System.out.println(loginUser);
        System.out.println(loginUser.getUsername());
        System.out.println(loginUser.getPassword());
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
//        authorities.add(new SimpleGrantedAuthority("common"));
//        return new User("admin",new BCryptPasswordEncoder().encode("admin"), authorities);
        return loginUser;
    }
}
