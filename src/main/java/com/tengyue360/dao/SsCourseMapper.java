package com.tengyue360.dao;

import com.tengyue360.bean.SsCourse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SsCourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsCourse record);

    int insertSelective(SsCourse record);

    SsCourse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsCourse record);

    int updateByPrimaryKey(SsCourse record);




}