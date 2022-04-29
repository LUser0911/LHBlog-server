package com.lh.blog.common.service;

import com.lh.blog.common.domain.CommonResult;
import com.lh.blog.common.exception.ServerException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;


/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/2 21:12
 * @description 负责一些通用的业务逻辑：比如文章中图片的上传，删除等等
 */
@Service
public class CommonService {

    /*
     根据不同的配置文件来进行确定，上传的路径到底是哪里
     dev=>E:/images/
     prod=>/usr/local/data/imaged/
     需要提供一个默认的路径:
     cover-default =>
     dev=>E:/images/default.jpg
     prod=>/usr/local/data/images/default.jpg
     */
    @Value("${lh.upload-path}")
    private String uploadPath;

    /**
     * 根据上传图片的多段二进制Multipart来进行处理，并返回其在服务器中的位置
     *
     * =>则需要的文章内图片的位置为
     * =>upload-path+"article/year/month"+originFilename-2022418102050.xxx
     *
     *  1.首先需要判断upload-path+"article/year/month"是否存在
     *  2.创建对应的需要保存的位置
     *  3.保存到指定的位置并返回对应需要的url
     *
     * @param multipartFile
     * @param request 提供HttpServletRequest的目的是或许项目路径
     * @return
     */
    public CommonResult handleUploadImage(MultipartFile multipartFile, HttpServletRequest request) {
        if (ObjectUtils.isNotEmpty(multipartFile)){
            LocalDateTime now = LocalDateTime.now();
            int year = now.getYear();
            int month = now.getMonthValue();
            //进行初始的文件目录的存在判断
            StringBuilder sb = new StringBuilder("");
            sb.append("article/").append(year).append("/").append(month).append("/");
            String common = sb.toString();
            File file = new File(uploadPath+sb.toString());
            //判断当前月份的目录是否存在
            if (!file.exists()){
                //如果不存在则递归的进行创建
                file.mkdirs();
            }
            String originalFilename = multipartFile.getOriginalFilename();
            int dotIndex = originalFilename.indexOf(".");
            //根据dot进行分割
//            String prefix = originalFilename.split(".")[0];
//            String suffix = originalFilename.split(".")[1];
            StringBuilder finalName = new StringBuilder(originalFilename);
            finalName.insert(dotIndex,new StringBuilder("").append(now.getYear()).append(now.getMonthValue()).append(now.getDayOfMonth())
                    .append(now.getHour()).append(now.getMinute()).append(now.getSecond()));
            try {
                multipartFile.transferTo(new File(uploadPath+sb.toString()+finalName.toString()));
            } catch (Exception e) {
                throw new RuntimeException("上传文章图片出错");
            }
            //如果没有报错则执行下一步
            HashMap<String, Object> map = new HashMap<>();
            //=>/images/article/2022xxxxx
            map.put("imagePathInServer","/images/"+sb.toString()+finalName.toString());
            return CommonResult.success("上传文章图片成功过",map);
        }
        //返回null，则表示上传的不是图片资源
        return CommonResult.failure("保存图片资源失败，上传的文件为空",null);
    }
//    public CommonResult handleUploadImage(MultipartFile multipartFile, HttpServletRequest request) {
//        //判断文件不为空
//        if (ObjectUtils.isNotEmpty(multipartFile)) {
//            //根据year/month/day来进行图片的存储
//            //获取项目路径
//            String completeImageName = "";
//            StringBuilder originNameJoin = null;
//            LocalDateTime now = null;
//            try {
//                //f:\\me\\LHBlog-Server
//                //StringBuilder是线程不安全的：多线程环境下可能出现安全问题
//                StringBuilder sb = new StringBuilder(new File(".").getCanonicalPath());
//                //拼接成static目录位置
//                sb.append("\\src\\main\\resources\\static\\images");
//                now = LocalDateTime.now();
//                sb.append("\\article\\" + now.getYear()).append("\\" + now.getMonth().getValue());
//                File staticResourceDirectory = new File(sb.toString());
//                if (!staticResourceDirectory.exists()) {
//                    //如果目录不存在则创建目录
//                    staticResourceDirectory.mkdirs();
//                }
//                //根据上面的步骤：则静态资源目录已存在,则保存文件到当前目录下
//                //图片原名字的准换:=>a.png=>a20220304105911.png的样式
//                String imageOriginName = multipartFile.getOriginalFilename();
//                originNameJoin = new StringBuilder(imageOriginName);
//                int dotIndex = originNameJoin.indexOf(".");
//                if (dotIndex != -1) {
//                    originNameJoin.insert(dotIndex, new StringBuilder().append(now.getYear()).append(now.getMonth().getValue()).append(now.getDayOfMonth()).append(now.getHour()).append(now.getMinute()).append(now.getSecond()).toString());
//                }
//                completeImageName = sb.append("\\"+originNameJoin.toString()).toString();
//                //store image into the server
//                multipartFile.transferTo(new File(completeImageName));
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//            HashMap<String, Object> map = new HashMap<>();
//            StringBuilder imagePathInServer = new StringBuilder();
//            imagePathInServer
//                    .append(request.getScheme()+"://")
//                    .append(request.getServerName());
//            if (request.getServerPort() != 80){
//                imagePathInServer.append(":"+request.getServerPort())
//                        .append(request.getContextPath())
//                        .append("/images/article")
//                        .append("/"+now.getYear())
//                        .append("/"+now.getMonth().getValue())
//                        .append("/"+originNameJoin);
//            }
//            map.put("imagePathInServer",imagePathInServer.toString());
//            return CommonResult.success("图片上面成功",map);
//        } else {
//            //错误原因：图片的大小，xxx
//            return CommonResult.failure("图片保存到服务器失败，请重新上传", null);
//        }
//    }

