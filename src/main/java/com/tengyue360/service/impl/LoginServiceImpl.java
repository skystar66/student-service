package com.tengyue360.service.impl;

import com.tengyue360.bean.SsUStudent;
import com.tengyue360.bean.SsUser;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.constant.RedisConstants;
import com.tengyue360.dao.SsUStudentMapper;
import com.tengyue360.dao.SsUserMapper;
import com.tengyue360.pool.ThreadProvider;
import com.tengyue360.service.LoginService;
import com.tengyue360.service.SsStudentService;
import com.tengyue360.service.TokenManagerService;
import com.tengyue360.service.UserLoginLogService;
import com.tengyue360.utils.CommonBeanUtils;
import com.tengyue360.utils.TokenFactory;
import com.tengyue360.vo.StudentVo;
import com.tengyue360.web.requestModel.LoginRequestModel;
import com.tengyue360.web.responseModel.AccountInfoResponseModel;
import com.tengyue360.web.responseModel.ResponseResult;
import com.tengyue360.web.responseModel.UserResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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
    UserLoginLogService userLoginLogService;

    @Autowired
    SsUStudentMapper studentMapper;

    @Autowired
    TokenManagerService tokenManagerService;

    private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    /**
     * 密码登录服务
     *
     * @return
     * @throws Exception
     */
    @Override
    public ResponseResult login(LoginRequestModel model) {
        long startcheck1 = System.currentTimeMillis();
        ResponseResult result = new ResponseResult();
        try {
            SsUser user = userMapper.login(model.getPhone());
            logger.info("查询消费时间：" + Math.abs(System.currentTimeMillis() - startcheck1));
            if (user == null) {
                //用户不存在
                result.setErrno(ReturnCode.NAME_PWD_FALSE.code());
                result.setError(ReturnCode.NAME_PWD_FALSE.msg());
                result.setData(null);
                return result;
            } else if (user != null && model.getPassword().equals(user.getPassword())) {
                if (user.getDeleteState().equals("0")) {
                    //表示该用户状态 不可用 通知后台处理
                    result.setErrno(ReturnCode.ERROR_EXPIRED.code());
                    result.setError(ReturnCode.ERROR_EXPIRED.msg());
                    result.setData(null);
                    return result;
                }
                long startcheck2 = System.currentTimeMillis();
                StudentVo studentVo = tokenManagerService.getToekn(model.getPhone());
                logger.info("生成token消费时间：" + Math.abs(System.currentTimeMillis() - startcheck2));
                //记录登录日志
                saveloginLog(studentVo.getId(), studentVo.getToken());
                result.setToken(studentVo.getToken());
            } else {
                result.setErrno(ReturnCode.NAME_PWD_FALSE.code());
                result.setError(ReturnCode.NAME_PWD_FALSE.msg());
                result.setData(null);
                return result;
            }
            result.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
            result.setError(ReturnCode.ACTIVE_SUCCESS.msg());
            //封装返回参数
            UserResponseModel userResponseModel = new UserResponseModel();
            CommonBeanUtils.copyProperties(user, userResponseModel);
            result.setData(userResponseModel);
        } catch (Exception e) {
            logger.error("系统异常", e);
            result.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
            result.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
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
                result.setErrno(ReturnCode.NAME_PWD_FALSE.code());
                result.setError(ReturnCode.NAME_PWD_FALSE.msg());
                result.setData(null);
                return result;
            } else if (user.getDeleteState().equals("0")) {
                //表示该用户状态 不可用 通知后台处理
                result.setErrno(ReturnCode.ERROR_EXPIRED.code());
                result.setError(ReturnCode.ERROR_EXPIRED.msg());
                result.setData(null);
                return result;
            } else {
                //获取学生端 app jwt topken
                StudentVo studentVo = tokenManagerService.getToekn(model.getPhone());
                //记录登录日志
                saveloginLog(studentVo.getId(), studentVo.getToken());
                //删除当日获取5次的验证码hash key
                redisTemplate.delete(RedisConstants.REDIS_TWENTY_FOUR_CODE_COUNT + model.getPhone());

                //记录登录日志
                saveloginLog(user.getId(), studentVo.getToken());

                result.setToken(studentVo.getToken());
                result.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
                result.setError(ReturnCode.ACTIVE_SUCCESS.msg());
                //封装返回参数
                UserResponseModel userResponseModel = new UserResponseModel();
                CommonBeanUtils.copyProperties(user, userResponseModel);
                return result;
            }
        } catch (Exception e) {
            logger.error("系统异常", e);
            result.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
            result.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
            result.setData(null);
        }
        return result;
    }

    /**
     * 记录用户登录日志11
     *
     * @return
     * @throws Exception
     */
    public void saveloginLog(Integer userId, String token) {
        ThreadProvider.getThreadPool().execute(() -> {
            userLoginLogService.saveloginLog(userId, token);
        });
    }


}
