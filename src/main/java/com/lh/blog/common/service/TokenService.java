package com.lh.blog.common.service;

import cn.hutool.jwt.JWT;
import com.lh.blog.common.domain.LoginUser;
import com.lh.blog.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/1 18:41
 */
@Component
@Slf4j
public class TokenService {

    @Autowired
    RedisUtil redisUtil;


    //这个常量是登录用户对应其存储在redis中的key,也是需要向token中进行传入的claim,对应其指示出每个对象的唯一性标识
    private static final String TOKEN_UNIQUE_KEY = "login_token_key:";

    private static final String TOKEN_USER_KEY = "login_user_key";

    private static final Integer MILLI_MINUTE = 1000*60;

    @Value("${token.expireTime}")
    private Integer expireTimes;

    @Value("${token.verifyKey}")
    String verifyKey;

    @Value("${token.refreshFactor}")
    Double refreshFactor;


    /**
     * 根据当前的用户来创建token,并存储到redis中
     * @param loginUser
     */
    public String createToken(LoginUser loginUser){
        String tokenKey = UUID.randomUUID().toString();
        loginUser.setTokenKey(tokenKey);
        //初始化loginUser的相关信息
        refreshToken(loginUser);
        HashMap<String, Object> claims = new HashMap<>();
        //token中保存的uuid生成的tokenKey
        claims.put(TOKEN_USER_KEY,tokenKey);
        return createToken(claims);
    }

    public String createToken(Map<String,Object> claims){
        return JWT.create().addPayloads(claims).setKey(verifyKey.getBytes()).sign();
    }

    public void  refreshToken(LoginUser loginUser){
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime()+expireTimes*MILLI_MINUTE);
        String tokenUserKey = getTokenUserKey(loginUser.getTokenKey());
        redisUtil.setAndExpire(tokenUserKey,loginUser,expireTimes, TimeUnit.MINUTES);
    }

    public void  verifyToken(LoginUser loginUser){
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        //表示从当前获取到的token在redis中的存储已经过期，需要进行重置
        //当token的有效时间已经达到refrshFactor则进行刷新
        if ( (currentTime - expireTime) /expireTimes * MILLI_MINUTE > refreshFactor){
            refreshToken(loginUser);
        }
    }

    public LoginUser  getLoginUserByRequest(HttpServletRequest request){
        //Authorization
        //Authorization
        //Authorization
//        System.out.println(request.getHeader("Authorization"));
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(token)){
            token = token.replace("Bearer ","");
            JWT parseToken = JWT.of(token);
            if (parseToken.setKey(verifyKey.getBytes()).verify()){
                return redisUtil.getObjectByCLass(getTokenUserKey((String) parseToken.getPayload(TOKEN_USER_KEY)),LoginUser.class);
            }else {
                log.info("token校验失败，存在被修改的风险");
                return null;
            }
        }
        return null;
    }

    public String  getTokenUserKey(String tokenKey){
        return TOKEN_UNIQUE_KEY+tokenKey;
    }


}
