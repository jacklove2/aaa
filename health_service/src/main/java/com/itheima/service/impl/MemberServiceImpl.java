package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @atuthor JackLove
 * @date 2019-10-07 0:02
 * @Package com.itheima.service.impl
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    //手机号查询会员信息
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    //添加会员
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    //查询会员信息
    @Override
    public Map<String, Object> getMemberReport() {
        //当前月份时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.MONTH, -12);
        //存放月份的集合
        List<Object> months = new ArrayList<>();
        //存放会员数据的集合
        List<Object> memberCount = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        for (int i = 0; i < 12; i++) {
            //计算当前月的值
            calendar.add(Calendar.MONTH, 1);
            String month = sdf.format(calendar.getTime());
            months.add(month);
            Integer monthCount = memberDao.findMemberCountBeforeDate(month + "-31");
            memberCount.add(monthCount);
        }
        //2.循环12个月,每月查询一次
        //3.封装数据,月份数和会员数
        Map<String, Object> map = new HashMap<>();
        map.put("months",months);
        map.put("memberCount",memberCount);
        return map;
    }
}
