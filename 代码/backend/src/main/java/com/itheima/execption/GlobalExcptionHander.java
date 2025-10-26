package com.itheima.execption;


import com.itheima.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExcptionHander {

    @ExceptionHandler
    public Result handleException(Exception e){
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())? e.getMessage():"操作失败");
    }
}
