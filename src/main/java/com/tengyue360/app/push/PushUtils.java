package com.tengyue360.app.push;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.tengyue360.utils.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * app 小程序推送
 * @author xuliang
 * @date 2018年8月10日 21:04:55
 */
@Component
public class PushUtils {

    private static Logger logger = LoggerFactory.getLogger(PushUtils.class);
    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
    private static String appId = ResourceUtil.getAPP_AppId();
    private static String appKey = ResourceUtil.getAPP_Key();
    private static String masterSecret = ResourceUtil.getAPP_MasterSecret();
    static String cid = ResourceUtil.getAPP_CID();
    static String host = ResourceUtil.getAPP_Host();

    public static Map<String, Object> pushAppMessage(String topic, String context) {
        try {
            logger.info("开始推送消息，topic：{}，context：{}", topic, context);
            IGtPush push = new IGtPush(host, appKey, masterSecret);
            LinkTemplate template = linkTemplateModel(topic, context);
            SingleMessage message = new SingleMessage();
            message.setOffline(true);
            // 离线有效时间，单位为毫秒，可选
            message.setOfflineExpireTime(24 * 3600 * 1000l);
            message.setData(template);
            // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
            message.setPushNetWorkType(0);
            Target target = new Target();
            target.setAppId(appId);
            target.setClientId(cid);
            //target.setAlias(Alias);
            IPushResult ret = null;
            try {
                ret = push.pushMessageToSingle(message, target);
            } catch (RequestException e) {
                logger.error(e.getMessage());
                ret = push.pushMessageToSingle(message, target, e.getRequestId());
            }
            if (ret != null) {
                logger.info("个推成功，topic：{}，context：{},返回信息:{}", topic, context, ret.getResponse().toString());
                return ret.getResponse();
            } else {
                logger.info("个推失败，topic：{}，context：{} 服务器响应异常", topic, context);
                return null;
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static LinkTemplate linkTemplateModel(String topic, String context) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(topic);
        style.setText(context);
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);
        // 设置打开的网址地址
        template.setUrl("http://www.baidu.com");
        return template;
    }
}