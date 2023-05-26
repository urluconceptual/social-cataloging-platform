package ro.project.service.impl;

import ro.project.model.Librarian;
import ro.project.model.PersonalShelf;
import ro.project.model.enums.ShelfType;
import ro.project.service.BookClubService;
import ro.project.service.LibrarianService;
import ro.project.service.ShelfService;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class LibrarianServiceImpl implements LibrarianService {
    private static ShelfService shelfService = new ShelfServiceImpl();
    private static BookClubService bookClubService = new BookClubServiceImpl();

    @Override
    public Librarian init(Librarian librarian) {
        shelfService.addShelf(
                PersonalShelf.builder()
                        .type(ShelfType.PERSONAL)
                             .owner(librarian.getId())
                             .name("written-books")
                             .bookList(new HashSet<>())
                             .build());
        return librarian;
    }

    @Override
    public void printBooks(Librarian librarian) {
        shelfService.printShelfData(librarian.getRecommendationsListId());
    }

    @Override
    public List<UUID> getRecommendedBooks(Librarian librarian) {
        return shelfService.getShelfBooks(librarian.getRecommendationsListId());
    }

    @Override
    public void printLibrarianData(Librarian librarian) {
        System.out.println("---- recommended books:");
        printBooks(librarian);
        System.out.println("---- book club posts:");
        bookClubService.printMessages(librarian.getBookClub());
    }
}
