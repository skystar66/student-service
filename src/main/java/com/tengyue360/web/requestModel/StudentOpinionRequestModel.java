package com.tengyue360.web.requestModel;

/**
 * 封装学员信息
 *
 * @author xuliang
 * @date 2018/8/10 12:37
 */

public class StudentOpinionRequestModel extends BaseRequestModel {


    private Integer id; //学员id
    private String name;//学员姓名
    private String uId;//学生id
    private String parentName;//家长姓名
    private String parentPhone;//家长电话
    private String content;//反馈内容
    private String queryElement;//查询信息项

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

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQueryElement() {
        return queryElement;
    }

    public void setQueryElement(String queryElement) {
        this.queryElement = queryElement;
    }

    @Override
    public String toString() {
        return "StudentOpinionRequestModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uId='" + uId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", parentPhone='" + parentPhone + '\'' +
                ", content='" + content + '\'' +
                ", queryElement='" + queryElement + '\'' +
                '}';
    }
}



