package com.tengyue360.bean;

import com.tengyue360.web.requestModel.BaseRequestModel;

import java.util.Date;

/**
 * 封装学生班级
 * @author panjt
 * @date 2018/8/14 下午10:27
 */
public class SsStudentClass{
    private Integer studentId;
    private Integer classId;
    private Date updateTime;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
