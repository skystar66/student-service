package com.tengyue360.service;


import com.tengyue360.web.responseModel.ResponseResult;

/**
 * @author panjt
 * @date 2018/8/3 下午2:44
 */
public interface SsIntegralService {


    /**
     * 根据学生id获取积分统计
     * @param userId
     * @return
     */
    ResponseResult integralRecord(Integer userId);
}
