package com.iweb.xt.sso.model.param.login;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
public class LoginParam {
    private String username;
    private String password;
//    微信回调的传参
    private String code;
    private String state;
    private HttpServletResponse response;
    private HttpServletRequest request;
    private String cookieUserId;
}
