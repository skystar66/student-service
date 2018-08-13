package com.tengyue360.service;


import com.tengyue360.enums.EMessageTemplateBusinessType;
import com.tengyue360.mns.IMessage;

/**
 * 消息通知
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */
public interface MessageService {


    /**
     * 发送短信
     *
     * @param account
     * @param account
     * @param businessType
     * @return
     */
    boolean sendSms(String account, IMessage iMessage, EMessageTemplateBusinessType businessType);


}
