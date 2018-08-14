package com.tengyue360.dao;

import com.tengyue360.bean.SsUStudent;
import com.tengyue360.web.responseModel.AccountInfoResponseModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SsUStudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsUStudent record);

    int insertSelective(SsUStudent record);

    SsUStudent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsUStudent record);

    int updateByPrimaryKey(SsUStudent record);

    /**
     * 根据家长电话查询该用户下的学员信息
     *
     * @return
     * @throws Exception
     */
    List<AccountInfoResponseModel> queryStudentByPhone(@Param("phone") String phone);


}