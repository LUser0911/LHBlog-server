package com.lh.blog.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.lh.blog.common.domain.PaginationInfo;
import com.lh.blog.po.Essay;
import com.lh.blog.po.Label;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/3 14:35
 * @description
 */
public interface EssayService {

    void addOrUpdateEssayByEssayInfo(Essay essay,List<String> labels);

    Essay queryEssayByEssayId(Long essayId);

    Essay queryEssayByEssayTitle(String essayTitle);

    PaginationInfo<Essay> queryByComplexCondition(Integer pageNum, Integer pageSize, Long  minBrowseCount, Long maxBrowseCount , Long minCommentCount, Long  maxCommentCount, List<String> labels, String  publishDatetime);

//    Integer queryEssayQuantityByComplexCondition(Long minBrowseCount,Long maxBrowseCount,Long minCommentCount,Long maxCommentCount,String publishDatetime);

    List<Essay> getByCustomMechanismN(Integer num);

    List<Essay> getHotEssayByBrowseCountHeadN(Integer count);

    List<Essay> getArchiveEssayBySpecificYearMonth(Integer year,Integer month);

    HashMap<String,Object> getArchiveFirst();

    HashMap<String,Object> getEssayListByLabels(Integer pageNum, Integer pageSize, List<String> labelNames);

    List<Essay> getEssayBySpecifiedYearMonth(Integer year,Integer month);


    Integer browseCountIncrement(Long essayId);


}
