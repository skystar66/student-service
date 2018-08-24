package com.tengyue360.cache;





/**
 * @author xuliang
 * @date 2018/08/24 19:53.
 **/
public interface ICountCache {

    boolean isRequestOutInterface(String key, Long time);

    boolean setFailure(String key);

    String getValue(String key);

    void setValue(Object key, Object value);
}
