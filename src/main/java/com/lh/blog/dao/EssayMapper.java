package com.lh.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.blog.po.Essay;
import com.lh.blog.po.Label;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Entity com.lh.blog.po.Essay
 */
public interface EssayMapper extends BaseMapper<Essay> {


    List<Essay> queryByComplexCondition(@Param("minBrowseCount") Long minBrowseCount, @Param("maxBrowseCount") Long maxBrowseCount, @Param("minCommentCount") Long minCommentCount, @Param("maxCommentCount") Long maxCommentCount, @Param("labels") List<String> labels, @Param("publishDatetime") List<Date> publishDatetime);

    List<Essay> queryByComplexConditionAndCustomPagination(@Param("pageNum")Integer pageNum,@Param("pageSize")Integer pageSize,@Param("minBrowseCount") Long minBrowseCount, @Param("maxBrowseCount") Long maxBrowseCount, @Param("minCommentCount") Long minCommentCount, @Param("maxCommentCount") Long maxCommentCount,@Param("labels") List<String> labels,@Param("publishDatetime") List<Date> publishDatetime );

    List<Essay> getArchiveEssayBySpecificYearMonth(@Param("year")Integer year,@Param("month")Integer month);

    Date getLatestDate();

    Date getPastestDate();

    List<Essay> getEssayByLabelNamesAndPagination(@Param("pageNum")Integer pageNum,@Param("pageSize")Integer pageSize,@Param("labelNames")List<String> labelNames);

    Integer incrementBrowseCount(@Param("essayId")Long essayId);

    Long getLatestEssayId();
}




