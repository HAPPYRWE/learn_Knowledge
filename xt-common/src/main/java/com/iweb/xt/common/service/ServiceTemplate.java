package com.iweb.xt.common.service;

import com.iweb.xt.common.model.CallResult;

public interface ServiceTemplate {
    <T> CallResult<T> execute(TemplateAction<T> action);

    <T> CallResult<T> executeQuery(TemplateAction<T> action);
}
