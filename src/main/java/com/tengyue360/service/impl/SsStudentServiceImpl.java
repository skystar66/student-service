package com.tengyue360.service.impl;

import com.tengyue360.bean.SsOpinionFeedback;
import com.tengyue360.bean.SsUStudent;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.dao.SsOpinionFeedbackMapper;
import com.tengyue360.dao.SsUStudentMapper;
import com.tengyue360.service.SsStudentService;
import com.tengyue360.utils.CommonBeanUtils;
import com.tengyue360.web.requestModel.StudentRequestModel;
import com.tengyue360.web.requestModel.UserRequestModel;
import com.tengyue360.web.responseModel.AccountInfoResponseModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 学生服务
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */
@Service
public class SsStudentServiceImpl implements SsStudentService {


    @Autowired
    SsUStudentMapper studentMapper;

    @Autowired
    private SsOpinionFeedbackMapper ssOpinionFeedbackMapper;

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
        try {
            //查询学员列表
            SsUStudent student = studentMapper.selectByPrimaryKey(Integer.parseInt(model.getId()));
            if (null != student) {
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
                studentMapper.updateByPrimaryKey(student);
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
            if ( student != null) {
                ssOpinionFeedback.setSsUStudent(student);
                int resultCount = ssOpinionFeedbackMapper.addOpinion(ssOpinionFeedback);
                if(resultCount >0){
                    responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                    responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                    responseResult.setData(null);
                    return responseResult;
                }

               // responseResult.setCode(ReturnCode.ADD_OPINION_ERROR.code());
               // responseResult.setMsg(ReturnCode.ADD_OPINION_ERROR.msg());
                responseResult.setCode(ReturnCode.ERROR_EMPTY_DATA.code());
                responseResult.setMsg(ReturnCode.ERROR_EMPTY_DATA.msg());
                responseResult.setData(null);
                return responseResult;
            }
            responseResult.setCode(ReturnCode.ERROR_EMPTY_DATA.code());
            responseResult.setMsg(ReturnCode.ERROR_EMPTY_DATA.msg());
            responseResult.setData(null);
            return responseResult;
        } catch (Exception ex) {
            responseResult.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }
}
