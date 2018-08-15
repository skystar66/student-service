package com.tengyue360.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 班级
 * @author: panjt
 * @Date: 2018/8/15 12:53
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsKlass implements Serializable {

    private Integer id;

    private String uid;

    private String name;//班级名称

    private String addr;//班级地址

    private SsCourse ssCourse;//所属课程

    private Integer classType;
    private String sendState;//红包发送成功与否状态

    private SsUser assistant;//所属的助教

    private SsUser teacher;//所属的主讲老师


    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private Date startTime;//开班时间

    @JsonFormat(pattern = "yyyy.MM.dd",timezone = "GMT+8")
    private Date endTime;//结课时间

    private Integer state;//班级状态

    private String msgId;//发送失败时的通信id

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


    public SsCourse getSsCourse() {
        return ssCourse;
    }

    public void setSsCourse(SsCourse ssCourse) {
        this.ssCourse = ssCourse;
    }

    public SsUser getAssistant() {
        return assistant;
    }

    public void setAssistant(SsUser assistant) {
        this.assistant = assistant;
    }

    public SsUser getTeacher() {
        return teacher;
    }

    public void setTeacher(SsUser teacher) {
        this.teacher = teacher;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public Integer getClassType() {
        return classType;
    }

    public void setClassType(Integer classType) {
        this.classType = classType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSendState() {
        return sendState;
    }

    public void setSendState(String sendState) {
        this.sendState = sendState;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "Klass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", ssCourse=" + ssCourse +
                ", assistant=" + assistant +
                ", teacher=" + teacher +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state='" + state + '\'' +
                '}';
    }
}
