package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import com.itheima.untils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @atuthor JackLove
 * @date 2019-09-26 0:08
 * @Package com.itheima.controller
 */
@RestController
@RequestMapping("/package")
public class PackageController {
    @Reference
    private PackageService packageService;
    @Autowired
    JedisPool jedisPool;

    //图片上传
    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        //1.拿到原图片的文件名
        String originalFilename = imgFile.getOriginalFilename();
        //2.通过处理拿到新的文件名
        //2.1.通过UUID产生唯一的key值,拿到文件名
        UUID uuid = UUID.randomUUID();
        //文件的扩展名
        //2.2 拿到文件的扩展名
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 2.3 拼接
        String newFileName = uuid.toString() + extension;
        try {
            //3.将文件转化成流
            QiNiuUtil.uploadViaByte(imgFile.getBytes(), newFileName);
            //保存到redis,所有图片
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newFileName);
            //4.将文件存到map里,文件名为key,值为上传到七牛的域名
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("picName", newFileName);
            resultMap.put("domain", QiNiuUtil.DOMAIN);
            //返回的值图片名和七牛云的访问路径
            return new Result(true, MessageConstant.UPLOAD_SUCCESS, resultMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    //添加套餐的方法
    @PostMapping("/add")
    public Result addPackage(@RequestBody Package pkg, Integer[] checkgroupIds) {
        //调用业务方法插入数据
        packageService.addPackage(pkg, checkgroupIds);
        //保存图片到redis中,DB集合
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pkg.getImg());
        return new Result(true, MessageConstant.ADD_PACKAGE_SUCCESS);
    }

}
