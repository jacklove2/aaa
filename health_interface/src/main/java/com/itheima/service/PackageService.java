package com.itheima.service;

import com.itheima.pojo.Package;

import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-26 12:11
 * @Package com.itheima.service
 */
public interface PackageService {
    //添加套餐信息
    void addPackage(Package pkg, Integer[] checkgroupIds);
    //查询套餐列表
    List<Package> findAll();
    //查询套餐详情
    Package findById(Integer id);
    //查询套餐基本详情
    Package findPackageById(Integer id);
}
