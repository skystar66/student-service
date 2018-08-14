package com.tengyue360.dao;

import com.tengyue360.bean.SsMqPushLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface SsMqPushLogMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(SsMqPushLog record);

    int insertSelective(SsMqPushLog record);

    SsMqPushLog selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(SsMqPushLog record);

    int updateByPrimaryKey(SsMqPushLog record);
}