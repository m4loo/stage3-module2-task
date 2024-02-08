package com.mjc.school.service.implementation;

import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.annotation.ValidateAuthorId;
import com.mjc.school.service.annotation.ValidateDto;
import com.mjc.school.service.dto.author.AuthorDTORequest;
import com.mjc.school.service.dto.author.AuthorDTORespond;
import com.mjc.school.service.exceptions.ExceptionService;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.AuthorMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService implements BaseService<AuthorDTORequest, AuthorDTORespond, Long> {

    private final AuthorRepository authorRepository;
    AuthorMapper authorMapper = new AuthorMapper();

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDTORespond> readAll() {
        return authorMapper.convertMoledListToDTOList(authorRepository.readAll());
    }

    @SneakyThrows
    @ValidateAuthorId
    @Override
    public AuthorDTORespond readById(Long id) {
        try {
        return authorRepository
                .readById(id)
                .map(authorMapper::convertModelToDTO)
                .orElseThrow(()
                        -> new NotFoundException(
                        ExceptionService.ERROR_NOT_EXIST.getErrorInfo(
                                ExceptionService.Constants.AUTHOR,
                                id)
                ));
        } catch (NotFoundException e) {
            System.out.println(e.getErrorMessage());
        }
        return null;
    }

    @ValidateDto
    @Override
    public AuthorDTORespond create(AuthorDTORequest createRequest) {
        AuthorModel authorModel = authorMapper.convertDTOtoModel(createRequest);
        authorRepository.create(authorModel);
        return authorMapper.convertModelToDTO(authorModel);
    }

    @SneakyThrows
    @ValidateDto
    @Override
    public AuthorDTORespond update(AuthorDTORequest updateRequest) {
        try {
        if (authorRepository.existById(updateRequest.getId())) {
            AuthorModel authorModel = authorMapper.convertDTOtoModel(updateRequest);
            authorRepository.update(authorModel);
            return authorMapper.convertModelToDTO(authorModel);
        } else
            throw new NotFoundException(ExceptionService.ERROR_NOT_EXIST.getErrorInfo(
                    ExceptionService.Constants.AUTHOR,
                    updateRequest.getId()));
        } catch (NotFoundException e) {
            System.out.println(e.getErrorMessage());
        }
        return null;
    }

    @SneakyThrows
    @ValidateAuthorId
    @Override
    public boolean deleteById(Long id) {
        try {
        if (authorRepository.existById(id))
            return authorRepository.deleteById(id);
        else throw new NotFoundException(ExceptionService.ERROR_NOT_EXIST.getErrorInfo(
                ExceptionService.Constants.AUTHOR,
                id));
        } catch (NotFoundException e) {
            System.out.println(e.getErrorMessage());
        }
        return false;
    }
}