package com.tengyue360.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.tengyue360.bean.*;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.constant.RedisConstants;
import com.tengyue360.dao.*;
import com.tengyue360.enums.EnumModelType;
import com.tengyue360.exception.BusinessException;
import com.tengyue360.pool.ThreadProvider;
import com.tengyue360.service.SsStudentService;
import com.tengyue360.utils.CommonBeanUtils;
import com.tengyue360.utils.ElseFiledsUtils;
import com.tengyue360.web.requestModel.StudentRequestModel;
import com.tengyue360.web.requestModel.UserRequestModel;
import com.tengyue360.web.responseModel.AccountInfoResponseModel;
import com.tengyue360.web.responseModel.ResponseResult;
import com.tengyue360.web.responseModel.StudentResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 学生服务
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */
@Service
public class SsStudentServiceImpl implements SsStudentService {


    private static Logger logger = LoggerFactory.getLogger(SsStudentServiceImpl.class);

    @Autowired
    SsUStudentMapper studentMapper;

    @Autowired
    private SsOpinionFeedbackMapper ssOpinionFeedbackMapper;

    @Autowired
    SStuClassMapper stuClassMapper;
    @Autowired
    SsCschoolMapper ssCschoolMapper;
    @Autowired
    SSClassMapper classMapper;
    @Autowired
    SsAttachFilePathMapper attachFilePathMapper;
    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 根据家长电话查询该用户下的学员信息
     *
     * @return
     * @throws Exception
     */

