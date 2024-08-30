package com.iweb.xt.common.service;

import com.iweb.xt.common.model.CallResult;
import me.chanjar.weixin.common.error.WxErrorException;

public interface TemplateAction<T> {
//    检查参数
    CallResult<T> checkParam();
//    检察业务逻辑
    CallResult<T> checkBiz();
//    执行
    CallResult<T> doAction() throws WxErrorException;
//    执行完成后，要进行的操作
    void finishUp(CallResult<T> callResult);

}
