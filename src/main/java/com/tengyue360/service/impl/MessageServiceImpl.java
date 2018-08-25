package com.tengyue360.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tengyue360.constant.Constants;
import com.tengyue360.constant.QueueConstant;
import com.tengyue360.enums.EMessageTemplateBusinessType;
import com.tengyue360.enums.EMqSendType;
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
    public boolean sendSms(EMessageTemplateBusinessType businessType, String phone, JSONObject context) {
        MessageTemplate messageTemplate = new MessageTemplate();
        try {
            messageTemplate = handleMessageParam(businessType, context);
            logger.info("sendSms message=" + messageTemplate + ",businessType=" + businessType
                    + "开始发送短信" + "，phone:" + phone);
            messageTemplate.setPhone(phone);
            mqMessageByTopic.send(messageTemplate);
            return true;
        } catch (Exception e) {
            logger.info("sendSms message=" + messageTemplate + ",businessType=" + businessType
                    + "开始发送失败" + "，phone:" + phone);
            return false;
        }
    }


    /**
     * 推送消息
     *
     * @param businessType
     * @return
     */

    @Override
    public boolean pushMessage(EMessageTemplateBusinessType businessType, JSONObject context) {
        MessageTemplate messageTemplate = new MessageTemplate();
        try {
            messageTemplate = handleMessageParam(businessType, context);
            logger.info("pushMessage message=" + messageTemplate + ",businessType=" + businessType
                    + "开始推送消息,主题：" + messageTemplate.getTopic() + "，内容：" + messageTemplate.getMessageInfo());
            mqMessageByTopic.send(messageTemplate);
            return true;
        } catch (Exception e) {
            logger.info("pushMessage message=" + messageTemplate + ",businessType=" + businessType
                    + "开始推送消息失败,主题：" + messageTemplate.getTopic() + "，内容：" + messageTemplate.getMessageInfo());
            return false;
        }
    }

    /**
     * 根据实际业务类型 选择相应文案模板
     *
     * @return
     * @throws Exception
     */

    private MessageTemplate handleMessageParam(EMessageTemplateBusinessType businessType, JSONObject context)
            throws Exception {
        MessageTemplate messageTemplate = new MessageTemplate();
        String templateMsg = "";
        switch (businessType) {
            case LOGIN_CODE: {
                //登录验证码
                templateMsg = "【腾跃校长在线】" + context.getString("random") + "（登录验证码，请完成验证），如非本人操作，请忽略本短信";
                //验证码登录
                messageTemplate = newMessageTemplate(templateMsg, QueueConstant.QUEUE_MESSAGE_SEND_VALIDATE_CODE, EMqSendType.LOGIN_TYPE_GET_CODE.getCode());
            }
            break;
            case UPDATE_PWD_CHECKCODE: {
                //修改验证码
                templateMsg = "【腾跃校长在线】" + context.getString("random") + "（修改验证码，请完成验证），如非本人操作，请忽略本短信";
                //验证码登录
                messageTemplate = newMessageTemplate(templateMsg, QueueConstant.QUEUE_MESSAGE_SEND_VALIDATE_CODE, EMqSendType.UPDATE_TYPE_GET_CODE.getCode());
            }
            break;
            case FINDBACK_LOGIN_PWD: {
                //忘记密码
                templateMsg = "【腾跃校长在线】" + context.getString("random") + "（找回登录密码验证码）。工作人员不会向您所要，请勿向任何人泄露，以免造成损失";
                //验证码登录
                messageTemplate = newMessageTemplate(templateMsg, QueueConstant.QUEUE_MESSAGE_SEND_VALIDATE_CODE, EMqSendType.FORGET_TYPE_GET_CODE.getCode());
            }
            break;

            case SEND_RUNING_CLASS_READED: {
                //发送上课准备短信
                templateMsg = "【腾跃校长在线】同学好，《" + context.getString("courName") + "》将于明日" + context.getString("startTime") + "开课，请提前做好预习等准备工作，祝你学习愉快～";
                //验证码登录
                messageTemplate = newMessageTemplate(templateMsg, QueueConstant.QUEUE_MESSAGE_SEND_COURSE_READED, EMqSendType.SEND_RUNING_CLASS_READED_ON_EIGHTEEN_CODE.getCode());
                messageTemplate.setTopic("开课准备");
            }
            break;
            case SEND_RUNING_CLASS_REMIND: {
                //发送上课准备短信
                templateMsg = "【腾跃校长在线】同学好，《" + context.getString("courName") + "》将于" + context.getString("startTime") + "开课，请带好必备物品，按时上课，注意安全～";
                messageTemplate = newMessageTemplate(templateMsg, QueueConstant.QUEUE_MESSAGE_SEND_COURSE_REMIND, EMqSendType.SEND_RUNING_CLASS_REMIND_ON_BEFOR_TWO_HOUR_CODE.getCode());
                messageTemplate.setTopic("上课提醒");
            }


            case PUSH_RUNING_CLASS_READED: {
                //推送上课准备短信
                templateMsg = "【腾跃校长在线】同学好，《" + context.getString("courName") + "》将于明日" + context.getString("startTime") + "开课，请提前做好预习等准备工作，祝你学习愉快～";
                messageTemplate = newMessageTemplate(templateMsg, QueueConstant.QUEUE_MESSAGE_PUSH_READED_MESSAGE, EMqSendType.PUSH_RUNING_CLASS_READED_ON_EIGHTEEN_CODE.getCode());
                messageTemplate.setTopic("开课准备");
            }
            break;
            case PUSH_RUNING_CLASS_REMIND: {
                //推送上课准备短信
                templateMsg = "【腾跃校长在线】同学好，《" + context.getString("courName") + "》将于" + context.getString("startTime") + "开课，请带好必备物品，按时上课，注意安全～";
                messageTemplate = newMessageTemplate(templateMsg, QueueConstant.QUEUE_MESSAGE_PUSH_REMIND_MESSAGE, EMqSendType.PUSH_RUNING_CLASS_REMIND_ON_BEFOR_TWO_HOUR_CODE.getCode());
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
        messageTemplate.setMessageQueueName(queueName);
        messageTemplate.setMmessgeType(type);
        messageTemplate.setSendTime(new Date());
        return messageTemplate;
    }


}
