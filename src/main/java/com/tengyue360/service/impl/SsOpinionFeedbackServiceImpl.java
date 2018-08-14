package com.tengyue360.service.impl;

import com.tengyue360.bean.SsOpinionFeedback;
import com.tengyue360.dao.SsOpinionFeedbackMapper;
import com.tengyue360.service.SsOpinionFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panjt
 * @date 2018/8/14 下午6:03
 */
@Service
public class SsOpinionFeedbackServiceImpl implements SsOpinionFeedbackService {

    @Autowired
    private SsOpinionFeedbackMapper ssOpinionFeedbackMapper;

    @Override
    public int addOpinion(SsOpinionFeedback opinionFeedback) {
        return ssOpinionFeedbackMapper.addOpinion(opinionFeedback);
    }
}
