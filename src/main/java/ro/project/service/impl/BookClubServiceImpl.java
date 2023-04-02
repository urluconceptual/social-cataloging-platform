package ro.project.service.impl;

import ro.project.model.Book;
import ro.project.model.BookClub;
import ro.project.model.records.Message;
import ro.project.service.BookClubService;

import java.util.*;

public class BookClubServiceImpl implements BookClubService {
    Map<UUID, BookClub> bookClubMap = new HashMap<>();

    @Override
    public Optional<BookClub> getById(UUID id) {
        if (bookClubMap.containsKey(id)) {
            return Optional.of(bookClubMap.get(id));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void addBookClub(BookClub bookClub) {
        bookClubMap.put(bookClub.getId(), bookClub);

    }

    @Override
    public void addBookClubs(List<BookClub> bookClubList) {
        bookClubList.forEach(this::addBookClub);
    }

    @Override
    public void removeBookClubById(UUID id) {
        bookClubMap.remove(id);
    }

    @Override
    public void printMessages(UUID id) {

    }

    @Override
    public List<Book> getListOfAllBooksClubs() {
        return null;
    }

    @Override
    public void addMessage(UUID bookClubId, Message message) {

    }
}
