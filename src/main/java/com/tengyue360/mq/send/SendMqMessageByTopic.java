package com.tengyue360.mq.send;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送mq to server
 *
 * @author xuliang 2018/08/12
 */
@Service
public class SendMqMessageByTopic implements RabbitTemplate.ReturnCallback {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String content,String routingKey) {
        System.out.println("HelloSender发送内容 : " + content);
//        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println("HelloSender消息发送失败" + cause + correlationData.toString());
            } else {
                System.out.println("HelloSender 消息发送成功 ");
            }
        });
        this.rabbitTemplate.convertAndSend("exchange",routingKey, content);
    }

//
//    @Autowired
//    private RabbitTemplate rabbitTemplate2;
//
//
//
//    public void send2() {
//        String context = "你好现在是 " + new Date() + "";
//        System.out.println("HelloSender2发送内容 : " + context);
////        this.rabbitTemplate.setConfirmCallback(this);
//        this.rabbitTemplate2.setReturnCallback(this);
//        this.rabbitTemplate2.setConfirmCallback((correlationData, ack, cause) -> {
//            if (!ack) {
//                System.out.println("HelloSender2消息发送失败" + cause + correlationData.toString());
//            } else {
//                System.out.println("HelloSender2 消息发送成功 ");
//            }
//        });
//        this.rabbitTemplate2.convertAndSend("exchange","topic.message2", context);
//    }


    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("sender return success" + message.toString() + "===" + i + "===" + s1 + "===" + s2);
    }
}
