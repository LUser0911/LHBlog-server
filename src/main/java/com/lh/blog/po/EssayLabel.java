package com.lh.blog.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName lh_essay_label
 */
@Data
@TableName(value = "lh_essay_label")
public class EssayLabel implements Serializable {
    /**
     * 文章-标签唯一性id
     */
    @TableId(type= IdType.AUTO)
    private Long id;

    /**
     * 文章id
     */
    private Long essayId;

    /**
     * 标签id
     */
    private Long labelId;

    /**
     * 删除标志(0未删除,1已删除)
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}