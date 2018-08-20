package com.tengyue360.service;

/**
 * 登陆日志管理
 *
 * @author xuliang
 * @date 2018/8/10 10:03
 */
public interface UserLoginLogService {


    /**
     * 增加删除 app 登录日志
     *
     * @return
     * @throws Exception
     */

    int saveloginLog(Integer userId, String token);


}
