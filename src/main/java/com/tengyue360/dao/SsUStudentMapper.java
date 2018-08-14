package com.tengyue360.dao;

import com.tengyue360.bean.SsUStudent;
import org.springframework.stereotype.Repository;

@Repository
public interface SsUStudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsUStudent record);

    int insertSelective(SsUStudent record);

    SsUStudent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsUStudent record);

    int updateByPrimaryKey(SsUStudent record);
}