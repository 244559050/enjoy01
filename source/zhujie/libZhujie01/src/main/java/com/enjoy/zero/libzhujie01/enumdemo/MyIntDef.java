package com.enjoy.zero.libzhujie01.enumdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.ANNOTATION_TYPE})
public @interface MyIntDef {
        int[] value() default {};
        boolean flag() default false;
}