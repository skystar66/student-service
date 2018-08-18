package com.tengyue360.dao;

import com.tengyue360.bean.SsCschool;
import org.springframework.stereotype.Repository;

@Repository
public interface SsCschoolMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsCschool record);

    int insertSelective(SsCschool record);

    SsCschool selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsCschool record);

    int updateByPrimaryKey(SsCschool record);
}