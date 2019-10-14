package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-24 10:01
 * @Package com.itheima.dao
 */
public interface CheckGroupDao {
    //添加检查组
    void add(CheckGroup checkGroup);
    //设置检查组和检查项的关系
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkitemId") Integer checkitemId);
    //模糊查询分页
    Page<CheckGroup> findPage(String queryString);
    //查询检查项id集合
    List<Integer> findCheckItemIdsById(int id);
    //查询检查组的基本信息的id
    CheckGroup findById(int id);
    //修改检查组信息
    void update(CheckGroup checkGroup);
    //更新中间表的信息
    void updateCheckGroupCheckItem(Integer checkGroupId, Integer checkitemId);
    //查询所有的检查组
    List<CheckGroup> findAll();
}
