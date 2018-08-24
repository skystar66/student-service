package com.tengyue360.service.impl;

import com.netflix.discovery.converters.Auto;
import com.tengyue360.bean.*;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.constant.RedisConstants;
import com.tengyue360.dao.*;
import com.tengyue360.service.SsCourseService;
import com.tengyue360.utils.FastJsonUtil;
import com.tengyue360.web.requestModel.SsClessonRequestModel;
import com.tengyue360.web.requestModel.SsCourseListRequestModel;
import com.tengyue360.web.requestModel.SsCourseRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private SsClessonMapper ssClessonMapper;

    @Autowired
    private SSClassMapper ssClassMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public ResponseResult findClassByStudentId(Integer id) {
        ResponseResult responseResult = new ResponseResult();
        try {
            boolean existHashKey = redisTemplate.hasKey(RedisConstants.COURSE_LIST + id);
            if (existHashKey) {
                String course = (String) redisTemplate.opsForValue().get(RedisConstants.COURSE_LIST + id);
                responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(FastJsonUtil.json2List(course));
            } else {
                List<SsCourseListRequestModel> ssCourseListRequestModels = new ArrayList<>();
                List<SsStudentClass> ssStudentClassList = ssStudentClassMapper.findClassByStudentId(id);
                for (SsStudentClass ssStudentClass : ssStudentClassList) {
                    List<SsKlass> ssKlassList = ssKlassMapper.findByKlassId(ssStudentClass.getClassId());
                    for (SsKlass ssKlass : ssKlassList) {
                        SsSpeakerAssistant assistant = ssSpeakerAssistantMapper.findByAccountId(ssKlass.getAssistant().getId());
                        SsSpeakerAssistant teacher = ssSpeakerAssistantMapper.findByAccountId(ssKlass.getTeacher().getId());
                        SsCourseListRequestModel ssCourseListRequestModel = ssCourseMapper.findByCourseId(ssKlass.getSsCourse().getId());
                        if (ssCourseListRequestModel != null) {
                            //查询课程下的课次总数
                            int lessonCount = ssCourseMapper.findLessonCount(ssCourseListRequestModel.getId());
                            //查询已上完的课次列表
                            int lessonFinishCount = ssCourseMapper.findFinishLessonCount(ssCourseListRequestModel.getId());
                            ssCourseListRequestModel.setTotalLseeon(lessonCount);
                            ssCourseListRequestModel.setTotalFinishLesson(lessonFinishCount);
                            ssCourseListRequestModel.setTeacherId(teacher.getId());
                            ssCourseListRequestModel.setTeacherName(teacher.getName());
                            ssCourseListRequestModel.setTeacherPhoto(teacher.getPhoto());
                            ssCourseListRequestModel.setAssistantId(assistant.getId());
                            ssCourseListRequestModel.setAssistantName(assistant.getName());
                            ssCourseListRequestModel.setAssistantPhoto(assistant.getPhoto());

                            ssCourseListRequestModels.add(ssCourseListRequestModel);
                        }
                    }
                }
                Collections.sort(ssCourseListRequestModels, new Comparator<SsCourseListRequestModel>() {
                    @Override
                    public int compare(SsCourseListRequestModel o1, SsCourseListRequestModel o2) {
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

                if (ssCourseListRequestModels.size() <= 0) {
                    responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                    responseResult.setError(ReturnCode.COURSE_EMPTY.msg());
                    responseResult.setData(null);
                    logger.info("out===>{}", responseResult);
                    return responseResult;
                }
                redisTemplate.opsForValue().set(RedisConstants.COURSE_LIST + id, FastJsonUtil.list2Json(ssCourseListRequestModels));
                redisTemplate.expire(RedisConstants.COURSE_LIST + id, 180, TimeUnit.SECONDS);
                responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(ssCourseListRequestModels);
            }
        } catch (Exception e) {
            logger.error("系统异常", e);
            responseResult.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        logger.info("out===>{}", responseResult);
        return responseResult;
    }

    @Override
    public ResponseResult findLessonList(Integer courseId, Integer lessonState, Integer userId) {
        ResponseResult responseResult = new ResponseResult();
        Integer resultCount =0;
        List<SSClass> ssClassList = ssClassMapper.selectClassesByStuId(userId);
        for(SSClass ssClass :ssClassList){
            SSClass ssClass1 = ssClassMapper.selectByIdAndCOurseId(ssClass.getId(),courseId);
            if(ssClass1 != null){
                resultCount++;
            }
        }
        if(resultCount<1){
            responseResult.setErrno(ReturnCode.COURSE_NOT_FOUND.code());
            responseResult.setError(ReturnCode.COURSE_NOT_FOUND.msg());
            responseResult.setData(null);
            return responseResult;
        }

        if (lessonState == 0) {
            HashOperations<String, String, String> redisValidateCode = redisTemplate.opsForHash();
            boolean existHashKey = redisTemplate.hasKey(RedisConstants.LESSON_NOT_FINISH + userId);
            if (existHashKey) {
                List<String> ssLessonList = redisValidateCode.values(RedisConstants.LESSON_NOT_FINISH + userId);
                List<SsClessonRequestModel> ssLessonLists = new ArrayList<>();
                logger.info("执行redis操作-----");
                for (String ssClessonRequestModel : ssLessonList) {
                    SsClessonRequestModel ssClessonRequestModels = FastJsonUtil.json2Bean(ssClessonRequestModel, SsClessonRequestModel.class);
                    ssLessonLists.add(ssClessonRequestModels);
                }

                responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(ssLessonLists);
            } else {
                logger.info("未执行redis----");
                try {
                    List<SsClessonRequestModel> ssLessonList = ssClessonMapper.findLessonList(courseId, lessonState);
                    for (SsClessonRequestModel ssClessonRequestModel : ssLessonList) {
                        if (ssClessonRequestModel != null) {
                            Integer signCount = ssClessonMapper.findSignState(ssClessonRequestModel.getId(), userId);
                            if (signCount > 0) {
                                ssClessonRequestModel.setSignState("1");//已签到
                            } else {
                                ssClessonRequestModel.setSignState("0");//未签到
                            }
                            redisTemplate.opsForHash().put(RedisConstants.LESSON_NOT_FINISH + userId, "" + userId, ssClessonRequestModel.toString());
                        }
                    }
                    redisTemplate.expire(RedisConstants.LESSON_NOT_FINISH + userId, 180, TimeUnit.SECONDS);
                    responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                    responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                    responseResult.setData(ssLessonList);
                } catch (Exception e) {
                    logger.error("系统异常", e);
                    responseResult.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
                    responseResult.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
                    responseResult.setData(null);
                }
            }
        } else if (lessonState == 1) {
            boolean existHashKey = redisTemplate.hasKey(RedisConstants.LESSON_ALREADY_FINISH + userId);
            if (existHashKey) {
                logger.info("执行redis操作-----");
                String ssLessonList = (String)redisTemplate.opsForValue().get(RedisConstants.LESSON_ALREADY_FINISH + userId);
                responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(FastJsonUtil.json2List(ssLessonList));
            } else {
                logger.info("未执行redis----");
                try {
                    List<SsClessonRequestModel> ssLessonList = ssClessonMapper.findLessonList(courseId, lessonState);
                    for (SsClessonRequestModel ssClessonRequestModel : ssLessonList) {
                        if (ssClessonRequestModel != null) {
                            Integer signCount = ssClessonMapper.findSignState(ssClessonRequestModel.getId(), userId);
                            if (signCount > 0) {
                                ssClessonRequestModel.setSignState("1");//已签到
                            } else {
                                ssClessonRequestModel.setSignState("0");//未签到
                            }
                        }
                    }
                    logger.info("FastJsonUtil.list2Json(ssLessonList){}",FastJsonUtil.list2Json(ssLessonList));
                    redisTemplate.opsForValue().set(RedisConstants.LESSON_ALREADY_FINISH +userId, FastJsonUtil.list2Json(ssLessonList));
                    redisTemplate.expire(RedisConstants.LESSON_ALREADY_FINISH + userId, 180, TimeUnit.SECONDS);
                    responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                    responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                    responseResult.setData(ssLessonList);
                } catch (Exception e) {
                    logger.error("系统异常", e);
                    responseResult.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
                    responseResult.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
                    responseResult.setData(null);
                }
            }
        }
        logger.info("out===>{}", responseResult);
        return responseResult;
    }
}
