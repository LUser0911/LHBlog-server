package com.lh.blog.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lh.blog.common.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/1 18:14
 */
@Component
public class RedisUtil {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    /**
     * 往redis中存入一个k-v对象，不会过期
     * 如果对象存在，则会对其的内容进行覆盖
     * @param key 键
     * @param value 值
     */
    public <T> void set(final String key,final T value){
        System.out.println(value.getClass());
        redisTemplate.opsForValue().set(key,value);
    }

    public <T> void  setAndExpire(final String key,final T value, Integer expireTime, TimeUnit unit){
        redisTemplate.opsForValue().set(key,value,expireTime,unit);
    }

    /**
     * 给redis中存在的key设置过期时间
     *
     * @param key redis中的键
     * @param expireTime 过期时间
     * @param unit 过期时间单位
     */
    public boolean  expireKey(String key,Integer expireTime,TimeUnit unit){
        return redisTemplate.expire(key,expireTime,unit);
    }

    /**
     *  从redis服务器中删除一个键
     * @param key 键
     * @return
     */
    public  boolean delete(String key){
        return redisTemplate.delete(key);
    }

    public <T> T getObjectByCLass(final String key,Class<T> clazz){
//        ValueOperations<String, T> valueOperations = (ValueOperations<String, T>) redisTemplate.opsForValue();
//        return valueOperations.get(key);
        JSONObject o = (JSONObject) redisTemplate.opsForValue().get(key);
        return JSON.toJavaObject(o,clazz);
//        System.out.println(redisTemplate.opsForValue().get(key).getClass());
//        return JSONObject.toJavaObject(redisTemplate.opsForValue().get(key),LoginUser.class)
//       return (LoginUser) redisTemplate.opsForValue().get(key);
//        ValueOperations<String,T> valueOperations = (ValueOperations<String, T>) redisTemplate.opsForValue();
//        return  valueOperations.get(key);
    }
}
