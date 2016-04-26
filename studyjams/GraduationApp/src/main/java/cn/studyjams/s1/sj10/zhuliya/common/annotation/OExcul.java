package cn.studyjams.s1.sj10.zhuliya.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Gson反序列化排除注解
 * Created by jan on 16/4/26.
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface OExcul {
}
