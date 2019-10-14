package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-24 10:02
 * @Package com.itheima.controller
 */
//ioc以及封装json数据
@RestController
//配置映射参数
@RequestMapping("/checkGroup")
public class CheckGroupController {
    //Di
    @Reference
    private CheckGroupService checkGroupService;

    //配置方法的映射路径
    @PostMapping("/add")
    //添加方法
    public Result add(@Validated @RequestBody CheckGroup checkGroup, @RequestParam("checkitemIds") Integer[] checkitemIds) throws Exception {
        //调用业务层方法
        checkGroupService.add(checkGroup, checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS, null);
    }

    //查询分页的方法
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        //调用业务层方法查询分页信息
        PageResult<CheckGroup> checkGroupList = checkGroupService.findPage(queryPageBean);
        //封装分组列表的
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroupList);
    }

    //编辑检查组
    //1.获取检查组基本信息的id
    @GetMapping("/findById")
    public Result findById(int id) {
        //调用业务层方法查询检查组id
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkGroup);
    }

    //2.获取检查项的id集合
    @GetMapping("/findCheckItemIdsById")
    public Result findCheckItemIdsById(int id) {
        //调用业务层的方法查询检查组id
        List<Integer> list = checkGroupService.findCheckItemIdsById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
    }


    //3.修改检查组
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, @RequestParam("checkitemIds") Integer[] checkitemIds) {
        checkGroupService.update(checkGroup, checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupList);
    }
}
