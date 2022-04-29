package com.lh.blog.service;

import com.github.pagehelper.PageInfo;
import com.lh.blog.po.Comment;

public interface CommentService {

    PageInfo<Comment> queryCommentByPaging(Integer pageNum,Integer pageSize);
}
