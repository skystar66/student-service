package com.tengyue360.service.impl;

import com.tengyue360.bean.SsUStudent;
import com.tengyue360.dao.SsUStudentMapper;
import com.tengyue360.service.TokenManagerService;
import com.tengyue360.utils.TokenFactory;
import com.tengyue360.vo.StudentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * token服务
 *
 * @author xuliang
 * @date 2018/8/10 10:03
 */
@Service
public class TokenManagerServiceImpl implements TokenManagerService {

    private static Logger logger = LoggerFactory.getLogger(TokenManagerServiceImpl.class);

    @Autowired
    SsUStudentMapper studentMapper;

    @Override
    public StudentVo getToekn(String phone) {
        StudentVo studentVo = new StudentVo();
        try {
            //默认分配最早的 一个学生token
            List<SsUStudent> stulist = studentMapper.queryStudentByPhone(phone);
            if (null != stulist && stulist.size() > 0) {
                studentVo.setToken(TokenFactory.getInstance().createToken(stulist.get(0).getId().toString(), "3"));
                studentVo.setId(stulist.get(0).getId());
            }
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return studentVo;
    }
}
