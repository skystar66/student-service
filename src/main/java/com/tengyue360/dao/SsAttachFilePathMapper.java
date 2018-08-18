package com.tengyue360.dao;

import com.tengyue360.bean.SsAttachFilePath;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SsAttachFilePathMapper {
    int deleteByPrimaryKey(String id);

    int insert(SsAttachFilePath record);

    int insertSelective(SsAttachFilePath record);

    SsAttachFilePath selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SsAttachFilePath record);

    int updateByPrimaryKey(SsAttachFilePath record);


    /**
     * 根据关联id查询url
     *
     * @return
     * @throws Exception
     */

    List<SsAttachFilePath> queryUrlByReleationId(@Param("releationId") String releationId);



}