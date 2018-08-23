package com.tengyue360.service.impl;

import com.tengyue360.bean.SsUStudent;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.dao.SsUStudentMapper;
import com.tengyue360.service.CheckTokenService;
import com.tengyue360.web.responseModel.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 校验token
 *
 * @return
 * @throws Exception
 */
@Service
public class CheckTokenServiceImpl implements CheckTokenService {

    private static Logger logger = LoggerFactory.getLogger(CheckTokenServiceImpl.class);

    @Autowired
    SsUStudentMapper studentMapper;

    /**
     * 校验token
     *
     * @return
     * @throws Exception
     */
    @Override
    public ResponseResult checkToken(String tokenId, String dataId, String phone) {
        try {
            //根据学员id查询家长手机号
            SsUStudent student = studentMapper.selectByPrimaryKey(Integer.parseInt(tokenId));
            if (null != student) {
                if (StringUtils.isNotBlank(phone)) {
                    if (!student.getParentPhone().equals(phone)) {
                        return ResponseResult.onFailure(null, ReturnCode.CURRENT_STUDENT_ID_ERROR);
                    }
                }
            } else if (StringUtils.isNotBlank(dataId)) {
                //表示该家长手机号不挂学员id
                if (!tokenId.equals(dataId)) {
                    return ResponseResult.onFailure(null, ReturnCode.CURRENT_STUDENT_ID_ERROR);
                }
            }

        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return null;
    }
}
