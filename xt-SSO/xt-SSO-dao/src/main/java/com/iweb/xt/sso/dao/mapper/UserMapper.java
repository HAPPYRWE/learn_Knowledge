package com.iweb.xt.sso.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iweb.xt.sso.dao.data.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
