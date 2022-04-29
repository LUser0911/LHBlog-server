package com.lh.blog.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @TableName lh_user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName(value = "lh_user")
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId
    private Integer userId;

    /**
     * 用户名(可以为null)
     */
    private String username;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码(经过加密)
     */
    private String password;

    /**
     * 角色名
     */
    private String userRole;

    /**
     * 权限名
     */
    private String userAuthority;

    /**
     * 创建
     */
    private Date createTime;

    /**
     *  更新时间
     */
    private Date updateTime;

    /**
     * 删除标志
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}