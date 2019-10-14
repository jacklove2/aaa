package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.MyException;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @atuthor JackLove
 * @date 2019-09-21 21:37
 * @Package com.itheima.service.impl
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    //保存检查项信息
    @Override
    public void save(CheckItem checkItem) {
        checkItemDao.save(checkItem);

    }

    //封装分页信息
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //有查询条件,实现模糊查询
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //调用dao层的方法查询分页数据
        Page<CheckItem> page = checkItemDao.findAll(queryPageBean.getQueryString());
        //将总记录条数和当前页数据设置到分页结果中
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(), page.getResult());
        return pageResult;
    }

    //查询检查项
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAllCheckitem();
    }

    //删除检查项
    @Override
    public void deleteById(int id) throws MyException {
        //判断检查项是否被检查组使用了
        int count = checkItemDao.findCountByCheckItemId(id);
        if (count > 0) {
            throw new RuntimeException(MessageConstant.DELETE_CHECKITEM_FAIL_USED);
        }
        //没有被检查组使用则删除
        checkItemDao.deleById(id);
    }
    //回显检查项
    @Override
    public CheckItem findCheckItme(int id) {
        //调用dao层的方法查询检查项信息
        return checkItemDao.findCheckItmeById(id);
    }
    //修改检查项
    @Override
    public void updataCheckItme(CheckItem checkItem) {
        checkItemDao.updataCheckItme(checkItem);
    }

}
