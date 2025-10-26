package com.itheima.anno;

import com.itheima.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented//元注解
@Target(ElementType.FIELD)//在属性上
@Retention(RetentionPolicy.RUNTIME)//运行时
@Constraint(validatedBy = {StateValidation.class})//哪个类提供校验规则
public @interface State {
    String message() default  "state只能是已发布或者草稿";
    //指定分组
    Class<?>[] groups() default {};
    //负载
    Class<? extends Payload>[] payload () default {};
}
