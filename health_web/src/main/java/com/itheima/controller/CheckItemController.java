package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-21 21:12
 * @Package com.itheima.controller
 */
//ioc且封装json数据
@RestController
@RequestMapping("/CheckItem")
public class CheckItemController {
    //注入
    @Reference
    private CheckItemService checkItemService;

    //添加检查项
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem) {
        //调用业务层方法保存检查项信息
        checkItemService.save(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS, null);

    }

    //分页
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        //调用业务层方法查询分页信息
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    //查询检查项
    @GetMapping("/findAll")
    public Result findAll() {
        //调用业务层方法查询checkItem
        List<CheckItem> checkItemList = checkItemService.findAll();
        //返回结果
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemList);
    }

    //删除检查项
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result deleteById(@RequestParam("id") int id) {
        //调用业务层方法删除id
        checkItemService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    //查询当前检查项信息
    @GetMapping("/findCheckItem")
    public Result findCheckItem(@RequestParam("id") int id) {
        //调用业务层的方法
        CheckItem checkItem = checkItemService.findCheckItme(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkItem);
    }

    //修改检查项信息
    @PostMapping("/update")
    public Result updataCheckItme(@RequestBody CheckItem checkItem) {
        //调用业务层方法修改检查项信息
        checkItemService.updataCheckItme(checkItem);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

}