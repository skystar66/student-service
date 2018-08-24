package com.tengyue360.cache;

/**
 * @author xuliang
 * @date 2018/08/24 19:53.
 **/
public interface ICache {
    Object readConfig(String key);

    void setConfig(String key, String value);

    Object readConfigByGroup(String group, Object key);

    void setConfigByGroup(String group, String key, Object value);

    boolean setLoseTime(String key, Long time);

    Long getCount(String key);

    Long getCountByGroup(String group, String key);

    void batchDel(String... pattern);
}