    @Override
    public ResponseResult queryStudentByPhone(UserRequestModel model) {
        ResponseResult responseResult = new ResponseResult();
        try {
            List<AccountInfoResponseModel> stuList = new ArrayList<AccountInfoResponseModel>();
            //校验是否存在缓存
            boolean exist = redisTemplate.hasKey(RedisConstants.STU_LIST_INFO + model.getPhone());
            if (exist) {
                stuList = (List<AccountInfoResponseModel>)JSON.parseObject(redisTemplate.opsForValue().get(RedisConstants.STU_LIST_INFO + model.getPhone()).toString(),List.class);
                responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(stuList);
                return responseResult;
            }
            //查询学员列表
            List<SsUStudent> uStudents = studentMapper.queryStudentByPhone(model.getPhone());
            if (null != uStudents && uStudents.size() > 0) {
                for (SsUStudent student : uStudents) {
                    AccountInfoResponseModel model1 = new AccountInfoResponseModel();
                    model1.setId(student.getId().toString());
                    model1.setName(student.getName());
                    //根据学员id查询附件
                    List<SsAttachFilePath> attachFilePaths = attachFilePathMapper.queryUrlByReleationId(student.getId().toString());
                    if (null != attachFilePaths && attachFilePaths.size() > 0) {
                        model1.setAttachaId(attachFilePaths.get(0).getId());
                        model1.setAttachPath(attachFilePaths.get(0).getAttachPath());
                    }
                    stuList.add(model1);
                }
                responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                //处理缓存
                redisTemplate.opsForValue().set(RedisConstants.STU_LIST_INFO + model.getPhone(), JSONObject.toJSON(stuList));
                responseResult.setData(stuList);
                return responseResult;
            }
            responseResult.setErrno(ReturnCode.ERROR_EMPTY_DATA.code());
            responseResult.setError(ReturnCode.ERROR_EMPTY_DATA.msg());
            responseResult.setData(stuList);
            return responseResult;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            responseResult.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }

    /**
     * 根据学生id查询学员信息
     *
     * @return
     * @throws Exception
     */

    @Override
    public ResponseResult queryStudentById(StudentRequestModel model) {
        ResponseResult responseResult = new ResponseResult();
        StudentResponseModel studentResponseModel = null;
        try {
            //校验是否存在缓存
            boolean exist = redisTemplate.hasKey(RedisConstants.STU_INFO + model.getId());
            if (exist) {
                studentResponseModel = JSON.parseObject(redisTemplate.opsForValue().get(RedisConstants.STU_INFO + model.getId()).toString(),
                        StudentResponseModel.class);
                responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(studentResponseModel);
                return responseResult;
            }
            //根据学员id查询学员信息
            SsUStudent student = studentMapper.selectByPrimaryKey(Integer.parseInt(model.getId()));
            if (null != student) {
                studentResponseModel = new StudentResponseModel();
                List<SStuClass> classes = stuClassMapper.queryClassBySid(String.valueOf(student.getId()));
                if (null != classes && classes.size() > 0) {
                    SStuClass sStuClass = classes.get(0);
                    //根据班级id查询学校
                    if (null != sStuClass) {
                        SSClass ssClass = classMapper.selectByPrimaryKey(sStuClass.getClassId());
                        if (null != ssClass) {
                            SsCschool ssCschool = ssCschoolMapper.selectByPrimaryKey(Integer.parseInt(ssClass.getSchoolId()));
                            if (ssCschool != null) {
                                studentResponseModel.setSchoolName(ssCschool.getSchoolName());
                            }
                        }
                    }
                }
                //根据学生id查询头像 url
                List<SsAttachFilePath> attachFilePaths = attachFilePathMapper.queryUrlByReleationId(student.getId().toString());
                if (null != attachFilePaths && attachFilePaths.size() > 0) {
                    SsAttachFilePath attachFilePath = attachFilePaths.get(0);
                    if (null != attachFilePath) {
                        studentResponseModel.setImageUrl(attachFilePath.getAttachPath());
                    }
                }
                CommonBeanUtils.copyPropertiesElseList(student, studentResponseModel, ElseFiledsUtils.elseFileds(EnumModelType.QUERY_STUDENT_BY_ID.code()));
            }
            if (null != studentResponseModel) {
                responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                //保存学员信息 --- redis缓存中
                redisTemplate.opsForValue().set(RedisConstants.STU_INFO + model.getId(), studentResponseModel);
                responseResult.setData(studentResponseModel);
                return responseResult;
            }
            responseResult.setErrno(ReturnCode.ERROR_EMPTY_DATA.code());
            responseResult.setError(ReturnCode.ERROR_EMPTY_DATA.msg());
            responseResult.setData(null);
            return responseResult;
        } catch (Exception ex) {
            logger.error(ex.toString());
//            throw new BusinessException(ex.toString(), true);
            responseResult.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }

    /**
     * 根据学生id修改学员信息
     *
     * @return
     * @throws Exception
     */


    @Override
    public ResponseResult updateStudentById(StudentRequestModel model) {
        ResponseResult responseResult = new ResponseResult();
        try {
            //查询学员列表
            SsUStudent student = studentMapper.selectByPrimaryKey(Integer.parseInt(model.getId()));
            CommonBeanUtils.copyProperties(model, student);
            if (null != student) {
                //修改学生信息
                ThreadProvider.getThreadPool().execute(() -> {
                    studentMapper.updateByPrimaryKey(student);
                });
                responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(student);
                //清除该学生缓存信息
                redisTemplate.delete(RedisConstants.STU_INFO + model.getId());
                return responseResult;
            }
            responseResult.setErrno(ReturnCode.ERROR_EMPTY_DATA.code());
            responseResult.setError(ReturnCode.ERROR_EMPTY_DATA.msg());
            responseResult.setData(null);
            return responseResult;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            responseResult.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }

    @Override
    public ResponseResult findById(SsOpinionFeedback ssOpinionFeedback) {
        ResponseResult responseResult = new ResponseResult();
        try {
            SsUStudent student = studentMapper.selectByPrimaryKey(ssOpinionFeedback.getSsUStudent().getId());
            if (student != null) {
                ssOpinionFeedback.setSsUStudent(student);
                ThreadProvider.getThreadPool().execute(() -> {
                    ssOpinionFeedbackMapper.addOpinion(ssOpinionFeedback);
                });

                responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(null);
                return responseResult;
            }
            responseResult.setErrno(ReturnCode.FIND_STUDENT_ERROR.code());
            responseResult.setError(ReturnCode.FIND_STUDENT_ERROR.msg());
            responseResult.setData(null);
            return responseResult;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            responseResult.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }

    @Override
    public ResponseResult queryStudentList(String queryElement,Integer pageNum,Integer pageSize) {

        ResponseResult responseResult = new ResponseResult();
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<SsUStudent> studentList = studentMapper.queryStudentList(queryElement);
            if (studentList.size()>0) {
                responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(studentList);
                logger.info("out====>studentList{}",studentList);
                return responseResult;
            }
            responseResult.setErrno(ReturnCode.ERROR_EMPTY_DATA.code());
            responseResult.setError(ReturnCode.ERROR_EMPTY_DATA.msg());
            responseResult.setData(null);
            return responseResult;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            responseResult.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }
}
