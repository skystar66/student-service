package com.tengyue360.service.impl;

import com.tengyue360.bean.SSstartLog;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.dao.SSstartLogMapper;
import com.tengyue360.pool.ThreadProvider;
import com.tengyue360.service.SsStartLogService;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 启动记录日志服务
 *
 * @author xuliang
 * @date 2018/8/10 10:03
 */
@Service
public class SsStartLogServiceImpl implements SsStartLogService {

    private static Logger logger = LoggerFactory.getLogger(SsStartLogServiceImpl.class);

    @Autowired
    SSstartLogMapper logMapper;


    /**
     * 启动
     *
     * @return
     * @throws Exception
     */

    @Override
    public ResponseResult queryStartLog() {

        ResponseResult responseResult = new ResponseResult();
        try {
            SSstartLog user = logMapper.selectByPrimaryKey("1");
            responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
            responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
            responseResult.setData(user);
        } catch (Exception e) {
            logger.error("系统异常", e);
            responseResult.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }
}
