package com.tengyue360.service;

import com.tengyue360.bean.SsUser;
import com.tengyue360.web.requestModel.LoginRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;

/**
 * 登录模块服务
 *
 * @author xuliang
 * @date 2018/8/10 10:03
 */
public interface LoginService {


    /**
     * 用户密码登录
     *
     * @return
     * @throws Exception
     */

    ResponseResult login(LoginRequestModel model);



    /**
     * 验证码登录
     *
     * @return
     * @throws Exception
     */

    ResponseResult codeLogin(LoginRequestModel model);


}
