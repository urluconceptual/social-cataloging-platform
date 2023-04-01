package ro.project.service.impl;

import ro.project.model.Book;
import ro.project.model.PersonalShelf;
import ro.project.model.Reader;
import ro.project.model.SharedShelf;
import ro.project.model.abstracts.Shelf;
import ro.project.model.abstracts.User;
import ro.project.service.BookService;

import java.util.*;

public class BookServiceImpl implements BookService {
    private static Map<UUID, Book> bookMap = new HashMap<>();

    @Override
    public Optional<Book> getById(UUID id) {
        if (bookMap.containsKey(id))
            return Optional.of(bookMap.get(id));
        else
            return Optional.empty();
    }

    @Override
    public void addBook(Book book) {
        bookMap.put(book.getId(), book);
    }

    @Override
    public void addBooks(List<Book> bookList) {
        bookList.forEach(this::addBook);
    }

    @Override
    public void editBookById(UUID id, Book newBook) {
        bookMap.remove(id);
        bookMap.put(id, newBook);
    }

    @Override
    public void removeBookById(UUID id) {
        bookMap.remove(id);
    }

}
