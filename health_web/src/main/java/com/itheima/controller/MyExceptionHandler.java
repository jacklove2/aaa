package com.itheima.controller;

import com.itheima.entity.Result;
import com.itheima.exception.MyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.AccessException;
import java.util.List;

/**
 * @atuthor JackLove
 * @date 2019-09-24 12:32
 * @Package com.itheima.controller
 */
//拦截controller抛出的异常
@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(MyException.class)
    public Result handlerMyException(MyException my) {
        my.printStackTrace();
        // 包装一下返回的结果
        return new Result(false, my.getLocalizedMessage());
    }

    //声明要捕获的异常类
    @ExceptionHandler(Exception.class)
    public Result handlerMyException(Exception unknown) {
        unknown.printStackTrace();
        // 包装一下返回的结果
        return new Result(false, "发生异常了");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result noAccessHandler(AccessDeniedException accessDeniedException) {
        return new Result(false, "权限不足");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result noLivaliHandler(MethodArgumentNotValidException e) {
        // 校验的结果
        BindingResult bindingResult = e.getBindingResult();
        // 校验没通过的属性
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuffer sb = new StringBuffer();
        if (null != fieldErrors) {
            for (FieldError error : fieldErrors) {
                sb.append(error.getField() + ":" + error.getDefaultMessage());
            }
        }
        return new Result(false, sb.toString());
    }
}
