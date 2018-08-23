package com.tengyue360.service.impl;

import com.tengyue360.bean.SsUStudent;
import com.tengyue360.bean.SsUser;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.dao.SsUStudentMapper;
import com.tengyue360.pool.ThreadProvider;
import com.tengyue360.service.ChangeStuService;
import com.tengyue360.service.SsStudentService;
import com.tengyue360.service.UserLoginLogService;
import com.tengyue360.utils.CommonBeanUtils;
import com.tengyue360.utils.TokenFactory;
import com.tengyue360.web.requestModel.ChangeStuRequestModel;
import com.tengyue360.web.responseModel.AccountInfoResponseModel;
import com.tengyue360.web.responseModel.ResponseResult;
import com.tengyue360.web.responseModel.UserResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChangeStuServiceImpl implements ChangeStuService {


    private static Logger logger = LoggerFactory.getLogger(ChangeStuServiceImpl.class);

    @Autowired
    UserLoginLogService loginLogService;
    @Autowired
    SsUStudentMapper studentMapper;


    @Override
    public ResponseResult changeStu(ChangeStuRequestModel model) {
        ResponseResult result = new ResponseResult();
        try {
            SsUStudent student = studentMapper.queryStudentByIdAndPhone(Integer.parseInt(model.getId()),model.getPhone());
            if (null == student) {
                result.setErrno(ReturnCode.CHANGE_STUDENT_ERROR.code());
                result.setError(ReturnCode.CHANGE_STUDENT_ERROR.msg());
                return result;
            }
            //获取学生端 app jwt topken
            String token = TokenFactory.getInstance().createToken(model.getId().toString(), "3");
            //记录登录日志
            ThreadProvider.getThreadPool().execute(() -> {
                loginLogService.saveloginLog(Integer.parseInt(model.getId()), token);
            });
            result.setToken(token);
            result.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
            result.setError(ReturnCode.ACTIVE_SUCCESS.msg());
            result.setData(null);
        } catch (Exception e) {
            logger.error("系统异常", e);
            result.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
            result.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
            result.setData(null);
        }
        return result;
    }
}
