package com.lh.blog.controller;

import com.github.pagehelper.PageInfo;
import com.lh.blog.common.domain.CommonResult;
import com.lh.blog.common.domain.PaginationInfo;
import com.lh.blog.common.service.CommonService;
import com.lh.blog.po.Essay;
import com.lh.blog.po.Label;
import com.lh.blog.service.EssayService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/2 21:08
 * @description
 */
@RestController
@RequestMapping(value = "/admin/essay")
public class EssayController {

    private final String LONG_MAX_VALUE = String.valueOf(Long.MAX_VALUE);

    @Autowired
    CommonService commonService;

    @Autowired
    EssayService essayService;


    /**
     *
     *
     *
     *
     * @param essay 文章的相关信息:title,introduce.content
     * @param essayCoverBinary 文章上传的封面图的二进制
     * @return
     */
    @PostMapping(value = "/upload")
    public CommonResult uploadEssayWithEssayInfo(Essay essay,@RequestParam("labels")List<String> labels,@RequestParam(value = "essayCoverBinary",required = false)MultipartFile essayCoverBinary,HttpServletRequest request){
        System.out.println(essay);
        System.out.println(essayCoverBinary);
        String essayCoverPathInServer = commonService.uploadEssayCover(essayCoverBinary, request);
        //可能为null
        essay.setEssayCover(essayCoverPathInServer);
        System.out.println("=="+essay);
        //执行存储文章/更新文章操作
        essayService.addOrUpdateEssayByEssayInfo(essay,labels);
        return CommonResult.success("上传/更新文章成功",null);
    }

    @GetMapping(value = "/getEssayByComplexCondition")
    public CommonResult queryEssayByComplexCondition(
            @RequestParam("pageNum")Integer pageNum,
            @RequestParam("pageSize")Integer pageSize,
            @RequestParam(value = "minBrowseCount",required = false,defaultValue = "0") Long minBrowseCount,
            @RequestParam(value = "maxBrowseCount",required = false,defaultValue = "0") Long maxBrowseCount,
            @RequestParam(value = "minCommentCount",required = false,defaultValue = "0") Long minCommentCount,
            @RequestParam(value = "maxCommentCount",required = false,defaultValue = "0") Long maxCommentCount,
            @RequestParam(value = "labels",required = false)List<String> labels,
            @RequestParam(value = "publishDatetime",required = false)String publishDatetime
            ){
        //@RequestParam(value = "labels",required = false)List<String> labels=>
        //@RequestParam(value = "labels",required = false)List<Label> labels,
        System.out.println(pageNum);
        System.out.println(pageSize);
        System.out.println(minBrowseCount);
        System.out.println(maxBrowseCount);
        System.out.println(minCommentCount);
        System.out.println(maxCommentCount);
        System.out.println(labels);
        System.out.println("=========");
        PaginationInfo<Essay> essayPageInfo = essayService.queryByComplexCondition(pageNum, pageSize, minBrowseCount, maxBrowseCount, minCommentCount, maxCommentCount, labels, publishDatetime);
//        System.out.println(essayPageInfo.getList().size());
//        essayPageInfo.getList().forEach(essay -> {
//            System.out.println(essay.getEssayLabels().size());
//        });
        /**
         *  pageNum:当前页=>getPageNum()
         *  pageSize:每页数量=>getPageSize()
         *  size:当前页的数量=>getSize()
         *  pages:总页数=>getPages()
         *  total:总记录数=>getTotal()
         */
        HashMap<String, Object> map = new HashMap<>();
        map.put("essayList",essayPageInfo.getData());
        map.put("pageSize",essayPageInfo.getPageSize());
        map.put("size",essayPageInfo.getPage());
        map.put("total",essayPageInfo.getTotal());
        map.put("pages",essayPageInfo.getPages());
        map.put("pageNum",essayPageInfo.getPageNum());
        return CommonResult.success("根据查询条件查询文章成功",map);
    }
}
