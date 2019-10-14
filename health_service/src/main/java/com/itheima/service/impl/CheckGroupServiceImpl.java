package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-24 10:02
 * @Package com.itheima.service.impl
 */
//IOC
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    //DI
    @Autowired
    private CheckGroupDao checkGroupDao;

    //添加检查组
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) throws Exception {
        //调用dao添加检查组
        checkGroupDao.add(checkGroup);
        //通过selectKey
        Integer checkGroupId = checkGroup.getId();
        //判断id不为空
        if (checkitemIds != null) {
            //遍历那代checkitemId
            for (Integer checkitemId : checkitemIds) {
                //添加检查组和检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkitemId);
            }
        }
    }

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        //拿到用户传过来的关键字
        //判断字符串是否为空,不为空则拼接
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        //通过分页插件进行分页
        PageHelper.startPage(currentPage, pageSize);
        //调用dao层方法查询分页结果
        Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean.getQueryString());
        //封装总记录数和分页数据
        PageResult<CheckGroup> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;

    }

    //根据id查找检查项的id信息
    @Override
    public List<Integer> findCheckItemIdsById(int id) {
        return checkGroupDao.findCheckItemIdsById(id);
    }

    //根据id查找检查组基本信息的id
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    //修改检查组id
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //调用业务层方法更新检查组信息
        checkGroupDao.update(checkGroup);
        //在执行修改之后拿到checkGroupId
        Integer checkGroupId = checkGroup.getId();
        if (checkitemIds != null) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.updateCheckGroupCheckItem(checkGroupId, checkitemId);
            }
        }
    }
    //查询所有的检查组
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
