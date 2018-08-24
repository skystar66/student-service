package com.tengyue360.dao;

import com.tengyue360.bean.SsCourse;
import com.tengyue360.web.requestModel.SsCourseListRequestModel;
import com.tengyue360.web.requestModel.SsCourseRequestModel;
import org.springframework.stereotype.Repository;

/**
 * @author panjt
 * @date 2018/8/15 下午9:04
 */

@Repository
public interface SsCourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsCourse record);

    int insertSelective(SsCourse record);

    SsCourse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsCourse record);

    int updateByPrimaryKey(SsCourse record);
    /**
     * 根据课程id查询课程信息
     * @param id
     * @return
     */
    SsCourseListRequestModel findByCourseId(Integer id);

    /**
     * 根据课程id查询课次总数
     * @param id
     * @return
     */
    int findLessonCount(Integer id);

    /**
     * 根据课程id查询已经完成课次
     * @param id
     * @return
     */
    int findFinishLessonCount(Integer id);



}
