package ro.project.service;

import ro.project.model.abstracts.Shelf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {
    Optional<Shelf> getById(UUID id);

    void addBook(Shelf shelf);

    void addBook(List<Shelf> shelfList);

    void editBookById(UUID id, Shelf newShelf);

    void removeBookById(UUID id);
}
