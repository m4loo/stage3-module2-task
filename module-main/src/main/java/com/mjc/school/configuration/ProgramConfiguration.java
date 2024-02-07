package com.mjc.school.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.mjc.school.repository",
        "com.mjc.school.controller",
        "com.mjc.school.service",
        "com.mjc.school"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ProgramConfiguration {

}