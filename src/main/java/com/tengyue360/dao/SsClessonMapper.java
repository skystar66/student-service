package com.tengyue360.dao;

import com.tengyue360.bean.SsClesson;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SsClessonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsClesson record);

    int insertSelective(SsClesson record);

    SsClesson selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsClesson record);

    int updateByPrimaryKey(SsClesson record);


    List<SsClesson> queryLoession(@Param("cid") String cid, @Param("state") String state, @Param("currentTime") Date currentTime);


    List<SsClesson> queryAllLesion(@Param("cid") String cid, @Param("state") String state);


}