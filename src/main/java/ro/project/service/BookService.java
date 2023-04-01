package ro.project.service;

import ro.project.model.Book;
import ro.project.model.abstracts.Shelf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {
    Optional<Book> getById(UUID id);

    void addBook(Book book);

    void addBooks(List<Book> bookList);

    void editBookById(UUID id, Book newBook);

    void removeBookById(UUID id);
}
