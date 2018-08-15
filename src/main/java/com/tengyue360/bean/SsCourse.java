package com.tengyue360.bean;

<<<<<<< HEAD
import java.util.Date;

public class SsCourse {
    private Integer id;

    private String name;

    private Integer teacherId;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private String createMan;

    private Date updateTime;

    private String updateMan;

    private String deleteState;
=======
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 课程
 * @author: panjt
 * @Date: 2018/8/14 13:03
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsCourse implements Serializable {

    private Integer id;//课程id

    private String name;//课程名称

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date startTime;//课程起始日期

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date endTime;//课程截止时间

    private List<SsLesson> ssLessons;//课程的课节列表
>>>>>>> d6ca47d26790a2d2ef41011eb8d2740474905d86

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
<<<<<<< HEAD
        this.name = name == null ? null : name.trim();
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
=======
        this.name = name;
    }

    public List<SsLesson> getSsLessons() {
        return ssLessons;
    }

    public void setSsLessons(List<SsLesson> ssLessons) {
        this.ssLessons = ssLessons;
>>>>>>> d6ca47d26790a2d2ef41011eb8d2740474905d86
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
<<<<<<< HEAD

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
}
=======
}
>>>>>>> d6ca47d26790a2d2ef41011eb8d2740474905d86
