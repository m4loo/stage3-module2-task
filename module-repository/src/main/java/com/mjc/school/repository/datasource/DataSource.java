package com.mjc.school.repository.datasource;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class DataSource {
    private List<AuthorModel> authorModelList;
    private List<NewsModel> newsModelList;
    private List<Integer> listIndexAuthor;
    private List<Integer> listIndexNews;
    DataInit dataInit = new DataInit();

    public DataSource (){
        init();
    }

    private void init() {
        this.authorModelList = dataInit.initAuthors();
        this.newsModelList = dataInit.initNews(authorModelList);
        this.listIndexAuthor = dataInit.getListIndexAuthor();
        this.listIndexNews = dataInit.getListIndexNews();
    }
}
