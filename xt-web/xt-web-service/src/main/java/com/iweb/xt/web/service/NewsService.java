package com.iweb.xt.web.service;

import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.web.model.param.NewsParam;

public interface NewsService {
    CallResult newsList(NewsParam newsParam);

    CallResult findNewsById(NewsParam newsParam);

    CallResult newsDetailList(NewsParam newsParam);
}
