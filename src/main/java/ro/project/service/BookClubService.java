package ro.project.service;

import ro.project.model.Book;
import ro.project.model.BookClub;
import ro.project.model.records.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookClubService {

    void printMessages(BookClub bookClub);

    List<Book> getListOfAllBooksClubs();

    void addMessage(BookClub bookClub, Message message);
}
