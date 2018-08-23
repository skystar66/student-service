package com.tengyue360.web.responseModel;

import com.alibaba.fastjson.JSONObject;
import com.tengyue360.common.ReturnCode;

import java.io.Serializable;

/**
 * @author 统一返回格式
 * @date 2018年8月08日 20:04:55
 */
public class ResponseResult   extends BaseBean implements Serializable{

    /**
     * 响应状态
     */
    private int errno = 200;
    /**
     * 响应状态说明
     */
    private String error = "正常调用";
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

    public ResponseResult(int errno, String error) {
        this.errno = errno;
        this.error = error;
    }

    public ResponseResult(int errno, String error, Object data) {
        this.errno = errno;
        this.error = error;
        this.data = data;
    }


    public static ResponseResult onFailure(Object rstObj, ReturnCode rstDesc) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setErrno(rstDesc.code());
        responseResult.setError(rstDesc.msg());
        responseResult.setData(rstObj);
        return responseResult;
    }

    public static ResponseResult onSuccess(Object rstObj, ReturnCode rstDesc) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setErrno(rstDesc.code());
        responseResult.setError(rstDesc.msg());
        responseResult.setData(rstObj);
        return responseResult;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
    }
}