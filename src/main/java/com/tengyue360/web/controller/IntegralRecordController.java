package com.tengyue360.web.controller;

import com.tengyue360.bean.SsOpinionFeedback;
import com.tengyue360.service.SsOpinionFeedbackService;
import com.tengyue360.service.SsIntegralService;
import com.tengyue360.service.SsStudentService;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生端app积分记录
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
     * @param userId
     * @return
     */
    @GetMapping("/stuApp/integralRecord")
    public ResponseResult integralRecord(Integer userId){
        logger.info("in====>userId:{}",userId);
        return ssIntegralService.integralRecord(userId);
    }


    /**
     * 添加反馈意见
     * @param ssOpinionFeedback
     * @return
     */
    @PostMapping("/stuApp/opinionFeedback")
    public ResponseResult opinionFeedback(@RequestBody SsOpinionFeedback ssOpinionFeedback){
        logger.info("in====>opinionFeedback:{}",ssOpinionFeedback);
        ResponseResult responseResult = new ResponseResult();
        return ssStudentService.findById(ssOpinionFeedback);

    }
}
