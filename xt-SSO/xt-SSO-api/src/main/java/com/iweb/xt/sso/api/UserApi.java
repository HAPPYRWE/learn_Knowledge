package com.iweb.xt.sso.api;

import com.iweb.xt.common.model.CallResult;
import com.iweb.xt.sso.model.param.user.UserParam;
import com.iweb.xt.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserApi {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "userInfo",method = RequestMethod.POST)
    public CallResult userInfo(){
        UserParam userParam = new UserParam();
        return userService.userInfo(userParam);
    }
}
