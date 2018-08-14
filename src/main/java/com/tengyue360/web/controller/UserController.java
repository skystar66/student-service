package com.tengyue360.web.controller;


import com.tengyue360.service.UserService;
import com.tengyue360.web.BeanValidators.BeanValidators;
import com.tengyue360.web.requestModel.UserRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户模块
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */
@RestController(value = "/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 修改密码
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/pwd", method = RequestMethod.POST)
    public ResponseResult updatePwd(UserRequestModel model) {
        logger.info("调用修改密码接口，参数信息为：{}", model);
        if (null != BeanValidators.isValidateUpdatePwd(model)) {
            logger.info("调用修改密码接口，参数信息校验失败，返回结果：{}", BeanValidators.isValidateUpdatePwd(model));
            return BeanValidators.isValidateUpdatePwd(model);
        }
        //调用后台服务
        ResponseResult responseResult = userService.updatePwd(model);
        if (null != responseResult) {
            logger.info("调用修改密码接口成功，返回结果：{}", responseResult);
            return responseResult;
        }
        return null;
    }


    /**
     * 忘记密码
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/backPwd", method = RequestMethod.POST)
    public ResponseResult backPwd(UserRequestModel model) {
        logger.info("调用忘记密码接口，参数信息为：{}", model);
        if (null != BeanValidators.isValidateBackPwd(model, redisTemplate)) {
            logger.info("调用忘记密码接口，参数信息校验失败，返回结果：{}", BeanValidators.isValidateBackPwd(model, redisTemplate));
            return BeanValidators.isValidateBackPwd(model, redisTemplate);
        }
        //调用后台服务
        ResponseResult responseResult = userService.backPwd(model);
        if (null != responseResult) {
            logger.info("调用忘记密码接口成功，返回结果：{}", responseResult);
            return responseResult;
        }
        return null;
    }


    /**
     * 登出
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    public ResponseResult loginOut(UserRequestModel model) {
        logger.info("调用退出登录接口，参数信息为：{}", model);
        if (null != BeanValidators.isValidateLoginOut(model)) {
            logger.info("调用退出登录接口，参数信息校验失败，返回结果：{}", BeanValidators.isValidateLoginOut(model));
            return BeanValidators.isValidateLoginOut(model);
        }
        //调用后台服务
        ResponseResult responseResult = userService.loginOut(model);
        if (null != responseResult) {
            logger.info("调用退出登录接口成功，返回结果：{}", responseResult);
            return responseResult;
        }
        return null;
    }


    /**
     * 根据用户id查询该用户下的学生列表
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/queryStudentsByUserId", method = RequestMethod.POST)
    public ResponseResult queryStudentsByUserId(UserRequestModel model) {






        return null;
    }


    /**
     * 根据用户id查询该用户下的学生详情
     *
     * @return
     * @throws Exception
     */


}
