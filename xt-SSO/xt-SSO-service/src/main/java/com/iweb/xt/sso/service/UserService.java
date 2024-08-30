package com.iweb.xt.sso.service;

import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.sso.model.param.user.UserParam;
import org.springframework.stereotype.Service;

@Service
public interface  UserService {

    CallResult userInfo(UserParam userParam);
}
