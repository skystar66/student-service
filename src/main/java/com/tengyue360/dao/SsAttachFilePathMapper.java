package com.tengyue360.dao;

import com.tengyue360.bean.SsAttachFilePath;
import org.springframework.stereotype.Repository;

@Repository
public interface SsAttachFilePathMapper {
    int deleteByPrimaryKey(String id);

    int insert(SsAttachFilePath record);

    int insertSelective(SsAttachFilePath record);

    SsAttachFilePath selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SsAttachFilePath record);

    int updateByPrimaryKey(SsAttachFilePath record);
}