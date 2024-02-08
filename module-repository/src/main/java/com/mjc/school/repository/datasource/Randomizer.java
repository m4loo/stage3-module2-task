package com.mjc.school.repository.datasource;

import com.mjc.school.repository.constants.Constants;
import com.mjc.school.repository.model.AuthorModel;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Random;

public class Randomizer {
    public static final Random random = new Random();

    public Long getRandomAuthorId(List<AuthorModel> authorModelList) {
        return (long) random.nextInt(authorModelList.size());
    }

    public String getRandomTitle() {
        return getRandomLineFromFile(Constants.TITLE_FILE);
    }

    public String getRandomContent() {
        return getRandomLineFromFile(Constants.CONTENT_FILE);
    }

    @SneakyThrows
    public String getRandomLineFromFile(String fileName){
        Path path = Path.of(fileName);
        List<String> list = Files.readAllLines(path);
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }


    public LocalDateTime getRandomCreatedDate() {
        return getRandomDateTime();
    }

    public LocalDateTime getRandomLastUpdatedDate(LocalDateTime createdDate) {
        LocalDateTime lastUpdatedDate = getRandomDateTime();
        while (lastUpdatedDate.isBefore(createdDate)) lastUpdatedDate = getRandomDateTime();
        return lastUpdatedDate;
    }

    public LocalDateTime getRandomDateTime() {
        int maxDaysInMonth = 31;
        int year = LocalDateTime.now().getYear() - random.nextInt(24);
        int month = 1 + random.nextInt(12);

        if (month == 2) maxDaysInMonth = Year.isLeap(year) ? 29 : 28;
        else if (month == 4 || month == 6 || month == 9 || month == 11) maxDaysInMonth = 30;

        int day = 1 + random.nextInt(maxDaysInMonth);
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }
}
