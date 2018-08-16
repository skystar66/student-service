package com.tengyue360.dao;

import com.tengyue360.bean.SSClass;
import org.springframework.stereotype.Repository;

@Repository
public interface SSClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SSClass record);

    int insertSelective(SSClass record);

    SSClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SSClass record);

    int updateByPrimaryKey(SSClass record);
}