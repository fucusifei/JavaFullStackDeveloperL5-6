package com.yevdokymov.task2.anotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
    String name() default "";
    String format() default "dd.MM.yyyy";
}
