package com.mjc.school.controller.annotations;

import com.mjc.school.controller.Commands.CommandType;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandHandler {
    CommandType value();
}
