package com.tengyue360.web.controller;


import com.tengyue360.service.SsStartLogService;
import com.tengyue360.web.responseModel.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * loading
 *
 * @author xuliang
 * @date 2018/8/14 上午11:22
 */

@RestController
public class StartController {

    @Autowired
    SsStartLogService logService;


    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ResponseResult start() {
        return logService.queryStartLog();
    }


}
