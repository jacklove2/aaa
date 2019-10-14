package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.Map;

/**
 * @atuthor JackLove
 * @date 2019-10-07 0:02
 * @Package com.itheima.service
 */
public interface MemberService {
    //通过手机号码查询
    Member findByTelephone(String telephone);
    //添加会员信息
    void add(Member member);
    //查询会员数据
    Map<String,Object> getMemberReport();
}
