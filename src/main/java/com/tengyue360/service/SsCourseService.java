package com.tengyue360.service;

import com.tengyue360.bean.SsStudentClass;
import com.tengyue360.web.responseModel.ResponseResult;

import java.util.List;

/**
 * @author panjt
 * @date 2018/8/15 下午6:34
 */
public interface SsCourseService {
    /**
     * 根据学生id查询班级
     * @param id
     * @return
     */
    public ResponseResult findClassByStudentId(Integer id);

    /**
     * 根据课程id，课次状态查询课次列表
     * @param courseId
     * @param lessonState
     * @param userId
     * @return
     */
    ResponseResult findLessonList(Integer courseId, Integer lessonState,Integer userId);
}
