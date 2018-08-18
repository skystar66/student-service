package com.tengyue360.web.requestModel;

/**
 * 封装学员信息
 *
 * @author xuliang
 * @date 2018/8/10 12:37
 */

public class StudentRequestModel extends BaseRequestModel {


    private String id; //学员id

    private String name;//学员姓名

    private String sex;//性别

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "StudentRequestModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
