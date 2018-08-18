package com.tengyue360.web.controller;

import com.tengyue360.service.SsStudentService;
import com.tengyue360.web.BeanValidators.BeanValidators;
import com.tengyue360.web.requestModel.StudentRequestModel;
import com.tengyue360.web.requestModel.UserRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生管理类
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */
@RestController
@RequestMapping("student")
public class StudentController {

    private static Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    SsStudentService studentService;


    /**
     * 根据学员id查询学员信息接口
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryStudentById", method = RequestMethod.POST)
    public ResponseResult queryStudentById(@RequestBody StudentRequestModel model) {
        logger.info("开始调用根据学员id查询学员信息接口，参数信息：{}", model);
        if (null != BeanValidators.isValidateQueryStudentById(model)) {
            logger.info("根据学员id查询学员信息接口参数信息校验失败，返回结果{}", BeanValidators.isValidateQueryStudentById(model));
            return BeanValidators.isValidateQueryStudentById(model);
        }
        ResponseResult responseResult = studentService.queryStudentById(model);
        if (null != responseResult) {
            logger.info("根据学员id查询学员信息接口成功，返回结果{}", responseResult);
            return responseResult;
        }
        return null;
    }


    /**
     * 根据学员id修改学员信息接口
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/updateStudentById", method = RequestMethod.POST)
    public ResponseResult updateStudentById(@RequestBody StudentRequestModel model) {
        logger.info("开始调用根据学员id更新学员信息接口，参数信息：{}", model);
        if (null != BeanValidators.isValidateQueryStudentById(model)) {
            logger.info("根据学员id更新学员信息接口参数信息校验失败，返回结果{}", BeanValidators.isValidateQueryStudentById(model));
            return BeanValidators.isValidateQueryStudentById(model);
        }
        ResponseResult responseResult = studentService.updateStudentById(model);
        if (null != responseResult) {
            logger.info("根据学员id更新学员信息接口成功，返回结果{}", responseResult);
            return responseResult;
        }
        return null;
    }

    /**
     * 根据用户id查询学员接口
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/queryStudentByPhone", method = RequestMethod.POST)
    public ResponseResult queryStudentByPhone(@RequestBody UserRequestModel model) {
        logger.info("开始调用根据用户手机号查询学员信息接口，参数信息：{}", model);
        if (null != BeanValidators.isValidateQueryStudentByPhone(model)) {
            logger.info("根据学员id查询学员信息接口参数信息校验失败，返回结果{}", BeanValidators.isValidateQueryStudentByPhone(model));
            return BeanValidators.isValidateQueryStudentByPhone(model);
        }
        ResponseResult responseResult = studentService.queryStudentByPhone(model);
        if (null != responseResult) {
            logger.info("根据学员id查询学员信息接口成功，返回结果{}", responseResult);
            return responseResult;
        }
        return null;
    }











}
