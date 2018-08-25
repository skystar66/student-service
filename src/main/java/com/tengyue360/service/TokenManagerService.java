package com.tengyue360.service;


import com.tengyue360.vo.StudentVo;

import java.util.Map;

/**
 * token服务
 *
 * @author xuliang
 * @date 2018/8/10 10:03
 */
public interface TokenManagerService {




    /**
     * 增加删除 app 登录日志
     *
     * @return
     * @throws Exception
     */

    StudentVo getToekn(String phone);

}
