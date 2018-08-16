package com.tengyue360.dao;

import com.tengyue360.bean.SsKlass;
import com.tengyue360.bean.SsStudentClass;

import java.util.List;

/**
 * @author panjt
 * @date 2018/8/15 下午7:04
 */
public interface SsKlassMapper {
    /**
     * 根据班级id查询班级信息
     * @param id
     * @return
     */
    List<SsKlass> findByKlassId(Integer id);
}
