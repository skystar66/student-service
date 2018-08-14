package com.tengyue360.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * @author panjt
 * @date 2018/8/6 下午2:51
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsIntegral {
    private Integer id;
    private SsLesson ssLesson;
    private SsUStudent ssUStudent;

    private SsQuestion ssQuestion;

    private String source;
    private Integer value;
    private Date createTime;
    private String createMan;
    private Date updateTime;
    private String updateMan;
    private String deleteStete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
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
        this.createMan = createMan;
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
        this.updateMan = updateMan;
    }

    public String getDeleteStete() {
        return deleteStete;
    }

    public void setDeleteStete(String deleteStete) {
        this.deleteStete = deleteStete;
    }

    public SsLesson getSsLesson() {
        return ssLesson;
    }

    public void setSsLesson(SsLesson ssLesson) {
        this.ssLesson = ssLesson;
    }

    public SsUStudent getSsUStudent() {
        return ssUStudent;
    }

    public void setSsUStudent(SsUStudent ssUStudent) {
        this.ssUStudent = ssUStudent;
    }

    public SsQuestion getSsQuestion() {
        return ssQuestion;
    }

    public void setSsQuestion(SsQuestion ssQuestion) {
        this.ssQuestion = ssQuestion;
    }
}
