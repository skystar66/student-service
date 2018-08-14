package com.tengyue360.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

/**
 * 题目类
 * @author: yijunhao
 * @Date: 2018/7/26 10:25
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsQuestion {

    private Integer id;

    private Integer answer;//正确答案 0a,1b,2c,3d 或 013 abd、02 ac等

    private Integer type;//题目类型： 0单选题 1多选题 2判断题

    private Integer category;// 题目分类： 0练习题 1互动题 2反馈题 3红包

    private SsLesson ssLesson;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    private String createMan;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    private String updateMan;

    private Integer deleteState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public SsLesson getSsLesson() {
        return ssLesson;
    }

    public void setSsLesson(SsLesson ssLesson) {
        this.ssLesson = ssLesson;
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

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

}
