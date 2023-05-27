package ro.project.service.impl;

import ro.project.model.Author;
import ro.project.model.PersonalShelf;
import ro.project.model.enums.ShelfType;
import ro.project.service.AuthorService;
import ro.project.service.ShelfService;
import ro.project.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class AuthorServiceImpl implements AuthorService {

    private static ShelfService shelfService = new ShelfServiceImpl();
    private static UserService userService = new UserServiceImpl();

    public Author init(Author author) {
        shelfService.addShelf(
                PersonalShelf.builder()
                             .type(ShelfType.PERSONAL)
                             .owner(author.getId())
                             .name("written-books")
                             .bookList(new HashSet<>())
                             .build());
        return author;
    }

    @Override
    public void printAuthorData(Author author) {
        System.out.println("written books:");
        printBooks(author);
        System.out.println("average rating:");
        System.out.println(author.getAverageRating() + "/10\n");
    }

    @Override
    public void printBooks(Author author) {
        shelfService.printShelfData(author.getBookIdList());
    }

    @Override
    public void addToBookList(Author author, UUID bookId) {
        ((PersonalShelf) shelfService.getById(author.getBookIdList()).get()).getBookList().add(bookId);
    }

    @Override
    public List<UUID> getWrittenBooks(Author author) {
        return shelfService.getShelfBooks(author.getBookIdList());
    }

    @Override
    public void updateRating(UUID authorId) {
        Author author = (Author) userService.getById(authorId).get();
        Double average = shelfService.getShelfAverage(author.getBookIdList());
        author.setAverageRating(average);
    }
}
