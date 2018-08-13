package com.tengyue360.web.responseModel;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 统一返回格式
 * @date 2018年8月08日 20:04:55
 */
public class ResponseResult extends BaseBean {

    /**
     * 响应状态
     */
    private int code = 200;
    /**
     * 响应状态说明
     */
    private String msg = "正常调用";
    /**
     * 响应数据
     */
    private Object data = new JSONObject();


    /**
     * 响应token
     */

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ResponseResult() {

    }

    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean equals(Object obj){ return super.equals(obj);}
    public int hashCode(){
        return super.hashCode();
    }
}