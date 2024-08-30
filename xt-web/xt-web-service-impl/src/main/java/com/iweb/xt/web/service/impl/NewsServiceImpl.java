package com.iweb.xt.web.service.impl;

import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.common.service.AbstractTemplateAction;
import com.iweb.xt.common.service.ServiceTemplate;
import com.iweb.xt.web.domain.NewsDomain;
import com.iweb.xt.web.domain.repository.NewsDomainRepository;
import com.iweb.xt.web.model.param.NewsParam;
import com.iweb.xt.web.service.NewsService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl extends AbstractService implements NewsService {
    @Autowired
    private NewsDomainRepository newsDomainRepository;

    @Autowired(required = false)
    private ServiceTemplate serviceTemplate;
    @Override
    public CallResult newsList(NewsParam newsParam) {
//        System.out.println(newsDomainRepository.getClass());
        //创建domain
        NewsDomain newsDomain=newsDomainRepository.createNewsDomain(newsParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            //检查参数
            @Override
            public CallResult<Object> checkParam() {
                return newsDomain.checkNewsListParam();
            }

            @Override
            public CallResult<Object> doAction(){
                return newsDomain.newsList(false);
            }
        });
    }

    @Override
    public CallResult findNewsById(NewsParam newsParam) {
        NewsDomain newsDomain = newsDomainRepository.createNewsDomain(newsParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() throws WxErrorException {
                return newsDomain.findNewsById();
            }
        });
    }

    @Override
    public CallResult newsDetailList(NewsParam newsParam) {
        NewsDomain newsDomain = newsDomainRepository.createNewsDomain(newsParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() throws WxErrorException {
                return newsDomain.newsList(true);
            }
        });
    }
}
