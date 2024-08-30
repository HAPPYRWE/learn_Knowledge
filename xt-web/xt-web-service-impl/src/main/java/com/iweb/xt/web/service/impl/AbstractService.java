package com.iweb.xt.web.service.impl;

import com.iweb.xt.common.service.ServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public abstract class AbstractService {
    @Autowired
    protected ServiceTemplate serviceTemplate;
}
