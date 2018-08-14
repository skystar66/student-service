package com.tengyue360.web.controller;

import com.tengyue360.service.SsAttachFilePathService;
import com.tengyue360.web.BeanValidators.BeanValidators;
import com.tengyue360.web.requestModel.UploadFileRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 上传模块
 *
 * @author xuliang
 * @date 2018/8/10 12:37
 */

@RequestMapping("/upload")
public class UploadController {

    private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    SsAttachFilePathService attachFilePathService;


    /**
     * 文件上传
     *
     * @author xuliang
     * @date 2018/8/10 12:37
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseResult upload(UploadFileRequestModel model) {
        logger.info("开始调用上传文件接口，参数信息", model);
        if (null != BeanValidators.isValidateUploadFile(model)) {
            logger.info("调用上传文件接口，参数信息校验失败，返回结果：{}", BeanValidators.isValidateUploadFile(model));
            return BeanValidators.isValidateUploadFile(model);
        }
        ResponseResult responseResult = attachFilePathService.saveAttachaFile(model);
        if (null != responseResult) {
            logger.info("调用上传文件接口成功，返回结果：{}", responseResult);
            return responseResult;
        }
        return null;

    }


}
