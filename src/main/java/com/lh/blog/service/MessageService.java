package com.lh.blog.service;

import com.github.pagehelper.PageInfo;
import com.lh.blog.po.Message;

import java.util.HashMap;
import java.util.List;

public interface MessageService {

    HashMap<String,Object> queryByPaging(Integer pageNum, Integer pageSize);

    List<Message> getTopMessages();

    Integer addOneMessage(Message message);
}
