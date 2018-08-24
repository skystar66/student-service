package com.tengyue360.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tengyue360.bean.SsClesson;
import com.tengyue360.bean.SsUser;
import com.tengyue360.cache.ICountCache;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.constant.Constants;
import com.tengyue360.constant.RedisConstants;
import com.tengyue360.dao.SsUserMapper;
import com.tengyue360.enums.EMessageTemplateBusinessType;
import com.tengyue360.enums.ValidateCodeEnum;
import com.tengyue360.service.MessageService;
import com.tengyue360.service.SmsService;
import com.tengyue360.service.UserService;
import com.tengyue360.utils.CommonTools;
import com.tengyue360.utils.DateUtil;
import com.tengyue360.web.requestModel.SendSmsRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * 短信验证码服务
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */
@Service
public class SmsServiceImpl implements SmsService {

    private static Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Autowired
    MessageService messageService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SsUserMapper userMapper;
    @Autowired
    ICountCache countCache;

    /**
     * 获取短信验证码
     *
     * @return
     * @throws Exception
     */

    @Override
    public ResponseResult getValidateCode(SendSmsRequestModel model) {
        ResponseResult responseResult = new ResponseResult();
        try {
            SsUser user = userMapper.login(model.getPhone());
            if (null != user) {
                if (StringUtils.isNotBlank(user.getDeleteState()) && !user.getDeleteState().equals("1")) {
                    return ResponseResult.onFailure(null, ReturnCode.NAME_EMPTY);
                }
            } else {
                return ResponseResult.onFailure(null, ReturnCode.NAME_EMPTY);
            }
            //获取验证码
            String random = getValidateCodeRandom(model.getValidateType(), model.getPhone());
            if (model.getValidateType().equals(ValidateCodeEnum.LOGIN_CODE.getKey())) {
                //登录验证码
                messageService.sendSms(EMessageTemplateBusinessType.LOGIN_CODE, model.getPhone(), sendParams(random));
                HashOperations<String, String, String> redisValidateCode = redisTemplate.opsForHash();
                boolean existHashKey = redisTemplate.hasKey(RedisConstants.REDIS_TWENTY_FOUR_CODE_COUNT);
                if (existHashKey) {
                    redisValidateCode.put(RedisConstants.REDIS_TWENTY_FOUR_CODE_COUNT + model.getPhone(), random, random);
                } else {
                    //不存在 新建
                    redisTemplate.opsForHash().put(RedisConstants.REDIS_TWENTY_FOUR_CODE_COUNT + model.getPhone(), random, random);
                    //设置过期 当天剩余时间
                    redisTemplate.expire(RedisConstants.REDIS_TWENTY_FOUR_CODE_COUNT + model.getPhone(), DateUtil.setValidateExprie(), TimeUnit.SECONDS);
                }
                //缓存验证码 60s
                redisTemplate.opsForValue().set(ValidateCodeEnum.LOGIN_CODE.getKey() + model.getPhone(), random);
                redisTemplate.expire(ValidateCodeEnum.LOGIN_CODE.getKey() + model.getPhone(), 5 * 60, TimeUnit.SECONDS);
                //60s缓存器
                countCache.isRequestOutInterface(ValidateCodeEnum.LOGIN_CODE.getKey() + RedisConstants.INCREMENT_COUNT_STU + model.getPhone(), 60l);
                //设置该用户  验证码缓存器
            } else if (model.getValidateType().equals(ValidateCodeEnum.UPDATE_PWD_CODE.getKey())) {
                //修改密码验证码
                messageService.sendSms(EMessageTemplateBusinessType.UPDATE_PWD_CHECKCODE, model.getPhone(), sendParams(random));
                //缓存验证码 60s
                redisTemplate.opsForValue().set(ValidateCodeEnum.UPDATE_PWD_CODE.getKey() + model.getPhone(), random);
                redisTemplate.expire(ValidateCodeEnum.UPDATE_PWD_CODE.getKey() + model.getPhone(), 60, TimeUnit.SECONDS);
            } else if (model.getValidateType().equals(ValidateCodeEnum.FORGET_PWD_CODE.getKey())) {
                //忘记密码验证码
                messageService.sendSms(EMessageTemplateBusinessType.FINDBACK_LOGIN_PWD, model.getPhone(), sendParams(random));
                //缓存验证码 60s
                redisTemplate.opsForValue().set(ValidateCodeEnum.FORGET_PWD_CODE.getKey() + model.getPhone(), random);
                redisTemplate.expire(ValidateCodeEnum.FORGET_PWD_CODE.getKey() + model.getPhone(), 5 * 60, TimeUnit.SECONDS);
                //60s缓存器
                countCache.isRequestOutInterface(ValidateCodeEnum.FORGET_PWD_CODE.getKey() + RedisConstants.INCREMENT_COUNT_STU + model.getPhone(), 60l);
            }
            responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
            responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
            responseResult.setData(null);
            return responseResult;
        } catch (Exception e) {
            logger.error("系统异常", e);
            responseResult.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }


    /**
     * 封装发送模板
     *
     * @return return_type 返回类型
     * @Title: excuteRemind
     * @Description: TODO()
     */


    public JSONObject sendParams(String random) {
        JSONObject object = new JSONObject();
        object.put("random", random);
        return object;
    }


    /**
     * 封装获取验证码
     *
     * @return return_type 返回类型
     * @Title: excuteRemind
     * @Description: TODO()
     */

    public String getValidateCodeRandom(String codeEnum, String phone) {
        String random = CommonTools.createRandom(true, 4);
        switch (codeEnum) {
            case "LOGIN_CODE": {
                random = redisTemplate.opsForValue().get(ValidateCodeEnum.LOGIN_CODE.getKey() + phone) == null ? random
                        : redisTemplate.opsForValue().get(ValidateCodeEnum.LOGIN_CODE.getKey() + phone).toString();
            }
            break;
            case "FORGET_PWD_CODE": {
                random = redisTemplate.opsForValue().get(ValidateCodeEnum.FORGET_PWD_CODE.getKey() + phone) == null ? random
                        : redisTemplate.opsForValue().get(ValidateCodeEnum.FORGET_PWD_CODE.getKey() + phone).toString();
            }
            break;
        }
        return random;

    }


}
