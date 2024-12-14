package com.iweb.xt.web.api;

import com.iweb.xt.common.cache.Cache;
import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.web.service.NewsService;
import com.iweb.xt.web.model.param.NewsParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PostMapping("/newsList")
    @Cache(time = 2 * 60 * 1000, name = "News")
    public CallResult newsList(@RequestBody NewsParam newsParam) {
        System.out.println("newList");
        return newsService.newsList(newsParam);
    }

    @PostMapping("detail")
    @Cache(time = 30 * 1000, name = "News")
    public CallResult news(@RequestBody NewsParam newsParam) {
        return newsService.findNewsById(newsParam);
    }

    @PostMapping("newsDetailList")
    @Cache(time = 30 * 1000, name = "News")
    public CallResult newsDetailList(@RequestBody NewsParam newsParam) {
        return newsService.newsDetailList(newsParam);
    }
}
