package com.tengyue360.service.impl;

import com.tengyue360.bean.SsUser;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.dao.SsUserLoginLogMapper;
import com.tengyue360.dao.SsUserMapper;
import com.tengyue360.enums.EMessageTemplateBusinessType;
import com.tengyue360.service.MessageService;
import com.tengyue360.service.UserService;
import com.tengyue360.utils.TokenFactory;
import com.tengyue360.web.requestModel.UserRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                    //更新密码
                    user.setPassword(model.getNewPwd());
                    userMapper.updateByPrimaryKey(user);
                    //发送修改密码成功短信 加入队列
//                    sendModifyPWDMessage(user, EMessageTemplateBusinessType.MODIFY_LOGIN_PWD);
                    //删除JWT中所有该用户的登录token
//                    TokenFactory.refreshToken()

                    //删除用户登录日志中的token
                    loginLogMapper.deleteToeknByUserId(user.getId(), "3");

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
            SsUser user = userMapper.selectByPrimaryKey(Integer.parseInt(model.getUserId()));
            if (null != user) {
                //更新密码
                user.setPassword(model.getNewPwd());
                userMapper.updateByPrimaryKey(user);
                //发送修改密码成功短信 加入队列
//                    sendModifyPWDMessage(user, EMessageTemplateBusinessType.MODIFY_LOGIN_PWD);


                //删除JWT中所有该用户的登录token
//                    TokenFactory.refreshToken()

                //删除用户登录日志中的token
                loginLogMapper.deleteToeknByUserId(user.getId(), "3");

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
                loginLogMapper.deleteToeknByUserId(user.getId(), "3");

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
}
