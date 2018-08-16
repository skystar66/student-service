package com.tengyue360.service;

import com.tengyue360.bean.SsStudentClass;
import com.tengyue360.web.responseModel.ResponseResult;

import java.util.List;

/**
 * @author panjt
 * @date 2018/8/15 下午6:34
 */
public interface SsCourseService {
    public ResponseResult findClassByStudentId(Integer id);
}
