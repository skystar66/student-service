package com.tengyue360.sms.util;


import com.alibaba.fastjson.JSON;
import com.tengyue360.sms.SmsHttpSend;
import com.tengyue360.sms.request.SmsSendRequest;
import com.tengyue360.sms.response.SmsSendResponse;
import com.tengyue360.sms.response.SmsVariableResponse;
import com.tengyue360.utils.ResourceUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 及时通讯短信发送
 *
 * @author xuliang 2017/06/18
 */
@Component
public class SmsUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    private static String url = ResourceUtil.getMessage_Url();// 短信接口地址
    private static String password = ResourceUtil.getMessage_Pwd();// 接口密码
    private static String username = ResourceUtil.getMessage_Account();// 接口用户名

    private static String yXpassword = ResourceUtil.getMessage_YX_Pwd();// 接口密码
    private static String yXusername = ResourceUtil.getMessage_YX_Account();// 接口用户名


    /**
     * 发送短信验证码方法---253
     *
     * @param mobile  手机号码
     * @param context 内容
     * @return
     * @throws Exception
     */
    public static boolean sendMessage(String mobile, String context) {
        SmsSendRequest smsVariableRequest = new SmsSendRequest(username, password, context, mobile, "true");
        String requestJson = JSON.toJSONString(smsVariableRequest);
        String responseResult = "";
        try {
            responseResult = SmsHttpSend.sendSmsByPost(url, requestJson);
            SmsVariableResponse smsVariableResponse = JSON.parseObject(responseResult, SmsVariableResponse.class);
            logger.info("手机号：{},短信【{}】发送成功,返回结果：{}", mobile, context, smsVariableResponse);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("手机号：{},短信【{}】发送失败,返回结果：{}", mobile, context, responseResult);
        }
        return false;
    }



    /**
     * 发送短信验证码方法---253
     *
     * @param mobile  手机号码
     * @param context 内容
     * @return
     * @throws Exception
     */
    public static boolean sendYxMessage(String mobile, String context) {
        SmsSendRequest smsVariableRequest = new SmsSendRequest(yXusername, yXpassword, context, mobile, "true");
        String requestJson = JSON.toJSONString(smsVariableRequest);
        String responseResult = "";
        try {
            responseResult = SmsHttpSend.sendSmsByPost(url, requestJson);
            SmsSendResponse smsVariableResponse = JSON.parseObject(responseResult, SmsSendResponse.class);
            logger.info("手机号：{},短信【{}】发送成功,返回结果：{}", mobile, context, smsVariableResponse);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("手机号：{},短信【{}】发送失败,返回结果：{}", mobile, context, responseResult);
        }
        return false;
    }



    public static void main(String[] args) throws Exception {

        sendMessage("17611210230", "【腾跃校长在线】6790（登录验证码，请完成验证），如非本人操作，请忽略本短信");


    }


}
