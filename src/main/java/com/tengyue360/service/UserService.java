package com.tengyue360.service;


import com.tengyue360.web.requestModel.UserRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;

/**
 * 用户模块服务
 *
 * @author xuliang
 * @date 2018/8/10 10:03
 */
public interface

UserService {


    /**
     * 修改密码
     *
     * @return
     * @throws Exception
     */

    public ResponseResult updatePwd(UserRequestModel model);



    /**
     * 忘记密码
     *
     * @return
     * @throws Exception
     */

    public ResponseResult backPwd(UserRequestModel model);




    /**
     * 退出登录
     *
     * @return
     * @throws Exception
     */

    public ResponseResult loginOut(UserRequestModel model);


}
