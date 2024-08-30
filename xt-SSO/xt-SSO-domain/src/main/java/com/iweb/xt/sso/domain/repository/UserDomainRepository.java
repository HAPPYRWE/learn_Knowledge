package com.iweb.xt.sso.domain.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.iweb.xt.sso.dao.data.User;
import com.iweb.xt.sso.dao.mapper.UserMapper;
import com.iweb.xt.sso.domain.UserDomain;
import com.iweb.xt.sso.model.param.user.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserDomainRepository {
    @Autowired
    private UserMapper userMapper;
    public UserDomain creatUserDomain(UserParam userParam) {
        return new UserDomain(this,userParam);
    }

    public User findUserByUnionId(String unionId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUnionId,unionId);
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    public void insertUser(User user) {
        userMapper.insert(user);
    }

    public void updateLastLoginTime(User user) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId,user.getId());
        wrapper.set(User::getLastLoginTime,user.getLastLoginTime());
        userMapper.update(null,wrapper);
    }

    public User findUserById(Long userId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,userId);
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }
}
