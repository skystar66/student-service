package com.tengyue360.service.impl;

import com.tengyue360.bean.*;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.constant.RedisConstants;
import com.tengyue360.dao.*;
import com.tengyue360.service.SsCourseService;
import com.tengyue360.utils.FastJsonUtil;
import com.tengyue360.web.requestModel.SsClessonRequestModel;
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
    RedisTemplate redisTemplate;

    @Override
    public ResponseResult findClassByStudentId(Integer id) {
        ResponseResult responseResult = new ResponseResult();
        try {
           // HashOperations<String, String, String> redisValidateCode = redisTemplate.opsForHash();
            boolean existHashKey = redisTemplate.hasKey(RedisConstants.COURSE_LIST + id);
            if (existHashKey) {
                String course = (String) redisTemplate.opsForValue().get(RedisConstants.COURSE_LIST + id);
                responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(FastJsonUtil.json2List(course));
            } else {
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
                            //查询已上完的课次列表
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
                    logger.info("out===>{}", responseResult);
                    return responseResult;
                }
                redisTemplate.opsForValue().set(RedisConstants.COURSE_LIST + id, FastJsonUtil.list2Json(ssCourseRequestModelList));
                redisTemplate.expire(RedisConstants.COURSE_LIST + id, 180, TimeUnit.SECONDS);
                responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(ssCourseRequestModelList);
            }
        } catch (Exception e) {
            logger.error("系统异常", e);
            responseResult.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        logger.info("out===>{}", responseResult);
        return responseResult;
    }

    @Override
    public ResponseResult findLessonList(Integer courseId, Integer lessonState, Integer userId) {
        ResponseResult responseResult = new ResponseResult();
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

                responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
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
                            redisTemplate.opsForHash().put(RedisConstants.LESSON_NOT_FINISH + userId, "" + ssClessonRequestModel.getId() + userId, ssClessonRequestModel.toString());
                        }
                    }
                    redisTemplate.expire(RedisConstants.LESSON_NOT_FINISH + userId, 180, TimeUnit.SECONDS);
                    responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                    responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                    responseResult.setData(ssLessonList);
                } catch (Exception e) {
                    logger.error("系统异常", e);
                    responseResult.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
                    responseResult.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
                    responseResult.setData(null);
                }
            }
        } else if (lessonState == 1) {
            boolean existHashKey = redisTemplate.hasKey(RedisConstants.LESSON_ALREADY_FINISH + userId);
            if (existHashKey) {
                logger.info("执行redis操作-----");
                String ssLessonList = (String)redisTemplate.opsForValue().get(RedisConstants.LESSON_ALREADY_FINISH + userId);
                responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
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
                    responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                    responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                    responseResult.setData(ssLessonList);
                } catch (Exception e) {
                    logger.error("系统异常", e);
                    responseResult.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
                    responseResult.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
                    responseResult.setData(null);
                }
            }
        }
        logger.info("out===>{}", responseResult);
        return responseResult;
    }
}
