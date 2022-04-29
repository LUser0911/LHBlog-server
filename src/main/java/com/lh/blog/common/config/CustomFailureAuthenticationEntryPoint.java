package com.lh.blog.common.config;

import com.alibaba.fastjson.JSON;
import com.lh.blog.common.domain.CommonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/3/31 20:25
 */
@Component
public class CustomFailureAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("into custom failure authentication entry point");
//        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JSON.toJSONString(CommonResult.failure("认证失败，请重新登录认证",null)));
    }
}
