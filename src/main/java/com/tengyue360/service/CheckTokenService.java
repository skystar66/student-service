package com.tengyue360.service;

import com.tengyue360.web.responseModel.ResponseResult;

public interface CheckTokenService {

    public ResponseResult checkToken(String tokenId,String dataId,String phone);

}
