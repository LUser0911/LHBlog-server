package com.lh.blog.po;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName lh_message
 */
@Data
@TableName(value = "lh_message")
public class Message implements Serializable {
    /**
     * 留言id
     */
    @TableId(type = IdType.AUTO)
    private Long messageId;

    /**
     * 留言内容
     */
    private String messageContent;

    /**
     * 留言创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 留言更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 是否为置顶(1:置顶,0不置顶)
     */
    private String isTop;

    /**
     * 审核标志：0通过，1未通过
     */
    private String auditFlag;
    /**
     * 回复id(对应message的子id,0表示为外层留言,没有回复)
     */
    private Long replayId;

    /**
     * 删除标志(0未删除,1已删除)
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}