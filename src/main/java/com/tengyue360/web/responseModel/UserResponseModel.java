package com.tengyue360.web.responseModel;

import java.io.Serializable;


public class UserResponseModel implements Serializable {


    private String id; // 用户id

    private String name;//用户姓名


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
}
