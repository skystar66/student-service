package com.tengyue360.dao;

import com.tengyue360.bean.SsIntegral;

import java.util.List;

/**
 * @author panjt
 * @date 2018/8/6 下午3:22
 */
public interface SsIntegralMapper {
    /**
     * 根据用户id统计积分详情
     * @param userId
     * @return
     */
    List<SsIntegral> integralRecord(Integer userId);

    /**
     * 根据学生id查询积分总数
     * @param userId
     * @return
     */
    int selectByUsrId(Integer userId);
}
