package com.tengyue360.web.BeanValidators;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Constant;
import com.sun.org.apache.regexp.internal.RE;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.constant.Constants;
import com.tengyue360.constant.RedisConstants;
import com.tengyue360.utils.DateUtil;
import com.tengyue360.web.requestModel.BaseRequestModel;
import com.tengyue360.web.requestModel.LoginRequestModel;
import com.tengyue360.web.requestModel.SendSmsRequestModel;
import com.tengyue360.web.requestModel.UserRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 接口校验类
 * @date 2018年8月08日 20:44:55
 */
public class BeanValidators {


    private static Logger logger = LoggerFactory.getLogger(BeanValidators.class);

    private static RedisOperations<String, Object> redisOperations;//redis 获取值对象
    private static HashOperations<String, String, String> reHashOperations; //redis 获取hash只对象

    /**
     * 基础校验
     *
     * @param requestParams
     * @return
     */

    public static final ResponseResult isBaseValidate(BaseRequestModel requestParams) {

        if (null == requestParams) {
            //参数不能为空
            return new ResponseResult(ReturnCode.ERROR_PARAMS_NOT_NULL.code(), ReturnCode.ERROR_PARAMS_NOT_NULL.msg(), null);
        }

        ResponseResult responseResult = new ResponseResult();
        long diff = Math.abs(new Date().getTime() - requestParams.getTimestamp());
        if (Constants.REQUESTVALIDITYPERIOD < diff) {
            logger.info("请求失效：timestamp diff=" + diff + ">" + Constants.REQUESTVALIDITYPERIOD);
            responseResult.setCode(ReturnCode.REQUEST_FAILURE.code());
            responseResult.setMsg(ReturnCode.REQUEST_FAILURE.msg());
        }
        return responseResult;

    }

    /**
     * 登录 输入参数校验
     *
     * @param model
     * @return
     */

    public static final ResponseResult isValidateLogin(LoginRequestModel model, RedisTemplate redisTemplate) {
        //基础校验
        if (null != isBaseValidate(model)) {
            return isBaseValidate(model);
        }
        redisOperations = redisTemplate.opsForValue().getOperations();
        if (StringUtils.isBlank(model.getPhone())) {
            //电话不能不能为空
            return new ResponseResult(ReturnCode.NAME_EMPTY.code(), ReturnCode.NAME_EMPTY.msg(), null);
        } else if (StringUtils.isBlank(model.getPassword())) {
            //密码不能不能为空
            return new ResponseResult(ReturnCode.PASSWORD_EMPTY.code(), ReturnCode.PASSWORD_EMPTY.msg(), null);
        } else if (model.getLoginType().equals(Constants.LOGIN_TYPE_CODE)) {
            if (StringUtils.isBlank(model.getMessageCode())) {
                //验证码不能为空
                return new ResponseResult(ReturnCode.VALIDATE_CODE_FALSE.code(), ReturnCode.VALIDATE_CODE_FALSE.msg(), null);
            }
            //校验缓存 验证码是否相同


        }
        return null;
    }


    /**
     * 获取验证码采纳数校验
     *
     * @param model
     * @return
     */

