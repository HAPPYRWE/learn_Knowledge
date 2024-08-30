package com.iweb.xt.sso.config.handler;

import com.alibaba.fastjson.JSON;
import com.iweb.xt.common.login.UserThreadLocal;
import com.iweb.xt.common.model.BusinessCodeEnum;
import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.sso.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        获取cookie， 从cookie获取token
//        解析token
        Cookie[] cookies  =request.getCookies();
        if (cookies == null) {
            returnJson(response);
            return false;
        }
        String token = "";
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("t_token")){
                token = cookie.getValue();
                break;
            }
        }
//        token可能存在
        if (StringUtils.isBlank(token)){
            returnJson(response);
            return false;
        }
//        token 一定存在
        Long userId = null;

        CallResult callResult = tokenService.parseToken(token);
        Object result = callResult.getResult();
        if (result == null) {
            //没有登陆过
            returnJson(response);
            return false;
        }else {
            userId = (Long) result;
//            System.out.println(request+"解析到token存在");
            UserThreadLocal.put(userId);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//       结束后清空localThread 否则会内存泄漏
        UserThreadLocal.remove();
    }

    private void returnJson(HttpServletResponse response) {
        PrintWriter writer = null;
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        try{
            writer = response.getWriter();
            CallResult callResult = CallResult.fail(BusinessCodeEnum.NO_LOGIN.getCode(),"您的登陆已失效，请重新登录");
            writer.write(JSON.toJSONString(callResult));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
