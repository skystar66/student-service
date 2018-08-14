package com.tengyue360.dao;

import com.tengyue360.bean.SsOpinionFeedback;

/**
 * @author panjt
 * @date 2018/8/14 下午6:05
 */
public interface SsOpinionFeedbackMapper {
    /**
     * 添加反馈意见
     * @param opinionFeedback
     * @return
     */
    int addOpinion(SsOpinionFeedback opinionFeedback);
}
