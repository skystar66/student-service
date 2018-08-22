package com.tengyue360.quartz.task.course;

import com.alibaba.fastjson.JSONObject;
import com.tengyue360.bean.*;
import com.tengyue360.dao.*;
import com.tengyue360.enums.EMessageTemplateBusinessType;
import com.tengyue360.service.MessageService;
import com.tengyue360.utils.DateUtil;
import com.tengyue360.web.responseModel.AccountInfoResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 上课提醒 定时任务
 *
 * @author xuliang
 * @date 2018/8/15 11:03
 */

public class CourseTask {

    private static Logger logger = LoggerFactory.getLogger(CourseTask.class);


    @Autowired
    SsAccountNumberMapper accountNumberMapper;
    @Autowired
    SsUStudentMapper studentMapper;
    @Autowired
    SsClessonMapper clessonMapper;
    @Autowired
    SsCourseMapper courseMapper;
    @Autowired
    SStuClassMapper sStuClassMapper;
    @Autowired
    MessageService messageService;
    @Autowired
    SSClassMapper classMapper;

    private static final int pageSize = 10;

    public void runBeforeOneDay() {
        // 得到用户总数量
        Integer sumCount1 = accountNumberMapper.countParent("2");
        // 得到共需多少页
        int pageCounts = sumCount1 % pageSize > 0 ? sumCount1 / pageSize + 1 : sumCount1 / pageSize;
        excuteCourser1(0, pageSize, pageCounts);
    }


    public void runBegingTwoHour() {
        // 得到用户总数量
        Integer sumCount1 = accountNumberMapper.countParent("2");
        // 得到共需多少页
        int pageCounts = sumCount1 % pageSize > 0 ? sumCount1 / pageSize + 1 : sumCount1 / pageSize;
        excuteCourser1(0, pageSize, pageCounts);
    }


    /**
     * 课程开始前一天18:00
     *
     * @param startPages 开始页
     * @param countPage  每页显示数量
     *                   参数说明
     * @return return_type 返回类型
     * @Title: excuteRemind
     * @Description: TODO()
     */

