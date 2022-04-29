package com.lh.blog.po;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName lh_label
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName(value = "lh_label")
public class Label implements Serializable {
    /**
     * 标签id
     */
    @TableId(type=IdType.AUTO)
    private Long labelId;

    /**
     * 标签名(最长十个中文字符，二十个非中文字符)
     */
    private String labelName;

    /**
     * 标签的背景色
     */
    private String labelBackground;

    /**
     * 标签的文字颜色
     */
    private String labelColor;

    /**
     * 标签创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 标签修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 标签是否已经在数据库中存储(1已存储,0未存储)
     */
    @JsonIgnore
    private String isStored;

    /**
     * 删除标志(0未删除,1已删除)
     */
    @JsonIgnore
    private String delFlag;

    private static final long serialVersionUID = 1L;
}