package com.tengyue360.bean;

import java.util.Date;

/**
 * @author panjt
 * @date 2018/8/14 下午4:09
 */
public class SsOpinionFeedback {
    private Integer id;
    private String content;
    private SsUStudent ssUStudent;
    private Date createTime;
    private String createMan;
    private Date updateTime;
    private String updateMan;
    private String deleteState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SsUStudent getSsUStudent() {
        return ssUStudent;
    }

    public void setSsUStudent(SsUStudent ssUStudent) {
        this.ssUStudent = ssUStudent;
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

    public String getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(String deleteState) {
        this.deleteState = deleteState;
    }
}
