package com.iweb.xt.sso.service.impl;

import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.common.service.AbstractTemplateAction;
import com.iweb.xt.sso.domain.LoginDomain;
import com.iweb.xt.sso.model.param.login.LoginParam;
import com.iweb.xt.sso.service.LoginService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iweb.xt.sso.domain.repository.LoginDomainRepository;

@Service
public class LoginServiceImpl extends AbstractService implements LoginService {

//      依赖于LoginDomainRepository
    @Autowired
    private LoginDomainRepository loginDomainRepository;
    @Override
    public CallResult getQECodeUrl() {

//      根据repo生成domain
        LoginDomain loginDomain =  loginDomainRepository.creatLoginDomain(new LoginParam());
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<String>() {

            @Override
            public CallResult<String> doAction() {
                return loginDomain.buildQrCodeUrl();
            }
        });
    }

    @Override
    public CallResult wxLoginCallBack(LoginParam loginParam) {
        LoginDomain loginDomain = loginDomainRepository.creatLoginDomain(loginParam);
        return this.serviceTemplate.execute(new AbstractTemplateAction<Object>() {
//          防止csrf攻击，所以要检查state和我传入的是否一致
            @Override
            public CallResult<Object> checkParam() {
                return loginDomain.checkState();
            }

            @SneakyThrows
            @Override
            public CallResult<Object> doAction() {
                return loginDomain.login();
            }
        });
    }
}
