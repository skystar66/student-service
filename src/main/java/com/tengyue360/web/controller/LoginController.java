package com.tengyue360.web.controller;

import com.tengyue360.constant.Constants;
import com.tengyue360.service.LoginService;
import com.tengyue360.web.BeanValidators.BeanValidators;
import com.tengyue360.web.requestModel.LoginRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 登录管理
 *
 * @author xuliang
 * @date 2018/8/10 12:37
 */
@RestController(value = "/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    LoginService userService;

    /**
     * 登录
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult login(LoginRequestModel model) {
        logger.info("调用登录接口，参数信息：{}", model);
        if (null != BeanValidators.isValidateLogin(model, redisTemplate)) {
            logger.info("调用登录接口参数信息校验失败，返回结果：{}", BeanValidators.isValidateLogin(model, redisTemplate));
            return BeanValidators.isValidateLogin(model, redisTemplate);
        }
        //调用后端服务
        if (model.getLoginType().equals(Constants.LOGIN_TYPE_CODE)) {
            //验证码登录
            return userService.codeLogin(model);
        } else if (model.getLoginType().equals(Constants.LOGIN_TYPE_PWD)) {
            //密码登录
            return userService.login(model);
        }
        return null;

    }


}
