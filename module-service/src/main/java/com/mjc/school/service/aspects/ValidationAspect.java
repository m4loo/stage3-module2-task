package com.mjc.school.service.aspects;

import com.mjc.school.service.exceptions.InputExceptions;
import com.mjc.school.service.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidationAspect {

    private final Validator validator;

    @Around("@annotation(com.mjc.school.service.annotation.ValidateDto) && execution(* *(..))")
    public void checkDTO(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            if (joinPoint.getArgs().length == 2) {
                validator.checkAuthorDto(
                        (String) joinPoint.getArgs()[0],
                        (String) joinPoint.getArgs()[1]
                );
                joinPoint.proceed();
            } else if (joinPoint.getArgs().length == 4) {
                validator.checkNewsDto(
                        (String) joinPoint.getArgs()[1],
                        (String) joinPoint.getArgs()[2],
                        (String) joinPoint.getArgs()[3]
                );
                joinPoint.proceed();
            }
        } catch (InputExceptions e) {
            throw new RuntimeException();
        }
    }

    @Around("@annotation(com.mjc.school.service.annotation.ValidateNewsId) && args(newsId)")
    public void checkNewsId(ProceedingJoinPoint joinPoint, String newsId) throws Throwable {
        try {
            validator.checkNewsId(newsId);
            joinPoint.proceed();
        } catch (InputExceptions e) {
            throw new RuntimeException();
        }
    }
}
