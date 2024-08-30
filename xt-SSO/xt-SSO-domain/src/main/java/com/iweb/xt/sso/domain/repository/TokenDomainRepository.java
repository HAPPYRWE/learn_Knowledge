package com.iweb.xt.sso.domain.repository;

import com.iweb.xt.common.constants.RedisKey;
import com.iweb.xt.sso.domain.TokenDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TokenDomainRepository {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public TokenDomain creatTokenDomain(){
        return new TokenDomain(this);
    }

    public String getToken(String token) {
        return stringRedisTemplate.opsForValue().get(RedisKey.TOKEN+token);
    }
}
