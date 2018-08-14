package com.tengyue360.web.controller;

import com.tengyue360.service.SsStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生管理类
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */
@RestController("student")
public class StudentController {


    @Autowired
    SsStudentService studentService;






}
