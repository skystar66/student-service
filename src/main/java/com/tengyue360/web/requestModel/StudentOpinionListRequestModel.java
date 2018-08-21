package com.tengyue360.web.requestModel;

/**
 * 封装学员信息
 *
 * @author xuliang
 * @date 2018/8/10 12:37
 */

public class StudentOpinionListRequestModel extends BaseRequestModel {


    private String queryElement;//查询信息项

    private Integer pageNum;//分页页数
    private Integer pageSize;//分页条数

    public String getQueryElement() {
        return queryElement;
    }

    public void setQueryElement(String queryElement) {
        this.queryElement = queryElement;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "StudentOpinionListRequestModel{" +
                "queryElement='" + queryElement + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}



