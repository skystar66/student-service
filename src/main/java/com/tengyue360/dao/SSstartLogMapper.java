package com.tengyue360.dao;

import com.tengyue360.bean.SSstartLog;
import org.springframework.stereotype.Repository;

@Repository
public interface SSstartLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(SSstartLog record);

    int insertSelective(SSstartLog record);

    SSstartLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SSstartLog record);

    int updateByPrimaryKey(SSstartLog record);
}