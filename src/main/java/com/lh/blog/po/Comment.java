package com.lh.blog.po;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName lh_comment
 */
@Data
@TableName(value = "lh_comment")
public class Comment implements Serializable {
    /**
     * 评论id
     */
    @TableId
    private Long commentId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 留言点赞量
     */
    private Integer applaudCount;

    /**
     * 评论创建时间
     */
    private Date createTime;

    /**
     * 评论修改时间
     */
    private Date updateTime;

    /**
     * 审核标志：0通过，1未通过
     */
    private String auditFlag;

    /**
     * 回复id
     */
    private Integer replayId;

    /**
     * 删除标志(0未删除,1已删除)
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}