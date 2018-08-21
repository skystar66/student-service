package com.tengyue360.web.requestModel;

/**
 * 封装积分信息
 *
 * @author panjt
 * @date 2018/8/20 21:37
 */

public class IntegralRequestModel extends BaseRequestModel {


    private String content; //积分内容

    private Integer userId;//学员id

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
