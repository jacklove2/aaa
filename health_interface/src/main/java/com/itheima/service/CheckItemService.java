package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-21 21:37
 * @Package com.itheima.service
 */
public interface CheckItemService {
    //添加检查项
    void save(CheckItem checkItem);
    //查询分页数据
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);
    //获取所有检查项信息
    List<CheckItem> findAll();
    //删除检查项
    void deleteById(int id);
    //查询检查项
    CheckItem findCheckItme(int id);
    //更新检查项
    void updataCheckItme(CheckItem checkItem);
}
