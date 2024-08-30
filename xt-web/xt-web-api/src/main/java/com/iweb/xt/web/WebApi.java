package com.iweb.xt.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class WebApi {
    public static void main(String[] args) {
        SpringApplication.run(WebApi.class,args);
    }
}
