package com.lh.blog.common.service;

import com.lh.blog.common.domain.CommonResult;
import com.lh.blog.common.domain.LoginUser;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/2 13:23
 * @description：
 *  LoginService需要完成的任务包含：根据传入的username,password进行登录的校验，
 *  如果校验成功，则返回对应token给客户端，如果校验不成功则返回需要返回重新登录的回调信息
 *
 */
@Service
public class LoginService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    /**
     *
     * @param username 登录用户名
     * @param password 登录密码
     * @return 根据登录是否成功，返回对应的消息
     */
    public String login(String username,String password){
        System.out.println(username);
        System.out.println(password);
        //判断用户名密码是否空
        System.out.println(StringUtils.isNotEmpty(username));
        System.out.println(StringUtils.isNotEmpty(password));
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return null;
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        }catch (Exception e){

        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        System.out.println(loginUser);
        return tokenService.createToken(loginUser);
    }
}
