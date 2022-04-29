package com.lh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lh.blog.dao.MessageMapper;
import com.lh.blog.po.Message;
import com.lh.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/5 11:11
 * @description
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public HashMap<String,Object> queryByPaging(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("is_top",0);
        List<Message> messages = messageMapper.selectList(null);
        PageInfo<Message> pageInfo = new PageInfo<>(messages);
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum",pageNum);
        map.put("pageSize",pageSize);
        map.put("page",pageInfo.getSize());
        map.put("pages",pageInfo.getPages());
        map.put("queryMessages",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return map;
    }

    @Override
    public List<Message> getTopMessages() {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_top",1);
        return messageMapper.selectList(queryWrapper);
    }

    @Override
    public Integer addOneMessage(Message message) {
        return messageMapper.insert(message);
    }
}
