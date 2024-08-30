package com.iweb.xt.sso.service.impl;

import com.iweb.xt.common.service.ServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {
    @Autowired
    protected ServiceTemplate serviceTemplate;
}
