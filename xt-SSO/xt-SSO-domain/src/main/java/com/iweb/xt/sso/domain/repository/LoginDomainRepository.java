package com.iweb.xt.sso.domain.repository;

import com.iweb.xt.common.constants.RedisKey;
import com.iweb.xt.common.wx.WxOpenConfig;
import com.iweb.xt.sso.domain.LoginDomain;
import com.iweb.xt.sso.domain.UserDomain;
import com.iweb.xt.sso.model.param.user.UserParam;
import com.iweb.xt.sso.model.param.login.LoginParam;

import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class LoginDomainRepository {
    @Autowired
    public WxMpService wxMpService;
    @Autowired
    public WxOpenConfig wxOpenConfig;
    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    private UserDomainRepository userDomainRepository;

    public LoginDomain creatLoginDomain(LoginParam loginParam) {
        return new LoginDomain(this,loginParam);
    }

    public String buildUrl() {
        String csrfKey = UUID.randomUUID().toString();
//        保存在redis里
        redisTemplate.opsForValue().set(RedisKey.WX_STATE_KEY+csrfKey,"",2, TimeUnit.MINUTES);
        return wxMpService.buildQrConnectUrl(wxOpenConfig.redirectUrl,wxOpenConfig.scope,csrfKey);
    }
    public boolean checkState(String state){
        return redisTemplate.hasKey(RedisKey.WX_STATE_KEY+state);
    }
    public UserDomain createUserDomain(UserParam userParam){
        return userDomainRepository.creatUserDomain(userParam);
    }
}