    /**
     * 根据文章上传得封面二进制流，然后存储到服务器中，其实本质还是和上面的一致，再写一遍的原因是为了提升理解
     *
     * 如果是修改文章，则文章已有essayCover;反之，则不存在essayCover即在服务器上不存在相关的图片资源
     * 如果是修改文章，那我们还必须确定现在的图片资源是否是原来的图片资源，如果不是原来的原来的图片资源，则我们还才进行重新进行生成
     *
     * 2022/4/3=>现在我们都是考虑都在添加文章，而不是在修改文章,则需要进行执行
     *
     * @param multipartFile
     * @return 文章封面存储到服务器的位置=>
     *  eg:http://localhost:8080/+images/cover/2022/4/3/xxxx.png=>存储到磁盘的位置/src/main/resources/static/+images/cover/
     *
     *  如果multipartfile为空，则文章封面使用默认的封面图default cover image
     *
     *
     *  =>文章封面需要的地址为:
     *  upload-path+"images/"+"cover/year/month/cover_final_name.xxx"
     *
     */
    public String  uploadEssayCover(MultipartFile multipartFile,HttpServletRequest request){
        if (ObjectUtils.isNotEmpty(multipartFile) &&  !multipartFile.isEmpty())
        {
            LocalDateTime now = LocalDateTime.now();
            int year = now.getYear();
            int month = now.getMonthValue();
            //进行初始的文件目录的存在判断
            StringBuilder sb = new StringBuilder("");
            sb.append("cover/").append(year).append("/").append(month).append("/");
            String common = sb.toString();
            File file = new File(uploadPath+sb.toString());
            //判断当前月份的目录是否存在
            if (!file.exists()){
                //如果不存在则递归的进行创建
                file.mkdirs();
            }
            String originalFilename = multipartFile.getOriginalFilename();
            int dotIndex = originalFilename.indexOf(".");
            //根据dot进行分割
//            String prefix = originalFilename.split(".")[0];
//            String suffix = originalFilename.split(".")[1];
            StringBuilder finalName = new StringBuilder(originalFilename);
            finalName.insert(dotIndex,new StringBuilder("").append(now.getYear()).append(now.getMonthValue()).append(now.getDayOfMonth())
                    .append(now.getHour()).append(now.getMinute()).append(now.getSecond()));
            try {
                multipartFile.transferTo(new File(uploadPath+sb.toString()+finalName.toString()));
            } catch (Exception e) {
                throw new ServerException("上传文章封面错误，请确定上传的封面图片是否符合要求",originalFilename);
            }
//            //如果没有报错则执行下一步
//            HashMap<String, Object> map = new HashMap<>();
//            //=>/images/article/2022xxxxx
//            map.put("imagePathInServer","/images/"+sb.toString()+finalName.toString());
            return "/images/"+sb.toString()+finalName.toString();
        }
//        {
//            //根据上面的分析磁盘路径和服务器的URL路径是包含共用的
//            String originalFilename = multipartFile.getOriginalFilename();
//            StringBuilder commonFileString = new StringBuilder();
//            LocalDateTime now = LocalDateTime.now();
//            //=>/images/cover/2022/4/3
//            commonFileString.append("/images/cover")
//                            .append("/"+now.getYear())
//                            .append("/"+now.getMonth().getValue())
//                            .append("/");
//            StringBuilder finalCoverName = new StringBuilder(originalFilename);
//            finalCoverName.insert(originalFilename.indexOf("."),
//                    new StringBuilder()
//                            .append(now.getYear())
//                            .append(now.getMonth().getValue())
//                            .append(now.getDayOfMonth())
//                            .append(now.getHour())
//                            .append(now.getMinute())
//                            .append(now.getSecond())
//                            .toString()
//                    );
//            System.out.println(finalCoverName);
//            File file = new File(".");
//            String projectPath = null;
//            try {
//                projectPath = file.getCanonicalPath();
//                File coverPath = new File(projectPath + "/src/main/resources/static" + commonFileString.toString());
//                if (!coverPath.exists())
//                {
//                    coverPath.mkdirs();
//                }
//                //传输文件
//                multipartFile.transferTo(new File(projectPath+"/src/main/resources/static"+commonFileString.toString()+"/"+finalCoverName.toString()));
//            } catch (IOException e) {
//                e.printStackTrace();
//                //需要进一步的错误判断
//            }
//            return new StringBuilder().append(request.getScheme()+"://")
//                    .append(request.getServerName())
//                    .append(request.getServerPort() != 80 ? ":"+request.getServerPort() : "")
//                    .append(request.getContextPath()).toString()
//                    +
//                    commonFileString.toString()
//                    +
//                    finalCoverName.toString();
//        }
        //如果返回为Null，则essayCover使用默认的封面地址
        return null;
    }
}
