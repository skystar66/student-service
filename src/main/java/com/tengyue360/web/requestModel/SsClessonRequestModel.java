package com.tengyue360.web.requestModel;

import java.util.Date;

public class SsClessonRequestModel {
    private Integer id;

    private String name;

    private String addr;

    private Integer courseId;

    private Integer classId;

    private Integer number;

    private String state;

    private Date realStartTime;

    private Date realEndTime;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private String createMan;

    private Date updateTime;

    private String updateMan;

    private String deleteState;

    private String signState;

    @Override
    public String toString() {
        String str = "{" +
                "id:" + id +
                ", name:\"" + name + '"' +
                ", addr:\"" + addr + '"' +
                ", courseId:" + courseId +
                ", classId:" + classId +
                ", number:" + number +
                ", state:\"" + state + '"';
        if (realStartTime != null) {
            str += ", realStartTime:" + realStartTime.getTime();
        } else {
            str += ", realStartTime:" + realStartTime;
        }
        if (realEndTime != null) {
            str += ", realEndTime:" + realEndTime.getTime();
        } else {
            str += ", realEndTime:" + realEndTime;
        }
        if (startTime != null) {
            str += ", startTime:" + startTime.getTime();
        } else {
            str += ", startTime:" + startTime;
        }
        if (endTime != null) {
            str += ", endTime:" + endTime.getTime();
        } else {
            str += ", endTime:" + endTime;
        }
        if (createTime != null) {
            str += ", createTime:" + createTime.getTime();
        } else {
            str += ", createTime:" + createTime;
        }
        str += ", createMan:\"" + createMan + '"';
        if (updateTime != null) {
            str += ", updateTime:" + updateTime.getTime();
        } else {
            str += ", updateTime:" + updateTime;
        }

        str += ", updateMan:\"" + updateMan + '"' +
                ", deleteState:\"" + deleteState + '"' +
                ", signState:\"" + signState + '"' +
                '}';
        return str ;
    }

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
        this.name = name == null ? null : name.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(Date realStartTime) {
        this.realStartTime = realStartTime;
    }

    public Date getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan == null ? null : createMan.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateMan() {
        return updateMan;
    }

    public void setUpdateMan(String updateMan) {
        this.updateMan = updateMan == null ? null : updateMan.trim();
    }

    public String getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(String deleteState) {
        this.deleteState = deleteState == null ? null : deleteState.trim();
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }
}