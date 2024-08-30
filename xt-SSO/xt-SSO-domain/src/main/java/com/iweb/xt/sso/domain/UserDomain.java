package com.iweb.xt.sso.domain;

import com.iweb.xt.common.login.UserThreadLocal;
import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.sso.dao.data.User;
import com.iweb.xt.sso.domain.repository.UserDomainRepository;
import com.iweb.xt.sso.model.UserModel;
import com.iweb.xt.sso.model.param.user.UserParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


public class UserDomain {
    private UserDomainRepository userDomainRepository;
    private UserParam userParam;
    public UserDomain(UserDomainRepository userDomainRepository, UserParam userParam) {
        this.userParam = userParam;
        this.userDomainRepository = userDomainRepository;
    }

    public User findUserByUnionId(String unionId) {
        return userDomainRepository.findUserByUnionId(unionId);
    }

    public void saveUser(User user) {
        userDomainRepository.insertUser(user);
    }

    public void updateLastLoginTime(User user) {
        userDomainRepository.updateLastLoginTime(user);
    }

    public CallResult<Object> getUserInfo() {
//        根据userId获取用户信息 userId是拦截器的时候 存储在threadLocal
        Long userId = UserThreadLocal.get();
        User user = userDomainRepository.findUserById(userId);
        UserModel userModel = new UserModel(); userModel.setCity(user.getCity());
//        BeanUtils.copyProperties(user,userModel);
        userModel.setCountry(user.getCountry());
        userModel.setHeadImageUrl(user.getHeadImageUrl());
        userModel.setNickname(user.getNickname());
        userModel.setSex(user.getSex());
        userModel.setProvince(user.getProvince());
        userModel.setMobile(user.getMobile());
        userModel.setArea(user.getArea());
        userModel.setGrade(user.getGrade());
        userModel.setSchool(user.getSchool());
        userModel.setCity(user.getCity());
        userModel.setName(user.getName());
        return CallResult.success(userModel);
    }
}
