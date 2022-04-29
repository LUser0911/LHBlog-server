package com.lh.blog.service;

import com.lh.blog.po.User;

import java.util.List;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/3/30 19:52
 */
public interface UserService {

    List<User> getAllUser();

    User getUserByUsername(String username);
}
