package com.iweb.xt.web.domain.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iweb.xt.web.dao.data.News;
import com.iweb.xt.web.dao.mapper.NewsMapper;
import com.iweb.xt.web.domain.NewsDomain;
import com.iweb.xt.web.model.enums.Status;
import com.iweb.xt.web.model.param.NewsParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component
public class NewsDomainRepository {
    @Resource
    private NewsMapper newsMapper;

    public NewsDomain createNewsDomain(NewsParam newsParam) {
        return new NewsDomain(this,newsParam);
    }

    public Page<News> findNewsListByTab(int currentPage, int pageSize, Integer tab,boolean isDetail) {
        Page<News> page = new Page<>(currentPage,pageSize);
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(News::getTab,tab);
        queryWrapper.eq(News::getStatus, Status.NORMAL.getCode());
        if (isDetail){
            queryWrapper.select(News::getId,News::getImageUrl,News::getCreateTime,News::getAuthor,News::getTitle,News::getTab,News::getSummary);
        }else {
            queryWrapper.select(News::getId,News::getTitle,News::getImageUrl);
        }
//        System.out.println(newsMapper);
        return newsMapper.selectPage(page,queryWrapper);
    }
    public News findNewsById(Long id) {
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(News::getId,id);
        queryWrapper.select(News::getId,News::getTitle,News::getImageUrl);
        return newsMapper.selectOne(queryWrapper);
    }
}
