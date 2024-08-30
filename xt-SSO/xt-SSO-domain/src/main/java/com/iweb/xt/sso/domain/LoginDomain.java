package com.iweb.xt.sso.domain;

import com.iweb.xt.common.constants.RedisKey;
import com.iweb.xt.common.model.BusinessCodeEnum;
import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.common.utils.JwtUtil;
import com.iweb.xt.sso.dao.data.User;
import com.iweb.xt.sso.domain.repository.LoginDomainRepository;
import com.iweb.xt.sso.model.param.enums.LoginType;
import com.iweb.xt.sso.model.param.login.LoginParam;
import com.iweb.xt.sso.model.param.user.UserParam;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class LoginDomain {
    private LoginDomainRepository loginDomainRepository;
    private LoginParam loginParam;
    private String secretKey = "asdf234@#fsd@#21";

    public LoginDomain(LoginDomainRepository loginDomainRepository, LoginParam loginParam) {
        this.loginDomainRepository = loginDomainRepository;
        this.loginParam = loginParam;
    }

    public CallResult<String> buildQrCodeUrl() {
        String url = loginDomainRepository.buildUrl();
        return CallResult.success(url);
    }

    public CallResult<Object> checkState() {
        boolean isExists = loginDomainRepository.checkState(loginParam.getState());
        return isExists ? CallResult.success() : CallResult.fail(BusinessCodeEnum.CHECK_PARAM_NO_RESULT.getCode(), BusinessCodeEnum.CHECK_PARAM_NO_RESULT.getMsg());
    }

    public CallResult<Object> login() throws WxErrorException {
        //        从Redis里面获取rfToken
        String refreshToken = (String) this.loginDomainRepository.redisTemplate.opsForValue().get(RedisKey.REFRESH_TOKEN + loginParam.getState());
        WxMpOAuth2AccessToken accessToken = null;
        if (StringUtils.isBlank(refreshToken)) {
//            没有登陆过
            String code = loginParam.getCode();
//       获取刷新的refresh_token
            accessToken = this.loginDomainRepository.wxMpService.oauth2getAccessToken(code);
            refreshToken = accessToken.getRefreshToken();
//           存储到redis里面 设置有效期天数，一般<28天
            this.loginDomainRepository.redisTemplate.opsForValue().set(RedisKey.REFRESH_TOKEN + loginParam.getState(), refreshToken, 7, TimeUnit.DAYS);
        }
//        之前登陆过 直接刷新有效期
        accessToken = this.loginDomainRepository.wxMpService.oauth2refreshAccessToken(refreshToken);
        WxMpUser wxMpUser = this.loginDomainRepository.wxMpService.oauth2getUserInfo(accessToken, "zh_CN");
//        获取用户的信息
//        获取unionID判断是否注册过
        String unionId = wxMpUser.getUnionId();
//        到数据库查询
        User user = this.loginDomainRepository.createUserDomain(new UserParam()).findUserByUnionId(unionId);
        boolean isNew = false;
        if (user == null) {
            // 注册
            user = new User();
            Long currentTime = System.currentTimeMillis();
            user.setNickname(wxMpUser.getNickname());
            user.setHeadImageUrl(wxMpUser.getHeadImgUrl());
            user.setSex(wxMpUser.getSex());
            user.setOpenid(wxMpUser.getOpenId());
            user.setLoginType(LoginType.WX.getCode());
            user.setCountry(wxMpUser.getCountry());
            user.setProvince(wxMpUser.getProvince());
            user.setCity(wxMpUser.getCity());
            user.setRegisterTime(currentTime);
            user.setLastLoginTime(currentTime);
            user.setUnionId(wxMpUser.getUnionId());
            user.setArea("");
            user.setGrade("");
            user.setSchool("");
            user.setName(wxMpUser.getNickname());
            this.loginDomainRepository.createUserDomain(new UserParam()).saveUser(user);
            isNew = true;
        }
        // 生成登录token
        String token = JwtUtil.createJWT(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000, user.getId(), secretKey);
        this.loginDomainRepository.redisTemplate.opsForValue().set(RedisKey.TOKEN + token, user.getId().toString());
        // 删除老的登陆token 如果有老的直接删了
        String loginToken = (String) this.loginDomainRepository.redisTemplate.opsForValue().get(RedisKey.LOGIN_USER_TOKEN + user.getId());
        if (loginToken != null) {
            this.loginDomainRepository.redisTemplate.delete(RedisKey.TOKEN + token);
        }
        // 将新的token放进redis
        this.loginDomainRepository.redisTemplate.opsForValue().set(RedisKey.LOGIN_USER_TOKEN + user.getId(), token);
        // 设置登录cookie
        HttpServletResponse response = loginParam.getResponse();
        Cookie cookie = new Cookie("t_token", token);
        cookie.setMaxAge(8 * 24 * 3600);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.addCookie(cookie);
        // 如果注册成功，更新最后登录时间
        if (!isNew) {
            user.setLastLoginTime(System.currentTimeMillis());
            this.loginDomainRepository.createUserDomain(new UserParam()).updateLastLoginTime(user);
        }
        return CallResult.success();
    }
}
