package com.tengyue360.exception;

import com.tengyue360.common.ReturnCode;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 程序/业务异常{
 *     后期可做 异常日志的增量表
 * }
 * @author xuliang
 * @date 2018年8月10日 21:04:55
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseResult handleBusinessException(BusinessException e) {
        LOGGER.error("业务异常:{}", e.getMessage());
        //异常日志添加
//        CommonSysLogUtils.updateSysLog("",0,e.getMessage());
        return new ResponseResult(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e) {
        LOGGER.error("程序异常:", e);
        //异常日志添加
//        CommonSysLogUtils.updateSysLog("0",0,e.getMessage());
        return new ResponseResult(ReturnCode.ACTIVE_EXCEPTION.code(), e.getMessage());
    }
}