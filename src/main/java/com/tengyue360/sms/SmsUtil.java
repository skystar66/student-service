package com.tengyue360.sms;

import com.alibaba.fastjson.JSONObject;
import com.tengyue360.service.LoginService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 及时通讯短信发送
 *
 * @author xuliang 2017/06/18
 */
@Component
public class SmsUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    private static String url = "";// 短信接口地址
    //private static String password = ResourceUtil.getPassword_new();// 接口密码
    //private static String username = ResourceUtil.getUsername_new();// 接口用户名

    @Autowired
    LoginService userAccountService;

    public static SmsUtil smsUtil;

    @PostConstruct
    public void init() {
        smsUtil = this;
        smsUtil.userAccountService = this.userAccountService;
    }


    /**
     * 发送短信方法---亿美软通
     *
     * @param mobile    手机号码
     * @param tmpId     模板名称
     * @param reg       模板变量名称
     * @param value     模板变量值
     * @param funcid    业务操作功能ID
     * @param visittype 访问类型值为：0表示为web方式；1为wap方式；2为app方式（ios方式）；3为app方式（android方式）；4为其他方式（app方式中的其他类型）
     * @param smstype   短信类型 有两端短信类型：0表示为一般短信；1表示为营销类短信，2为运维短信
     * @param extcode   扩展码  00：全融信贷 01：凤凰钱包 02：全薪宝
     * @return
     * @throws Exception
     */
    public static boolean sendMessage(String mobile, String tmpId,
                                      String[] reg, String[] value, String funcid, String visittype, String smstype, String extcode) throws Exception {

        Map<String, Object> data = new HashMap<String, Object>();
        /** 短信模板ID */
        data.put("smsmodelid", tmpId);
        /** 接收人手机号，可以有多个手机号 */
        data.put("mobilephone", mobile);
        /** 业务操作功能ID */
        data.put("funcid", funcid);
        /** 访问类型值为：0表示为web方式；1为wap方式；2为app方式（ios方式）；3为app方式（android方式）；4为其他方式（app方式中的其他类型） */
        data.put("visittype", visittype);
        /** 短信类型 有两端短信类型：0表示为一般短信；1表示为营销类短信，2为运维短信 */
        data.put("smstype", smstype);
        /** 模板中需要替换的变量 */
        data.put("reg", reg);
        /** 模板中需要替换的变量的值 */
        data.put("value", value);
        /** 模板中需要替换的变量的值 */
        data.put("extcode", extcode);
        String result = "";
        JSONObject jb = (JSONObject) JSONObject.toJSON(data);
        try {
            result = SmsSmsHttpSend.postSend(url, jb.toString());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!"0".equals(result)) {
            result = SmsSmsHttpSend.postSend(url, jb.toString());
            if (!"0".equals(result)) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }

    }

    /**
     * 发送短信方法---亿美软通
     *
     * @param mobile    手机号码
     * @param content   模板内容
     * @param visittype 访问类型值为：0表示为web方式；1为wap方式；2为app方式（ios方式）；3为app方式（android方式）；4为其他方式（app方式中的其他类型）
     * @param smstype   短信类型 有两端短信类型：0表示为一般短信；1表示为营销类短信，2为运维短信
     * @param extcode   扩展码  产品名称
     * @param bussid    业务类型ID 用于区分发送的短信属于哪个平台（）
     * @param url
     * @return
     * @throws Exception
     */
    public static boolean sendMessage(String mobile, String content, String visittype, String smstype, String extcode, String bussid, String url) throws Exception {

        Map<String, Object> data = new HashMap<String, Object>();
        /** 接收人手机号，可以有多个手机号 */
        data.put("mobilePhone", mobile);
        /** 访问类型值为：0表示为web方式；1为wap方式；2为app方式（ios方式）；3为app方式（android方式）；4为其他方式（app方式中的其他类型） */
        data.put("visittype", visittype);
        /** 短信类型 有两端短信类型：0表示为一般短信；1表示为营销类短信，2为运维短信 */
        data.put("smstype", smstype);
        /** 模板中需要替换的变量 */
        data.put("sendContent", content);
        /** 模板中需要替换的变量的值 */
        data.put("extcode", extcode);
        data.put("bussid", bussid);
        String result = "";
        JSONObject jb = (JSONObject) JSONObject.toJSON(data);
        try {
            result = SmsSmsHttpSend.postSend(url, jb.toString());
            logger.info("发送短信mobile=" + mobile + ",content=" + content + ",result=" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!"0".equals(result)) {
            result = SmsSmsHttpSend.postSend(url, jb.toString());
            if (!"0".equals(result)) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }

    }

    /**
     * 发送短信方法
     *
     * @param mobile  手机号码
     * @param tmpId 模板名称
     * @param reg 模板变量名称
     * @param value 模板变量值
     * @return
     * @throws Exception
     */
/*	public static boolean sendMessage(String mobile, String tmpId,
			String[] reg, String[] value) throws Exception {
		String method = "SendSms";
		Service service = new Service();

		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(url));
		call.setUseSOAPAction(true);
		call.setReturnType(XMLType.SOAP_STRING);
		call.setOperationName(new QName("http://www.vcomcn.co/", method));
		call.setSOAPActionURI("http://www.vcomcn.co/SendSms");

		call.addParameter(new QName("http://www.vcomcn.co/", "login_name"),
				XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName("http://www.vcomcn.co/", "password"),
				XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName("http://www.vcomcn.co/", "mobile"),
				XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName("http://www.vcomcn.co/", "message"),
				XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName("http://www.vcomcn.co/", "start_time"),
				XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName("http://www.vcomcn.co/", "Search_ID"),
				XMLType.XSD_STRING, ParameterMode.IN);
		String content = dealMsgTmp(tmpId, reg, value);
		System.out.println(DateUtil.getNowDateTime());
		String retVal = (String) call.invoke(new Object[] { username, password,
				mobile, content, null, "-1" });
		if (!"504".equals(retVal)) {
			System.out.println(retVal);
			return false;
		}
		return true;
	}*/

//		public static boolean sendMessageByUserId(String userId, String tmpId,
//				String[] reg, String[] value, String funcid, String visittype, String smstype,String extcode){
//			boolean flag = true;
//			String mobile="";
//			Useraccount ua=smsUtil.userAccountService.selectByPrimaryKey(userId);
//			if(ua!=null){
//				mobile=ua.getTelephone();
//			}
//			if(!"".equals(mobile)){
//				logger.info("手机号为"+mobile);
//				logger.info("开始时间："+DateUtil.getNowDateTime());
//				try {
//					flag = sendMessage(mobile, tmpId, reg, value, funcid, visittype, smstype,extcode);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}else{
//				logger.info("手机号为空");
//				flag = false;
//			}
//
//			return flag;
//		}
    /**
     * 发送短信方法(通过用户id查询用户手机号)
     *
     * @param userId  用户id
     * @param tmpId 模板名称
     * @param reg 模板变量名称
     * @param value 模板变量值
     * @return
     * @throws Exception
     */
	/*public static boolean sendMessageByUserId(String userId, String tmpId,
			String[] reg, String[] value) throws Exception {
		String mobile="";
		Useraccount ua=smsUtil.userAccountService.selectByPrimaryKey(userId);
		if(ua!=null){
			mobile=ua.getTelephone();
		}
		if(null!=mobile){
			logger.info("手机号为"+mobile);
		}else{
			logger.info("手机号为空");
		}
		logger.info("开始时间："+DateUtil.getNowDateTime());
		String method = "SendSms";
		Service service = new Service();
		
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(url));
		call.setUseSOAPAction(true);
		call.setReturnType(XMLType.SOAP_STRING);
		call.setOperationName(new QName("http://www.vcomcn.co/", method));
		call.setSOAPActionURI("http://www.vcomcn.co/SendSms");
		
		call.addParameter(new QName("http://www.vcomcn.co/", "login_name"),
				XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName("http://www.vcomcn.co/", "password"),
				XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName("http://www.vcomcn.co/", "mobile"),
				XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName("http://www.vcomcn.co/", "message"),
				XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName("http://www.vcomcn.co/", "start_time"),
				XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter(new QName("http://www.vcomcn.co/", "Search_ID"),
				XMLType.XSD_STRING, ParameterMode.IN);
		String content = dealMsgTmp(tmpId, reg, value);
		String retVal = (String) call.invoke(new Object[] { username, password,
				mobile, content, null, "-1" });
		logger.info("结束时间："+DateUtil.getNowDateTime());
		if (!"504".equals(retVal)) {
			logger.error(retVal);
			return false;
		}
		return true;
	}*/


    /**
     * 处理短信模板
     *
     * @param tmpid 邮件模板id
     * @param reg   关键字
     * @param value 关键字值
     * @return
     */
//    public static String dealMsgTmp(String tmpid, String reg[], String value[]) {
//        String tempContent = ResourceUtil.getByConfig(tmpid);
//        tempContent = UnicodeUtil.ascii2Native(tempContent);// unicode编码转换成中文
//        String result = tempContent;
//        if (tempContent != null) {
//            for (int i = 0; i < reg.length; i++) {
//                if (tempContent.indexOf(reg[i]) != -1) {
//                    result = result.replace(reg[i], value[i]);
//                }
//            }
//        }
//        System.out.println(result);
//        return result;
//    }

}
