package com.lh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lh.blog.common.domain.PaginationInfo;
import com.lh.blog.common.exception.RequestException;
import com.lh.blog.dao.EssayMapper;
import com.lh.blog.po.Essay;
import com.lh.blog.po.Label;
import com.lh.blog.service.EssayLabelService;
import com.lh.blog.service.EssayService;
import com.lh.blog.service.LabelService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lh
 * @version 1.0v
 * @date 2022/4/3 14:36
 * @description
 */
@Service
public class EssayServiceImpl implements EssayService {

    @Autowired
    EssayMapper essayMapper;

    @Autowired
    LabelService labelService;

    @Autowired
    EssayLabelService essayLabelService;

    /**
     * 根据从前端获取的essay信息来进行add/update操作
     * @param essay
     * @return
     */
    @Override
    @Transactional//管理事务
    public void addOrUpdateEssayByEssayInfo(Essay essay,List<String> labels) {

        //在进行下面的操作之间还需要进行的操作->确定文章封面图
        /*
            在添加完文章后，还需要使得文章和label进行关联操作，但通过insert插入到数据库后，返回的之后影响的行数，那么我们必须提前进行
            存储可以唯一性表示的文章和标签的数据项
            文章：文章标题/(文章封面的URL：近似的可以认为是唯一的)
            标签：标签名
            则可以提前保存：essayTitle,List<String> labelNames



            =========在进行label的添加的时候，也还可以进行进一步的优化操作：
            一个标签，前端在进行传输的时候，并不知道它到底是用户自己创建的还是原数据库中已有的
            那么我们是否可以给label添加一个字段，用来唯一性的判断当前的标签是否为自定义标签还是已存在数据库的标签

            =>如果不添加这个字段带来的性能消耗：不管是否这个标签已存在都会去跟数据库中的标签进行比较，这样就非常的耗费与数据库进行比对的时间
            则我们通过一个耗费内存的大小，用来进行缩减时间和更大的内存消耗
         */


        //先判断到底是add/update:判断条件essayId
        //essayId不为null=>表明已有文章，还需要判断根据这个essayId是否能从数据库中获取到Essay,上述的条件满足则进行update操作
//        String essayTitle = essay.getEssayTitle();

//        List<String> essayLabelsOnlyName = essay.getEssayLabels();
//        essayLabelsOnlyName.forEach(labelName->essayLabels.add(new Label(null,labelName,null,null,null,null,"1",null)));
        //还需要根据上面的标签，过滤出未存储的标签

        //如果是更新文章，则essayId != null
        Long essayId = null;
        try {
            if (ObjectUtils.isNotEmpty(essay.getEssayId()) && ObjectUtils.isNotEmpty(queryEssayByEssayId(essay.getEssayId()))){
                UpdateWrapper<Essay> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("essay_id",essay.getEssayId());
                essayMapper.update(essay,updateWrapper);
                essayId = essay.getEssayId();
            }
            else {
                essayMapper.insert(essay);
                //通过上一步，essay已存在，则essayId已存在,需要通过essayTitle进行查询
//                String essayCover = essay.getEssayCover();
//                QueryWrapper<Essay> queryWrapper = new QueryWrapper<>();
//                //最终还是应该通过essay_id来进行查询
//                queryWrapper.eq("essay_cover",essayCover);
//                essayId = essayMapper.selectOne(queryWrapper).getEssayId();
                essayId = essayMapper.getLatestEssayId();
                //修改的文章无需进行进行标签的关联

                //将自定的标签存储到数据库中
                //根据标签名再从数据库中查询到先自定义的label
                List<Label> essayAssociationLabels = new LinkedList<>();
                labels.forEach(label -> {
                    essayAssociationLabels.add(labelService.queryLabelByLabelName(label));
                });
                //essay-label的关联
                essayLabelService.insertBatch(essayId,essayAssociationLabels);
//                Essay essayInDataBase = queryEssayByEssayTitle(essayTitle);
                //因为没有对文章的标题进行限制，因而可能存在，则需要变换一种=》essayCover是唯一的
//                essayId = essayInDataBase.getEssayId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //当出现错误需要注意的点
        }

    }
//    @Override
//    @Transactional//管理事务
//    public void addOrUpdateEssayByEssayInfo(Essay essay) {
//
//        //在进行下面的操作之间还需要进行的操作->确定文章封面图
//        /*
//            在添加完文章后，还需要使得文章和label进行关联操作，但通过insert插入到数据库后，返回的之后影响的行数，那么我们必须提前进行
//            存储可以唯一性表示的文章和标签的数据项
//            文章：文章标题/(文章封面的URL：近似的可以认为是唯一的)
//            标签：标签名
//            则可以提前保存：essayTitle,List<String> labelNames
//
//
//
//            =========在进行label的添加的时候，也还可以进行进一步的优化操作：
//            一个标签，前端在进行传输的时候，并不知道它到底是用户自己创建的还是原数据库中已有的
//            那么我们是否可以给label添加一个字段，用来唯一性的判断当前的标签是否为自定义标签还是已存在数据库的标签
//
//            =>如果不添加这个字段带来的性能消耗：不管是否这个标签已存在都会去跟数据库中的标签进行比较，这样就非常的耗费与数据库进行比对的时间
//            则我们通过一个耗费内存的大小，用来进行缩减时间和更大的内存消耗
//         */
//
//
//        //先判断到底是add/update:判断条件essayId
//        //essayId不为null=>表明已有文章，还需要判断根据这个essayId是否能从数据库中获取到Essay,上述的条件满足则进行update操作
//        String essayTitle = essay.getEssayTitle();
////        List<String> essayLabelsOnlyName = essay.getEssayLabels();
//        List<Label> essayLabelsStored = essay.getEssayLabels();
//        List<Label> essayLabelsNotStored = new LinkedList<>();
////        essayLabelsOnlyName.forEach(labelName->essayLabels.add(new Label(null,labelName,null,null,null,null,"1",null)));
//        //还需要根据上面的标签，过滤出未存储的标签
////        List<Label> essayLabelStored = new LinkedList<>();
////        List<Label> essayLabelsNoStored = essayLabels.stream().filter(l -> {
////            if (l.getIsStored().equals("1"))
////            {
////                essayLabelStored.add(l);
////                return false;
////            }
////            else
////            {
////                return true;
////            }
////        }).collect(Collectors.toList());
//        essayLabelsStored.forEach(label -> {
//            if (label.getIsStored().equals("0") ){
//                essayLabelsNotStored.add(label);
//                essayLabelsStored.remove(label);
//            }
//        });
//        Long essayId = null;
//        try {
//            if (ObjectUtils.isNotEmpty(essay.getEssayId()) && ObjectUtils.isNotEmpty(queryEssayByEssayId(essay.getEssayId()))){
//                UpdateWrapper<Essay> updateWrapper = new UpdateWrapper<>();
//                updateWrapper.eq("essay_id",essay.getEssayId());
//                essayMapper.update(essay,updateWrapper);
//                essayId = essay.getEssayId();
//            }
//            else {
//                essayMapper.insert(essay);
//                //通过上一步，essay已存在，则essayId已存在,需要通过essayTitle进行查询
//                Essay essayInDataBase = queryEssayByEssayTitle(essayTitle);
//                essayId = essayInDataBase.getEssayId();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            //当出现错误需要注意的点
//        }
//        //将自定的标签存储到数据库中
//        if (essayLabelsNotStored.size() > 0)  {
//            labelService.insertLabelBatch(essayLabelsNotStored);
//        }
//        //根据标签名再从数据库中查询到先自定义的label
//        List<Label> labels = new ArrayList<>();
//        essayLabelsStored.forEach(label -> {
//            labels.add(labelService.queryLabelByLabelName(label.getLabelName()));
//        });
//        labels.forEach(l->System.out.println(l));
//        //essay-label的关联
//        essayLabelService.insertBatch(essayId,labels);
//    }

    @Override
    public Essay queryEssayByEssayId(Long essayId) {
        return essayMapper.selectById(essayId);
    }

    @Override
    public Essay queryEssayByEssayTitle(String essayTitle) {
        QueryWrapper<Essay> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("essay_title",essayTitle);
        return essayMapper.selectOne(queryWrapper);
    }

//    @Override
//    public PageInfo<Essay> queryByComplexCondition(Integer pageNum, Integer pageSize, Long minBrowseCount, Long maxBrowseCount, Long minCommentCount, Long maxCommentCount, List<String> labels, String publishDatetime) {
//        if (maxBrowseCount  == null || maxBrowseCount == 0) maxBrowseCount = Long.MAX_VALUE;
//        if (maxCommentCount  == null || maxCommentCount == 0) maxCommentCount = Long.MAX_VALUE;
//        PageHelper.startPage(pageNum,pageSize);
//        List<Date> dates = null;
//        if (publishDatetime != null){
//            String[] publishDatetimes = publishDatetime.split(",");
//            dates = new LinkedList<>();
//            System.out.println("publishdates---"+Arrays.toString(publishDatetimes));
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.out.println("len=="+publishDatetimes.length);
//            System.out.println(publishDatetimes[0]);
//            System.out.println(publishDatetimes[1]);
//            for (int i = 0; i < publishDatetimes.length; i++) {
//                System.out.println(i);
//                try {
//                    dates.add(sdf.parse(publishDatetimes[i]));
//                    System.out.println(sdf.parse(publishDatetimes[i]));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        System.out.println("dates--"+dates);
//        List<Essay> essays = essayMapper.queryByComplexCondition(minBrowseCount, maxBrowseCount, minCommentCount, maxCommentCount, labels, dates);
//        return new PageInfo<>(essays);
//    }


    @Override
    public PaginationInfo<Essay> queryByComplexCondition(Integer pageNum, Integer pageSize, Long minBrowseCount, Long maxBrowseCount, Long minCommentCount, Long maxCommentCount, List<String> labels, String publishDatetime) {
        //1.获取所有的文章记录数
        if (maxBrowseCount == 0 ) maxBrowseCount = Long.MAX_VALUE;
        if (maxCommentCount == 0 ) maxCommentCount = Long.MAX_VALUE;
        QueryWrapper<Essay> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("browse_count",minBrowseCount,maxBrowseCount);
        queryWrapper.between("comment_count",minCommentCount,maxCommentCount);
        List<Date> dates = null;
        String[] datetimes = null;
        if (publishDatetime != null){
            Date date1 = null;
            Date date2 = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            datetimes = publishDatetime.split(",");
            try {
                date1 = sdf.parse(datetimes[0]);
                date2 = sdf.parse(datetimes[1]);
                dates = Arrays.asList(date1,date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            queryWrapper.between("create_time",date1,date2);
        }
        int total = Math.toIntExact(essayMapper.selectCount(queryWrapper));//总记录数
        //获取所有页码=>pages
        int pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        //判断pageNum是否越界
        if (pageNum < 1 ) pageNum = 1;
        else  if (pageNum > pages) pageNum = pages;
        //设置当前的起始分页位置
        int startNum = (pageNum - 1) * pageSize;
        //做到上面后即可进行自定义的分页查询
        List<Essay> essays = essayMapper.queryByComplexConditionAndCustomPagination(startNum, pageSize, minBrowseCount, maxBrowseCount, minCommentCount, maxCommentCount, labels, dates);
        //创建PaginationInfo
        PaginationInfo<Essay> paginationInfo = new PaginationInfo<>();
        paginationInfo.setPageSize(pageSize);
        paginationInfo.setPageNum(pageNum);
        paginationInfo.setPage(essays.size());
        paginationInfo.setPages(pages);
        paginationInfo.setData(essays);
        paginationInfo.setTotal(total);
        return paginationInfo;
    }

    @Override
    public List<Essay> getByCustomMechanismN(Integer num) {
        //防止注入，获取过多的文章
        if (num > 10) num = 10;
        //对文章进行
        QueryWrapper<Essay> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("essay_id");
        queryWrapper.last("limit 0 , " + num);
        List<Essay> essayList = essayMapper.selectList(queryWrapper);
        essayList.forEach(essay -> essay.setEssayLabels(labelService.queryLabelsByEssayId(essay.getEssayId())));
        return essayList;
    }

    @Override
    public List<Essay> getHotEssayByBrowseCountHeadN(Integer count) {
        if (count > 10) count = 10;
        else if(count < 5) count = 5;
        QueryWrapper<Essay> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(Arrays.asList("browse_count","create_time"));
        queryWrapper.last("limit 0 , "+count);
        List<Essay> essayList = essayMapper.selectList(queryWrapper);
        essayList.forEach(essay -> essay.setEssayLabels(labelService.queryLabelsByEssayId(essay.getEssayId())));
        return essayList;
    }

    @Override
    public List<Essay> getArchiveEssayBySpecificYearMonth(Integer year,Integer month) {
        return essayMapper.getArchiveEssayBySpecificYearMonth(year,month);
    }

    @Override
    public HashMap<String, Object> getArchiveFirst() {
        //获取最新的日期
        Date latestDate = essayMapper.getLatestDate();
        Date oldestDate = essayMapper.getPastestDate();
        System.out.println(latestDate);
        System.out.println(oldestDate);
        HashMap<String, Object> map = new HashMap<>();
        map.put("latestYear",latestDate.getYear()+1900);
        map.put("latestMonth",latestDate.getMonth()+1);
        map.put("oldestYear",oldestDate.getYear()+1900);
        map.put("oldestMonth",oldestDate.getMonth()+1);
        //根据最新月份获取文章
        map.put("latestMonthEssayList",getArchiveEssayBySpecificYearMonth(latestDate.getYear()+1900,latestDate.getMonth()+1));
        return map;
    }

    @Override
    public HashMap<String,Object> getEssayListByLabels(Integer pageNum,Integer pageSize,List<String> labelNames) {
        if (labelNames == null) {
            labelNames = new LinkedList<>();
            List<String> allLabelNames = labelService.getAllLabelNames();
            System.out.println(allLabelNames.size());
            int start_index = (int) (Math.random()*(allLabelNames.size()));
            for (int i = 0; i < 5; i++) {
                labelNames.add(allLabelNames.get((start_index+i) % allLabelNames.size()));
            }
        }
        //这个包含了根据标签查询到的所有文章
        List<Essay> essay = essayMapper.getEssayByLabelNamesAndPagination(pageNum, pageSize, labelNames);
        //然后就可以复用getEssayByComplexCondition来进行获取文章
        PaginationInfo<Essay> paginationInfo = new PaginationInfo<>();
        //则进行数据的分页的时候，需要在java代码层次进行判断
        int total = essay.size();
        int pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        int start_num = (pageNum - 1) * pageSize;
        int end_num = start_num + pageSize > total ? total : start_num + pageSize;
        paginationInfo.setData(essay.subList(start_num,end_num));
        paginationInfo.setPageNum(pageNum);
        paginationInfo.setPageSize(pageSize);
        paginationInfo.setPages(pages);
        paginationInfo.setTotal(total);
        paginationInfo.setPage(end_num - start_num);
        HashMap<String, Object> map = new HashMap<>();
        map.put("paginationInfo",paginationInfo);
        map.put("customLabels",labelNames);
        map.put("allEssay",essay);
        System.out.println(essay);
        return map;
    }

    @Override
    public List<Essay> getEssayBySpecifiedYearMonth(Integer year, Integer month) {
        return essayMapper.getArchiveEssayBySpecificYearMonth(year,month);
    }

    @Override
    public Integer browseCountIncrement(Long essayId) {
        return essayMapper.incrementBrowseCount(essayId);
    }
}
