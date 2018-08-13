package com.tengyue360.service.impl;

import com.tengyue360.service.MessageService;
import com.tengyue360.service.SmsService;
import com.tengyue360.web.requestModel.SendSmsRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 短信验证码服务
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */
@Service
public class SmsServiceImpl implements SmsService {



    @Autowired
    MessageService messageService;


    /**
     * 获取短信验证码
     *
     * @return
     * @throws Exception
     */

    @Override
    public ResponseResult getValidateCode(SendSmsRequestModel model) {





        return null;
    }
}
