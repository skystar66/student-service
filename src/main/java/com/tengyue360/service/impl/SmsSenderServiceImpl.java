package com.tengyue360.service.impl;

import com.tengyue360.service.SmsSenderService;
import com.tengyue360.sms.SmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 发送短信
 *
 * @author xuliang
 * @date 2018/8/10 10:03
 */
@Service
public class SmsSenderServiceImpl implements SmsSenderService {

    private static Logger logger = LoggerFactory.getLogger(SmsSenderServiceImpl.class);


    /**
     * 获取短信验证码
     *
     * @return
     * @throws Exception
     */

    @Override
    public boolean sendMessage(String account, Map<String, String> keyValue, String content) throws Exception {
        if (keyValue != null) {
            for (String key : keyValue.keySet()) {
                String value = keyValue.get(key);
                if (value != null) {
                    content = content.replace("${" + key + "}", value);
                }
            }
        }
		boolean flag = SmsUtil.sendMessage(account, content, null, null, null, "da8d560ef71244b7ae1e4f47ff9408ec", " http://172.16.1.215/phoenix/webService/sendMessage");
//        boolean flag = SmsUtil.sendMessage(account, content, visitType, smsType, extcode, businessId, url);

        if (flag) {
            logger.info("短信发送成功: account=" + account + ", content=" + content);
        } else {
            logger.info("短信发送失败: account=" + account + ", content=" + content);
        }
        return flag;
    }
}
