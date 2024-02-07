package com.mjc.school.menu.printers;

import com.mjc.school.controller.Commands.NewsCommandHandler;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.interfaces.BaseMenuPrinter;
import com.mjc.school.menu.Buttons;

import java.util.Scanner;

import static java.lang.System.out;

public class NewsMenuPrinter implements BaseMenuPrinter<NewsController> {

    NewsCommandHandler newsCommandHandler = new NewsCommandHandler();

    @Override
    public void printReadAllMenu(NewsController controller) {
        out.println(Buttons.ConstantsString.OPERATIONS + Buttons.READ_ALL_NEWS.getButtonMessage());
        out.println(newsCommandHandler.handleCommand(controller, Buttons.READ_ALL_NEWS.name()));
    }

    @Override
    public void printReadByIdlMenu(NewsController controller, Scanner input) {
        out.println(Buttons.ConstantsString.OPERATIONS + Buttons.READ_BY_ID_NEWS.getButtonMessage());
        out.println(Buttons.ConstantsString.ENTER_NEWS_ID);
        Long id = input.nextLong();
        newsCommandHandler.createRequest(id, null, null, null);
        out.println(newsCommandHandler.handleCommand(controller, Buttons.READ_BY_ID_NEWS.name()));
    }

    @Override
    public void printCreateMenu(NewsController controller, Scanner input) {
        out.println(Buttons.ConstantsString.OPERATIONS + Buttons.CREATE_NEWS.getButtonMessage());
        out.println(Buttons.ConstantsString.ENTER_NEWS_TITLE);
        String title = input.nextLine();
        out.println(Buttons.ConstantsString.ENTER_NEWS_CONTENT);
        String content = input.nextLine();
        out.println(Buttons.ConstantsString.ENTER_AUTHOR_ID);
        Long authorId = input.nextLong();
        newsCommandHandler.createRequest(null, title, content, authorId);
        out.println(newsCommandHandler.handleCommand(controller, Buttons.CREATE_NEWS.name()));
    }

    @Override
    public void printUpdateMenu(NewsController controller, Scanner input) {
        out.println(Buttons.ConstantsString.OPERATIONS + Buttons.UPDATE_NEWS.getButtonMessage());
        out.println(Buttons.ConstantsString.ENTER_NEWS_ID);
        Long newsId = input.nextLong();
        out.println(Buttons.ConstantsString.ENTER_NEWS_TITLE);
        String title = input.nextLine();
        out.println(Buttons.ConstantsString.ENTER_NEWS_CONTENT);
        String content = input.nextLine();
        out.println(Buttons.ConstantsString.ENTER_AUTHOR_ID);
        Long authorId = input.nextLong();
        newsCommandHandler.createRequest(newsId, title, content, authorId);
        out.println(newsCommandHandler.handleCommand(controller, Buttons.UPDATE_NEWS.name()));
    }

    @Override
    public void printDeleteByIdMenu(NewsController controller, Scanner input) {
        out.println(Buttons.ConstantsString.OPERATIONS + Buttons.DELETE_BY_ID_NEWS.getButtonMessage());
        out.println(Buttons.ConstantsString.ENTER_NEWS_ID);
        newsCommandHandler.createRequest(input.nextLong(), null, null, null);
        out.println(newsCommandHandler.handleCommand(controller, Buttons.DELETE_BY_ID_NEWS.name()));
    }

    @Override
    public void getDefault(NewsController controller) {
        newsCommandHandler.handleCommand(controller, "DEFAULT");
    }
}
