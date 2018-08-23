package com.tengyue360.web.controller;

import com.tengyue360.bean.SsOpinionFeedback;
import com.tengyue360.bean.SsUStudent;
import com.tengyue360.service.SsIntegralService;
import com.tengyue360.service.SsStudentService;
import com.tengyue360.web.BeanValidators.BeanValidators;
import com.tengyue360.web.requestModel.IntegralRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 学生端app积分记录
 *
 * @author panjt
 * @date 2018/8/14 上午11:22
 */
@RestController
public class IntegralRecordController {
    Logger logger = LoggerFactory.getLogger(IntegralRecordController.class);

    @Autowired
    private SsIntegralService ssIntegralService;

    @Autowired
    private SsStudentService ssStudentService;


    /**
     * 查询学生积分
     *
     * @param userId
     * @return
     */
    @PostMapping("/stuApp/integralRecord")
    public ResponseResult integralRecord(Integer userId,String state) {
        logger.info("in====>userId:{},state{}", userId,state);
        return ssIntegralService.integralRecord(userId,state);
    }


    /**
     * 添加反馈意见
     *
     * @param integralRequestModel
     * @return
     */
    @PostMapping(value = "/stuApp/opinionFeedback")
    public ResponseResult opinionFeedback(@RequestBody IntegralRequestModel integralRequestModel, Integer userId) {
        if (null != BeanValidators.isValidateContent(integralRequestModel)) {
            logger.info("in======>content{}", BeanValidators.isValidateContent(integralRequestModel));
            return BeanValidators.isValidateContent(integralRequestModel);
        }
        logger.info("in=====>integralRequestModel{},{},{}", integralRequestModel, integralRequestModel.getContent(), userId);
        SsOpinionFeedback ssOpinionFeedback = new SsOpinionFeedback();
        ssOpinionFeedback.setContent(integralRequestModel.getContent());
        SsUStudent ssUStudent = new SsUStudent();
        ssUStudent.setId(userId);
        ssOpinionFeedback.setSsUStudent(ssUStudent);
        return ssStudentService.findById(ssOpinionFeedback);

    }
}
