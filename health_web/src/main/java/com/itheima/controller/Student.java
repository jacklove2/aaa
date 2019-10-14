package com.itheima.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @atuthor JackLove
 * @date 2019-09-27 13:50
 * @Package com.itheima.controller
 */
public class Student {
    String name;
    int age;
    String gender;

    public Student(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf =new SimpleDateFormat("d");
        System.out.println(sdf.format(new Date()));
    }
}
