package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.news.NewsDTORequest;
import com.mjc.school.service.dto.news.NewsDTORespond;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class NewsMapper implements BaseMapper<NewsDTORespond, NewsModel, NewsDTORequest>{

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public NewsDTORespond convertModelToDTO(NewsModel newsModel) {
        return modelMapper.map(newsModel, NewsDTORespond.class);
    }

    @Override
    public NewsModel convertDTOtoModel(NewsDTORequest newsDTORequests) {
        return modelMapper.map(newsDTORequests, NewsModel.class);
    }

    @Override
    public List<NewsDTORespond> convertMoledListToDTOList(List<NewsModel> newsModelList) {
        List<NewsDTORespond> newsDTORespondList = new ArrayList<>();
        for (NewsModel newsModel : newsModelList){
            NewsDTORespond newsDTORespond = convertModelToDTO(newsModel);
            newsDTORespondList.add(newsDTORespond);
        }
        return newsDTORespondList;
    }
}
