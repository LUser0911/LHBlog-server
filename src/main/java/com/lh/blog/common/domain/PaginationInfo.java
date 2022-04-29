package com.lh.blog.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/8 11:50
 * @description
 */
@Data
@NoArgsConstructor
@ToString
public class PaginationInfo<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer page;//当前页大小
    private Integer pages;//所有页
    private Integer total;//总记录数
    private List<T> data;//查询到的分页数据
}
