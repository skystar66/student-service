package com.tengyue360.bean;

import java.util.Date;

public class SsMqPushLog {
    private String messageId;

    private String messageInfo;

    private String messageQueueName;

    private String mmessgeType;

    private Integer mqStatus;

    private Date sendTime;

    private Date acceptTime;

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
        this.messageInfo = messageInfo == null ? null : messageInfo.trim();
    }

    public String getMessageQueueName() {
        return messageQueueName;
    }

    public void setMessageQueueName(String messageQueueName) {
        this.messageQueueName = messageQueueName == null ? null : messageQueueName.trim();
    }

    public String getMmessgeType() {
        return mmessgeType;
    }

    public void setMmessgeType(String mmessgeType) {
        this.mmessgeType = mmessgeType == null ? null : mmessgeType.trim();
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

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }
}