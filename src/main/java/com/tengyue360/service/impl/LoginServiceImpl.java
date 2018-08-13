package com.tengyue360.service.impl;

import com.tengyue360.bean.SsUser;
import com.tengyue360.bean.SsUserLoginLog;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.constant.Constants;
import com.tengyue360.constant.RedisConstants;
import com.tengyue360.dao.SsUserLoginLogMapper;
import com.tengyue360.dao.SsUserMapper;
import com.tengyue360.service.LoginService;
import com.tengyue360.utils.TokenFactory;
import com.tengyue360.web.requestModel.LoginRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 登录模块服务
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    SsUserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SsUserLoginLogMapper userLoginLogMapper;

    private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    /**
     * 密码登录服务
     *
     * @return
     * @throws Exception
     */
    @Override
    public ResponseResult login(LoginRequestModel model) {

        ResponseResult result = new ResponseResult();
        try {
            SsUser user = userMapper.login(model.getPhone());
            if (user == null) {
                //用户不存在
                result.setCode(ReturnCode.NAME_PWD_FALSE.code());
                result.setMsg(ReturnCode.NAME_PWD_FALSE.msg());
                result.setData(null);
                return result;
            } else if (user != null && model.getPassword().equals(user.getPassword())) {
                if (user.getDeleteState().equals("0")) {
                    //表示该用户状态 不可用 通知后台处理
                    result.setCode(ReturnCode.ERROR_EXPIRED.code());
                    result.setMsg(ReturnCode.ERROR_EXPIRED.msg());
                    result.setData(null);
                    return result;
                }
                //获取学生端 app jwt topken
                String token = TokenFactory.createToken(user.getId().toString(), Constants.SOURCE_APP_STUDENT);
                //记录登录日志
                saveloginLog(user.getId(), token);
                result.setToken(token);
            } else {
                result.setCode(ReturnCode.NAME_PWD_FALSE.code());
                result.setMsg(ReturnCode.NAME_PWD_FALSE.msg());
                result.setData(null);
                return result;
            }

            result.setCode(ReturnCode.ACTIVE_SUCCESS.code());
            result.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
            result.setData(user);
        } catch (Exception e) {
            logger.error("系统异常", e);
            result.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
            result.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
            result.setData(null);
        }
        return result;
    }


    /**
     * 验证码登录服务
     *
     * @return
     * @throws Exception
     */
    @Override
    public ResponseResult codeLogin(LoginRequestModel model) {
        ResponseResult result = new ResponseResult();
        try {
            SsUser user = userMapper.login(model.getPhone());
            if (user == null) {
                //用户不存在
                result.setCode(ReturnCode.NAME_PWD_FALSE.code());
                result.setMsg(ReturnCode.NAME_PWD_FALSE.msg());
                result.setData(null);
                return result;
            } else if (user.getDeleteState().equals("0")) {
                //表示该用户状态 不可用 通知后台处理
                result.setCode(ReturnCode.ERROR_EXPIRED.code());
                result.setMsg(ReturnCode.ERROR_EXPIRED.msg());
                result.setData(null);
                return result;
            } else {
                //获取学生端 app jwt topken
                String token = TokenFactory.createToken(user.getId().toString(), Constants.SOURCE_APP_STUDENT);
                //记录登录日志
                saveloginLog(user.getId(), token);
                //删除当日获取5次的验证码hash key
                redisTemplate.delete(RedisConstants.REDIS_TWENTY_FOUR_CODE_COUNT);
                result.setToken(token);
                result.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                result.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                result.setData(user);
                return result;
            }
        } catch (Exception e) {
            logger.error("系统异常", e);
            result.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
            result.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
            result.setData(null);
        }
        return result;
    }

    /**
     * 记录用户登录日志
     *
     * @return
     * @throws Exception
     */

    public void saveloginLog(Integer userId, String token) {
        //删除该用户PC端的登陆token日志
        userLoginLogMapper.deleteToeknByUserId(userId, "3");
        //记录登录日志
        SsUserLoginLog userLoginLog = new SsUserLoginLog();
        userLoginLog.setState("3");//来源 学生端app
        userLoginLog.setUserId(userId);
        userLoginLog.setToken(token);
        userLoginLogMapper.insert(userLoginLog);
    }


}
