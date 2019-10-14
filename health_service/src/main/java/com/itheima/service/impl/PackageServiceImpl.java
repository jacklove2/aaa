package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.dao.PackageDao;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.support.ObjectNameManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-26 12:10
 * @Package com.itheima.service.impl
 */
@Service(interfaceClass = PackageService.class)
//ioc
public class PackageServiceImpl implements PackageService {
    //di
    @Autowired
    private PackageDao packageDao;
    @Autowired
    private JedisPool jedisPool;

    //添加套餐
    @Override
    //添加事务控制
    public void addPackage(Package pkg, Integer[] checkgroupIds) {
        //添加套餐
        packageDao.addPackage(pkg);
        //通过selectkey方法拿到pkgId
        Integer package_Id = pkg.getId();
        //遍历出检查组的id
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                //调用业务层的方法向中间表插入数据
                packageDao.addcheckgroupId(package_Id, checkgroupId);
            }
        }
    }

    //查询套餐列表
    @Override
    public List<Package> findAll() {
        //拿到jedis连接对象
        Jedis jedis = jedisPool.getResource();
        //通过key拿到健值
        String pckageJson = jedis.get("package");
        Gson gson = new Gson();
        //判断redis是否存在
        if (pckageJson == null || "".equals(pckageJson)) {
            //不存在调用dao层进行查找
            List<Package> packageList = packageDao.findAll();
            //将json字符串转化为json字符串
            String jsonStr = gson.toJson(packageList);
            //存入redis
            jedis.set(pckageJson, jsonStr);
        }
        Type typeOfT = new TypeToken<List<Package>>() {
        }.getType();
        //将json字符串转化为list
        List<Package> packageList = gson.fromJson(pckageJson, typeOfT);
        jedis.close();
        return packageList;
    }

    //查询套餐详情
    @Override
    public Package findById(Integer id) {
        return packageDao.findById(id);
    }

    //查询套餐基本详情
    @Override
    public Package findPackageById(Integer id) {
        return packageDao.findPackageById(id);
    }
}

