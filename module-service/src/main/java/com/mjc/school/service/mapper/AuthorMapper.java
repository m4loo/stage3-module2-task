package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.author.AuthorDTORequest;
import com.mjc.school.service.dto.author.AuthorDTORespond;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class AuthorMapper implements BaseMapper<AuthorDTORespond, AuthorModel, AuthorDTORequest> {

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public AuthorDTORespond convertModelToDTO(AuthorModel authorModel) {
        return modelMapper.map(authorModel, AuthorDTORespond.class);
    }

    @Override
    public AuthorModel convertDTOtoModel(AuthorDTORequest authorDTORequest) {
        return modelMapper.map(authorDTORequest, AuthorModel.class);
    }

    @Override
    public List<AuthorDTORespond> convertMoledListToDTOList(List<AuthorModel> authorModelList) {
        List<AuthorDTORespond> authorDTORespondList = new ArrayList<>();
        for (AuthorModel authorModel : authorModelList)
            authorDTORespondList.add(convertModelToDTO(authorModel));
        return authorDTORespondList;
    }
}
