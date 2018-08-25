package com.tengyue360.mq.recive;


import com.rabbitmq.client.Channel;
import com.tengyue360.bean.SsMqPushLog;
import com.tengyue360.constant.QueueConstant;
import com.tengyue360.dao.SsMqPushLogMapper;
import com.tengyue360.mq.topic.message.MessageTemplate;
import com.tengyue360.sms.util.SmsUtil;
import com.tengyue360.utils.CommonBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * 接收端 QUEUE_MESSAGE_PUSH_READED_MESSAGE
 * 课程开始前2小时： topic
 *
 * @author xuliang 2018/08/12
 */

@Component
@RabbitListener(queues = QueueConstant.QUEUE_MESSAGE_SEND_COURSE_REMIND)
public class SendRmindQueueRecive {


    private static Logger logger = LoggerFactory.getLogger(SendRmindQueueRecive.class);
    @Autowired
    SsMqPushLogMapper mqPushLogMapper;

    @RabbitHandler
    public void process(@Payload MessageTemplate messageTemplate1, Channel channel, Message message) throws IOException {
        logger.info("队列：{},收到消息：{}", QueueConstant.QUEUE_MESSAGE_SEND_COURSE_REMIND, messageTemplate1);
        try {
            //发送短信
            SmsUtil.sendMessage(messageTemplate1.getPhone(), messageTemplate1.getMessageInfo());
            messageTemplate1.setAcceptTime(new Date());//接收时间
            messageTemplate1.setMqStatus(1);//已处理
            messageTemplate1.setAcceptTime(new Date());//接收时间
            SsMqPushLog pushLog = new SsMqPushLog();
            CommonBeanUtils.copyProperties(messageTemplate1, pushLog);
            mqPushLogMapper.updateByPrimaryKey(pushLog);
            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            logger.info("队列：{}, 消息已被成功处理：{}", messageTemplate1.getMessageQueueName(),messageTemplate1);
        } catch (IOException e) {
            logger.error(e.getMessage());
            //丢弃这条消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            if (message.getMessageProperties().getRedelivered()) {
                logger.info("消息已重复处理失败,拒绝再次接收...");
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true); // 拒绝消息
            } else {
                logger.info("消息即将再次返回队列处理...");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // requeue为是否重新回到队列
            }
            System.out.println("receiver1 fail");
        }
    }


}