    public static final ResponseResult isValidateLoginSms(SendSmsRequestModel model, RedisTemplate redisTemplate) {
        //基础校验
        if (null != isBaseValidate(model)) {
            return isBaseValidate(model);
        }
        if (StringUtils.isBlank(model.getPhone())) {
            //用户名不能为空
            return new ResponseResult(ReturnCode.NAME_EMPTY.code(), ReturnCode.NAME_EMPTY.msg(), null);
        } else if (StringUtils.isBlank(model.getOperateType())
                || RedisConstants.GET_MESSAGE_CODE_LOGIN_OPERATE_TYPE.equals(model.getOperateType())) {
            //验证码操作类型不正确不能为空
            return new ResponseResult(ReturnCode.ERROR_GET_CODE.code(), ReturnCode.ERROR_GET_CODE.msg(), null);
        }
        //校验 24h 之内 只能获取五次验证码
        boolean existHashKey = redisTemplate.hasKey(RedisConstants.REDIS_TWENTY_FOUR_CODE_COUNT + model.getPhone());
        if (existHashKey) {
            int count = redisTemplate.opsForHash().keys(RedisConstants.REDIS_TWENTY_FOUR_CODE_COUNT).size();
            if (count >= 5) {
                return new ResponseResult(ReturnCode.ERROR_GET_LOGIN_CODE_COUNT.code(), ReturnCode.ERROR_GET_LOGIN_CODE_COUNT.msg(), null);
            }
        }
        //校验一个手机号 只能间隔60s才会获取第二次
        String loginCodeKey = model.getPhone() + "_" + model.getOperateType();
        if (null != redisTemplate.opsForValue().get(loginCodeKey)) {
            return new ResponseResult(ReturnCode.ERROR_AGIN_COUNT.code(), ReturnCode.ERROR_AGIN_COUNT.msg(), null);
        }
        return null;
    }

    /**
     * 修改密码 输入参数校验
     *
     * @param model
     * @return
     */

    public static ResponseResult isValidateUpdatePwd(UserRequestModel model) {
        //基础校验
        if (null != isBaseValidate(model)) {
            return isBaseValidate(model);
        }

        if (StringUtils.isBlank(model.getMessageCode())) {
            //验证码不能为空
            return new ResponseResult(ReturnCode.VALIDAT_CODE_EMPTY.code(), ReturnCode.VALIDAT_CODE_EMPTY.msg(), null);
        } else if (StringUtils.isBlank(model.getOldPwd()) || StringUtils.isBlank(model.getNewPwd())) {
            //原始/新密码不能不能为空
            return new ResponseResult(ReturnCode.PASSWORD_EMPTY.code(), ReturnCode.PASSWORD_EMPTY.msg(), null);
        } else if (StringUtils.isBlank(model.getMessageCode())
                || !model.getMessageCode().equals(Constants.LOGIN_TYPE_CODE)) {
            if (StringUtils.isBlank(model.getMessageCode())) {
                //验证码不能为空  缓存期校验
                return new ResponseResult(ReturnCode.VALIDATE_CODE_FALSE.code(), ReturnCode.VALIDATE_CODE_FALSE.msg(), null);
            }

        }
        return null;
    }


    /**
     * 忘记密码 输入参数校验
     *
     * @param model
     * @return
     */

