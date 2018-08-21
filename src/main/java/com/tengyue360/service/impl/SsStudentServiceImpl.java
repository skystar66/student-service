package com.tengyue360.service.impl;

import com.tengyue360.bean.*;
import com.tengyue360.common.ReturnCode;
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
import org.springframework.stereotype.Service;

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
            //查询学员列表
            List<AccountInfoResponseModel> stuList = studentMapper.queryStudentByPhone(model.getPhone());
            if (null != stuList && stuList.size() > 0) {
                responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(stuList);
                return responseResult;
            }
            responseResult.setCode(ReturnCode.ERROR_EMPTY_DATA.code());
            responseResult.setMsg(ReturnCode.ERROR_EMPTY_DATA.msg());
            responseResult.setData(stuList);
            return responseResult;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            responseResult.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
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
                responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(studentResponseModel);
                return responseResult;
            }
            responseResult.setCode(ReturnCode.ERROR_EMPTY_DATA.code());
            responseResult.setMsg(ReturnCode.ERROR_EMPTY_DATA.msg());
            responseResult.setData(null);
            return responseResult;
        } catch (Exception ex) {
            logger.error(ex.toString());
//            throw new BusinessException(ex.toString(), true);
            responseResult.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
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
                responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(student);
                return responseResult;
            }
            responseResult.setCode(ReturnCode.ERROR_EMPTY_DATA.code());
            responseResult.setMsg(ReturnCode.ERROR_EMPTY_DATA.msg());
            responseResult.setData(null);
            return responseResult;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            responseResult.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
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

                responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(null);
                return responseResult;
            }
            responseResult.setCode(ReturnCode.ADD_OPINION_ERROR.code());
            responseResult.setMsg(ReturnCode.ADD_OPINION_ERROR.msg());
            responseResult.setData(null);
            return responseResult;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            responseResult.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }
}
