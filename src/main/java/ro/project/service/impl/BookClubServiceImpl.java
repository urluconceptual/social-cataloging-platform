package ro.project.service.impl;

import ro.project.model.Book;
import ro.project.model.BookClub;
import ro.project.model.records.Message;
import ro.project.service.BookClubService;

import java.util.List;

public class BookClubServiceImpl implements BookClubService {


    @Override
    public void printMessages(BookClub bookClub) {
        bookClub.getMessageMap().forEach((time, message) -> {
            System.out.println("time: " + time);
            System.out.println("message:" + message);
            System.out.println();
        });
    }

    @Override
    public List<Book> getListOfAllBooksClubs() {
        return null;
    }

    @Override
    public void addMessage(BookClub bookClub, Message message) {
        bookClub.getMessageMap().put(message.sentTime(), message);
    }
}
