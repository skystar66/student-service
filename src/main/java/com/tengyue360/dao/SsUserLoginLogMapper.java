package com.tengyue360.dao;

import com.tengyue360.bean.SsUserLoginLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SsUserLoginLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsUserLoginLog record);

    int insertSelective(SsUserLoginLog record);

    SsUserLoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsUserLoginLog record);

    int updateByPrimaryKey(SsUserLoginLog record);

    /**
     * 根据用户id，state系统来源 删除登陆状态token
     * @param
     * @return
     */

    int deleteToeknByUserId(@Param("userId") Integer userId, @Param("state") String state);





}