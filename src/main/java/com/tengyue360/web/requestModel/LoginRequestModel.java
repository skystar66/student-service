package com.tengyue360.web.requestModel;

/**
 * 封装登录请求参数
 *
 * @author xuliang
 * @date 2018/8/10 12:37
 */

public class LoginRequestModel extends BaseRequestModel {


    private String phone; //电话

    private String password;//密码

    private String messageCode;//短信验证码

    private String deviceId;//设备号

    private String source;//来源

    private String loginType;//登录类型 验证码登录，密码登录

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }


    @Override
    public String toString() {
        return "LoginRequestModel{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", messageCode='" + messageCode + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", source='" + source + '\'' +
                ", loginType='" + loginType + '\'' +
                '}';
    }
}
