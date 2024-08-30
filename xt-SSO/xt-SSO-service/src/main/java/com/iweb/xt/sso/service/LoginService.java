package com.iweb.xt.sso.service;

import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.sso.model.param.login.LoginParam;
import org.springframework.context.annotation.Bean;

public interface LoginService {
    CallResult getQECodeUrl();

    CallResult wxLoginCallBack(LoginParam loginParam);
}
