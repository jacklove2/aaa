package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;


/**
 * @atuthor JackLove
 * @date 2019-09-24 10:02
 * @Package com.itheima.service
 */
@Service
public interface CheckGroupService {
    //添加检查组
    void add(CheckGroup checkGroup, Integer[] checkitemIds) throws Exception;
    //查询检查组列表信息
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);
    //查询检查项的id的集合
    List<Integer> findCheckItemIdsById(int id);
    //查询检查组的基本信息的id
    CheckGroup findById(int id);
    //修改检查组信息
    void update(CheckGroup checkGroup, Integer[] checkitemIds);
    //查询所有
    List<CheckGroup> findAll();
}
