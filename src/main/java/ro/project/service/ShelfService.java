package ro.project.service;

import ro.project.model.abstracts.Shelf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShelfService {
    Optional<Shelf> getById(UUID id);

    void addShelf(Shelf shelf);

    void addShelves(List<Shelf> shelfList);

    void editShelfById(UUID id, Shelf newShelf);

    void removeShelfById(UUID id);

    void printShelfData(UUID id);
    List<UUID> getShelfBooks(UUID id);

    void addBookToShelf(UUID shelfId, UUID bookId);
}
