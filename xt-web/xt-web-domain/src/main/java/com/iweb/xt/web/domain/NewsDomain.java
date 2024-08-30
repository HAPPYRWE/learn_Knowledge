package com.iweb.xt.web.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iweb.xt.common.model.BusinessCodeEnum;
import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.common.model.ListPageModel;
import com.iweb.xt.web.dao.data.News;
import com.iweb.xt.web.domain.repository.NewsDomainRepository;
import com.iweb.xt.web.model.NewsModel;
import com.iweb.xt.web.model.param.NewsParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class NewsDomain {
    private NewsDomainRepository newsDomainRepositortry;
    private NewsParam newsParam;
    public NewsDomain(NewsDomainRepository newsDomainRepositortry, NewsParam newsParam) {
        this.newsDomainRepositortry=newsDomainRepositortry;
        this.newsParam=newsParam;
    }
    public NewsModel copy(News news){
        if (news == null){
            return null;
        }
        NewsModel newsModel = new NewsModel();
        //属性copy
        BeanUtils.copyProperties(news,newsModel);
        if (news.getCreateTime() != null) {
            newsModel.setCreateTime(new DateTime(news.getCreateTime()).toString("yyyy年MM月dd日 HH:mm:ss"));
        }
        if (news.getImageUrl() != null) {
            if (!news.getImageUrl().startsWith("http")) {
//                 newsModel.setImageUrl(newsDomainRepositortry.qiniuConfig.getFileServerUrl() + news.getImageUrl());
            }
        }
        return newsModel;
    }

    public List<NewsModel> copyList(List<News> newsList){
        List<NewsModel> newsModelList = new ArrayList<>();
        for (News news : newsList){
            newsModelList.add(copy(news));
        }
        return newsModelList;
    }
    public CallResult<Object> checkNewsListParam() {
        // 每页 只能展示五条数据 最多五条数据
        if (newsParam.getPageSize() > 5){
            newsParam.setPageSize(5);
        }
        if (newsParam.getTab() == null){
            return CallResult.fail(BusinessCodeEnum.CHECK_PARAM_NO_RESULT.getCode(),
                    "param error: tab is null");
        }
        return CallResult.success();
    }

    public CallResult<Object> newsList(boolean isDetail) {
        //获取分页参数  和 标签参数
        int page = newsParam.getPage();
        int pageSize = newsParam.getPageSize();
        Integer tab = newsParam.getTab();
        //使用repository 到数据库中去查询
        Page<News> list = newsDomainRepositortry.findNewsListByTab(page,pageSize,tab,isDetail);
        //此时 我们只查询了 id  title
        //这个是pojo对象 那么如果要返回给前端
        //前端不要pojo 需要一个转门的对象
        //将我们从数据库中查询的数据 复制给 model
        List<News> news = list.getRecords();//因为是page集合 里面的数据 在recode里面
        List<NewsModel> newsModels = copyList(news);
        //专门返回给前端的一个格式对象
        ListPageModel<NewsModel> listPageModel = new ListPageModel<>();
        //放置分页参数
        listPageModel.setList(newsModels);
        listPageModel.setPage(page);
        listPageModel.setPageSize(pageSize);
        listPageModel.setPageCount(list.getPages());
        listPageModel.setSize(list.getTotal());
        return CallResult.success(listPageModel);
    }

    public CallResult<Object> findNewsById() {
        News news = newsDomainRepositortry.findNewsById(newsParam.getId());
        NewsModel newsModel = copy(news);
        return CallResult.success(newsModel);
    }
}

