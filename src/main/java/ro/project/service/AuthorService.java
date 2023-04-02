package ro.project.service;

import ro.project.model.Author;

import java.util.List;
import java.util.UUID;

public interface AuthorService {
    Author init(Author author);

    void printAuthorData(Author author);

    void printBooks(Author author);

    void addToBookList(Author author, UUID bookId);

    List<UUID> getWrittenBooks(Author author);

    void updateRating(UUID authorId);
}
