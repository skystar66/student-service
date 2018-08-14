package com.tengyue360.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tengyue360.constant.Constants;
import com.tengyue360.constant.QueueConstant;
import com.tengyue360.enums.EMessageTemplateBusinessType;
import com.tengyue360.mq.send.SendMqMessageByTopic;
import com.tengyue360.mq.topic.message.MessageTemplate;
import com.tengyue360.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

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
    SendMqMessageByTopic mqMessageByTopic;


    @Override
    public boolean sendSms(EMessageTemplateBusinessType businessType, String phone, String context) {
        MessageTemplate messageTemplate = null;
        try {
            messageTemplate = handleMessageParam(businessType, context);
            logger.info("sendSms message=" + messageTemplate + ",businessType=" + businessType
                    + "开始发送短信" + "，phone:" + phone);
            mqMessageByTopic.send(messageTemplate, QueueConstant.QUEUE_MESSAGE_SEND_VALIDATE_CODE);
        } catch (Exception e) {
            logger.info("sendSms message=" + messageTemplate + ",businessType=" + businessType
                    + "开始发送失败" + "，phone:" + phone);
            return false;
        }
        if (messageTemplate == null) {
            return false;
        }
        return false;
    }


    /**
     * 根据实际业务类型 选择相应文案模板
     *
     * @return
     * @throws Exception
     */

    private MessageTemplate handleMessageParam(EMessageTemplateBusinessType businessType, String context)
            throws Exception {
        MessageTemplate messageTemplate = new MessageTemplate();
        switch (businessType) {
            case LOGIN_CODE: {
                //登录验证码
                context = "【腾跃校长在线】" + context + "（登录验证码，请完成验证），如非本人操作，请忽略本短信";
                //验证码登录
                messageTemplate = newMessageTemplate(context, QueueConstant.QUEUE_MESSAGE_SEND_VALIDATE_CODE, Constants.LOGIN_TYPE_CODE);
            }
            break;
            case UPDATE_PWD_CHECKCODE: {
                //修改验证码
                context = "【腾跃校长在线】" + context + "（修改验证码，请完成验证），如非本人操作，请忽略本短信";
                //验证码登录
                messageTemplate = newMessageTemplate(context, QueueConstant.QUEUE_MESSAGE_SEND_VALIDATE_CODE, Constants.LOGIN_TYPE_CODE);
            }
            break;
            case FINDBACK_LOGIN_PWD: {
                //忘记密码
                context = "【腾跃校长在线】" + context + "（找回登录密码验证码）。工作人员不会向您所要，请勿向任何人泄露，以免造成损失";
                //验证码登录
                messageTemplate = newMessageTemplate(context, QueueConstant.QUEUE_MESSAGE_SEND_VALIDATE_CODE, Constants.LOGIN_TYPE_CODE);
            }
            break;

            case PUSH_RUNING_CLASS_READED: {
                //推送上课准备消息
                context = "同学好，《" + context + "》将于明日xx：xx开课，请提前做好预习等准备工作，祝你学习愉快～";
                //验证码登录
                messageTemplate = newMessageTemplate(context, QueueConstant.QUEUE_MESSAGE_SEND_VALIDATE_CODE, Constants.LOGIN_TYPE_CODE);
                messageTemplate.setTopic("开课准备");
            }
            break;
            case PUSH_RUNING_CLASS_REMIND: {
                //推送上课提醒消息
                context = "同学好，《" + context + "》将于xx：xx开课，请带好必备物品，按时上课，注意安全～";
                //验证码登录
                messageTemplate = newMessageTemplate(context, QueueConstant.QUEUE_MESSAGE_SEND_VALIDATE_CODE, Constants.LOGIN_TYPE_CODE);
                messageTemplate.setTopic("上课提醒");
            }
            break;

            default:
                logger.error("消息错误");
                return null;
        }
        return messageTemplate;
    }


    /**
     * 封装消息实体类
     *
     * @return
     * @throws Exception
     */

    public static MessageTemplate newMessageTemplate(String context, String queueName, String type) {
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setMessageId(UUID.randomUUID().toString().replace("-", ""));
        messageTemplate.setMessageInfo(context);
        messageTemplate.setMqQueueName(queueName);
        messageTemplate.setMqType(type);
        messageTemplate.setSendTime(new Date());
        return messageTemplate;
    }


}