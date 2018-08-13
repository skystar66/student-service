package com.tengyue360.service;

import com.tengyue360.web.requestModel.SendSmsRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;

/**
 * 获取短信验证码服务
 *
 * @author xuliang
 * @date 2018/8/10 10:03
 */
public interface SmsService {


    /**
     * 获取短信验证码
     *
     * @return
     * @throws Exception
     */

    ResponseResult getValidateCode(SendSmsRequestModel model);


}
