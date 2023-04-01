package ro.project.service;

import ro.project.model.Author;

import java.util.UUID;

public interface AuthorService {
    void printAuthorData(Author author);
    void addToBookList(Author author, UUID bookId);
}
