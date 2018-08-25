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
import com.tengyue360.utils.ValidateCodeUtils;
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
    @Autowired
    ValidateCodeUtils codeUtils;

    /**
     * 获取短信验证码
     *
     * @return
     * @throws Exception
     */

    @Override
    public ResponseResult getValidateCode(SendSmsRequestModel model) {
        try {
            //校验手机号是否存在库中
            SsUser user = userMapper.login(model.getPhone());
            if (null != user) {
                if (StringUtils.isNotBlank(user.getDeleteState()) && !user.getDeleteState().equals("1")) {
                    return ResponseResult.onFailure(null, ReturnCode.NAME_EMPTY);
                }
            } else {
                return ResponseResult.onFailure(null, ReturnCode.NAME_EMPTY);
            }
            //获取验证码
            String random = codeUtils.getValidateCode(model.getValidateType(), model.getPhone());
            //发送验证码
            messageService.sendSms(EMessageTemplateBusinessType.LOGIN_CODE, model.getPhone(), sendParams(random));
            return ResponseResult.onSuccess(null,ReturnCode.ACTIVE_SUCCESS);
        } catch (Exception e) {
            logger.error("系统异常", e);
            return ResponseResult.onFailure(null, ReturnCode.ACTIVE_EXCEPTION);
        }
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

}
