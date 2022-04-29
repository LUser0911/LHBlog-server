package com.lh.blog.common.filter;

import com.lh.blog.common.domain.LoginUser;
import com.lh.blog.common.service.TokenService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/3/31 22:09
 * @description:
 *  JWTVerityFilter需要做的任务是：
 *      在UsernamePasswordAuthentication之前，校验是否请求包含token
 *      如果请求包含token且可以根据TOKEN_UNIQUE_KEY从数据库中获取到相应的LoginUser并且Authentication为null则放行
 *      如果LoginUser为Null，或者Authentication已经存在，则直接放行通过，然后需要到UserDetailsService中进行判断
 */
@Component
public class JWTVerifyFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("into custom confirm filter");
//        System.out.println(request.getHeader("Authorization"));
        LoginUser loginUser = tokenService.getLoginUserByRequest(request);
//        System.out.println(loginUser);
//        System.out.println(ObjectUtils.isNotEmpty(loginUser));
//        System.out.println(ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication()));
        //满足可以放行的条件,且重新设置Authentication
        if (ObjectUtils.isNotEmpty(loginUser) && ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())){
            //根据刷新机制判断是否要刷新这次请求的token
            System.out.println("into inner jwt verify filter");
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), loginUser.getAuthorities());
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(token);
        }
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("admin", "admin",AuthorityUtils.NO_AUTHORITIES);
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
