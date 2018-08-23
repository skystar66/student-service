package com.tengyue360.dao;

import com.tengyue360.bean.SStuClass;
import com.tengyue360.bean.SStuClassKey;
import com.tengyue360.bean.SsUStudent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SStuClassMapper {
    int deleteByPrimaryKey(SStuClassKey key);

    int insert(SStuClass record);

    int insertSelective(SStuClass record);

    SStuClass selectByPrimaryKey(SStuClassKey key);

    int updateByPrimaryKeySelective(SStuClass record);

    int updateByPrimaryKey(SStuClass record);


    List<SStuClass> queryClassBySid(@Param("sid") String sid);



}