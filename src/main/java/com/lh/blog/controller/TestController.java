package com.lh.blog.controller;

import com.lh.blog.common.domain.CommonResult;
import com.lh.blog.common.domain.LoginUser;
import com.lh.blog.common.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/3/30 11:33
 */
@RestController
public class TestController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;


    @GetMapping(value = {"/",""})
    @ResponseBody
    public String handleIndex(){
        System.out.println("into test controller");
        return "Hello World";
    }

    @GetMapping("/test")
    @ResponseBody
    @PreAuthorize("hasRole('admin') and hasAuthority('user:read')")
//    @PreFilter("hasRole('admin') or hasAuthority('user:read') or hasAuthority('common')")
//    @PreAuthorize("hasPermission(#authentication,read)")
    public String handleTest(Authentication authentication){
        return "test";
    }

    @RequestMapping(value = "/ReLogin")
    public String handleLogin(){
        return "inxdex";
    }

    @GetMapping(value = "/getAuth")
    @ResponseBody
    @PreAuthorize("hasRole('user') or hasAuthority('common')")
//    @PreFilter("hasAuthority('user:read')")
    public CommonResult handleAuth(){
        return CommonResult.success("成功的消息", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @GetMapping(value = "/testAnonymous")
    @ResponseBody
    public String handleAnonymous(){
        return "hello anonymous";
    }

    @GetMapping(value = "/testDevtool")
    @ResponseBody
    public String handle1(){
        return "hello handle1";
    }

//    @PostMapping(value = "/admin/login")
//    @ResponseBody
//    public CommonResult handleLogin(String username,String password){
//        System.out.println(username+"-"+password);
//         UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        String token = tokenService.createToken(loginUser);
//        return CommonResult.success("成功的返回消息",token);
//    }

    @GetMapping(value = "/logout")
    @ResponseBody
    public String handleLogout(){
        return "logout";
    }

    @GetMapping(value = "/getAuthentication")
    @ResponseBody
    public Object handleGetAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
