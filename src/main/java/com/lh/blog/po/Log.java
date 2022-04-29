package com.lh.blog.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName lh_log
 */
@Data
public class Log implements Serializable {
    /**
     * 日志iid
     */
    private Integer logId;

    /**
     * 记录操作sql
     */
    private String logSql;

    /**
     * 记录操作说明
     */
    private String logIntroduce;

    /**
     * 记录操作用户
     */
    private String logUser;

    /**
     * 记录操作ip
     */
    private String logIp;

    /**
     * 记录操作浏览器
     */
    private String logBrowser;

    /**
     * 创建log记录时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}