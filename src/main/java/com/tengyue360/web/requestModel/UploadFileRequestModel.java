package com.tengyue360.web.requestModel;


import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

/**
 * 封装上传图片请求参数
 *
 * @author xuliang
 * @date 2018/8/10 12:37
 */

public class UploadFileRequestModel extends BaseRequestModel {


    private MultipartFile file;

    private String studentId;

    private String uploadType;

    private String attachaFileId;//附件id


    public String getAttachaFileId() {
        return attachaFileId;
    }

    public void setAttachaFileId(String attachaFileId) {
        this.attachaFileId = attachaFileId;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
