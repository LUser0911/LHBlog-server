package com.lh.blog.common.config;

import com.lh.blog.common.domain.CommonResult;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/2 1:15
 */

@Component
public class LogOutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("into LogOutSuccessHandlerImpl");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (ObjectUtils.isNotEmpty(securityContext)){
            //如果是登录状态，则需要将authentication置为null,清除Authentication
            securityContext.setAuthentication(null);
        }
        //相应登出成功的状态码，以及相应的消息
        PrintWriter out = response.getWriter();
        out.flush();
        out.println(CommonResult.success("logout成功，已清除相应的数据，请重新登录!",null));
    }
}
