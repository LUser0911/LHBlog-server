package com.lh.blog.po;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName lh_essay
 */
@Data
@TableName(value = "lh_essay")
public class Essay implements Serializable {
    /**
     * 文章id
     */
    @TableId(type= IdType.AUTO)
    private Long essayId;

    /**
     * 文章标题
     */
    private String essayTitle;

    /**
     * 粗略的文章内容描述
     */
    private String essayIntroduce;

    /**
     * 相较于工程目录的封面图路径
     */
    private String essayCover;

    /**
     * 文章内容
     */
    private String essayContent;

    /**
     * 文章浏览数
     */
    private Long browseCount;

    /**
     * 评论数
     */
    private Long commentCount;

    /**
     * 文章创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 文章修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 删除标志(0未删除,1已删除)
     */
    private String delFlag;

    @TableField(exist = false)
    private List<Label> essayLabels;

    private static final long serialVersionUID = 1L;
}