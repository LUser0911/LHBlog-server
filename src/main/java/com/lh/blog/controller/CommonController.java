package com.lh.blog.controller;

import com.lh.blog.common.domain.CommonResult;
import com.lh.blog.common.service.CommonService;
import com.lh.blog.po.Message;
import com.lh.blog.service.EssayService;
import com.lh.blog.service.LabelService;
import com.lh.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/3 14:03
 * @description
 */
@RestController
public class CommonController {

    @Autowired
    CommonService commonService;

    @Autowired
    LabelService labelService;

    @Autowired
    EssayService essayService;

    @Autowired
    MessageService messageService;

    @PostMapping(value = "/admin/uploadMDImage")
    public CommonResult handleUploadImage(@RequestParam("image") MultipartFile multipartFile, HttpServletRequest request) {
        System.out.println("into upload md image");
        return commonService.handleUploadImage(multipartFile, request);
    }

    @GetMapping(value = "/common/label/getAll")
    public CommonResult getLabelAll() {
        return CommonResult.success("获取标签成功", labelService.queryAll());
    }

    @GetMapping(value = "/common/essay/getByCustomMechanismHeadN")
    public CommonResult getByCustomMechanismHeadN(@RequestParam(value = "num") Integer num) {
        return CommonResult.success("获取推荐文章成功", essayService.getByCustomMechanismN(num));
    }

    @GetMapping(value = "/common/essay/getHotEssayByBrowseHeadN")
    public CommonResult getHotEssayByBrowseHeadN(@RequestParam("count") Integer count) {
        return CommonResult.success("获取热点文章成功", essayService.getHotEssayByBrowseCountHeadN(count));
    }

    @GetMapping(value = "/common/essay/getArchiveFirst")
    public CommonResult getArchiveFirst() {
        return CommonResult.success("获取日期和当前月份的文章成功", essayService.getArchiveFirst());
    }

    @GetMapping(value = "/common/essay/getEssayByLabelNamesAndPaging")
    public CommonResult getEssayListByLabels(@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize,@RequestParam(value = "labelNames", required = false) List<String> labelNames) {
        return CommonResult.success("通过标签获取文章成功",essayService.getEssayListByLabels(pageNum,pageSize,labelNames));
    }

    @GetMapping(value = "/common/essay/getEssayBySpecifiedYearMonth")
    public CommonResult getEssayBySpecifiedYearMonth(@RequestParam("year")Integer year,@RequestParam("month")Integer month){
        //一定需要进行判断，如果没有则会报错ServerException,但是我希望抛出RequestException
        return CommonResult.success("根据year和month获取文章成功",essayService.getArchiveEssayBySpecificYearMonth(year,month));
    }

    /**
     *
     * @return 置顶留言
     */
    @GetMapping(value = "/common/message/topMessages")
    public CommonResult getTopMessages(){
        return CommonResult.success("获取置顶留言成功",messageService.getTopMessages());
    }

    /**
     *
     * @param message 留言对象
     * @return 返回是否成功添加留言到数据库中
     */
    @PostMapping(value = "/common/message/addOne")
    public CommonResult addOneMessage(Message message){
        if (messageService.addOneMessage(message) == 1){
            return CommonResult.success("添加留言成功",1);
        }else {
            return CommonResult.failure("添加留言失败",message);
        }
    }

    @GetMapping(value = "/common/message/getMessageByPagination")
    public CommonResult getMessageByPagination(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "8")Integer pageSize
            ){
        return CommonResult.success("分页获取留言成功",messageService.queryByPaging(pageNum,pageSize));
    }

    @PostMapping(value = "/common/essay/incrementBrowseCount")
    public CommonResult incrementBrowseCount(@RequestParam("essayId")Long essayId){
        if (essayService.browseCountIncrement(essayId) == 1){
            return  CommonResult.success("更新文章浏览量成功，请进行刷新",1);
        }else {
            return CommonResult.failure("更新文章浏览量失败",-1);
        }
    }
}
