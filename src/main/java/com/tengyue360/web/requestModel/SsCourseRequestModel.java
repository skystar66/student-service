package com.tengyue360.web.requestModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tengyue360.bean.SsKlass;
import com.tengyue360.bean.SsLesson;
import com.tengyue360.bean.SsSpeakerAssistant;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 课程
 * @author: panjt
 * @Date: 2018/8/14 13:03
 */
public class SsCourseRequestModel extends BaseRequestModel {

    private Integer id;//课程id

    private String name;//课程名称

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date startTime;//课程起始日期

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date endTime;//课程截止时间

    private List<SsLesson> ssLessons;//课程的课节列表

    private SsKlass ssKlass;

    private SsSpeakerAssistant teacher;

    private SsSpeakerAssistant assistant;

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

    public List<SsLesson> getSsLessons() {
        return ssLessons;
    }

    public void setSsLessons(List<SsLesson> ssLessons) {
        this.ssLessons = ssLessons;
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

    public SsKlass getSsKlass() {
        return ssKlass;
    }

    public void setSsKlass(SsKlass ssKlass) {
        this.ssKlass = ssKlass;
    }

    public SsSpeakerAssistant getTeacher() {
        return teacher;
    }

    public void setTeacher(SsSpeakerAssistant teacher) {
        this.teacher = teacher;
    }

    public SsSpeakerAssistant getAssistant() {
        return assistant;
    }

    public void setAssistant(SsSpeakerAssistant assistant) {
        this.assistant = assistant;
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
}
