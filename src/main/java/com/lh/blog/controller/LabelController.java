package com.lh.blog.controller;

import com.github.pagehelper.PageInfo;
import com.lh.blog.common.domain.CommonResult;
import com.lh.blog.common.exception.RequestException;
import com.lh.blog.po.Label;
import com.lh.blog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/8 21:12
 * @description
 */
@RestController
@RequestMapping(value = "/admin/label")
public class LabelController {

    @Autowired
    LabelService labelService;

    /**
     * 根据 pageNum,pageSize 分页条件进行获取标签
     *
     * @param pageNum  当前页
     * @param pageSize 页大小
     * @return
     */
    @GetMapping(value = "/getByPagination")
    public CommonResult getLabelsPagination(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize) {
        PageInfo<Label> pageInfo = labelService.queryByPagination(pageNum, pageSize);
        HashMap<String, Object> map = new HashMap<>();
        map.put("labels", pageInfo.getList());
        map.put("pageNum", pageInfo.getPageNum());
        map.put("pageSize", pageInfo.getPageSize());
        map.put("pages", pageInfo.getPages());//总页码
        map.put("size", pageInfo.getSize());//当前页的大小
        map.put("total", pageInfo.getTotal());
        return CommonResult.success("分页获取标签成功", map);
    }

    @PostMapping(value = "/addOrUpdate")
    public CommonResult addOrUpdate(Label label) {
        int impactCount = labelService.addOrUpdateLabel(label);
//        if (impactCount != 1){
//            return CommonResult.failure("添加或更新标签失败，请重新操作",impactCount);
//        }else {
        return CommonResult.success("添加/更新标签成功", impactCount);
//        }
    }
}
