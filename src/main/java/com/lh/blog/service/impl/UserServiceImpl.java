package com.lh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lh.blog.dao.UserMapper;
import com.lh.blog.po.User;
import com.lh.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/3/30 19:52
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        return userMapper.selectList(null);
    }


    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        return userMapper.selectOne(userQueryWrapper);
    }
}
