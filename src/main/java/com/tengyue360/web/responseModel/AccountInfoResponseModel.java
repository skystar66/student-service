package com.tengyue360.web.responseModel;

import java.io.Serializable;


/**
 * 封装返回学生账号列表信息
 *
 * @author xuliang
 * @date
 */
public class AccountInfoResponseModel implements Serializable {


    private String id;//学生id

    private String name;//学生姓名

    private String attachPath;//url头像

    private String attachaId;//附件id

    private String stuStatus;//学生状态

    public String getAttachaId() {
        return attachaId;
    }

    public void setAttachaId(String attachaId) {
        this.attachaId = attachaId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttachPath() {
        return attachPath;
    }

    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
    }


    public String getStuStatus() {
        return stuStatus;
    }

    public void setStuStatus(String stuStatus) {
        this.stuStatus = stuStatus;
    }
}
