package com.tengyue360.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * @author: panjt
 * @Date: 2018/8/14 19:05
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsLesson {

    private Integer id;

    private String name;

    private String addr;


    private Integer state; //课节状态 0 未开始  1 已结束  2 正在上课

    private Integer number;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;//开始时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;//结束时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date realStartTime;//课节的真实开始时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date realEndTime;//课节的真实结束时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(Date realStartTime) {
        this.realStartTime = realStartTime;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
    }
}
