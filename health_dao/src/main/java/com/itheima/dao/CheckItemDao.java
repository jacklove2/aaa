package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-21 21:45
 * @Package com.itheima.dao
 */
public interface CheckItemDao {
    //添加检查项
    void save(CheckItem checkItem);
    //有条件的分页查询
    Page<CheckItem> findAll(String queryString);
    //查询所有检查项
    List<CheckItem> findAllCheckitem();
    //查询检查组的id
    int findCountByCheckItemId(int id);
    //删除检查项
    void deleById(int id);
    //回显检查项信息
    CheckItem findCheckItmeById(int id);
    //更新检查项信息
    void updataCheckItme(CheckItem checkItem);
}
