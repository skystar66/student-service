package com.tengyue360.service.impl;

import com.tengyue360.bean.SsKlass;
import com.tengyue360.bean.SsSpeakerAssistant;
import com.tengyue360.bean.SsStudentClass;
import com.tengyue360.bean.SsUser;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.dao.*;
import com.tengyue360.service.SsCourseService;
import com.tengyue360.web.requestModel.SsCourseRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author panjt
 * @date 2018/8/15 下午6:34
 */
@Service
public class SsCourseServiceImpl implements SsCourseService {
    private static Logger logger = LoggerFactory.getLogger(SsCourseServiceImpl.class);

    @Autowired
    private SsStudentClassMapper ssStudentClassMapper;

    @Autowired
    private SsKlassMapper ssKlassMapper;

    @Autowired
    private SsCourseMapper ssCourseMapper;

    @Autowired
    private SsSpeakerAssistantMapper ssSpeakerAssistantMapper;

    @Override
    public ResponseResult findClassByStudentId(Integer id) {
        ResponseResult responseResult = new ResponseResult();
        try {
            List<SsCourseRequestModel> ssCourseRequestModelList = new ArrayList<>();
            List<SsStudentClass> ssStudentClassList = ssStudentClassMapper.findClassByStudentId(id);
            for (SsStudentClass ssStudentClass : ssStudentClassList) {
                List<SsKlass> ssKlassList = ssKlassMapper.findByKlassId(ssStudentClass.getClassId());
                for (SsKlass ssKlass : ssKlassList) {
                    SsSpeakerAssistant assistant = ssSpeakerAssistantMapper.findByAccountId(ssKlass.getAssistant().getId());
                    SsSpeakerAssistant teacher = ssSpeakerAssistantMapper.findByAccountId(ssKlass.getTeacher().getId());
                    SsCourseRequestModel ssCourseRequestModel = ssCourseMapper.findByCourseId(ssKlass.getSsCourse().getId());
                    if (ssCourseRequestModel != null) {
                        //查询课程下的课次总数
                        int lessonCount = ssCourseMapper.findLessonCount(ssCourseRequestModel.getId());
                        int lessonFinishCount = ssCourseMapper.findFinishLessonCount(ssCourseRequestModel.getId());
                        ssCourseRequestModel.setTotalLseeon(lessonCount);
                        ssCourseRequestModel.setTotalFinishLesson(lessonFinishCount);
                        ssCourseRequestModel.setSsKlass(ssKlass);
                        ssCourseRequestModel.setTeacher(teacher);
                        ssCourseRequestModel.setAssistant(assistant);
                        ssCourseRequestModelList.add(ssCourseRequestModel);
                    }
                }
            }
            Collections.sort(ssCourseRequestModelList, new Comparator<SsCourseRequestModel>() {
                @Override
                public int compare(SsCourseRequestModel o1, SsCourseRequestModel o2) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date dt1 = o1.getStartTime();
                        Date dt2 = o2.getStartTime();
                        if (dt1.getTime() < dt2.getTime()) {
                            return 1;
                        } else if (dt1.getTime() > dt2.getTime()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
            if (ssCourseRequestModelList.size() <= 0) {
                responseResult.setCode(ReturnCode.ERROR_EMPTY_DATA.code());
                responseResult.setMsg(ReturnCode.ERROR_EMPTY_DATA.msg());
                responseResult.setData(null);
                return responseResult;
            }
            responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
            responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
            responseResult.setData(ssCourseRequestModelList);
        } catch (Exception e) {
            logger.error("系统异常", e);
            responseResult.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }
}