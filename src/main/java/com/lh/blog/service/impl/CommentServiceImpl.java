package com.lh.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lh.blog.dao.CommentMapper;
import com.lh.blog.po.Comment;
import com.lh.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/5 11:08
 * @description
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public PageInfo<Comment> queryCommentByPaging(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Comment> comments = commentMapper.selectList(null);
        return new PageInfo<Comment>(comments);
    }
}
