package com.tengyue360.dao;

import com.tengyue360.bean.SsSpeakerAssistant;

/**
 * @author panjt
 * @date 2018/8/15 下午11:04
 */
public interface SsSpeakerAssistantMapper {
    /**
     * 根据账号id查询讲师/助教信息
     * @param id
     * @return
     */
    SsSpeakerAssistant findByAccountId(Integer id);
}
