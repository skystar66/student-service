package com.tengyue360.service.impl;

import com.tengyue360.bean.SsUserLoginLog;
import com.tengyue360.dao.SsUserLoginLogMapper;
import com.tengyue360.service.UserLoginLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLoginLogServiceImpl implements UserLoginLogService {

    private static Logger logger = LoggerFactory.getLogger(UserLoginLogServiceImpl.class);

    @Autowired
    SsUserLoginLogMapper userLoginLogMapper;
    /**
     * 增加删除 app 登录日志
     *
     * @return
     * @throws Exception
     */

    @Override
    @Transactional
    public int saveloginLog(Integer userId, String token) {
        long startcheck1 = System.currentTimeMillis();
        //删除该用户PC端的登陆token日志
        userLoginLogMapper.deleteToeknByUserId(userId, "3");
        logger.info("删除消费时间：" + Math.abs(System.currentTimeMillis() - startcheck1));
        long startcheck2 = System.currentTimeMillis();
        //记录登录日志
        SsUserLoginLog userLoginLog = new SsUserLoginLog();
        userLoginLog.setState("3");//来源 学生端app
        userLoginLog.setUserId(userId);
        userLoginLog.setToken(token);
        int num = userLoginLogMapper.insert(userLoginLog);
        logger.info("新增消费时间：" + Math.abs(System.currentTimeMillis() - startcheck2));
        return num;
    }
}
