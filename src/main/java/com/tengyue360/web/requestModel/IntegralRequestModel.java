package com.tengyue360.web.requestModel;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import java.util.Date;

/**
 * 封装积分信息
 *
 * @author panjt
 * @date 2018/8/20 21:37
 */

public class IntegralRequestModel extends BaseRequestModel {


    private String content; //积分内容
    private String name;
    private String value;
    private Date startTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "IntegralRequestModel{" +
                "content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
