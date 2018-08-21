package com.tengyue360.service;


import com.tengyue360.bean.SsOpinionFeedback;
import com.tengyue360.web.requestModel.StudentRequestModel;
import com.tengyue360.web.requestModel.UserRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;

/**
 * 学生服务
 *
 * @author xuliang
 * @date 2018/8/10 10:03
 */
public interface SsStudentService {

    /**
     * 根据家长电话查询该用户下的学员信息
     *
     * @return
     * @throws Exception
     */

    public ResponseResult queryStudentByPhone(UserRequestModel model);


    /**
     * 根据学生id查询学员信息
     *
     * @return
     * @throws Exception
     */

    public ResponseResult queryStudentById(StudentRequestModel model) ;


    /**
     * 根据学生id修改学员信息
     *
     * @return
     * @throws Exception
     */

    public ResponseResult updateStudentById(StudentRequestModel model);

    /**
     * 根据id查询学生信息
     * @param ssOpinionFeedback
     * @return
     * @throws Exception
     */
    public ResponseResult findById(SsOpinionFeedback ssOpinionFeedback);


    /**
     * 根据条件查询学生列表
     * @param queryElement
     * @return
     */
    ResponseResult queryStudentList(String queryElement,Integer pageNum,Integer pageSize);
}
