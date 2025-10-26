package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor//无参构造方法
@AllArgsConstructor//全参构造方法
//统一响应结果
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    //返回操作成功
    public static <E> Result<E> success(E data){
        return new Result<>(0,"操作成功",data);
    }
    public static <E> Result<E> success(E data ,String message){
        return new Result<>(0,message,data);
    }
    //无data时
    public static <E> Result<E> success(){
        return new Result<>(0,"操作成功",null);
    }
    //返回操作失败
    public static <E> Result<E> error(String message){
        return new Result<>(1,message,null);
    }
    public static <E> Result<E> error(){
        return new Result<>(1,"操作失败",null);
    }
}
