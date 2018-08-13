package com.tengyue360.service;


import java.util.Map;

/**
 * 发送短信
 *
 * @author xuliang
 * @date 2018/8/10 10:03
 */

public interface SmsSenderService {


    /**
     * 发送短信验证码
     *
     * @return
     * @throws Exception
     */

    boolean sendMessage(String account, Map<String, String> keyValue, String content) throws Exception;


}
