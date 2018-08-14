package com.tengyue360.mq.topic.message;

import org.springframework.amqp.rabbit.support.CorrelationData;

import java.util.Date;

/**
 * 短信消息模板
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */

public class MessageTemplate extends CorrelationData {

    private String messageId; // 消息id（自动生成）
    private String messageInfo; // 消息信息
    private String mqQueueName; // 队列名称
    private String mqType; //消息类型
    private Integer mqStatus;
    private Date sendTime;
    private Date messageAcceptTime; // 消息接收时间;

    private String topic;//消息主题

    private String phone;//电话


    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }

    public String getMqQueueName() {
        return mqQueueName;
    }

    public void setMqQueueName(String mqQueueName) {
        this.mqQueueName = mqQueueName;
    }

    public String getMqType() {
        return mqType;
    }

    public void setMqType(String mqType) {
        this.mqType = mqType;
    }

    public Integer getMqStatus() {
        return mqStatus;
    }

    public void setMqStatus(Integer mqStatus) {
        this.mqStatus = mqStatus;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getMessageAcceptTime() {
        return messageAcceptTime;
    }

    public void setMessageAcceptTime(Date messageAcceptTime) {
        this.messageAcceptTime = messageAcceptTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