    public static ResponseResult isValidateBackPwd(UserRequestModel model) {
        //基础校验
        if (null != isBaseValidate(model)) {
            return isBaseValidate(model);
        }
        if (StringUtils.isBlank(model.getMessageCode())) {
            //验证码不能为空
            return new ResponseResult(ReturnCode.VALIDAT_CODE_EMPTY.code(), ReturnCode.VALIDAT_CODE_EMPTY.msg(), null);
        } else if (StringUtils.isBlank(model.getNewPwd())) {
            //原始/新密码不能不能为空
            return new ResponseResult(ReturnCode.PASSWORD_EMPTY.code(), ReturnCode.PASSWORD_EMPTY.msg(), null);
        } else if (StringUtils.isBlank(model.getMessageCode())
                || !model.getMessageCode().equals(Constants.LOGIN_TYPE_CODE)) {
            if (StringUtils.isBlank(model.getMessageCode())) {
                //验证码不能为空 缓存期校验
                return new ResponseResult(ReturnCode.VALIDATE_CODE_FALSE.code(), ReturnCode.VALIDATE_CODE_FALSE.msg(), null);
            }

        }
        return null;
    }


//    /**
//     * 访问方法频率控制在指定秒内
//     *
//     * @param model用于redis命名的模块名
//     * @param method用于redis命名的方法名
//     * @param userId用于redis命名的用户id
//     * @param sec控制在秒内（单位秒）
//     * @return true为可通过；false不通过
//     */
//    public static boolean compareTimesOfAccess2(String model, String method, String userId, long sec) {
//        logger.info("========控制时间=========" + sec);
//        //是否通过
//        boolean isThrough = true;
//        if (sec == 0) {//如果是0则不控制时间间隔
//            return isThrough;
//        }
//        String timeNow = String.valueOf(System.currentTimeMillis());
//        //上次时间
//        String timeBef = RedisAPI.getsetKey2compareTime(model + method + userId, timeNow);
//        //现在时间
//        if (!StringUtils.isEmpty(timeBef)) {
//            if (Long.valueOf(timeNow) - Long.valueOf(timeBef) < sec * 1000) {
//                isThrough = false;
//            }
//        }
//        //若存在则更新时间戳，若不存在则添加时间戳
//        return isThrough;
//    }


//    /**
//     * 登录缓存期缓存器校验
//     *
//     * @param jsonParam
//     * @return
//     */
//
//    public static final ResponseResult isValidateLoginCache(JSONObject jsonParam, RedisTemplate redisTemplate) {
//
//        //用户登录次数key
//        String loginCountKey = Constants.REDIS_LOGINERROR_COUNT_ + jsonParam.getString("userName");
//        redisOperations = redisTemplate.opsForValue().getOperations();
//        //登录锁定时间
//        String loginLockTime = Constants.REDIS_LOGINERROR_TIME_ + jsonParam.getString("userName");
//        //错误登陆次数
//        int redisVal = redisOperations.opsForValue().get(loginCountKey) == null ? 0
//                : Integer.parseInt(redisOperations.opsForValue().get(loginCountKey).toString());
//        //当冻结时间失效，并且错误次数已经超过5次时 将删除redis 次数缓存期缓存信息
//        if (redisOperations.opsForValue().get(loginLockTime) == null && redisVal >= 5) {
//            redisOperations.delete(loginCountKey);
//        }
//        //用户输入次数大于5次  提醒前端  让用户过多长时间恢复
//        if (redisOperations.opsForValue().get(loginLockTime) != null && redisVal >= 5) {
//            //fix 用户密码输入错误超过5次，账户被冻结后，提示在多久时间后恢复
//            long twoHourTime = 1000 * 60 * 60 * 2;
//            String dis = DateUtil.dateDiffHourAndMin(Long.valueOf(System.currentTimeMillis()),
//                    Long.valueOf(redisOperations.opsForValue().get(loginLockTime).toString()) + twoHourTime);
//            return new ResponseResult(ReturnCode.DISABLE_USER.code(), "账号被冻结，" + dis + "后恢复", null);
//        }
//        return null;
//    }

//    /**
//     * 判断用户是否输入错误次数达到五次 设置冻结时间
//     *
//     * @param jsonParam
//     * @return
//     */
//
//    public static final ResponseResult isValidateLopckTime(JSONObject jsonParam, RedisTemplate redisTemplate) {
//        //用户登录次数key
//        String loginCountKey = Constants.REDIS_LOGINERROR_COUNT_ + jsonParam.getString("userName");
//        //登录锁定时间
//        String loginLockTime = Constants.REDIS_LOGINERROR_TIME_ + jsonParam.getString("userName");
//        //错误登陆次数
//        int redisVal = redisTemplate.opsForValue().getOperations().opsForValue().get(loginCountKey) == null ? 0
//                : Integer.parseInt(redisTemplate.opsForValue().getOperations().opsForValue().get(loginCountKey).toString());
//        //如果用户输入错误次数 大于5次时
//        if (redisVal >= 5) {
//            redisTemplate.opsForValue().set(loginLockTime, String.valueOf(System.currentTimeMillis()));
//            //设置冻结失效时间
//            redisTemplate.expire(loginLockTime, 2 * 60 * 60, TimeUnit.SECONDS);
//            return new ResponseResult(ReturnCode.UN_ENABLE.code(), ReturnCode.UN_ENABLE.msg(), null);
//        }
//        return null;
//    }


}
