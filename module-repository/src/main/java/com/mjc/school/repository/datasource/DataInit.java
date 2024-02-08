package com.mjc.school.repository.datasource;

import com.mjc.school.repository.constants.Constants;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class DataInit {

    private final List<Integer> listIndexAuthor = new ArrayList<>();
    private final List<Integer> listIndexNews = new ArrayList<>();

    private static final Randomizer randomizer = new Randomizer();

    public DataInit getNew() {
        return new DataInit();
    }

    @SneakyThrows
    List<AuthorModel> initAuthors() {
        List<AuthorModel> authorModelList = new ArrayList<>();
        Path path = Path.of(Constants.AUTHOR_FILE);
        List<String> lines = Files.readAllLines(path);
        for (int i = 0; i < lines.size(); i++) {
            listIndexAuthor.add(i);
            Long authorId = (long) (i + 1);
            String authorName = lines.get(i);
            LocalDateTime createDate = randomizer.getRandomCreatedDate();
            LocalDateTime lastUpdateDate = randomizer.getRandomLastUpdatedDate(createDate);
            authorModelList.add(
                    new AuthorModel(
                            authorId,
                            authorName,
                            createDate,
                            lastUpdateDate));
        }
        return authorModelList;
    }

    List<NewsModel> initNews(List<AuthorModel> authorModelList) {
        List<NewsModel> newsModelList = new ArrayList<>();
        for (int i = 0; i < Constants.TOTAL_NUMBER_OF_NEWS; i++) {
            listIndexNews.add(i);
            Long newsId = (long) (i + 1);
            String title = randomizer.getRandomTitle();
            String content = randomizer.getRandomContent();
            LocalDateTime createDate = randomizer.getRandomCreatedDate();
            LocalDateTime lastUpdateDate = randomizer.getRandomLastUpdatedDate(createDate);
            Long authorId = randomizer.getRandomAuthorId(authorModelList);
            newsModelList.add(
                    new NewsModel(
                            newsId,
                            title,
                            content,
                            createDate,
                            lastUpdateDate,
                            authorId));
        }
        return newsModelList;
    }
}
