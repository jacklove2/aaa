package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.PackageService;
import com.itheima.untils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.itheima.pojo.Package;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-28 20:15
 * @Package com.itheima.controller
 */
@RestController
@RequestMapping("/package")
public class PackageController {
    @Reference
    private PackageService packageService;

    //查询套餐列表
    @GetMapping("/getPackage")
    public Result getPackage() {
        //查询所有的套餐列表
        List<Package> packageList = packageService.findAll();
        if (packageList != null) {
            packageList.forEach(pkg -> {
                pkg.setImg("http://" + QiNiuUtil.DOMAIN + "/" + pkg.getImg());
            });
        }
        return new Result(true, MessageConstant.QUERY_PACKAGELIST_SUCCESS, packageList);
    }

    //查询套餐详情
    @GetMapping("/findById")
    public Result findById(Integer id) {
        //调用业务层方法查询套餐详情
        Package pkg = packageService.findById(id);
        pkg.setImg("http://" + QiNiuUtil.DOMAIN + "/" + pkg.getImg());
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS, pkg);
    }

    //查询套餐基本详情
    @GetMapping("/findPackageById")
    public Result findPackageById(Integer id) {
        //调用业务层方法查询套餐基本信息
        Package pkg = packageService.findPackageById(id);
        pkg.setImg("http://" + QiNiuUtil.DOMAIN + "/" + pkg.getImg());
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS, pkg);
    }
}
