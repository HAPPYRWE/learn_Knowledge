package com.iweb.xt.common.service;

import com.iweb.xt.common.model.BusinessCodeEnum;
import com.iweb.xt.common.model.CallResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component
@Slf4j
public class ServiceTemplateImpl implements ServiceTemplate{
    //假如事务的声明
    @Transactional
    @Override
    public <T> CallResult<T> execute(TemplateAction<T> action) {
        try {
            CallResult<T> callResult = action.checkParam();
            if (callResult == null){
                log.warn("execute : Null result while checkParam");
                return CallResult.fail(BusinessCodeEnum.CHECK_PARAM_NO_RESULT.getCode(),BusinessCodeEnum.CHECK_BIZ_NO_RESULT.getMsg());
            }
            if (!callResult.isSuccess()){
                return callResult;
            }
            callResult = action.checkBiz();
            if (callResult == null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.warn("execute : Null result while checkBiz");
                return CallResult.fail(BusinessCodeEnum.CHECK_BIZ_NO_RESULT.getCode(), BusinessCodeEnum.CHECK_BIZ_NO_RESULT.getMsg());
            }
            if (!callResult.isSuccess()){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return callResult;
            }
            long start = System.currentTimeMillis();
            CallResult<T> cr = action.doAction();
            log.info("execute datasource method run time: {} ms",System.currentTimeMillis()-start);
            if (cr == null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return CallResult.fail(BusinessCodeEnum.CHECK_ACTION_NO_RESULT.getCode(), BusinessCodeEnum.CHECK_ACTION_NO_RESULT.getMsg());
            }
            if (cr.isSuccess()){
                action.finishUp(cr);
            }
            return cr;
        }catch (Exception e){
//            回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("execute error", e);
            return CallResult.fail();
        }
    }

    @Override
    public <T> CallResult<T> executeQuery(TemplateAction<T> action) {
        try {
            CallResult<T> callResult = action.checkParam();
            if (callResult == null){
                log.warn("executeQuery : Null result while checkParam");
                return CallResult.fail(BusinessCodeEnum.CHECK_PARAM_NO_RESULT.getCode(),BusinessCodeEnum.CHECK_BIZ_NO_RESULT.getMsg());
            }
            if (!callResult.isSuccess()){
                return callResult;
            }
            callResult = action.checkBiz();
            if (callResult == null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.warn("executeQuery : Null result while checkBiz");
                return CallResult.fail(BusinessCodeEnum.CHECK_BIZ_NO_RESULT.getCode(), BusinessCodeEnum.CHECK_BIZ_NO_RESULT.getMsg());
            }
            if (!callResult.isSuccess()){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return callResult;
            }
            long start = System.currentTimeMillis();
            CallResult<T> cr = action.doAction();
            log.info("executeQuery datasource method run time: {} ms",System.currentTimeMillis()-start);
            if (cr == null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return CallResult.fail(BusinessCodeEnum.CHECK_ACTION_NO_RESULT.getCode(), BusinessCodeEnum.CHECK_ACTION_NO_RESULT.getMsg());
            }
            if (cr.isSuccess()){
                action.finishUp(cr);
            }
            return cr;
        }catch (Exception e){
//            回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            log.error("executeQuery error", e);
            return CallResult.fail();
        }
    }
}
