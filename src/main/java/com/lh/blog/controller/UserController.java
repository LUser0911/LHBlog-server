package com.lh.blog.controller;

import com.lh.blog.common.domain.CommonResult;
import com.lh.blog.common.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/2 20:28
 * @description
 */
@RestController
@RequestMapping(value = "/admin")
public class UserController {

    @Autowired
    LoginService loginService;

    @PostMapping(value = "/login")
    public CommonResult handleLogin(String username,String password){
        String token = loginService.login(username, password);
        HashMap<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(token)){
            map.put("userToken",token);
            //没有通知到client端，token的存活时间，因而需要到logout成功的处理中去通知处理
            return CommonResult.success("登录成功!",map);
        }else {
            return CommonResult.failure("用户名或密码错误,请重新登录！",null);
        }
    }
}
