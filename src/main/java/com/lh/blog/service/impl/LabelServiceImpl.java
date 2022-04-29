package com.lh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lh.blog.common.exception.RequestException;
import com.lh.blog.dao.LabelMapper;
import com.lh.blog.po.Label;
import com.lh.blog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/3 15:02
 * @description
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    LabelMapper labelMapper;

    @Override
    public int insertLabel(Label label) {
        return labelMapper.insert(label);
    }

    @Override
    public int insertLabelBatch(List<Label> labels) {
        return labelMapper.insertLabelBatch(labels);
    }


    @Override
    public Label queryLabelByLabelName(String labelName) {
        QueryWrapper<Label> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("label_name",labelName);
        return labelMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Label> queryAll() {
        return labelMapper.selectList(null);
    }

    @Override
    public PageInfo<Label> queryByPagination(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Label> labels = labelMapper.selectList(null);
        return new PageInfo<Label>(labels);
    }

    @Override
    public int addOrUpdateLabel(Label label) {
        //update
        if (label.getLabelId() != null){
            UpdateWrapper<Label> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("label_id",label.getLabelId());
            return labelMapper.update(label,updateWrapper);
        }else {
            // add
            //需要先根据标签名查询是否存在
            QueryWrapper<Label> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("label_name",label.getLabelName());
            Label selectLabel = labelMapper.selectOne(queryWrapper);
            if (selectLabel != null){
                throw new RequestException("当前标签<"+label.getLabelName()+">已存在",-1);
            }
            return  labelMapper.insert(label);
        }
    }

    @Override
    public List<Label> queryLabelsByEssayId(Long essayId) {
        return labelMapper.queryLabelsByEssayId(essayId);
    }

    @Override
    public List<String> getAllLabelNames() {
        return labelMapper.queryLabelNames();
    }
}
