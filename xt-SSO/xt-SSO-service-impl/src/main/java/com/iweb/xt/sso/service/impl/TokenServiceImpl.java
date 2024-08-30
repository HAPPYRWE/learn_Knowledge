package com.iweb.xt.sso.service.impl;

import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.common.service.AbstractTemplateAction;
import com.iweb.xt.sso.domain.TokenDomain;
import com.iweb.xt.sso.domain.repository.TokenDomainRepository;
import com.iweb.xt.sso.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl extends AbstractService implements TokenService {
    @Autowired
    public TokenDomainRepository tokenDomainRepository;
    @Override
    public CallResult parseToken(String token) {
//      创建tokenDomain
       TokenDomain tokenDomain =  tokenDomainRepository.creatTokenDomain();
        return this.serviceTemplate.executeQuery(new AbstractTemplateAction<Object>() {
            @Override
            public CallResult<Object> doAction() {
                return tokenDomain.parseToken(token);
            }
        });
    }
}
