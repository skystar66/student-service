package com.tengyue360.service.impl;

import com.tengyue360.bean.SsUser;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.dao.SsUStudentMapper;
import com.tengyue360.dao.SsUserLoginLogMapper;
import com.tengyue360.dao.SsUserMapper;
import com.tengyue360.pool.ThreadProvider;
import com.tengyue360.service.UserService;
import com.tengyue360.web.requestModel.UserRequestModel;
import com.tengyue360.web.responseModel.AccountInfoResponseModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户模块服务
 *
 * @author xuliang
 * @date 2018/8/10 10:03
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    SsUserMapper userMapper;
    @Autowired
    SsUserLoginLogMapper loginLogMapper;
    @Autowired
    SsUStudentMapper studentMapper;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * 修改密码
     *
     * @return
     * @throws Exception
     */

    @Override
    public ResponseResult updatePwd(UserRequestModel model) {

        ResponseResult responseResult = new ResponseResult();
        try {
            SsUser user = userMapper.selectByPrimaryKey(Integer.parseInt(model.getUserId()));
            if (null != user) {
                if (model.getOldPwd().equals(user.getPassword())) {
                    //更新 密码
                    user.setPassword(model.getNewPwd());
                    //删除JWT中所有该用户的登录token
//                    TokenFactory.refreshToken()
                    //删除用户登录日志中的token
                    ThreadProvider.getThreadPool().execute(() -> {
                        updatePwd(user);
                    });
                    responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                    responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                    responseResult.setData(null);
                    return responseResult;
                } else {
                    //旧密码与库中原始密码不一样
                    responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                    responseResult.setMsg("原始密码输入不正确");
                    responseResult.setData(null);
                    return responseResult;
                }
            }
            //用户不存在
            responseResult.setCode(ReturnCode.NAME_PWD_FALSE.code());
            responseResult.setMsg(ReturnCode.NAME_PWD_FALSE.msg());
            responseResult.setData(null);
        } catch (Exception e) {
            logger.error("系统异常", e);
            responseResult.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }

    /**
     * 忘记密码
     *
     * @return
     * @throws Exception
     */

    @Override
    public ResponseResult backPwd(UserRequestModel model) {
        ResponseResult responseResult = new ResponseResult();
        try {
            SsUser user = userMapper.login(model.getPhone());
            if (null != user) {
                //更新密码
                user.setPassword(model.getNewPwd());
                //删除JWT中所有该用户的登录token
//                    TokenFactory.refreshToken()
                //删除用户登录日志中的token
                ThreadProvider.getThreadPool().execute(() -> {
                    updatePwd(user);
                });
                responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(null);
                return responseResult;
            }
            //用户不存在
            responseResult.setCode(ReturnCode.NAME_PWD_FALSE.code());
            responseResult.setMsg(ReturnCode.NAME_PWD_FALSE.msg());
            responseResult.setData(null);
        } catch (Exception e) {
            logger.error("系统异常", e);
            responseResult.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }


    /**
     * 退出登录
     *
     * @return
     * @throws Exception
     */

    @Override
    public ResponseResult loginOut(UserRequestModel model) {
        ResponseResult responseResult = new ResponseResult();
        try {
            SsUser user = userMapper.selectByPrimaryKey(Integer.parseInt(model.getUserId()));
            if (null != user) {
                //删除JWT中所有该用户的登录token
//               TokenFactory.refreshToken()

                //删除用户登录日志中的token
                ThreadProvider.getThreadPool().execute(() -> {
                    loginLogMapper.deleteToeknByUserId(user.getId(), "3");
                });

                responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(null);
                return responseResult;
            }
            //用户不存在
            responseResult.setCode(ReturnCode.NAME_PWD_FALSE.code());
            responseResult.setMsg(ReturnCode.NAME_PWD_FALSE.msg());
            responseResult.setData(null);
        } catch (Exception e) {
            logger.error("系统异常", e);
            responseResult.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }


    /**
     * 根据用户id查询该学生列表
     *
     * @return
     * @throws Exception
     */

    @Override
    public ResponseResult queryStudentsByUserId(UserRequestModel model) {
        try {
            List<AccountInfoResponseModel> stuList = studentMapper.queryStudentByPhone(model.getPhone());
            if (null != stuList && stuList.size() > 0) {
                return ResponseResult.onSuccess(stuList, ReturnCode.ACTIVE_SUCCESS);
            }
            return ResponseResult.onSuccess(null, ReturnCode.ERROR_EMPTY_DATA);
        } catch (Exception e) {
            logger.error("系统异常", e);
            ResponseResult.onFailure(null, ReturnCode.ACTIVE_EXCEPTION);
        }
        return null;
    }


    /**
     * 忘记密码 删除登录token 修改密码
     *
     * @return
     * @throws Exception
     */
    @Transactional
    public int updatePwd(SsUser user) {
        loginLogMapper.deleteToeknByUserId(user.getId(), "3");
        int num = userMapper.updateByPrimaryKey(user);
        return num;
    }

}
