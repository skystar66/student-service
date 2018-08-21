package com.tengyue360.dao;

import com.tengyue360.bean.SsUStudent;
import com.tengyue360.web.responseModel.AccountInfoResponseModel;
import com.tengyue360.web.responseModel.StudentResponseModel;
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
    List<SsUStudent> queryStudentByPhone(@Param("phone") String phone);



    /**
     * 根据家长电话查询该用户下的学员信息
     *
     * @return
     * @throws Exception
     */
    StudentResponseModel queryStudentById(@Param("id") String id);


    /**
     * 根据家长电话 学生id 查询学员信息
     *
     * @return
     * @throws Exception
     */

    SsUStudent queryStudentByIdAndPhone(@Param("id") Integer id,@Param("phone") String phone);




    /**
     * 根据条件查询学生列表
     * @param queryElement
     * @return
     */
    List<SsUStudent> queryStudentList(@Param("queryElement") String queryElement);
}