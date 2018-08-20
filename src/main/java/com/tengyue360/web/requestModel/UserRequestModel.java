package com.tengyue360.web.requestModel;

/**
 * 封装用户请求类
 *
 * @author xuliang
 * @date 2018/8/10 12:37
 */

public class UserRequestModel extends BaseRequestModel {


    private String oldPwd;//旧密码
    private String newPwd;//新密码
    private String messageCode;//验证码
    private String userId;//用户ID
    private String phone;//电话
    private String type;//校验类型 0 忘记密码验证码校验

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserRequestModel{" +
                "oldPwd='" + oldPwd + '\'' +
                ", newPwd='" + newPwd + '\'' +
                ", messageCode='" + messageCode + '\'' +
                ", userId='" + userId + '\'' +
                ", phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
