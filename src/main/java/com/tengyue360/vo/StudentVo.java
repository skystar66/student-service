package com.tengyue360.vo;

import java.io.Serializable;

public class StudentVo implements Serializable {

    private Integer id;

    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
