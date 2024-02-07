package com.mjc.school.service.aspects;

import com.mjc.school.service.dto.author.AuthorDTORequest;
import com.mjc.school.service.dto.news.NewsDTORequest;
import com.mjc.school.service.exceptions.InputExceptions;
import com.mjc.school.service.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidationAspect {

    private final Validator validator;

    @Before("@annotation(com.mjc.school.service.annotation.ValidateDto) && args(request)")
    public void checkDTO(Object request) throws InputExceptions {

        if (request instanceof AuthorDTORequest authorDTORequest) {
            validator.checkAuthorDto(authorDTORequest);
        } else if (request instanceof NewsDTORequest newsDTORequest) {
            validator.checkNewsDto(newsDTORequest);
        }
    }

    @Before("@annotation(com.mjc.school.service.annotation.ValidateNewsId) && args(id)")
    public void checkNewsId(Long id) {
        validator.checkNewsId(id);
    }

    @Before("@annotation(com.mjc.school.service.annotation.ValidateAuthorId) && args(id)")
    public void checkAuthorId(Long id) {
        validator.checkAuthorId(id);
    }
}
