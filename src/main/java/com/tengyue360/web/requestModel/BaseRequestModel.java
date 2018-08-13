package com.tengyue360.web.requestModel;
/**
 * 请求基类
 *
 * @author xuliang
 * @date 2018/8/10 12:37
 */
public class BaseRequestModel{



    private long timestamp; // 请求时间戳


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
