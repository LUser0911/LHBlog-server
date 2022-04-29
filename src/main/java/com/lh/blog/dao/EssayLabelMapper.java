package com.lh.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.blog.po.EssayLabel;
import com.lh.blog.po.Label;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.lh.blog.po.EssayLabel
 */
public interface EssayLabelMapper extends BaseMapper<EssayLabel> {


    int insertBatch(@Param("labels") List<Label> labels,@Param("essayId") Long essayId);
}




