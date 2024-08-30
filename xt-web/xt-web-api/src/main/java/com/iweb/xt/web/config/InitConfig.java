package com.iweb.xt.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.iweb.xt.common.service","com.iweb.xt.common.cache"})
public class InitConfig {
}
