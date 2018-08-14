package com.tengyue360.web.requestModel;

import java.util.Date;

/**
 * 封装积分model
 * @author panjt
 * @date 2018/8/14 下午10:27
 */
public class IntegralRequestModel  extends BaseRequestModel{
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
}
