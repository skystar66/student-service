package com.tengyue360.web.controller;

import com.tengyue360.service.ChangeStuService;
import com.tengyue360.utils.TokenFactory;
import com.tengyue360.web.BeanValidators.BeanValidators;
import com.tengyue360.web.requestModel.ChangeStuRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 切换学员身份
 *
 * @author xuliang
 * @date 2018/8/10 12:37
 */

@RestController
public class ChangeStudentController {


    private static Logger logger = LoggerFactory.getLogger(ChangeStudentController.class);


    @Autowired
    ChangeStuService stuService;

    /**
     * 切换学员身份 == 退出登录
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/changeStuInfo", method = RequestMethod.POST)
    public ResponseResult changeStuInfo(@RequestBody ChangeStuRequestModel model, HttpServletResponse response, HttpServletRequest request) {
        logger.info("调用切换学员身份接口，参数信息：{}", model);
        if (null != BeanValidators.isValidateChangeStu(model)) {
            logger.info("调用切换学员身份接口参数信息校验失败，返回结果：{}", BeanValidators.isValidateChangeStu(model));
            return BeanValidators.isValidateChangeStu(model);
        }
        model.setTokenId(TokenFactory.analysisToken(TokenFactory.SIGNING_KEY, request.getHeader(TokenFactory.HEADER_NAME)));
        ResponseResult responseResult = stuService.changeStu(model);
        if (null != responseResult) {
            response.setHeader(TokenFactory.HEADER_NAME, responseResult.getToken());
            responseResult.setToken(null);
        }
        return responseResult;


    }


}
