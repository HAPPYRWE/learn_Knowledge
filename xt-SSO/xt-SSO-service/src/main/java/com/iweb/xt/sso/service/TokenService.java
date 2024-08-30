package com.iweb.xt.sso.service;


import com.iweb.xt.common.model.CallResult;

public interface TokenService {

    CallResult parseToken(String token);
}
