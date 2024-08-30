package com.iweb.xt.sso.service.impl;

import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.common.service.AbstractTemplateAction;
import com.iweb.xt.sso.domain.UserDomain;
import com.iweb.xt.sso.domain.repository.UserDomainRepository;
import com.iweb.xt.sso.model.param.user.UserParam;
import com.iweb.xt.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService implements UserService {
    @Autowired
    private UserDomainRepository userDomainRepository;
    @Override
    public CallResult userInfo(UserParam userParam) {
        UserDomain userDomain=userDomainRepository.creatUserDomain(userParam);
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return userDomain.getUserInfo();
            }
        });
    }
}
