package com.mjc.school.configuration;

import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.menu.MenuHelper;
import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.service.implementation.AuthorService;
import com.mjc.school.service.implementation.NewsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.mjc.school"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ProgramConfiguration {

    @Bean
    public DataSource dataSource() {
        return new DataSource();
    }

    @Bean
    public NewsController newsController() {
        return new NewsController(new NewsService(new NewsRepository(dataSource())));
    }

    @Bean
    public AuthorController authorController() {
        return new AuthorController(new AuthorService(new AuthorRepository(dataSource())));
    }

    @Bean
    public MenuHelper menuHelper() {
        return new MenuHelper(newsController(), authorController());
    }
}