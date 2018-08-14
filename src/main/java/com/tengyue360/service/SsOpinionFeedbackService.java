package com.tengyue360.service;


import com.tengyue360.bean.SsOpinionFeedback;

/**
 * @author panjt
 * @date 2018/8/14 下午6:01
 */
public interface SsOpinionFeedbackService {

    /**
     * 添加反馈信息
     * @param opinionFeedback
     * @return
     */
    int addOpinion(SsOpinionFeedback opinionFeedback);
}
