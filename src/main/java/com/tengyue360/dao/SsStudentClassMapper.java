package com.tengyue360.dao;

import com.tengyue360.bean.SsStudentClass;

import java.util.List;

/**
 * @author panjt
 * @date 2018/8/15 下午7:04
 */
public interface SsStudentClassMapper {
    /**
     * 根据学生id查询班级
     * @param id
     * @return
     */
    List<SsStudentClass> findClassByStudentId(Integer id);
}
