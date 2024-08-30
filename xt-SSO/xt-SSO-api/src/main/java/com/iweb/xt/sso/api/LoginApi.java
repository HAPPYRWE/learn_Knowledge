package com.iweb.xt.sso.api;

import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.sso.model.param.login.LoginParam;
import com.iweb.xt.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@RestController
@Controller
@RequestMapping("login")
public class LoginApi {
    @Autowired
    private LoginService loginService;

    @PostMapping("getQRCodeUrl")
    @ResponseBody
    public CallResult getQRCodeUrl(){
        return loginService.getQECodeUrl();
    }

    @GetMapping("wxLoginCallBack")//微信的回调不认识后端的结构
    public String wxLoginCallBack(HttpServletRequest request,
                                      HttpServletResponse response,
                                      String code,String state){
//        手动封装
        LoginParam loginParam = new LoginParam();
        loginParam.setState(state);
        loginParam.setCode(code);
        loginParam.setResponse(response);
        loginParam.setRequest(request);
        CallResult callResult =  loginService.wxLoginCallBack(loginParam);
        if (callResult.isSuccess()){
            return "redirect:http://www.lzxtedu.com/course";
        }else {
            return "redirect:http://www.lzxtedu.com";
        }
    }
}
