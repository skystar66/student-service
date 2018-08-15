package com.tengyue360.bean;

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
}
