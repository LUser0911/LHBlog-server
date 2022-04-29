package com.lh.blog.po;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName lh_essay_comment
 */
@Data
public class EssayComment implements Serializable {
    /**
     * 文章-评论的唯一性id
     */
    private Long id;

    /**
     * 文章id
     */
    private Long essayId;

    /**
     * 评论id
     */
    private Long commentId;

    /**
     * 删除标志(0未删除,1已删除)
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}