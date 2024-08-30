package com.iweb.xt.web;

import com.iweb.xt.common.cache.Cache;

public class GetCache {

    @Cache(name = "token",time = 60*1000)
    public void isCache(){

    }
}
