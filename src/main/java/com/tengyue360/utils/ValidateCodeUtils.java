package com.tengyue360.utils;

import com.tengyue360.cache.ICountCache;
import com.tengyue360.constant.ExpireTimeConstants;
import com.tengyue360.constant.RedisConstants;
import com.tengyue360.enums.ValidateCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ValidateCodeUtils {


    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ICountCache countCache;

    /**
     * 获取验证码 并根据类型缓存其验证码
     *
     * @return
     * @throws Exception
     */
    public String getValidateCode(ValidateCodeEnum codeEnum, String phone) {
        //获取验证码 缓存
        String random = redisTemplate.opsForValue().get(codeEnum.getKey() + phone) == null ? CommonTools.createRandom(true, 4)
                : redisTemplate.opsForValue().get(codeEnum.getKey() + phone).toString();
        switch (codeEnum) {
            case LOGIN_CODE: {
                //=================================================登录验证码=================================================
                HashOperations<String, String, String> redisValidateCode = redisTemplate.opsForHash();
                boolean existHashKey = redisTemplate.hasKey(RedisConstants.REDIS_TWENTY_FOUR_CODE_COUNT + phone);
                if (existHashKey) {
                    long count = redisValidateCode.size(RedisConstants.REDIS_TWENTY_FOUR_CODE_COUNT + phone);
                    redisValidateCode.put(RedisConstants.REDIS_TWENTY_FOUR_CODE_COUNT + phone, String.valueOf(count + Long.valueOf(1)), random);
                } else {
                    //不存在 新建
                    redisTemplate.opsForHash().put(RedisConstants.REDIS_TWENTY_FOUR_CODE_COUNT + phone, "0", random);
                    //设置过期 当天剩余时间 5次校验
                    redisTemplate.expire(RedisConstants.REDIS_TWENTY_FOUR_CODE_COUNT + phone, DateUtil.setValidateExprie(), TimeUnit.SECONDS);
                }
                boolean existLogKey = redisTemplate.hasKey(ValidateCodeEnum.LOGIN_CODE.getKey() + phone);
                if (!existLogKey) {
                    //缓存验证码 5分钟
                    redisTemplate.opsForValue().set(ValidateCodeEnum.LOGIN_CODE.getKey() + phone, random);
                    redisTemplate.expire(ValidateCodeEnum.LOGIN_CODE.getKey() + phone, ExpireTimeConstants.VALIDATE_CODE_EXPIRE_TIME, TimeUnit.SECONDS);
                }
                //60s该手机号 缓存、期
                countCache.isRequestOutInterface(ValidateCodeEnum.LOGIN_CODE.getKey() + RedisConstants.INCREMENT_COUNT_STU + phone, ExpireTimeConstants.CHECK_VALIDATE_CODE_EXPIRE_TIME);
            }
            break;
            case FORGET_PWD_CODE: {
                //=================================================忘记验证码=================================================
                boolean existForgKey = redisTemplate.hasKey(ValidateCodeEnum.FORGET_PWD_CODE.getKey() + phone);
                if (!existForgKey) {
                    //缓存验证码 60s
                    redisTemplate.opsForValue().set(ValidateCodeEnum.FORGET_PWD_CODE.getKey() + phone, random);
                    redisTemplate.expire(ValidateCodeEnum.FORGET_PWD_CODE.getKey() + phone, ExpireTimeConstants.VALIDATE_CODE_EXPIRE_TIME, TimeUnit.SECONDS);
                }
                //60s缓存器
                countCache.isRequestOutInterface(ValidateCodeEnum.FORGET_PWD_CODE.getKey() + RedisConstants.INCREMENT_COUNT_STU + phone, ExpireTimeConstants.CHECK_VALIDATE_CODE_EXPIRE_TIME);
            }
            break;
            case UPDATE_PWD_CODE: {
                //=================================================修改验证码=================================================
                boolean existUpdKey = redisTemplate.hasKey(ValidateCodeEnum.UPDATE_PWD_CODE.getKey() + phone);
                if (!existUpdKey) {
                    //缓存验证码 60s
                    redisTemplate.opsForValue().set(ValidateCodeEnum.UPDATE_PWD_CODE.getKey() + phone, random);
                    redisTemplate.expire(ValidateCodeEnum.UPDATE_PWD_CODE.getKey() + phone, ExpireTimeConstants.VALIDATE_CODE_EXPIRE_TIME, TimeUnit.SECONDS);
                }
                //60s缓存器
                countCache.isRequestOutInterface(ValidateCodeEnum.UPDATE_PWD_CODE.getKey() + RedisConstants.INCREMENT_COUNT_STU + phone, ExpireTimeConstants.CHECK_VALIDATE_CODE_EXPIRE_TIME);

            }
            break;
        }
        return random;
    }


}
