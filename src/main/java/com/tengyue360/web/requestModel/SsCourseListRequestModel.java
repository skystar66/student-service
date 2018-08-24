package com.tengyue360.web.requestModel;

import com.tengyue360.bean.SsKlass;
import com.tengyue360.bean.SsLesson;
import com.tengyue360.bean.SsSpeakerAssistant;

import java.util.Date;
import java.util.List;

/**
 * 课程
 * @author: panjt
 * @Date: 2018/8/24 13:03
 */
public class SsCourseListRequestModel extends BaseRequestModel {

    private Integer id;//课程id

    private String name;//课程名称

    private Date startTime;//课程起始日期

    private Date endTime;//课程截止时间

    private Integer teacherId;

    private String teacherName;

    private String teacherPhoto;

    private Integer assistantId;

    private String assistantName;

    private String assistantPhoto;

    private String type;

    private String level;

    private Integer totalLseeon;

    private Integer totalFinishLesson;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getTotalLseeon() {
        return totalLseeon;
    }

    public void setTotalLseeon(Integer totalLseeon) {
        this.totalLseeon = totalLseeon;
    }

    public Integer getTotalFinishLesson() {
        return totalFinishLesson;
    }

    public void setTotalFinishLesson(Integer totalFinishLesson) {
        this.totalFinishLesson = totalFinishLesson;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherPhoto() {
        return teacherPhoto;
    }

    public void setTeacherPhoto(String teacherPhoto) {
        this.teacherPhoto = teacherPhoto;
    }

    public Integer getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(Integer assistantId) {
        this.assistantId = assistantId;
    }

    public String getAssistantName() {
        return assistantName;
    }

    public void setAssistantName(String assistantName) {
        this.assistantName = assistantName;
    }

    public String getAssistantPhoto() {
        return assistantPhoto;
    }

    public void setAssistantPhoto(String assistantPhoto) {
        this.assistantPhoto = assistantPhoto;
    }
}