    public int excuteCourser1(Integer startPages, Integer countPage, Integer sumPage) {

        logger.info("开始执行定时任务，执行方法：excuteCourser1，【课程开始前一天18:00提醒】，执行时间：{}", DateUtil.dateToStr(new Date(), DateUtil.DATE_FORMAT_LONG));
        logger.info("============================================================================================当前页：" + startPages);
        if (startPages == sumPage) {
            return 1;
        }
        // 定义每页500条数据
        List<SsAccountNumber> accountNumbers = accountNumberMapper.queryParentsByRole("2", startPages, countPage);
        for (int j = startPages * countPage + 1; j <= sumPage; j++) {
            if (null != accountNumbers && accountNumbers.size() > 0) {
                for (int i = 0; i < accountNumbers.size(); i++) {
                    SsAccountNumber ssAccountNumber = accountNumbers.get(i);
                    //根据家长手机号查询相应未退班的学生信息
                    List<SsUStudent> accountInfoResponseModels = studentMapper.queryStudentByPhone(ssAccountNumber.getAccountNumber());
                    if (null != accountInfoResponseModels && accountInfoResponseModels.size() > 0) {
                        for (SsUStudent model : accountInfoResponseModels) {
                            if (StringUtils.isNotBlank(model.getState())) {
                                //如果不是退班学员  发送短信
                                if (!"2".equals(model.getState())) {
                                    //根据学员id查询  所在班级
                                    List<SStuClass> stuClassList = sStuClassMapper.queryClassBySid(model.getId().toString());
                                    if (null != stuClassList && stuClassList.size() > 0) {
                                        for (SStuClass cls : stuClassList) {
                                            SSClass sclass = classMapper.selectByPrimaryKey(cls.getClassId());
                                            if (null != sclass) {
                                                SsCourse course = courseMapper.selectByPrimaryKey(sclass.getCourseId());
                                                if (null != course) {
                                                    //根据课程查询 待上课次
                                                    List<SsClesson> clessonList = clessonMapper.queryLoession(course.getId().toString(), "0", new Date());
                                                    if (null != clessonList && clessonList.size() > 0) {
                                                        for (SsClesson clesson : clessonList) {
                                                            //开课前 前一天 18:00 发送短信 push消息
                                                            //发送短信
                                                            messageService.sendSms(EMessageTemplateBusinessType.SEND_RUNING_CLASS_READED, ssAccountNumber.getAccountNumber(), sendParams(clesson));
                                                            //push消息
                                                            messageService.pushMessage(EMessageTemplateBusinessType.PUSH_RUNING_CLASS_READED, sendParams(clesson));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //继续执行递归
        excuteCourser1(startPages + 1, countPage + pageSize, sumPage);
        return 0;
    }


    /**
     * 课程开始前2小时 每秒
     *
     * @param startPages 开始页
     * @param countPage  每页显示数量
     *                   参数说明
     * @return return_type 返回类型
     * @Title: excuteRemind
     * @Description: TODO()
     */

    public int excuteCourser2(Integer startPages, Integer countPage, Integer sumPage) {
        logger.info("开始执行定时任务，执行方法：excuteCourser2，【课程开始2h提醒】，执行时间：{}", DateUtil.dateToStr(new Date(), DateUtil.DATE_FORMAT_LONG));
        logger.info("============================================================================================当前页：" + startPages);
        if (startPages == sumPage) {
            return 1;
        }
        // 定义每页500条数据
        List<SsAccountNumber> accountNumbers = accountNumberMapper.queryParentsByRole("2", startPages, countPage);
        for (int j = startPages * countPage + 1; j <= sumPage; j++) {
            if (null != accountNumbers && accountNumbers.size() > 0) {
                for (int i = 0; i < accountNumbers.size(); i++) {
                    SsAccountNumber ssAccountNumber = accountNumbers.get(i);
                    //根据家长手机号查询相应未退班的学生信息
                    List<SsUStudent> accountInfoResponseModels = studentMapper.queryStudentByPhone(ssAccountNumber.getAccountNumber());
                    if (null != accountInfoResponseModels && accountInfoResponseModels.size() > 0) {
                        for (SsUStudent model : accountInfoResponseModels) {
                            if (StringUtils.isNotBlank(model.getState())) {
                                //如果不是退班学员  发送短信
                                if (!"2".equals(model.getState())) {
                                    //根据学员id查询  所在班级
                                    List<SStuClass> stuClassList = sStuClassMapper.queryClassBySid(model.getId().toString());
                                    if (null != stuClassList && stuClassList.size() > 0) {
                                        for (SStuClass cls : stuClassList) {
                                            SSClass sclass = classMapper.selectByPrimaryKey(cls.getClassId());
                                            if (null != sclass) {
                                                SsCourse course = courseMapper.selectByPrimaryKey(sclass.getCourseId());
                                                if (null != course) {
                                                    //根据课程查询 待上课次
                                                    List<SsClesson> clessonList = clessonMapper.queryAllLesion(course.getId().toString(), "0");
                                                    if (null != clessonList && clessonList.size() > 0) {
                                                        for (SsClesson clesson : clessonList) {
                                                            //开课前 2h  发送短信 push消息
                                                            long hour = DateUtil.dateDiffHour(clesson.getStartTime().getTime(), new Date().getTime());
                                                            if (hour >= 2) {
                                                                //发送短信
                                                                messageService.sendSms(EMessageTemplateBusinessType.SEND_RUNING_CLASS_REMIND, ssAccountNumber.getAccountNumber(), sendParams(clesson));
                                                                //push消息
                                                                messageService.pushMessage(EMessageTemplateBusinessType.PUSH_RUNING_CLASS_REMIND, sendParams(clesson));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        //继续执行递归
        excuteCourser2(startPages + 1, countPage + pageSize, sumPage);
        return 0;
    }


    /**
     * 封装发送模板
     *
     * @return return_type 返回类型
     * @Title: excuteRemind
     * @Description: TODO()
     */


    public JSONObject sendParams(SsClesson clesson) {
        JSONObject object = new JSONObject();
        object.put("courName", clesson.getName());
        object.put("startTime", DateUtil.dateToStr(clesson.getStartTime(), DateUtil.DATE_FORMAT_SHORT));
        return object;
    }


}
