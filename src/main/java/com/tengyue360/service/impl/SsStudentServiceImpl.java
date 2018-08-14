package com.tengyue360.service.impl;

import com.tengyue360.bean.SsUStudent;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.dao.SsUStudentMapper;
import com.tengyue360.service.SsStudentService;
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
}
