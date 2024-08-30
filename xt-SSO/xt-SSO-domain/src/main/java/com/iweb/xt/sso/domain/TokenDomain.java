package com.iweb.xt.sso.domain;

import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.sso.domain.repository.TokenDomainRepository;
import org.apache.commons.lang3.StringUtils;

public class TokenDomain {
    private TokenDomainRepository tokenDomainRepository;
    public TokenDomain(TokenDomainRepository tokenDomainRepository) {
        this.tokenDomainRepository = tokenDomainRepository;
    }
    public CallResult<Object> parseToken(String token){
//        从redis里判断是否有Token;
        String data = tokenDomainRepository.getToken(token);
//        System.out.println(Long.parseLong(data));
        return StringUtils.isBlank(data)?CallResult.success(null):CallResult.success(Long.parseLong(data));
    }
}
