package com.lh.blog.common.config;

import com.alibaba.fastjson.JSON;
import com.lh.blog.common.domain.CommonResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/3/31 20:55
 */
@Component
public class CustomSuccessAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        System.out.println("into custom success handler");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(CommonResult.success("正确的消息",null)));
//        super.onAuthenticationSuccess(request,response,authentication);
    }
}
