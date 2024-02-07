package com.mjc.school.service.implementation;

import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.annotation.ValidateDto;
import com.mjc.school.service.annotation.ValidateNewsId;
import com.mjc.school.service.dto.news.NewsDTORequest;
import com.mjc.school.service.dto.news.NewsDTORespond;
import com.mjc.school.service.exceptions.ExceptionService;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.NewsMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsService implements BaseService<NewsDTORequest, NewsDTORespond, Long> {

    NewsRepository newsRepository = new NewsRepository();
    NewsMapper newsMapper = new NewsMapper();

    @Override
    public List<NewsDTORespond> readAll() {
        return newsMapper.convertMoledListToDTOList(newsRepository.readAll());
    }

    @SneakyThrows
    @ValidateNewsId
    @Override
    public NewsDTORespond readById(Long id) {
        try {
            return newsRepository
                    .readById(id)
                    .map(newsMapper::convertModelToDTO)
                    .orElseThrow(() -> new NotFoundException(
                            ExceptionService.ERROR_NOT_EXIST.getErrorInfo(
                                    ExceptionService.Constants.NEWS,
                                    id)
                    ));
        } catch (NotFoundException e) {
            System.out.println(e.getErrorMessage());
        }
        return null;
    }

    @ValidateDto
    @Override
    public NewsDTORespond create(NewsDTORequest createRequest) {
        NewsModel newsModel = newsMapper.convertDTOtoModel(createRequest);
        newsModel.setCreateDate(LocalDateTime.now());
        newsModel.setLastUpdateDate(LocalDateTime.now());
        newsRepository.create(newsModel);
        return newsMapper.convertModelToDTO(newsModel);
    }

    @SneakyThrows
    @ValidateDto
    @Override
    public NewsDTORespond update(NewsDTORequest updateRequest) {
        try {
            if (newsRepository.existById(updateRequest.getNewsId())) {
                NewsModel newsModel = newsMapper.convertDTOtoModel(updateRequest);
                newsModel.setLastUpdateDate(LocalDateTime.now());
                newsRepository.update(newsModel);
                return newsMapper.convertModelToDTO(newsModel);
            } else throw new NotFoundException(ExceptionService.ERROR_NOT_EXIST.getErrorInfo(
                    ExceptionService.Constants.NEWS,
                    updateRequest.getNewsId()));
        } catch (NotFoundException e) {
            System.out.println(e.getErrorMessage());
        }
        return null;
    }

    @SneakyThrows
    @ValidateNewsId
    @Override
    public boolean deleteById(Long id) {
        try {
            if (newsRepository.existById(id))
                return newsRepository.deleteById(id);
            else throw new NotFoundException(ExceptionService.ERROR_NOT_EXIST.getErrorInfo(
                    ExceptionService.Constants.NEWS,
                    id));
        } catch (NotFoundException e) {
            System.out.println(e.getErrorMessage());
        }
        return false;
    }
}