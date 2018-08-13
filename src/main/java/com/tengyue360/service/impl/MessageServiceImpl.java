package com.tengyue360.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.tengyue360.constant.RedisConstants;
import com.tengyue360.enums.EMessageTemplateBusinessType;
import com.tengyue360.enums.EMessageTemplateType;
import com.tengyue360.mns.IMessage;
import com.tengyue360.mns.MessageTemplate;
import com.tengyue360.mns.queue.CheckCodeMessage;
import com.tengyue360.service.MessageService;
import com.tengyue360.service.SmsSenderService;
import com.tengyue360.utils.BaseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息通知
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */
@Service
public class MessageServiceImpl implements MessageService {


    private static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    SmsSenderService smsSenderService;


    @Override
    public boolean sendSms(String account, IMessage iMessage, EMessageTemplateBusinessType businessType) {

        String messageJson = JSON.toJSONString(iMessage);
        logger.info("sendSms account=" + account + ", message=" + messageJson + ",businessType=" + businessType);
        Map<String, Object> paramMap = null;
        ;
        try {
            paramMap = handleMessageParam(messageJson, businessType);
        } catch (Exception e) {
            logger.error("sendSms account=" + account + ", message=" + messageJson + ",businessType=" + businessType
                    + "发送短信失败:" + e.getMessage(), e);
            return false;
        }
        if (paramMap == null) {
            return false;
        }
        Map<String, String> keyValue = (Map) paramMap.get("keyValue");

        boolean checkTemplate = redisTemplate.hasKey(RedisConstants.MESSAGE_TEMPLATE);


        if (checkTemplate) {
            String messageTemplateJson = redisTemplate.opsForHash()
                    .get(RedisConstants.MESSAGE_TEMPLATE, businessType.getKey()) == null ? "" : redisTemplate.opsForHash()
                    .get(RedisConstants.MESSAGE_TEMPLATE, businessType.getKey()).toString();
            if (StringUtils.isBlank(messageTemplateJson)) {
                logger.error("sendSms account=" + account + ", message=" + messageJson + ",businessType=" + businessType
                        + "发送短信失败:缺少模板数据");
                return false;
            }
            MessageTemplate messageTemplate = new Gson().fromJson(messageTemplateJson, MessageTemplate.class);
            try {
                return smsSenderService.sendMessage(account, keyValue, messageTemplate.getMessage());
            } catch (Exception e) {
                logger.error("发送短信account=" + account + ",keyValue=" + keyValue == null ? null
                        : JSON.toJSON(keyValue) + ",messageTemplateJson=" + messageTemplateJson + "," + e.getMessage());
                return false;
            }
        } else {
            logger.info("sendSms account=" + account + ", message=" + messageJson + ",businessType=" + businessType
                    + "发送短信失败:redis中模板为空，key值：" + RedisConstants.MESSAGE_TEMPLATE + businessType.getKey());
        }
        return false;
    }


    private Map<String, Object> handleMessageParam(String messageJson, EMessageTemplateBusinessType businessType)
            throws Exception {
        // 公用参数
        String account = null;
        String userId = null;
        Map<String, String> keyValue = new HashMap<String, String>();
        switch (businessType) {
            case LOGIN_PWD_CHECKCODE: {
                CheckCodeMessage message = new Gson().fromJson(messageJson, CheckCodeMessage.class);
                account = message.getAccount();
                keyValue.put("time", message.getTime());
                keyValue.put("date", BaseUtil.getDateStringYmd(BaseUtil.getDate19(message.getTime())));
                keyValue.put("check_code", message.getCheckCode());
            }
            break;

            default:
                logger.error(messageJson + "消息错误");
                return null;
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("account", account);
//        paramMap.put("logType", logType);
        paramMap.put("keyValue", keyValue);
        return paramMap;
    }


}
