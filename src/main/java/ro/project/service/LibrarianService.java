package ro.project.service;

import ro.project.model.Librarian;

import java.util.List;
import java.util.UUID;

public interface LibrarianService {
    Librarian init(Librarian librarian);

    void printBooks(Librarian librarian);

    List<UUID> getRecommendedBooks(Librarian librarian);

    void printLibrarianData(Librarian librarian);
}
