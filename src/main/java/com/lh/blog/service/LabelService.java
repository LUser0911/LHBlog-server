package com.lh.blog.service;

import com.github.pagehelper.PageInfo;
import com.lh.blog.po.Label;

import java.util.List;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/3 14:56
 * @description
 */
public interface LabelService {

    int insertLabel(Label label);

    int insertLabelBatch(List<Label> labels);

    Label queryLabelByLabelName(String labelName);

    List<Label> queryAll();

    PageInfo<Label> queryByPagination(Integer pageNum, Integer pageSize);

    int addOrUpdateLabel(Label label);

    List<Label> queryLabelsByEssayId(Long essayId);

    List<String> getAllLabelNames();

}
