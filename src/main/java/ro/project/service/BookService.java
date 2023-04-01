package ro.project.service;

import ro.project.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {
    void init();

    Optional<Book> getById(UUID id);

    void addBook(Book book);

    void addBooks(List<Book> bookList);

    void editBookById(UUID id, Book newBook);

    void removeBookById(UUID id);

    void printBookData(UUID id);

    List<Book> getListOfAllBooks();
}
