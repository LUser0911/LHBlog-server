package com.lh.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.blog.po.Label;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.lh.blog.po.Label
 */
public interface LabelMapper extends BaseMapper<Label> {

    int insertLabelBatch(@Param("labels") List<Label> labels);

    List<Label> queryLabelsByEssayId(@Param("essayId") Long essayId);

    List<String> queryLabelNames();
}




