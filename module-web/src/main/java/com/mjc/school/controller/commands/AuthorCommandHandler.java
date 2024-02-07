package com.mjc.school.controller.commands;

import com.mjc.school.controller.annotations.CommandHandler;
import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.service.dto.author.AuthorDTORequest;
import com.mjc.school.service.dto.author.AuthorDTORespond;
import com.mjc.school.service.exceptions.ExceptionService;
import com.mjc.school.service.exceptions.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public class AuthorCommandHandler implements BaseCommandHandler<AuthorController, AuthorDTORespond> {

    AuthorDTORequest authorDTORequest;

    @Override
    @SuppressWarnings("unchecked")
    @SneakyThrows
    public String handleCommand(AuthorController controller, String buttonName) {

        CommandType commandType = Arrays.stream(CommandType.values())
                .filter(type -> buttonName.contains(type.name()))
                .findFirst()
                .orElse(null);

        Method method = Stream.of(controller.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(CommandHandler.class))
                .filter(m -> m.getAnnotation(CommandHandler.class).value().equals(commandType))
                .findFirst()
                .orElse(null);
        try {
            if (method != null) {
                return switch (commandType) {
                    case READ_ALL ->
                            toString((List<AuthorDTORespond>) method.invoke(controller));
                    case READ_BY_ID ->
                            toString((AuthorDTORespond) method.invoke(controller, authorDTORequest.getId()));
                    case CREATE, UPDATE ->
                            toString((AuthorDTORespond) method.invoke(controller, authorDTORequest));
                    case DELETE_BY_ID ->
                            String.valueOf(method.invoke(controller, authorDTORequest.getId()));
                };
            } else throw new NotFoundException(ExceptionService.ERROR_COMMAND_NOT_FOUND.getErrorInfo());
        } catch (NotFoundException e) {
            System.out.println(e.getErrorMessage());
        }
        return null;
    }

    public void createRequest(Long id, String name) {
        this.authorDTORequest = new AuthorDTORequest(id, name);
    }

    @Override
    public String toString(AuthorDTORespond authorDTORespond) {
        return "NewsDtoResponse[id=" + authorDTORespond.getId()
                + ", name=" + authorDTORespond.getName()
                + ", createDate=" + authorDTORespond.getCreateDate()
                + ", lastUpdatedDate=" + authorDTORespond.getLastUpdateDate()
                + "]";
    }

    @Override
    public String toString(List<AuthorDTORespond> authorDTORespondList) {
        StringBuilder result = new StringBuilder();
        for (AuthorDTORespond authorDTORespond : authorDTORespondList)
            result.append(toString(authorDTORespond)).append("\n");
        return result.toString();
    }
}