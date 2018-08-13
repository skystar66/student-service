package com.tengyue360.web.controller;


import com.alibaba.fastjson.JSON;
import com.tengyue360.service.SmsService;
import com.tengyue360.web.BeanValidators.BeanValidators;
import com.tengyue360.web.requestModel.SendSmsRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Request;

/**
 * 发送短信管理类
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */

@RestController(value = "/sms")
public class SendMessageController {


    private static Logger logger = LoggerFactory.getLogger(SendMessageController.class);

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SmsService smsService;

    /**
     * 获取登录短信验证码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login/sendLoginSms", method = RequestMethod.POST)
    public ResponseResult sendLoginSms(SendSmsRequestModel model) {
        logger.info("获取登录短信验证码，参数信息：{}", model);
        if (null != BeanValidators.isValidateLoginSms(model, redisTemplate)) {
            logger.info("获取登录短信验证码，参数验证失败：{}", BeanValidators.isValidateLoginSms(model, redisTemplate));
            return BeanValidators.isValidateLoginSms(model, redisTemplate);
        }
        //调用后台服务 获取验证码
        ResponseResult responseResult = smsService.getValidateCode(model);
        if (null != responseResult) {
            logger.info("获取登录短信验证码成功吗，返回信息x：{}", responseResult);
            return responseResult;
        }

        return null;

    }


    /**
     * 获取修改/忘记密码短信验证码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login/sendUpdatePwdSms", method = RequestMethod.POST)
    public ResponseResult sendUpdatePwdSms(SendSmsRequestModel model) {
        logger.info("获取修改密码短信验证码，参数信息：{}", model);
        if (null != BeanValidators.isValidateLoginSms(model, redisTemplate)) {
            logger.info("获取修改密码短信验证码，参数验证失败：{}", BeanValidators.isValidateLoginSms(model, redisTemplate));
            return BeanValidators.isValidateLoginSms(model, redisTemplate);
        }
        //调用后台服务 获取验证码
        ResponseResult responseResult = smsService.getValidateCode(model);
        if (null != responseResult) {
            logger.info("获取修改短信验证码成功吗，返回信息x：{}", responseResult);
            return responseResult;
        }
        return null;

    }





}
