package com.tengyue360.dao;

import com.tengyue360.bean.SsAccountNumber;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SsAccountNumberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsAccountNumber record);

    int insertSelective(SsAccountNumber record);

    SsAccountNumber selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsAccountNumber record);

    int updateByPrimaryKey(SsAccountNumber record);


    /**
     * 查询所有家长信息
     *
     * @return
     * @throws Exception
     */

    List<SsAccountNumber> queryParentsByRole(@Param("role") String role, @Param("startPages") Integer startPages, @Param("countPage") Integer countPage);

    /**
     * 查询所有家长count
     *
     * @return
     * @throws Exception
     */

    Long countParent(@Param("role") String role);

}