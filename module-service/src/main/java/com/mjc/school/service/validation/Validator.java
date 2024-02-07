package com.mjc.school.service.validation;

import com.mjc.school.service.dto.author.AuthorDTORequest;
import com.mjc.school.service.dto.news.NewsDTORequest;
import com.mjc.school.service.exceptions.ExceptionService;
import com.mjc.school.service.exceptions.InputExceptions;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    private static final int CHAR_MIN = 5;
    private static final int NEWS_TITLE_MAX = 30;
    private static final int NEWS_CONTENT_MAX = 255;
    private static final int AUTHOR_NAME_MAX = 30;

    public void checkNewsId(Long id) {
        checkFormat(id, ExceptionService.Constants.NEWS);
        checkId(id, ExceptionService.Constants.NEWS);
    }

    public void checkAuthorId(Long id) {
        checkFormat(id, ExceptionService.Constants.AUTHOR);
        checkId(id, ExceptionService.Constants.AUTHOR);
    }

    public void checkTitle(String title) {
        checkCharLength(title, ExceptionService.Constants.NEWS, ExceptionService.Constants.TITLE, NEWS_TITLE_MAX);
    }

    public void checkContent(String content) {
        checkCharLength(content, ExceptionService.Constants.NEWS, ExceptionService.Constants.CONTENT, NEWS_CONTENT_MAX);
    }

    public void checkName(String name) {
        checkCharLength(name, ExceptionService.Constants.AUTHOR, ExceptionService.Constants.NAME, AUTHOR_NAME_MAX);
    }

    public void checkFormat(Long id, String entity){
        try {
            if (!NumberUtils.isParsable(Long.toString(id)))
                throw new InputExceptions(String.format(ExceptionService.ERROR_FORMAT.getErrorInfo(entity)));
        } catch (InputExceptions e) {
            System.out.println(e.getErrorMessage());
        }
    }

    public void checkId(Long id, String entity){
        try {
            if (id == null || id <= 0)
                throw new InputExceptions(String.format(ExceptionService.ERROR_ID_LENGTH.getErrorInfo(entity, id)));
        } catch (InputExceptions e) {
            System.out.println(e.getErrorMessage());
        }
    }

    public void checkCharLength(String str, String object, String entity, int max) {
        try {
            if (str.length() < CHAR_MIN || str.length() > max)
                throw new InputExceptions(String.format(ExceptionService.ERROR_CHAR_LENGTH.getErrorInfo(entity, object, max)));
        } catch (InputExceptions e) {
            System.out.println(e.getErrorMessage());
        }
    }

    public void checkNewsDto(NewsDTORequest newsDTORequest) throws InputExceptions {
        checkTitle(newsDTORequest.getTitle());
        checkContent(newsDTORequest.getContent());
        checkAuthorId(newsDTORequest.getAuthorId());
    }

    public void checkAuthorDto(AuthorDTORequest authorDTORequest) throws InputExceptions {
        checkAuthorId(authorDTORequest.getId());
        checkName(authorDTORequest.getName());
    }
}