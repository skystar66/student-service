package com.tengyue360.bean;

import java.util.Date;

public class SsCschool {
    private Integer id;

    private String uid;

    private String schoolName;

    private String principalId;

    private Integer classroomNumber;

    private String province;

    private String city;

    private String area;

    private String mark;

    private Date createTime;

    private String createMan;

    private Date updateTime;

    private String updateMan;

    private String deleteState;

    private String subjects;

    private Date exitTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId == null ? null : principalId.trim();
    }

    public Integer getClassroomNumber() {
        return classroomNumber;
    }

    public void setClassroomNumber(Integer classroomNumber) {
        this.classroomNumber = classroomNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
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

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects == null ? null : subjects.trim();
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }
}