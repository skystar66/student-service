package com.tengyue360.service.impl;

import com.tengyue360.bean.SsIntegral;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.dao.SsIntegralMapper;
import com.tengyue360.service.SsIntegralService;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: panjt
 * @Date: 2018/8/14 18:10
 */
@Service
public class SsIntegralServiceImpl implements SsIntegralService {
    Logger logger = LoggerFactory.getLogger(SsIntegralServiceImpl.class);

    @Autowired
    private SsIntegralMapper ssIntegralMapper;

    @Override
    public ResponseResult integralRecord(Integer userId) {
        logger.info("in====>{}userId:", logger);
        ResponseResult responseResult = new ResponseResult();
        try {
            int resultCount = ssIntegralMapper.selectByUsrId(userId);
            List<SsIntegral> ssIntegralList = ssIntegralMapper.integralRecord(userId);
            Map<String, Object> map = new HashMap<>();
            map.put("totalIntegral", resultCount);
            map.put("integralDetail", ssIntegralList);
            logger.info("out====>{}map", map);
            responseResult.setErrno(ReturnCode.ACTIVE_SUCCESS.code());
            responseResult.setError(ReturnCode.ACTIVE_SUCCESS.msg());
            responseResult.setData(map);
            return responseResult;
        } catch (Exception ex) {
            responseResult.setErrno(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setError(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }
}
