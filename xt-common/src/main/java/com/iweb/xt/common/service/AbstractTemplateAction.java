package com.iweb.xt.common.service;

import com.iweb.xt.common.model.CallResult;
import org.apache.poi.ss.formula.functions.T;

public abstract class AbstractTemplateAction<T> implements TemplateAction<T>{
    @Override
    public CallResult<T> checkParam() {
        return CallResult.success();
    }

    @Override
    public CallResult<T> checkBiz() {
        return CallResult.success();
    }

    @Override
    public void finishUp(CallResult<T> callResult) {

    }
}
