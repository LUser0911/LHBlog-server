package com.lh.blog.service.impl;

import com.lh.blog.dao.EssayLabelMapper;
import com.lh.blog.po.Label;
import com.lh.blog.service.EssayLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/3 20:47
 * @description
 */
@Service
public class EssayLabelServiceImpl implements EssayLabelService {

    @Autowired
    EssayLabelMapper essayLabelMapper;

    @Override
    public int insertBatch(Long essayId, List<Label> labels) {
        return essayLabelMapper.insertBatch(labels,essayId);
    }
}
