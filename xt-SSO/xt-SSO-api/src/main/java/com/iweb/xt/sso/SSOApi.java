package com.iweb.xt.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@MapperScan("com.iweb.xt.sso.dao.mapper")
@SpringBootApplication
public class SSOApi {
    public static void main(String[] args) {
        SpringApplication.run(SSOApi.class,args);
    }
}