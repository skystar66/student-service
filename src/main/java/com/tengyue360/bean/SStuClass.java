package com.tengyue360.bean;

import java.util.Date;

public class SStuClass extends SStuClassKey {
    private Integer state;

    private Date updateTime;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}