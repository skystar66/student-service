package com.tengyue360.dao;

import com.tengyue360.bean.SSClass;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SSClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SSClass record);

    int insertSelective(SSClass record);

    SSClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SSClass record);

    int updateByPrimaryKey(SSClass record);

    /**
     * 根据学生id查询班级
     * @param userId
     * @return
     */
    List<SSClass> selectClassesByStuId(Integer userId);

    /**
     * 根据id和课程id查询
     * @param id
     * @param courseId
     * @return
     */
    SSClass selectByIdAndCOurseId(@Param("id") Integer id, @Param("courseId") Integer courseId);
}