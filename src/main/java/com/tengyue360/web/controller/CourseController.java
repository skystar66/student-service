package com.tengyue360.web.controller;

import com.tengyue360.rpcModel.requestModel.CommonRequestModel;
import com.tengyue360.service.SsCourseService;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 课程
 * @author panjt
 * @date 2018/8/15 下午6:32
 */
@RestController
public class CourseController {
    private static Logger logger = LoggerFactory.getLogger(CourseController.class);
    @Autowired
    private SsCourseService ssCourseService;

    /**
     * 课程列表
     * @param userId
     * @return
     */
    @PostMapping("/stuApp/courseList")
    public ResponseResult courseList(Integer userId){
        logger.info("in学生id{}",userId);
        return ssCourseService.findClassByStudentId(userId);
    }

    /**
     * 已上/待上课次列表
     * @param commonRequestModel
     * @param userId
     * @return
     */
    @PostMapping("/stuApp/lessonList")
    public ResponseResult lessonList(@RequestBody CommonRequestModel commonRequestModel, Integer userId){
        logger.info("in===>course{},lessonState{},userId{}",commonRequestModel.getCourseId(),commonRequestModel.getLessonState(),userId);
        return ssCourseService.findLessonList(commonRequestModel.getCourseId(),commonRequestModel.getLessonState(),userId);

    }

}
