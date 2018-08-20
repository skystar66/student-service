package com.tengyue360.dao;


import com.tengyue360.bean.SsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SsUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsUser record);

    int insertSelective(SsUser record);

    SsUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsUser record);

    int updateByPrimaryKey(SsUser record);


    SsUser login(@Param("phone") String phone);





}