package ro.project.service;

import ro.project.model.Reader;
import ro.project.model.abstracts.User;

import java.util.Set;
import java.util.UUID;

public interface ReaderService {
    Reader init(Reader reader);

    Set<User> getFriends();

    void removeShelf(UUID shelfId);

    void printShelves(Reader reader);
}