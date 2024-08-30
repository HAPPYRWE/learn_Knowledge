package com.iweb.xt.web.api;


import com.iweb.xt.common.cache.redis.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {
	@GetMapping("/aopTest")
    @Auth
    public String aopTest(@RequestParam String name){
        //远程调用
        System.out.println("正在执行接口name" + name);
        return "success";
    }
}
