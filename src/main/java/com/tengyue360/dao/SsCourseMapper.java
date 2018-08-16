package com.tengyue360.dao;

import com.tengyue360.bean.SsCourse;
import com.tengyue360.bean.SsKlass;
import com.tengyue360.web.requestModel.SsCourseRequestModel;

import java.util.List;

/**
 * @author panjt
 * @date 2018/8/15 下午9:04
 */
public interface SsCourseMapper {
    /**
     * 根据课程id查询课程信息
     * @param id
     * @return
     */
    SsCourseRequestModel findByCourseId(Integer id);

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
