package ro.project.service.impl;

import ro.project.model.BookClub;
import ro.project.model.records.Message;
import ro.project.repository.BookClubRepository;
import ro.project.repository.MessageRepository;
import ro.project.repository.impl.BookClubRepositoryImpl;
import ro.project.repository.impl.MessageRepositoryImpl;
import ro.project.service.BookClubService;

import java.util.UUID;

public class BookClubServiceImpl implements BookClubService {
    private static MessageRepository messageRepository = new MessageRepositoryImpl();
    private static BookClubRepository bookClubRepository = new BookClubRepositoryImpl();

    @Override
    public void printMessages(UUID bookClubId) {
        BookClub bookClub = bookClubRepository.getById(bookClubId).get();
        bookClub.getMessageMap().forEach((time, message) -> {
            System.out.println("time: " + time);
            System.out.println("message:" + message);
            System.out.println();
        });
    }

    @Override
    public void addMessage(BookClub bookClub, Message message) {
        messageRepository.add(message);
    }
}
