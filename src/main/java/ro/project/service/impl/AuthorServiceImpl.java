package ro.project.service.impl;

import ro.project.model.Author;
import ro.project.model.PersonalShelf;
import ro.project.model.Reader;
import ro.project.service.AuthorService;
import ro.project.service.ShelfService;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class AuthorServiceImpl implements AuthorService {

    public Author init(Author author) {
        shelfService.addShelf(
                PersonalShelf.builder()
                             .owner(author.getId())
                             .name("written-books")
                             .bookList(new HashSet<>())
                             .build());
        return author;
    }
    private static ShelfService shelfService = new ShelfServiceImpl();
    @Override
    public void printAuthorData(Author author) {

    }

    @Override
    public void printBooks(Author author) {
        shelfService.printShelfData(author.getBookIdList());
    }

    @Override
    public void addToBookList(Author author, UUID bookId) {
        ((PersonalShelf)shelfService.getById(author.getBookIdList()).get()).getBookList().add(bookId);
    }
}
