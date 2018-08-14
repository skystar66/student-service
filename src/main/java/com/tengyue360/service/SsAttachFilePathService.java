package com.tengyue360.service;


import com.tengyue360.bean.SsAttachFilePath;
import com.tengyue360.web.requestModel.UploadFileRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;

/**
 * 附件服务
 *
 * @author xuliang
 * @date 2018/8/10 10:03
 */
public interface SsAttachFilePathService {




    /**
     * 保存附件信息
     *
     * @return
     * @throws Exception
     */

    ResponseResult saveAttachaFile(UploadFileRequestModel model);





}
