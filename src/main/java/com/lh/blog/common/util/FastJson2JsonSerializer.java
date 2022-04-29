package com.lh.blog.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.lh.blog.common.domain.LoginUser;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/3/30 20:30
 */
public class FastJson2JsonSerializer<T> implements RedisSerializer<T> {

    @SuppressWarnings("unused")
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    static
    {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    public FastJson2JsonSerializer(Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {

        if (t == null) return new byte[0];
//        System.out.println("fastjosn serializer "+t.getClass());
        return JSON.toJSONString(t).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        //首先需要判断bytes是否为空或者长度为0
        if (bytes == null || bytes.length <= 0)
        {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
//        System.out.println("fastjson serializer");
//        System.out.println(JSON.parseObject(str, clazz));
//        System.out.println(JSON.parseObject(str, clazz).getClass());
//        System.out.println(clazz);
//        Class<LoginUser> loginUserClass = LoginUser.class;
//        loginUserClass = (Class<LoginUser>) clazz;
//        System.out.println(loginUserClass);
        return (T) JSON.parseObject(str,clazz);
    }

    //设置这个ObjectMapper的作用是什么
    public void setObjectMapper(ObjectMapper objectMapper)
    {
        Assert.notNull(objectMapper, "'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }

    //?获取Java类型的作用是什么
    protected JavaType getJavaType(Class<?> clazz)
    {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}
